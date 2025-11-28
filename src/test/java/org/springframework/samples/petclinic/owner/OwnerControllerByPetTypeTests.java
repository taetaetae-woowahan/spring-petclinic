package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OwnerControllerByPetTypeTests {

	@LocalServerPort
	int port;

	@Autowired
	private RestTemplateBuilder builder;

	@Test
	void shouldGetOwnersByPetType() {
		RestTemplate template = builder.rootUri("http://localhost:" + port).build();
		ResponseEntity<OwnersByPetTypeResponse> response = template.exchange("/owners/by-pet-type?petTypeId=1",
				HttpMethod.GET, null, new ParameterizedTypeReference<>() {
				});

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().owners()).isNotEmpty();
	}

	@Test
	void shouldGetOwnersByPetTypeWithCursor() {
		RestTemplate template = builder.rootUri("http://localhost:" + port).build();
		ResponseEntity<OwnersByPetTypeResponse> response = template.exchange("/owners/by-pet-type?petTypeId=1&cursor=5",
				HttpMethod.GET, null, new ParameterizedTypeReference<>() {
				});

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().owners()).allMatch(owner -> owner.getId() > 5);
	}

	@Test
	void shouldLimitResultsWithSize() {
		RestTemplate template = builder.rootUri("http://localhost:" + port).build();
		ResponseEntity<OwnersByPetTypeResponse> response = template.exchange("/owners/by-pet-type?petTypeId=1&size=2",
				HttpMethod.GET, null, new ParameterizedTypeReference<>() {
				});

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().owners()).hasSizeLessThanOrEqualTo(2);
	}

}
