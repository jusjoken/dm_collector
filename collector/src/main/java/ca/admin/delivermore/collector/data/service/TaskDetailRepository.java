package ca.admin.delivermore.collector.data.service;

import ca.admin.delivermore.collector.data.entity.DriverPayoutEntity;
import ca.admin.delivermore.collector.data.entity.TaskEntity;
import ca.admin.delivermore.collector.data.reportitem.CustomerTasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface  TaskDetailRepository extends JpaRepository<TaskEntity, UUID> {
    @Query("select t from TaskEntity t WHERE t.creationDate BETWEEN :fromDate AND :toDate")
    List<TaskEntity> search(@Param("fromDate") LocalDateTime fromDate, @Param("toDate") LocalDateTime toDate);

    @Query("select t from TaskEntity t WHERE t.creationDate BETWEEN :fromDate AND :toDate and t.restaurantId = :restaurantId and t.jobStatus = 2")
    List<TaskEntity> getTaskEntityByDateAndRestaurant(@Param("fromDate") LocalDateTime fromDate, @Param("toDate") LocalDateTime toDate, @Param("restaurantId") Long restaurantId);

    @Query("select t from TaskEntity t WHERE t.creationDate BETWEEN :fromDate AND :toDate and t.restaurantId = :restaurantId and t.jobStatus = 9")
    List<TaskEntity> getTaskEntityByDateAndRestaurantCancelled(@Param("fromDate") LocalDateTime fromDate, @Param("toDate") LocalDateTime toDate, @Param("restaurantId") Long restaurantId);

    @Query("select new DriverPayoutEntity(t.jobId,t.restaurantName,t.customerUsername,t.creationDate,t.tip,t.notes,t.tipInNotesIssue,t.paymentMethod,t.driverPay,t.totalSale,t.fleetId,t.fleetName,t.driverIncome,t.driverCash,t.driverPayout,t.webOrder) from TaskEntity t WHERE t.creationDate BETWEEN :fromDate AND :toDate and t.jobStatus = 2 order by t.fleetName, t.creationDate")
    List<DriverPayoutEntity> getDriverPayout(@Param("fromDate") LocalDateTime fromDate, @Param("toDate") LocalDateTime toDate);

    @Query("select t from TaskEntity t order by t.fleetName, t.creationDate")
    List<TaskEntity> findByOrderByFleetNameAscCreationDateAsc();

    @Query("select distinct t.fleetId from TaskEntity t where t.creationDate BETWEEN :fromDate AND :toDate and t.jobStatus = 2")
    List<Long> findDistinctFleetIdBetweenDates(@Param("fromDate") LocalDateTime fromDate, @Param("toDate") LocalDateTime toDate);

    @Query("select new DriverPayoutEntity(t.jobId,t.restaurantName,t.customerUsername,t.creationDate,t.tip,t.notes,t.tipInNotesIssue,t.paymentMethod,t.driverPay,t.totalSale,t.fleetId,t.fleetName,t.driverIncome,t.driverCash,t.driverPayout,t.webOrder) from TaskEntity t WHERE t.fleetId = :fleetId and t.creationDate BETWEEN :fromDate AND :toDate and t.jobStatus = 2 order by t.creationDate")
    List<DriverPayoutEntity> getDriverPayoutByFleetId(@Param("fleetId") Long fleetId, @Param("fromDate") LocalDateTime fromDate, @Param("toDate") LocalDateTime toDate);

    @Query("select new DriverPayoutEntity(t.jobId,t.restaurantName,t.customerUsername,t.creationDate,t.tip,t.notes,t.tipInNotesIssue,t.paymentMethod,t.driverPay,t.totalSale,t.fleetId,t.fleetName,t.driverIncome,t.driverCash,t.driverPayout,t.webOrder) from TaskEntity t WHERE t.creationDate BETWEEN :fromDate AND :toDate and t.jobStatus = 2 and t.tipInNotesIssue = true order by t.fleetName, t.creationDate")
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

    @Query("select new ca.admin.delivermore.collector.data.reportitem.CustomerTasks(t.customerEmail, t.customerUsername, count(t.jobId)) from TaskEntity t where t.createdBy = :createdBy and t.creationDate BETWEEN :fromDate AND :toDate group by nullif(t.customerEmail,'')")
    List<CustomerTasks> findCustomerTasksByCreatedByAndDates(@Param("createdBy") Long createdBy, @Param("fromDate") LocalDateTime fromDate, @Param("toDate") LocalDateTime toDate);

    @Query("select new ca.admin.delivermore.collector.data.reportitem.CustomerTasks(t.customerEmail, t.customerUsername, count(t.jobId)) from TaskEntity t where t.creationDate BETWEEN :fromDate AND :toDate group by nullif(t.customerEmail,'')")
    List<CustomerTasks> findCustomerTasksByDates(@Param("fromDate") LocalDateTime fromDate, @Param("toDate") LocalDateTime toDate);

    @Query("select count(t.jobId) from TaskEntity t WHERE DATE(t.creationDate) = :date and t.jobStatus = 2")
    Long findTaskCountByDate(@Param("date") Date date);

    @Query("select t from TaskEntity t WHERE t.creationDate BETWEEN :fromDate AND :toDate and t.createdBy = 43 and t.globalSubtotal = 0 and t.jobStatus = 2")
    List<TaskEntity> getTaskEntityByDateMissingGlobalInfo(@Param("fromDate") LocalDateTime fromDate, @Param("toDate") LocalDateTime toDate);

    @Query("select t from TaskEntity t WHERE t.creationDate BETWEEN :fromDate AND :toDate and t.createdBy = 43 and t.paidToVendor IS NULL and t.jobStatus = 2 and t.restaurantId IN :restaurantIds order by t.restaurantName, t.creationDate")
    List<TaskEntity> getTaskEntityByDateMissingPOSInfo(@Param("fromDate") LocalDateTime fromDate, @Param("toDate") LocalDateTime toDate, @Param("restaurantIds") List<Long> restaurantIds);

    @Query("select t from TaskEntity t WHERE t.creationDate BETWEEN :fromDate AND :toDate and t.createdBy = 43 and t.jobStatus = 2 and t.restaurantId IN :restaurantIds order by t.restaurantName, t.creationDate")
    List<TaskEntity> getTaskEntityByDatePOSInfo(@Param("fromDate") LocalDateTime fromDate, @Param("toDate") LocalDateTime toDate, @Param("restaurantIds") List<Long> restaurantIds);

}

