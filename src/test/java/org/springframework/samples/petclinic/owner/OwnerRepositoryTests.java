package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class OwnerRepositoryTests {

	@Autowired
	private OwnerRepository ownerRepository;

	@Test
	void shouldFindOwnersByPetTypeWithCursor() {
		List<OwnerSummary> owners = ownerRepository.findOwnersByPetTypeWithCursor(1, null, 5);

		assertThat(owners).isNotEmpty();
		assertThat(owners).allMatch(owner -> owner.id() != null);
	}

	@Test
	void shouldFindOwnersByPetTypeWithCursorPagination() {
		List<OwnerSummary> firstPage = ownerRepository.findOwnersByPetTypeWithCursor(1, null, 2);
		assertThat(firstPage).hasSize(2);

		Integer lastId = firstPage.get(firstPage.size() - 1).id();
		List<OwnerSummary> secondPage = ownerRepository.findOwnersByPetTypeWithCursor(1, lastId, 2);

		assertThat(secondPage).isNotEmpty();
		assertThat(secondPage).allMatch(owner -> owner.id() > lastId);
	}

	@Test
	void shouldReturnEmptyListForNonExistentPetType() {
		List<OwnerSummary> owners = ownerRepository.findOwnersByPetTypeWithCursor(999, null, 10);

		assertThat(owners).isEmpty();
	}

}
