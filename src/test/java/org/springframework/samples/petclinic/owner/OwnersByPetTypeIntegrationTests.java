package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OwnersByPetTypeIntegrationTests {

	@LocalServerPort
	int port;

	private RestTemplate restTemplate;

	@BeforeEach
	void setUp() {
		restTemplate = new RestTemplate();
	}

	@Test
	void shouldGetOwnersByPetTypeFromDatabase() {
		String url = "http://localhost:" + port + "/api/owners/by-pet-type?petTypeId=1&size=5";
		ResponseEntity<OwnersByPetTypeResponse> response = restTemplate.getForEntity(url,
				OwnersByPetTypeResponse.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().owners()).isNotNull();
	}

	@Test
	void shouldPaginateWithCursor() {
		String url = "http://localhost:" + port + "/api/owners/by-pet-type?petTypeId=1&size=2";
		ResponseEntity<OwnersByPetTypeResponse> firstResponse = restTemplate.getForEntity(url,
				OwnersByPetTypeResponse.class);

		assertThat(firstResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(firstResponse.getBody()).isNotNull();

		if (firstResponse.getBody().hasNext()) {
			Integer cursor = firstResponse.getBody().nextCursor();
			String secondUrl = "http://localhost:" + port + "/api/owners/by-pet-type?petTypeId=1&cursor=" + cursor
					+ "&size=2";
			ResponseEntity<OwnersByPetTypeResponse> secondResponse = restTemplate.getForEntity(secondUrl,
					OwnersByPetTypeResponse.class);

			assertThat(secondResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
			assertThat(secondResponse.getBody()).isNotNull();
			assertThat(secondResponse.getBody().owners()).isNotEmpty();
		}
	}

}
