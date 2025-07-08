package org.example.assignment01.repository;

import org.example.assignment01.entity.Donation;
import org.example.assignment01.useenum.DonationStatus;
import org.springframework.data.jpa.domain.Specification;

public class DonationSpecification {
    public static Specification<Donation> hasStatus(DonationStatus status) {
        return (root, query, cb) -> status == null ? cb.conjunction() : cb.equal(root.get("status"), status);
    }

    public static Specification<Donation> hasNameOrCode(String searchTerm) {
        return (root, query, cb) -> {
            if (searchTerm == null || searchTerm.isBlank()) {
                return cb.conjunction();
            }
            String likePattern = "%" + searchTerm.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("name")), likePattern),
                    cb.like(cb.lower(root.get("code")), likePattern)
            );
        };
    }
}
