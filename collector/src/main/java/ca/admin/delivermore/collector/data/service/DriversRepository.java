package ca.admin.delivermore.collector.data.service;

import ca.admin.delivermore.collector.data.tookan.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface DriversRepository extends JpaRepository<Driver, UUID> {

    @Query("select t from Driver t WHERE t.fleetId = :fleetId")
    Driver findByFleetId(@Param("fleetId") Long fleetId);

    Driver getDriverByFleetId(Long aLong);

    Driver findDriverByFleetId(Long aLong);

}
