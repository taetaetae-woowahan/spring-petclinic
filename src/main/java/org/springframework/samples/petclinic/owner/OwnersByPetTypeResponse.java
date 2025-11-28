package org.springframework.samples.petclinic.owner;

import java.util.List;

public record OwnersByPetTypeResponse(List<OwnerSummary> owners, Integer nextCursor, boolean hasNext) {
}
