package ca.admin.delivermore.collector.data.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.admin.delivermore.collector.data.entity.StagedRestaurantOrderLine;

public interface StagedRestaurantOrderLineRepository extends JpaRepository<StagedRestaurantOrderLine, Long> {

    List<StagedRestaurantOrderLine> findByStagedOrderIdOrderByLineNumberAsc(Long stagedOrderId);
}