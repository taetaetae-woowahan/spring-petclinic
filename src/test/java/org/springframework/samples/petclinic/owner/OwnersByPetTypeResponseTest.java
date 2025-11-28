package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OwnersByPetTypeResponseTest {

	@Test
	void shouldCreateResponseWithOwners() {
		OwnerSummary owner = new OwnerSummary(1, "George", "Franklin", "110 W. Liberty St.", "Madison", "6085551023");
		OwnersByPetTypeResponse response = new OwnersByPetTypeResponse(List.of(owner), 2, true);

		assertThat(response.owners()).hasSize(1);
		assertThat(response.nextCursor()).isEqualTo(2);
		assertThat(response.hasNext()).isTrue();
	}

	@Test
	void shouldCreateOwnerSummary() {
		OwnerSummary owner = new OwnerSummary(1, "George", "Franklin", "110 W. Liberty St.", "Madison", "6085551023");

		assertThat(owner.id()).isEqualTo(1);
		assertThat(owner.firstName()).isEqualTo("George");
		assertThat(owner.lastName()).isEqualTo("Franklin");
	}

}
