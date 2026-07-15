package ca.admin.delivermore.collector.data.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.admin.delivermore.collector.data.entity.StagedRestaurantOrder;
import ca.admin.delivermore.collector.data.entity.StagedRestaurantOrder.ApprovalStatus;

public interface StagedRestaurantOrderRepository extends JpaRepository<StagedRestaurantOrder, Long> {

    List<StagedRestaurantOrder> findByApprovalStatusOrderBySubmittedAtDesc(ApprovalStatus approvalStatus);

    List<StagedRestaurantOrder> findAllByOrderBySubmittedAtDesc();
}