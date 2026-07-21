package ca.admin.delivermore.collector.data.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.admin.delivermore.collector.data.entity.RestaurantMenuVersion;
import ca.admin.delivermore.collector.data.entity.RestaurantMenuVersion.WorkflowStatus;

public interface RestaurantMenuVersionRepository extends JpaRepository<RestaurantMenuVersion, Long> {

    long countByHeaderImageAssetId(Long headerImageAssetId);

    RestaurantMenuVersion findTopByRestaurantIdOrderByVersionNumberDesc(Long restaurantId);

    RestaurantMenuVersion findTopByRestaurantIdAndWorkflowStatusOrderByVersionNumberDesc(Long restaurantId, WorkflowStatus workflowStatus);

    List<RestaurantMenuVersion> findByRestaurantIdOrderByVersionNumberDesc(Long restaurantId);

    RestaurantMenuVersion findByRestaurantIdAndActiveTrue(Long restaurantId);

    boolean existsByRestaurantIdAndWorkflowStatus(Long restaurantId, WorkflowStatus workflowStatus);

    boolean existsByRestaurantIdAndWorkflowStatusIn(Long restaurantId, Set<WorkflowStatus> workflowStatus);

    RestaurantMenuVersion findByIdAndRestaurantId(Long id, Long restaurantId);
}