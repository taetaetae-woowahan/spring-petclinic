package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CursorPageRequestTest {

	@Test
	void shouldCreateCursorPageRequestWithDefaults() {
		CursorPageRequest request = new CursorPageRequest();
		
		assertThat(request.getCursor()).isNull();
		assertThat(request.getSize()).isEqualTo(10);
	}

	@Test
	void shouldCreateCursorPageRequestWithCustomValues() {
		CursorPageRequest request = new CursorPageRequest(5, 20);
		
		assertThat(request.getCursor()).isEqualTo(5);
		assertThat(request.getSize()).isEqualTo(20);
	}
}
