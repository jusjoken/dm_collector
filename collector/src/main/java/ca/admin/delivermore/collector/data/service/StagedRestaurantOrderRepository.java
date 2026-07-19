package ca.admin.delivermore.collector.data.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ca.admin.delivermore.collector.data.entity.StagedRestaurantOrder;
import ca.admin.delivermore.collector.data.entity.StagedRestaurantOrder.ApprovalStatus;

public interface StagedRestaurantOrderRepository extends JpaRepository<StagedRestaurantOrder, Long> {

    List<StagedRestaurantOrder> findByApprovalStatusOrderBySubmittedAtDesc(ApprovalStatus approvalStatus);

    @Query("""
            select o
            from StagedRestaurantOrder o
            where o.restaurantId = :restaurantId
              and o.approvalStatus in :statuses
              and coalesce(o.statusUpdatedAt, o.approvedAt, o.submittedAt) >= :since
            order by coalesce(o.statusUpdatedAt, o.approvedAt, o.submittedAt) desc
            """)
    List<StagedRestaurantOrder> findCompletedHistorySince(
            @Param("restaurantId") Long restaurantId,
            @Param("statuses") Set<ApprovalStatus> statuses,
            @Param("since") java.time.LocalDateTime since);

    List<StagedRestaurantOrder> findAllByOrderBySubmittedAtDesc();
}