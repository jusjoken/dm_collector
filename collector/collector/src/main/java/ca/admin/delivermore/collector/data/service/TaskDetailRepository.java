package ca.admin.delivermore.collector.data.service;

import ca.admin.delivermore.data.entity.DriverPayoutEntity;
import ca.admin.delivermore.collector.data.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface  TaskDetailRepository extends JpaRepository<TaskEntity, UUID> {
    @Query("select t from TaskEntity t WHERE t.creationDate BETWEEN :fromDate AND :toDate")
    List<TaskEntity> search(@Param("fromDate") LocalDateTime fromDate, @Param("toDate") LocalDateTime toDate);

    @Query("select new DriverPayoutEntity(t.jobId,t.restaurantName,t.customerUsername,t.creationDate,t.tip,t.notes,t.tipInNotesIssue,t.paymentMethod,t.driverPay,t.totalSale,t.fleetId,t.fleetName,t.driverIncome,t.driverCash,t.driverPayout) from TaskEntity t WHERE t.creationDate BETWEEN :fromDate AND :toDate and t.jobStatus = 2 order by t.fleetName, t.creationDate")
    List<DriverPayoutEntity> getDriverPayout(@Param("fromDate") LocalDateTime fromDate, @Param("toDate") LocalDateTime toDate);

    @Query("select t from TaskEntity t order by t.fleetName, t.creationDate")
    List<TaskEntity> findByOrderByFleetNameAscCreationDateAsc();

    @Query("select distinct t.fleetId from TaskEntity t where t.creationDate BETWEEN :fromDate AND :toDate and t.jobStatus = 2")
    List<Long> findDistinctFleetIdBetweenDates(@Param("fromDate") LocalDateTime fromDate, @Param("toDate") LocalDateTime toDate);

    @Query("select new DriverPayoutEntity(t.jobId,t.restaurantName,t.customerUsername,t.creationDate,t.tip,t.notes,t.tipInNotesIssue,t.paymentMethod,t.driverPay,t.totalSale,t.fleetId,t.fleetName,t.driverIncome,t.driverCash,t.driverPayout) from TaskEntity t WHERE t.fleetId = :fleetId and t.creationDate BETWEEN :fromDate AND :toDate and t.jobStatus = 2 order by t.creationDate")
    List<DriverPayoutEntity> getDriverPayoutByFleetId(@Param("fleetId") Long fleetId, @Param("fromDate") LocalDateTime fromDate, @Param("toDate") LocalDateTime toDate);

    @Query("select new DriverPayoutEntity(t.jobId,t.restaurantName,t.customerUsername,t.creationDate,t.tip,t.notes,t.tipInNotesIssue,t.paymentMethod,t.driverPay,t.totalSale,t.fleetId,t.fleetName,t.driverIncome,t.driverCash,t.driverPayout) from TaskEntity t WHERE t.creationDate BETWEEN :fromDate AND :toDate and t.jobStatus = 2 and t.tipInNotesIssue = true order by t.fleetName, t.creationDate")
    List<DriverPayoutEntity> getDriverPayoutTipIssues(@Param("fromDate") LocalDateTime fromDate, @Param("toDate") LocalDateTime toDate);

    @Query("select t from TaskEntity t where t.orderId = ?1")
    List<TaskEntity> findByOrderId(String orderId);

    @Query(value="select creation_date from task_entity order by creation_date desc limit 1", nativeQuery = true)
    LocalDateTime getMaxDate();

    @Query(value="select job_id from task_entity where job_status = 2 order by job_id desc limit 1", nativeQuery = true)
    Long getMaxSuccessfulJobId();

    @Query("select (count(t) > 0) from TaskEntity t where t.jobId = :jobId and t.jobStatus = 2")
    boolean hasSuccessfulJobID(@Param("jobId") Long jobId);

    @Query("select t from TaskEntity t where t.jobId = :jobId")
    List<TaskEntity> findByJobId(@Param("jobId") Long jobId);


}

/*

t.getJobId,t.getRestaurantName,t.getCustomerUsername,t.getCreationDate,t.getTip,t.getNotes,t.getTipInNotesIssue,t.getPaymentMethod,t.getDriverPay,t.getTotalSale,t.getFleetId,t.getFleetName,t.getDriverIncome,t.getDriverCash,t.getDriverPayout

    @Query("select t from TaskEntity t ")
    List<TaskEntity> search(@Param("fromDate") LocalDateTime fromDate, @Param("toDate") LocalDateTime toDate);

    @Query("select t from TaskEntity t " +
            "where t.completedDateTimeLocal <= :toDate " +
            "and t.completedDateTimeLocal >= :fromDate")
    List<TaskEntity> search(@Param("fromDate") LocalDateTime fromDate, @Param("toDate") LocalDateTime toDate); }


    @Query("select t from TaskEntity t " +
            "where lower(t.restaurantName) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(t.customerUsername) like lower(concat('%', :searchTerm, '%'))")
    List<TaskEntity> search(@Param("searchTerm") String searchTerm); }

 */
