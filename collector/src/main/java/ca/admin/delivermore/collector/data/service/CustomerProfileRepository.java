package ca.admin.delivermore.collector.data.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.admin.delivermore.collector.data.entity.CustomerProfile;

public interface CustomerProfileRepository extends JpaRepository<CustomerProfile, Long> {

    Optional<CustomerProfile> findFirstByNormalizedEmail(String normalizedEmail);

    Optional<CustomerProfile> findFirstByNormalizedPhone(String normalizedPhone);

    long countByFirstOrderAtBetween(LocalDateTime startInclusive, LocalDateTime endInclusive);

    List<CustomerProfile> findAllByOrderByTotalOrdersDescLastOrderAtDesc();
}
