package ca.admin.delivermore.collector.data.service;

import ca.admin.delivermore.collector.data.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {

    @Query("select t from Restaurant t WHERE t.restaurantId = :restaurantId")
    Restaurant findByRestaurantId(@Param("restaurantId") Long restaurantId);

    @Query("select t from Restaurant t WHERE t.formId = :formId")
    Restaurant findByFormId(@Param("formId") Long formId);


    //Restaurant findByFormId(Long formId);
    //Restaurant findByRestaurantId(Long restaurantId);
}
