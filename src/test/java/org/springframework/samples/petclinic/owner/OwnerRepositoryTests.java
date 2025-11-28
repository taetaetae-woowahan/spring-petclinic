package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class OwnerRepositoryTests {

	@Autowired
	private OwnerRepository ownerRepository;

	@Test
	void shouldFindOwnersByPetType() {
		List<Owner> owners = ownerRepository.findOwnersByPetType(1, null, PageRequest.of(0, 10));

		assertThat(owners).isNotEmpty();
		assertThat(owners).allMatch(owner -> owner.getPets()
			.stream()
			.anyMatch(pet -> pet.getType().getId().equals(1)));
	}

	@Test
	void shouldFindOwnersByPetTypeWithCursor() {
		List<Owner> owners = ownerRepository.findOwnersByPetType(1, 5, PageRequest.of(0, 10));

		assertThat(owners).allMatch(owner -> owner.getId() > 5);
	}

	@Test
	void shouldLimitResults() {
		List<Owner> owners = ownerRepository.findOwnersByPetType(1, null, PageRequest.of(0, 2));

		assertThat(owners).hasSizeLessThanOrEqualTo(2);
	}

}
