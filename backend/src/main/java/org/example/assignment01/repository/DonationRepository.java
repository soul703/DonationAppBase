package org.example.assignment01.repository;

import org.example.assignment01.entity.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Integer>, JpaSpecificationExecutor<Donation> {
    boolean existsByCode(String code);
}