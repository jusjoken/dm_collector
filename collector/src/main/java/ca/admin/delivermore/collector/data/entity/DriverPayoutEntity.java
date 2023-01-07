package ca.admin.delivermore.collector.data.entity;

import ca.admin.delivermore.collector.data.Utility;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class DriverPayoutEntity{
    @CsvBindByName(column = "jobId")
    @Id
    private Long jobId = 0L;

    //@NotEmpty
    @CsvBindByName(column = "restaurantName")
    private String restaurantName = "";

    @CsvBindByName(column = "customerUsername")
    private String customerUsername = "";

    //@NotNull
    @CsvDate("yyyy/MM/dd")
    @CsvBindByName(column = "creationDate")
    private LocalDate creationDate;

    //@NotNull
    @CsvDate("yyyy/MM/dd hh:mm:ss")
    @CsvBindByName(column = "creationDateTime")
    private LocalDateTime creationDateTime;

    @CsvBindByName(column = "tip")
    private Double tip = 0.0;

    @CsvBindByName(column = "notes")
    private String notes = "";

    @CsvBindByName(column = "tipInNotesIssue")
    private Boolean tipInNotesIssue;

    @CsvBindByName(column = "webOrder")
    private Boolean webOrder = Boolean.FALSE;

    @CsvBindByName(column = "paymentMethod")
    private String paymentMethod = "";

    @CsvBindByName(column = "driverPay")
    private Double driverPay = 0.0;

    @CsvBindByName(column = "totalSale")
    private Double totalSale = 0.0;

    @CsvBindByName(column = "fleetId")
    private Long fleetId;
    @CsvBindByName(column = "fleetName")
    private String fleetName;

    @CsvBindByName(column = "driverIncome")
    private Double driverIncome = 0.0;
    @CsvBindByName(column = "driverCash")
    private Double driverCash = 0.0;
    @CsvBindByName(column = "driverPayout")
    private Double driverPayout = 0.0;

    public DriverPayoutEntity() {
    }

    public DriverPayoutEntity(Long jobId, String restaurantName, String customerUsername, LocalDateTime creationDateTime, Double tip, String notes, Boolean tipInNotesIssue, String paymentMethod, Double driverPay, Double totalSale, Long fleetId, String fleetName, Double driverIncome, Double driverCash, Double driverPayout, Boolean webOrder) {
        this.jobId = jobId;
        this.restaurantName = restaurantName;
        this.customerUsername = customerUsername;
        this.creationDateTime = creationDateTime;
        this.tip = tip;
        this.notes = notes;
        this.tipInNotesIssue = tipInNotesIssue;
        this.paymentMethod = paymentMethod;
        this.driverPay = driverPay;
        this.totalSale = totalSale;
        this.fleetId = fleetId;
        this.fleetName = fleetName;
        this.driverIncome = driverIncome;
        this.driverCash = driverCash;
        this.driverPayout = driverPayout;
        this.webOrder = webOrder;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public LocalDate getCreationDate(){
        return creationDateTime.toLocalDate();
    }

    public Double getTip() {
        //check for a webOrder with payment method ONLINE as those tip are paid directly to the driver from the restaurant
        // so ignore any tip entered by the driver
        if(webOrder && paymentMethod.equals("ONLINE")) return 0.0;
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

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Double getDriverPay() {
        return driverPay;
    }

    public void setDriverPay(Double driverPay) {
        this.driverPay = driverPay;
    }

    public Double getTotalSale() {
        return totalSale;
    }

    public void setTotalSale(Double totalSale) {
        this.totalSale = totalSale;
    }

    public Long getFleetId() {
        return fleetId;
    }

    public void setFleetId(Long fleetId) {
        this.fleetId = fleetId;
    }

    public String getFleetName() {
        return fleetName;
    }

    public void setFleetName(String fleetName) {
        this.fleetName = fleetName;
    }

    /*
        setDriverIncome(Utility.getInstance().round(getDriverPay() + getTip(),2));
        setDriverCash(0.0); //default it to zero then if CASH then calculate it
        if(getPaymentMethod()!=null && getPaymentMethod().equalsIgnoreCase("CASH")){
            //log.info("setDriverCash for task:" + jobId + " getTotalSale:" + taskEntity.getTotalSale() + " getTip" + taskEntity.getTip());
            setDriverCash(Utility.getInstance().round(getTip() + getTotalSale(),2));
        }
        setDriverPayout(Utility.getInstance().round(getDriverIncome() - getDriverCash(),2));

     */

    public Double getDriverIncome() {
        return Utility.getInstance().round(getDriverPay() + getTip(),2);
    }

    public void setDriverIncome(Double driverIncome) {
        this.driverIncome = driverIncome;
    }

    public Double getDriverCash() {
        Double cash = 0.0;
        if(getPaymentMethod()!=null && getPaymentMethod().equalsIgnoreCase("CASH")){
            cash = Utility.getInstance().round(getTip() + getTotalSale(),2);
        }
        return cash;
    }

    public void setDriverCash(Double driverCash) {
        this.driverCash = driverCash;
    }

    public Double getDriverPayout() {
        return Utility.getInstance().round(getDriverIncome() - getDriverCash(),2);
    }

    public void setDriverPayout(Double driverPayout) {
        this.driverPayout = driverPayout;
    }

    @Override
    public String toString() {
        return "DriverPayoutEntity{" +
                "jobId=" + jobId +
                ", fleetName='" + fleetName + '\'' +
                ", driverIncome=" + driverIncome +
                ", driverCash=" + driverCash +
                ", driverPayout=" + driverPayout +
                ", restaurantName='" + restaurantName + '\'' +
                ", customerUsername='" + customerUsername + '\'' +
                ", creationDate=" + creationDate +
                ", tip=" + tip +
                ", notes='" + notes + '\'' +
                ", tipInNotesIssue=" + tipInNotesIssue +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", driverPay=" + driverPay +
                ", totalSale=" + totalSale +
                ", fleetId=" + fleetId +
                '}';
    }


}
