package org.springframework.samples.petclinic.owner;

public record OwnerSummary(Integer id, String firstName, String lastName, String address, String city,
		String telephone) {
}
