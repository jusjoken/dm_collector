package ca.admin.delivermore.collector.data.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ca.admin.delivermore.collector.data.entity.CustomerRestaurantLink;

public interface CustomerRestaurantLinkRepository extends JpaRepository<CustomerRestaurantLink, Long> {

    Optional<CustomerRestaurantLink> findByCustomerProfileIdAndRestaurantId(Long customerProfileId, Long restaurantId);

    List<CustomerRestaurantLink> findByCustomerProfileId(Long customerProfileId);

    @Query("""
            select l.customerProfileId
            from CustomerRestaurantLink l
            where l.lastOrderAt is not null
              and l.lastOrderAt between :start and :end
            group by l.customerProfileId
            """)
    List<Long> findActiveProfileIdsBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("""
            select l.customerProfileId
            from CustomerRestaurantLink l
            group by l.customerProfileId
            having count(distinct l.restaurantId) > 1
            """)
    List<Long> findProfileIdsWithMultipleRestaurants();
}
