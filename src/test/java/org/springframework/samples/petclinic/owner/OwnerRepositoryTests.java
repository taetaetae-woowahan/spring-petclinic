/*
 * Copyright 2012-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OwnerRepositoryTests {

	@Autowired
	private OwnerRepository ownerRepository;

	@Test
	void shouldFindOwnersByPetTypeId() {
		// Given: petTypeId 1 (cat)
		Integer petTypeId = 1;

		// When
		List<Owner> owners = ownerRepository.findByPetsTypeId(petTypeId);

		// Then
		assertThat(owners).isNotEmpty();
		assertThat(owners).allMatch(owner -> owner.getPets()
			.stream()
			.anyMatch(pet -> pet.getType().getId().equals(petTypeId)));
	}

	@Test
	void shouldReturnEmptyListWhenNoPetsWithGivenType() {
		// Given: non-existent petTypeId
		Integer petTypeId = 999;

		// When
		List<Owner> owners = ownerRepository.findByPetsTypeId(petTypeId);

		// Then
		assertThat(owners).isEmpty();
	}

	@Test
	void shouldReturnMultipleOwnersWithSamePetType() {
		// Given: petTypeId 2 (dog)
		Integer petTypeId = 2;

		// When
		List<Owner> owners = ownerRepository.findByPetsTypeId(petTypeId);

		// Then
		assertThat(owners).hasSizeGreaterThan(1);
	}

}
