    package org.example.assignment01.repository;

    import org.example.assignment01.entity.UserDonation;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.stereotype.Repository;

    @Repository
    public interface UserDonationRepository extends JpaRepository<UserDonation, Integer> {
        Page<UserDonation> findByUser_Id(Integer userId, Pageable pageable);

        Page<UserDonation> findByDonation_Id(Integer donationId, Pageable pageable);
    }
