package ca.admin.delivermore.collector.data.service;

import ca.admin.delivermore.collector.data.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {

    @Query("select t from Restaurant t WHERE t.restaurantId = :restaurantId")
    List<Restaurant> findByRestaurantId(@Param("restaurantId") Long restaurantId);

    @Query("select r from Restaurant r where r.restaurantId = :restaurantId and ((r.dateEffective <= :dateEffective and r.dateExpired >= :dateEffective) or (r.dateEffective <= :dateEffective and r.dateExpired is null))")
    List<Restaurant> findEffectiveByRestaurantId(@Param("restaurantId") Long restaurantId, @Param("dateEffective") LocalDate dateEffective);

    @Query("select r from Restaurant r where r.name = :restaurantName and ((r.dateEffective <= :dateEffective and r.dateExpired >= :dateEffective) or (r.dateEffective <= :dateEffective and r.dateExpired is null))")
    List<Restaurant> findEffectiveByRestaurantName(@Param("restaurantName") String restaurantName, @Param("dateEffective") LocalDate dateEffective);

    @Query("select t from Restaurant t WHERE t.formId = :formId")
    List<Restaurant> findByFormId(@Param("formId") Long formId);

    @Query("select r from Restaurant r where r.formId = :formId and ((r.dateEffective <= :dateEffective and r.dateExpired >= :dateEffective) or (r.dateEffective <= :dateEffective and r.dateExpired is null))")
    List<Restaurant> findEffectiveByFormId(@Param("formId") Long formId, @Param("dateEffective") LocalDate dateEffective);

    @Query("select r from Restaurant r where r.activeForPayout = true order by r.name")
    List<Restaurant> getRestaurantsForPayout();

    @Query("select r from Restaurant r where r.activeForPayout = true and ((r.dateEffective <= :dateEffective and r.dateExpired >= :dateEffective) or (r.dateEffective <= :dateEffective and r.dateExpired is null)) order by r.name")
    List<Restaurant> getEffectiveRestaurantsForPayout(@Param("dateEffective") LocalDate dateEffective);

    @Query("select distinct r from Restaurant r where r.activeForPayout = true and r.dateExpired is null order by r.name")
    List<Restaurant> findDistinctNonExpiredRestaurants();

    @Query("select r.restaurantId from Restaurant r where r.posGlobal = true and ((r.dateEffective <= :dateEffective and r.dateExpired >= :dateEffective) or (r.dateEffective <= :dateEffective and r.dateExpired is null))")
    List<Long> getEffectiveRestaurantIdsGlobalPos(@Param("dateEffective") LocalDate dateEffective);



    /* query for restaurant_new to get single effective row
    select * from restaurant r where (r.date_effective <= '2022-10-17' and r.date_expired >= '2022-10-17') or (r.date_effective <= '2022-10-17' and r.date_expired is null)
     */



    //Restaurant findByFormId(Long formId);
    //Restaurant findByRestaurantId(Long restaurantId);
}
