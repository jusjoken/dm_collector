package ca.admin.delivermore.collector.data.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Entity
@IdClass(RestaurantPk.class)
public class Restaurant{

    @Id
    private Long restaurantId;
    @Id
    private LocalDate dateEffective;

    private LocalDate dateExpired;

    @NotEmpty
    private String name = "";

    private Long formId = 0L;

    private Double commissionRate = 0.0;

    private Double commissionPerDelivery = 0.0;

    private Double deliveryFeeFromVendor = 0.0;

    private Double deliveryFeeFromVendorWebOrder = 0.0;

    private String globalAuthCode = "";

    @Column(name = "start_day_offset", nullable = false)
    private int startDayOffset = 0;

    public int getStartDayOffset() {
        return startDayOffset;
    }

    public void setStartDayOffset(int startDayOffset) {
        this.startDayOffset = startDayOffset;
    }

    @Column(name = "weeks_in_period", nullable = false)
    private int weeksInPeriod = 1;

    public int getWeeksInPeriod() {
        return weeksInPeriod;
    }

    public void setWeeksInPeriod(int weeksInPeriod) {
        this.weeksInPeriod = weeksInPeriod;
    }

    @Column(name = "range_start_date")
    private LocalDate rangeStartDate;

    public LocalDate getRangeStartDate() {
        return rangeStartDate;
    }

    public void setRangeStartDate(LocalDate rangeStartDate) {
        this.rangeStartDate = rangeStartDate;
    }

    @Column(name = "active_for_payout", nullable = false)
    private boolean activeForPayout;

    public boolean getActiveForPayout() {
        return activeForPayout;
    }

    public void setActiveForPayout(boolean activeForPayout) {
        this.activeForPayout = activeForPayout;
    }

    @Column(name = "pos_global", nullable = false)
    private boolean posGlobal;

    public boolean getPosGlobal() {
        return posGlobal;
    }

    public void setPosGlobal(boolean posGlobal) {
        this.posGlobal = posGlobal;
    }

    @Column(name = "pos_phonein", nullable = false)
    private boolean posPhonein;

    public boolean getPosPhonein() {
        return posPhonein;
    }

    public void setPosPhonein(boolean posPhonein) {
        this.posPhonein = posPhonein;
    }

    @Column(name = "email")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "process_order_text")
    private Boolean processOrderText;

    @Column(name = "teamId")
    private Long teamId;

    @Column(name = "driver_pay_override")
    private Double driverPayOverride = null;

    @Column(name = "use_invoice_processing")
    private Boolean useInvoiceProcessing = Boolean.FALSE;

    public Restaurant() {
        super();
    }

    public Restaurant(Long restaurantId, String name, LocalDate dateEffective) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.dateEffective = dateEffective;
    }

    public LocalDate getDateEffective() {
        return dateEffective;
    }

    public void setDateEffective(LocalDate dateEffective) {
        this.dateEffective = dateEffective;
    }

    public LocalDate getDateExpired() {
        return dateExpired;
    }

    public void setDateExpired(LocalDate dateExpired) {
        this.dateExpired = dateExpired;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    public Double getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(Double commissionRate) {
        this.commissionRate = commissionRate;
    }

    @Column(name = "commission_rate_phonein")
    private Double commissionRatePhonein;

    public Double getDeliveryFeeFromVendor() {
        return deliveryFeeFromVendor;
    }

    public void setDeliveryFeeFromVendor(Double deliveryFeeFromVendor) {
        this.deliveryFeeFromVendor = deliveryFeeFromVendor;
    }

    public Double getDeliveryFeeFromVendorWebOrder() {
        return deliveryFeeFromVendorWebOrder;
    }

    public void setDeliveryFeeFromVendorWebOrder(Double deliveryFeeFromVendorWebOrder) {
        this.deliveryFeeFromVendorWebOrder = deliveryFeeFromVendorWebOrder;
    }

    public Double getCommissionPerDelivery() {
        return commissionPerDelivery;
    }

    public void setCommissionPerDelivery(Double commissionPerDelivery) {
        this.commissionPerDelivery = commissionPerDelivery;
    }

    @Column(name = "commission_per_phonein")
    private Double commissionPerPhonein;

    public Double getCommissionPerPhonein() {
        return commissionPerPhonein;
    }

    public void setCommissionPerPhonein(Double commissionPerPhonein) {
        this.commissionPerPhonein = commissionPerPhonein;
    }

    @Column(name = "delivery_fee_from_external")
    private Double deliveryFeeFromExternal;

    public Double getDeliveryFeeFromExternal() {
        return deliveryFeeFromExternal;
    }

    public void setDeliveryFeeFromExternal(Double deliveryFeeFromExternal) {
        this.deliveryFeeFromExternal = deliveryFeeFromExternal;
    }

    @Column(name = "delivery_fee_from_external_vendor_name")
    private String deliveryFeeFromExternalVendorName;

    public Boolean getUseInvoiceProcessing() {
        return useInvoiceProcessing;
    }

    public void setUseInvoiceProcessing(Boolean useInvoiceProcessing) {
        this.useInvoiceProcessing = useInvoiceProcessing;
    }

    public Double getDriverPayOverride() {
        return driverPayOverride;
    }

    public void setDriverPayOverride(Double driverPayOverride) {
        this.driverPayOverride = driverPayOverride;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Boolean getProcessOrderText() {
        return processOrderText;
    }

    public void setProcessOrderText(Boolean processOrderText) {
        this.processOrderText = processOrderText;
    }

    public Double getCommissionRatePhonein() {
        return commissionRatePhonein;
    }

    public void setCommissionRatePhonein(Double commissionRatePhonein) {
        this.commissionRatePhonein = commissionRatePhonein;
    }

    public String getDeliveryFeeFromExternalVendorName() {
        return deliveryFeeFromExternalVendorName;
    }

    public void setDeliveryFeeFromExternalVendorName(String deliveryFeeFromExternalVendorName) {
        this.deliveryFeeFromExternalVendorName = deliveryFeeFromExternalVendorName;
    }

    public String getGlobalAuthCode() {
        return globalAuthCode;
    }

    public void setGlobalAuthCode(String globalAuthCode) {
        this.globalAuthCode = globalAuthCode;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "restaurantId=" + restaurantId +
                ", teamId=" + teamId +
                ", dateEffective=" + dateEffective +
                ", dateExpired=" + dateExpired +
                ", name='" + name + '\'' +
                ", formId=" + formId +
                ", commissionRate=" + commissionRate +
                ", commissionPerDelivery=" + commissionPerDelivery +
                ", deliveryFeeFromVendor=" + deliveryFeeFromVendor +
                ", deliveryFeeFromVendorWebOrder=" + deliveryFeeFromVendorWebOrder +
                ", globalAuthCode='" + globalAuthCode + '\'' +
                ", startDayOffset=" + startDayOffset +
                ", activeForPayout=" + activeForPayout +
                ", posGlobal=" + posGlobal +
                ", posPhonein=" + posPhonein +
                ", email='" + email + '\'' +
                ", processOrderText=" + processOrderText +
                ", commissionRatePhonein=" + commissionRatePhonein +
                ", commissionPerPhonein=" + commissionPerPhonein +
                '}';
    }
}
