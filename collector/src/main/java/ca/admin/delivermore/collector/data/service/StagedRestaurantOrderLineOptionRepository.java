package ca.admin.delivermore.collector.data.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.admin.delivermore.collector.data.entity.StagedRestaurantOrderLineOption;

public interface StagedRestaurantOrderLineOptionRepository extends JpaRepository<StagedRestaurantOrderLineOption, Long> {

    List<StagedRestaurantOrderLineOption> findByStagedOrderIdOrderByLineNumberAscIdAsc(Long stagedOrderId);
}