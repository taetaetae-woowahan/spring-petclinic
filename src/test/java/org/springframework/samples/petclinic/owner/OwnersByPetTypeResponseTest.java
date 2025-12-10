package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OwnersByPetTypeResponseTest {

	@Test
	void shouldCreateResponseWithOwnersAndCursor() {
		Owner owner = new Owner();
		owner.setId(1);
		List<Owner> owners = List.of(owner);
		
		OwnersByPetTypeResponse response = new OwnersByPetTypeResponse(owners, 1, true);
		
		assertThat(response.getOwners()).hasSize(1);
		assertThat(response.getNextCursor()).isEqualTo(1);
		assertThat(response.isHasNext()).isTrue();
	}

	@Test
	void shouldCreateResponseWithoutNextPage() {
		List<Owner> owners = List.of();
		
		OwnersByPetTypeResponse response = new OwnersByPetTypeResponse(owners, null, false);
		
		assertThat(response.getOwners()).isEmpty();
		assertThat(response.getNextCursor()).isNull();
		assertThat(response.isHasNext()).isFalse();
	}
}
