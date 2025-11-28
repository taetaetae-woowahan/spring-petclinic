package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OwnersByPetTypeResponseTest {

	@Test
	void shouldCreateResponseWithOwners() {
		Owner owner = new Owner();
		owner.setId(1);
		owner.setFirstName("John");
		owner.setLastName("Doe");

		OwnersByPetTypeResponse response = new OwnersByPetTypeResponse(List.of(owner), 1, true);

		assertThat(response.owners()).hasSize(1);
		assertThat(response.nextCursor()).isEqualTo(1);
		assertThat(response.hasNext()).isTrue();
	}

	@Test
	void shouldCreateResponseWithoutNextPage() {
		Owner owner = new Owner();
		owner.setId(1);

		OwnersByPetTypeResponse response = new OwnersByPetTypeResponse(List.of(owner), null, false);

		assertThat(response.hasNext()).isFalse();
		assertThat(response.nextCursor()).isNull();
	}

}
