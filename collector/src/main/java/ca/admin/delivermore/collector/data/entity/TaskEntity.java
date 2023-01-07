package ca.admin.delivermore.collector.data.entity;

import ca.admin.delivermore.collector.data.Config;
import ca.admin.delivermore.collector.data.Utility;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@IdClass(TaskEntityPk.class)
public class TaskEntity{
    //TODO: add job type to allow pickup or deliver in task entity - then filter DriverPayouts

    @Transient
    private Logger log = LoggerFactory.getLogger(TaskEntity.class);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskEntity that = (TaskEntity) o;
        return source.equals(that.source) && sourceId.equals(that.sourceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, sourceId);
    }

    @Id
    @CsvBindByName(column = "source")
    private String source = "tookan";
    @Id
    @CsvBindByName(column = "sourceId")
    private Long sourceId = 0L;

    @CsvBindByName(column = "jobId")
    private Long jobId = 0L;

    @CsvBindByName(column = "lastUpdated")
    private LocalDateTime lastUpdated = LocalDateTime.now();

    //@NotEmpty
    @CsvBindByName(column = "restaurantName")
    private String restaurantName = "";

    @NotEmpty
    @CsvBindByName(column = "jobStatusName")
    private String jobStatusName = "";

    //@NotEmpty
    @CsvBindByName(column = "customerUsername")
    private String customerUsername = "";

    @NotNull
    @CsvDate("yyyy/MM/dd hh:mm:ss")
    @CsvBindByName(column = "completedDate")
    private LocalDateTime completedDate;

    @NotNull
    @CsvDate("yyyy/MM/dd hh:mm:ss")
    @CsvBindByName(column = "creationDate")
    private LocalDateTime creationDate;

    @CsvBindByName(column = "tip")
    private Double tip = 0.0;

    @CsvBindByName(column = "notes")
    private String notes = "";

    @CsvBindByName(column = "tipInNotesIssue")
    private Boolean tipInNotesIssue;

    @CsvBindByName(column = "webOrder")
    private Boolean webOrder = Boolean.FALSE;

    @CsvBindByName(column = "feesOnly")
    private Boolean feesOnly = Boolean.FALSE;

    @CsvBindByName(column = "templateId")
    private String templateId = "";
    @CsvBindByName(column = "jobStatus")
    private Long jobStatus;
    @CsvBindByName(column = "restaurantId")
    private Long restaurantId;
    @CsvBindByName(column = "customerEmail")
    private String customerEmail = "";
    @CsvBindByName(column = "customerId")
    private Long customerId;
    @CsvBindByName(column = "customerPhone")
    private String customerPhone = "";
    @CsvBindByName(column = "jobAddress")
    private String jobAddress = "";
    @Lob
    @CsvBindByName(column = "jobDescription")
    private String jobDescription = "";
    @CsvBindByName(column = "paymentMethod")
    private String paymentMethod = "";
    @CsvBindByName(column = "receiptTotal")
    private Double receiptTotal;
    @CsvBindByName(column = "paidToVendor")
    private Double paidToVendor;
    @CsvBindByName(column = "deliveryFee")
    private Double deliveryFee = 0.0;
    @CsvBindByName(column = "serviceFeePercent")
    private Double serviceFeePercent;
    @CsvBindByName(column = "serviceFee")
    private Double serviceFee = 0.0;
    @CsvBindByName(column = "totalFees")
    private Double totalFees = 0.0;
    @CsvBindByName(column = "driverPay")
    private Double driverPay = 0.0;
    @CsvBindByName(column = "feeBalance")
    private Double feeBalance = 0.0;
    @CsvBindByName(column = "totalSale")
    private Double totalSale = 0.0;
    @CsvBindByName(column = "dispatcherId")
    private Long dispatcherId;
    @CsvBindByName(column = "teamId")
    private Long teamId;
    @CsvBindByName(column = "fleetId")
    private Long fleetId;
    @CsvBindByName(column = "fleetName")
    private String fleetName;
    @CsvBindByName(column = "formId")
    private Long formId;
    @CsvBindByName(column = "jobLatitude")
    private String jobLatitude = "";
    @CsvBindByName(column = "jobLongitude")
    private String jobLongitude = "";
    @CsvBindByName(column = "orderId")
    private String orderId = "";

    private Long longOrderId;
    @CsvBindByName(column = "autoAssignment")
    private Long autoAssignment;
    @CsvBindByName(column = "userId")
    private Long userId;
    @CsvBindByName(column = "createdBy")
    private Long createdBy;

    @CsvBindByName(column = "globalSubtotal")
    private Double globalSubtotal;
    @CsvBindByName(column = "globalTotalTaxes")
    private Double globalTotalTaxes;
    @CsvBindByName(column = "commission")
    private Double commission = 0.0;
    @CsvBindByName(column = "commissionRate")
    private Double commissionRate = 0.0;
    @CsvBindByName(column = "totalIncome")
    private Double totalIncome = 0.0;
    @CsvBindByName(column = "deliveryFeeFromVendor")
    private Double deliveryFeeFromVendor = 0.0;

    @CsvBindByName(column = "driverIncome")
    private Double driverIncome = 0.0;
    @CsvBindByName(column = "driverCash")
    private Double driverCash = 0.0;
    @CsvBindByName(column = "driverPayout")
    private Double driverPayout = 0.0;

    @Column(name = "pos_payment", nullable = false)
    private Boolean posPayment;

    public Boolean getPosPayment() {
        return posPayment;
    }

    public void setPosPayment(Boolean posPayment) {
        this.posPayment = posPayment;
    }

    public Boolean getWebOrder() {
        return webOrder;
    }

    public void setWebOrder(Boolean webOrder) {
        this.webOrder = webOrder;
    }

    public Boolean getFeesOnly() {
        return feesOnly;
    }

    public void setFeesOnly(Boolean feesOnly) {
        this.feesOnly = feesOnly;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public LocalDateTime getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(LocalDateTime completedDate) {
        this.completedDate = completedDate;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getJobStatusName() {
        return jobStatusName;
    }

    public void setJobStatusName(String jobStatusName) {
        this.jobStatusName = jobStatusName;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public Double getTip() {
        return tip;
    }

    public void setTip(Double tip) {
        this.tip = tip;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getTipInNotesIssue() {
        return tipInNotesIssue;
    }

    public void setTipInNotesIssue(Boolean tipInNotesIssue) {
        this.tipInNotesIssue = tipInNotesIssue;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public Long getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(Long jobStatus) {
        this.jobStatus = jobStatus;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getJobAddress() {
        return jobAddress;
    }

    public void setJobAddress(String jobAddress) {
        this.jobAddress = jobAddress;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Double getReceiptTotal() {
        return receiptTotal;
    }

    public void setReceiptTotal(Double receiptTotal) {
        this.receiptTotal = receiptTotal;
    }

    public Double getPaidToVendor() {
        return paidToVendor;
    }

    public void setPaidToVendor(Double paidToVendor) {
        this.paidToVendor = paidToVendor;
    }

    public Double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(Double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public Double getServiceFeePercent() {
        return serviceFeePercent;
    }

    public void setServiceFeePercent(Double serviceFeePercent) {
        this.serviceFeePercent = serviceFeePercent;
    }

    public Double getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(Double serviceFee) {
        this.serviceFee = serviceFee;
    }

    public Double getTotalFees() {
        return totalFees;
    }

    public void setTotalFees(Double totalFees) {
        this.totalFees = totalFees;
    }

    public Double getDriverPay() {
        return driverPay;
    }

    public void setDriverPay(Double driverPay) {
        this.driverPay = driverPay;
    }

    public Double getFeeBalance() {
        return feeBalance;
    }

    public void setFeeBalance(Double feeBalance) {
        this.feeBalance = feeBalance;
    }

    public Double getTotalSale() {
        return totalSale;
    }

    public void setTotalSale(Double totalSale) {
        this.totalSale = totalSale;
    }

    public Long getDispatcherId() {
        return dispatcherId;
    }

    public void setDispatcherId(Long dispatcherId) {
        this.dispatcherId = dispatcherId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getFleetId() {
        return fleetId;
    }

    public void setFleetId(Long fleetId) {
        this.fleetId = fleetId;
    }

    public Object getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    public String getJobLatitude() {
        return jobLatitude;
    }

    public void setJobLatitude(String jobLatitude) {
        this.jobLatitude = jobLatitude;
    }

    public String getJobLongitude() {
        return jobLongitude;
    }

    public void setJobLongitude(String jobLongitude) {
        this.jobLongitude = jobLongitude;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getAutoAssignment() {
        return autoAssignment;
    }

    public void setAutoAssignment(Long autoAssignment) {
        this.autoAssignment = autoAssignment;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Double getGlobalSubtotal() {
        return globalSubtotal;
    }

    public void setGlobalSubtotal(Double globalSubtotal) {
        this.globalSubtotal = globalSubtotal;
    }

    public Double getGlobalTotalTaxes() {
        return globalTotalTaxes;
    }

    public void setGlobalTotalTaxes(Double globalTotalTaxes) {
        this.globalTotalTaxes = globalTotalTaxes;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public Double getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(Double commissionRate) {
        this.commissionRate = commissionRate;
    }

    public Double getDeliveryFeeFromVendor() {
        return deliveryFeeFromVendor;
    }

    public void setDeliveryFeeFromVendor(Double deliveryFeeFromVendor) {
        this.deliveryFeeFromVendor = deliveryFeeFromVendor;
    }

    public Double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public String getFleetName() {
        return fleetName;
    }

    public void setFleetName(String fleetName) {
        this.fleetName = fleetName;
    }

    public Double getDriverIncome() {
        return driverIncome;
    }

    public void setDriverIncome(Double driverIncome) {
        this.driverIncome = driverIncome;
    }

    public Double getDriverCash() {
        return driverCash;
    }

    public void setDriverCash(Double driverCash) {
        this.driverCash = driverCash;
    }

    public Double getDriverPayout() {
        return driverPayout;
    }

    public void setDriverPayout(Double driverPayout) {
        this.driverPayout = driverPayout;
    }

    public Long getLongOrderId() {
        return longOrderId;
    }

    public void setLongOrderId(Long longOrderId) {
        this.longOrderId = longOrderId;
    }

    public Boolean updateGlobalData(OrderDetail orderDetail){
        Boolean skipGlobal = Boolean.FALSE;
        if(orderDetail==null){
            log.info("getTaskEntity: jobId:" + jobId + " orderDetail was null for orderId:" + longOrderId);
            skipGlobal = Boolean.TRUE;
            setTotalSale(0.0);
            setGlobalSubtotal(0.0);
            setGlobalTotalTaxes(0.0);
        }else{
            /*  DO NOT Update the payment method from Global as Tookan is the one we can edit after the fact
            if(getPaymentMethod()==null || getPaymentMethod().isEmpty()){
                setPaymentMethod(orderDetail.getPaymentMethod());
            }

             */
            setGlobalSubtotal(orderDetail.getSubtotal());
            setGlobalTotalTaxes(orderDetail.getTotalTaxes());
            setServiceFee(orderDetail.getServiceFee());
            //totalSale for Global is subTotal + taxes + serviceFee + deliveryFee
            setTotalSale(Utility.getInstance().round(orderDetail.getSubtotal() + orderDetail.getTotalTaxes() + orderDetail.getDeliveryFee() + orderDetail.getServiceFee(),2));

            if(getGlobalSubtotal()!=null){
                setCommission(Utility.getInstance().round(getCommissionRate() * getGlobalSubtotal(),2));
            }

        }
        return skipGlobal;
    }

    public void updateCalculatedFields(){
        //provide a calculated field for totalFees
        setTotalFees(Utility.getInstance().round(getServiceFee() + getDeliveryFee() + getDeliveryFeeFromVendor(),2));

        //provide a calculated field for the feeBalance
        setFeeBalance(Utility.getInstance().round(getTotalFees() - getDriverPay(),2));

        //provide a calculated field for totalIncome
        setTotalIncome(Utility.getInstance().round(getFeeBalance() + getCommission(),2));

        //provide calculated fields for driver payout
        //Moved these driver payout calculations to the fields inside DriverPayoutEntity
        /*
        setDriverIncome(Utility.getInstance().round(getDriverPay() + getTip(),2));
        setDriverCash(0.0); //default it to zero then if CASH then calculate it
        if(getPaymentMethod()!=null && getPaymentMethod().equalsIgnoreCase("CASH")){
            setDriverCash(Utility.getInstance().round(getTip() + getTotalSale(),2));
        }
        setDriverPayout(Utility.getInstance().round(getDriverIncome() - getDriverCash(),2));
         */
    }

    public String getTaskTypeName(){
        if(createdBy.equals(0L) || createdBy.equals(1L)) { //Admin or Manager created task
            return Config.TaskType.Custom.toString();
        }else if(createdBy.equals(3L)) { //Form created tasks
            return Config.TaskType.Form.toString();
        }else if(createdBy.equals(5L)) { //API created tasks
            if(orderId.equals("0")){ //orderId is zero for custom API created tasks
                return Config.TaskType.Custom.toString();
            }else{
                return Config.TaskType.Form.toString();
            }
        }else if(createdBy.equals(43L)) { //Global created tasks
            return Config.TaskType.Global.toString();
        }else{
            return Config.TaskType.Custom.toString();
        }
    }

    public DriverPayoutEntity updateDriverPayoutEntity(){
        return new DriverPayoutEntity(
                getJobId(),
                getRestaurantName(),
                getCustomerUsername(),
                getCreationDate(),
                getTip(),
                getNotes(),
                getTipInNotesIssue(),
                getPaymentMethod(),
                getDriverPay(),
                getTotalSale(),
                getFleetId(),
                getFleetName(),
                getDriverIncome(),
                getDriverCash(),
                getDriverPayout(),
                getWebOrder());
    }

    @Override
    public String toString() {
        return "TaskEntity{" +
                "jobId=" + jobId +
                ", restaurantName='" + restaurantName + '\'' +
                ", jobStatusName='" + jobStatusName + '\'' +
                ", customerUsername='" + customerUsername + '\'' +
                ", completedDate=" + completedDate +
                ", creationDate=" + creationDate +
                ", tip=" + tip +
                ", notes='" + notes + '\'' +
                ", tipInNotesIssue=" + tipInNotesIssue +
                ", templateId='" + templateId + '\'' +
                ", jobStatus=" + jobStatus +
                ", restaurantId=" + restaurantId +
                ", customerEmail='" + customerEmail + '\'' +
                ", customerId=" + customerId +
                ", customerPhone='" + customerPhone + '\'' +
                ", jobAddress='" + jobAddress + '\'' +
                ", jobDescription='" + jobDescription + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", receiptTotal=" + receiptTotal +
                ", paidToVendor=" + paidToVendor +
                ", deliveryFee=" + deliveryFee +
                ", serviceFeePercent=" + serviceFeePercent +
                ", serviceFee=" + serviceFee +
                ", totalFees=" + totalFees +
                ", driverPay=" + driverPay +
                ", feeBalance=" + feeBalance +
                ", totalSale=" + totalSale +
                ", dispatcherId=" + dispatcherId +
                ", teamId=" + teamId +
                ", fleetId=" + fleetId +
                ", fleetName='" + fleetName + '\'' +
                ", formId=" + formId +
                ", jobLatitude='" + jobLatitude + '\'' +
                ", jobLongitude='" + jobLongitude + '\'' +
                ", orderId='" + orderId + '\'' +
                ", autoAssignment=" + autoAssignment +
                ", userId=" + userId +
                ", createdBy=" + createdBy +
                ", globalSubtotal=" + globalSubtotal +
                ", globalTotalTaxes=" + globalTotalTaxes +
                ", commission=" + commission +
                ", totalIncome=" + totalIncome +
                ", deliveryFeeFromVendor=" + deliveryFeeFromVendor +
                ", driverIncome=" + driverIncome +
                ", driverCash=" + driverCash +
                ", driverPayout=" + driverPayout +
                '}';
    }

}
