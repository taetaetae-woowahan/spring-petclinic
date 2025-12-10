package org.springframework.samples.petclinic.owner;

import java.util.List;

public class OwnersByPetTypeResponse {

	private final List<Owner> owners;
	private final Integer nextCursor;
	private final boolean hasNext;

	public OwnersByPetTypeResponse(List<Owner> owners, Integer nextCursor, boolean hasNext) {
		this.owners = owners;
		this.nextCursor = nextCursor;
		this.hasNext = hasNext;
	}

	public List<Owner> getOwners() {
		return owners;
	}

	public Integer getNextCursor() {
		return nextCursor;
	}

	public boolean isHasNext() {
		return hasNext;
	}
}
