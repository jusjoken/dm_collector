package ca.admin.delivermore.collector.data.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.admin.delivermore.collector.data.entity.StagedRestaurantOrderTaxRate;

public interface StagedRestaurantOrderTaxRateRepository extends JpaRepository<StagedRestaurantOrderTaxRate, Long> {

    List<StagedRestaurantOrderTaxRate> findByStagedOrderIdOrderByTaxationCategoryAsc(Long stagedOrderId);
}