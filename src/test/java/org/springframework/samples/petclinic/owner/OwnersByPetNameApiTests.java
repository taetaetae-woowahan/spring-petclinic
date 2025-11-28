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
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OwnerController.class)
@DisabledInAotMode
class OwnersByPetNameApiTests {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private OwnerRepository owners;

	@Test
	void shouldReturnOwnersWithPetName() throws Exception {
		Owner owner = new Owner();
		owner.setId(1);
		owner.setFirstName("George");
		owner.setLastName("Franklin");
		owner.setCity("Madison");
		owner.setTelephone("6085551023");

		given(this.owners.findOwnersByPetName(eq("Leo"), any(), eq(6))).willReturn(List.of(owner));

		mockMvc.perform(get("/api/owners/by-pet-name").param("petName", "Leo").param("size", "5"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.owners").isArray())
			.andExpect(jsonPath("$.owners[0].id").value(1))
			.andExpect(jsonPath("$.hasNext").value(false));
	}

	@Test
	void shouldReturnEmptyListForNonExistentPetName() throws Exception {
		given(this.owners.findOwnersByPetName(eq("NonExistentPet"), any(), eq(6))).willReturn(new ArrayList<>());

		mockMvc.perform(get("/api/owners/by-pet-name").param("petName", "NonExistentPet").param("size", "5"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.owners").isEmpty())
			.andExpect(jsonPath("$.hasNext").value(false));
	}

	@Test
	void shouldHandleCursorPagination() throws Exception {
		Owner owner1 = new Owner();
		owner1.setId(2);
		owner1.setFirstName("Betty");
		owner1.setLastName("Davis");
		owner1.setCity("Madison");
		owner1.setTelephone("6085551749");

		Owner owner2 = new Owner();
		owner2.setId(3);
		owner2.setFirstName("Eduardo");
		owner2.setLastName("Rodriquez");
		owner2.setCity("Madison");
		owner2.setTelephone("6085558763");

		given(this.owners.findOwnersByPetName(eq("Leo"), eq(1), eq(2))).willReturn(List.of(owner1, owner2));

		mockMvc.perform(get("/api/owners/by-pet-name").param("petName", "Leo").param("size", "1").param("cursor", "1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.owners").isArray())
			.andExpect(jsonPath("$.owners[0].id").value(2))
			.andExpect(jsonPath("$.hasNext").value(true))
			.andExpect(jsonPath("$.nextCursor").value(2));
	}

}
