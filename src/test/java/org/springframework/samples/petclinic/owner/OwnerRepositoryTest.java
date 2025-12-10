package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OwnerRepositoryTest {

	@Autowired
	private OwnerRepository ownerRepository;

	@Test
	void shouldFindOwnersByPetTypeWithCursor() {
		List<Owner> owners = ownerRepository.findOwnersByPetTypeWithCursor(1, null, PageRequest.of(0, 10));
		
		assertThat(owners).isNotNull();
	}

	@Test
	void shouldFindOwnersByPetTypeWithCursorAndLimit() {
		List<Owner> owners = ownerRepository.findOwnersByPetTypeWithCursor(1, 5, PageRequest.of(0, 5));
		
		assertThat(owners).isNotNull();
	}
}
