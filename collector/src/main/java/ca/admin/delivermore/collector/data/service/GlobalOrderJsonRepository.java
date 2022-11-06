package ca.admin.delivermore.collector.data.service;

import ca.admin.delivermore.collector.data.entity.GlobalOrderJson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GlobalOrderJsonRepository extends JpaRepository<GlobalOrderJson, Long> {
    @Query(value = "select * from global_order_json where status = 'NEW'",nativeQuery = true)
    List<GlobalOrderJson> getAllNewJson();

}