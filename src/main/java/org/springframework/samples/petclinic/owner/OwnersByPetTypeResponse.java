package org.springframework.samples.petclinic.owner;

import java.util.List;

public record OwnersByPetTypeResponse(List<Owner> owners, Integer nextCursor, boolean hasNext) {
}
