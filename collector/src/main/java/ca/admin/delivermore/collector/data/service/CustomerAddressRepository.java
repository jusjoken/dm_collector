package ca.admin.delivermore.collector.data.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.admin.delivermore.collector.data.entity.CustomerAddress;

public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, Long> {

    Optional<CustomerAddress> findFirstByCustomerProfileIdAndNormalizedAddress(Long customerProfileId, String normalizedAddress);

    List<CustomerAddress> findByCustomerProfileIdOrderByLastUsedAtDescUpdatedAtDescIdDesc(Long customerProfileId);
}
