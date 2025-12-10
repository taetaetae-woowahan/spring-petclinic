package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OwnerController.class)
class OwnersByPetTypeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private OwnerRepository ownerRepository;

	@Test
	void shouldReturnOwnersByPetType() throws Exception {
		Owner owner = new Owner();
		owner.setId(1);
		owner.setFirstName("John");
		owner.setLastName("Doe");
		
		given(ownerRepository.findOwnersByPetTypeWithCursor(eq(1), eq(null), any(PageRequest.class)))
			.willReturn(List.of(owner));

		mockMvc.perform(get("/api/owners/by-pet-type/1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.owners").isArray())
			.andExpect(jsonPath("$.owners[0].id").value(1))
			.andExpect(jsonPath("$.hasNext").value(false));
	}

	@Test
	void shouldReturnEmptyListForNonExistentPetType() throws Exception {
		given(ownerRepository.findOwnersByPetTypeWithCursor(eq(999), eq(null), any(PageRequest.class)))
			.willReturn(List.of());

		mockMvc.perform(get("/api/owners/by-pet-type/999"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.owners").isEmpty())
			.andExpect(jsonPath("$.hasNext").value(false));
	}
}
