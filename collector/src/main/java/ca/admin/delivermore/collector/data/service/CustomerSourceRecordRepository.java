package ca.admin.delivermore.collector.data.service;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.admin.delivermore.collector.data.entity.CustomerSourceRecord;

public interface CustomerSourceRecordRepository extends JpaRepository<CustomerSourceRecord, Long> {

    Optional<CustomerSourceRecord> findByRestaurantIdAndSourceClientId(Long restaurantId, String sourceClientId);
}
