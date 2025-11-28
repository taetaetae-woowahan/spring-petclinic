package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OwnerController.class)
class OwnersByPetTypeControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private OwnerRepository ownerRepository;

	@MockitoBean
	private PetTypeRepository petTypeRepository;

	@Test
	void shouldGetOwnersByPetType() throws Exception {
		OwnerSummary owner = new OwnerSummary(1, "George", "Franklin", "110 W. Liberty St.", "Madison", "6085551023");
		given(ownerRepository.findOwnersByPetTypeWithCursor(1, null, 21)).willReturn(List.of(owner));

		mockMvc.perform(get("/api/owners/by-pet-type").param("petTypeId", "1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.owners[0].id").value(1))
			.andExpect(jsonPath("$.owners[0].firstName").value("George"))
			.andExpect(jsonPath("$.hasNext").value(false));
	}

	@Test
	void shouldGetOwnersByPetTypeWithCursor() throws Exception {
		OwnerSummary owner = new OwnerSummary(3, "Eduardo", "Rodriquez", "2693 Commerce St.", "McFarland",
				"6085558763");
		given(ownerRepository.findOwnersByPetTypeWithCursor(1, 2, 21)).willReturn(List.of(owner));

		mockMvc.perform(get("/api/owners/by-pet-type").param("petTypeId", "1").param("cursor", "2"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.owners[0].id").value(3))
			.andExpect(jsonPath("$.nextCursor").doesNotExist());
	}

}
