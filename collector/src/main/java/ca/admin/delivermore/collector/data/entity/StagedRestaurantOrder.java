package ca.admin.delivermore.collector.data.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class StagedRestaurantOrder {

    public enum ApprovalStatus {
        PENDING_APPROVAL,
        APPROVED,
        DECLINED,
        CANCELED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;

    @Column(name = "restaurant_name", nullable = false)
    private String restaurantName;

    @Column(name = "menu_version_id")
    private Long menuVersionId;

    @Column(name = "submission_source", nullable = false)
    private String submissionSource = "ADMIN_PREVIEW";

    @Column(name = "contact_name", nullable = false)
    private String contactName;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "contact_phone", nullable = false)
    private String contactPhone;

    @Column(name = "street_address", nullable = false)
    private String streetAddress;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "customer_profile_id")
    private Long customerProfileId;

    @Column(name = "customer_latitude")
    private Double customerLatitude;

    @Column(name = "customer_longitude")
    private Double customerLongitude;

    @Column(name = "location_confirmed_at")
    private LocalDateTime locationConfirmedAt;

    @Column(name = "delivery_distance_km")
    private Double deliveryDistanceKm = 0.0;

    @Column(name = "delivery_zone_name")
    private String deliveryZoneName;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    @Column(name = "in_person_delivery", nullable = false)
    private boolean inPersonDelivery;

    @Column(name = "subtotal", nullable = false)
    private Double subtotal = 0.0;

    @Column(name = "service_fee", nullable = false)
    private Double serviceFee = 0.0;

    @Column(name = "service_fee_tax", nullable = false)
    private Double serviceFeeTax = 0.0;

    @Column(name = "gst", nullable = false)
    private Double gst = 0.0;

    @Column(name = "item_tax", nullable = false)
    private Double itemTax = 0.0;

    @Column(name = "delivery_fee", nullable = false)
    private Double deliveryFee = 0.0;

    @Column(name = "delivery_fee_tax", nullable = false)
    private Double deliveryFeeTax = 0.0;

    @Column(name = "tip", nullable = false)
    private Double tip = 0.0;

    @Column(name = "total", nullable = false)
    private Double total = 0.0;

    @Column(name = "approval_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus = ApprovalStatus.PENDING_APPROVAL;

    @Column(name = "status_reason", length = 1000)
    private String statusReason;

    @Column(name = "order_detail_id")
    private Long orderDetailId;

    @Column(name = "auto_approved", nullable = false)
    private boolean autoApproved;

    @Column(name = "submitted_at", nullable = false)
    private LocalDateTime submittedAt;

    @Column(name = "approval_requested_at")
    private LocalDateTime approvalRequestedAt;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "status_updated_at")
    private LocalDateTime statusUpdatedAt;

    @Column(name = "support_email_sent_at")
    private LocalDateTime supportEmailSentAt;

    @Lob
    @Column(name = "customer_comments", columnDefinition = "LONGTEXT")
    private String customerComments;

    @Lob
    @Column(name = "order_summary", columnDefinition = "LONGTEXT")
    private String orderSummary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Long getMenuVersionId() {
        return menuVersionId;
    }

    public void setMenuVersionId(Long menuVersionId) {
        this.menuVersionId = menuVersionId;
    }

    public String getSubmissionSource() {
        return submissionSource;
    }

    public void setSubmissionSource(String submissionSource) {
        this.submissionSource = submissionSource;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Long getCustomerProfileId() {
        return customerProfileId;
    }

    public void setCustomerProfileId(Long customerProfileId) {
        this.customerProfileId = customerProfileId;
    }

    public Double getCustomerLatitude() {
        return customerLatitude;
    }

    public void setCustomerLatitude(Double customerLatitude) {
        this.customerLatitude = customerLatitude;
    }

    public Double getCustomerLongitude() {
        return customerLongitude;
    }

    public void setCustomerLongitude(Double customerLongitude) {
        this.customerLongitude = customerLongitude;
    }

    public LocalDateTime getLocationConfirmedAt() {
        return locationConfirmedAt;
    }

    public void setLocationConfirmedAt(LocalDateTime locationConfirmedAt) {
        this.locationConfirmedAt = locationConfirmedAt;
    }

    public Double getDeliveryDistanceKm() {
        return deliveryDistanceKm;
    }

    public void setDeliveryDistanceKm(Double deliveryDistanceKm) {
        this.deliveryDistanceKm = deliveryDistanceKm;
    }

    public String getDeliveryZoneName() {
        return deliveryZoneName;
    }

    public void setDeliveryZoneName(String deliveryZoneName) {
        this.deliveryZoneName = deliveryZoneName;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean getInPersonDelivery() {
        return inPersonDelivery;
    }

    public void setInPersonDelivery(boolean inPersonDelivery) {
        this.inPersonDelivery = inPersonDelivery;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(Double serviceFee) {
        this.serviceFee = serviceFee;
    }

    public Double getServiceFeeTax() {
        return serviceFeeTax;
    }

    public void setServiceFeeTax(Double serviceFeeTax) {
        this.serviceFeeTax = serviceFeeTax;
    }

    public Double getGst() {
        return gst;
    }

    public void setGst(Double gst) {
        this.gst = gst;
    }

    public Double getItemTax() {
        return itemTax;
    }

    public void setItemTax(Double itemTax) {
        this.itemTax = itemTax;
    }

    public Double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(Double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public Double getDeliveryFeeTax() {
        return deliveryFeeTax;
    }

    public void setDeliveryFeeTax(Double deliveryFeeTax) {
        this.deliveryFeeTax = deliveryFeeTax;
    }

    public Double getTotal() {
        return total;
    }

    public Double getTip() {
        return tip;
    }

    public void setTip(Double tip) {
        this.tip = tip;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public ApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(ApprovalStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getStatusReason() {
        return statusReason;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

    public Long getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Long orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public boolean getAutoApproved() {
        return autoApproved;
    }

    public void setAutoApproved(boolean autoApproved) {
        this.autoApproved = autoApproved;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public LocalDateTime getApprovalRequestedAt() {
        return approvalRequestedAt;
    }

    public void setApprovalRequestedAt(LocalDateTime approvalRequestedAt) {
        this.approvalRequestedAt = approvalRequestedAt;
    }

    public LocalDateTime getApprovedAt() {
        return approvedAt;
    }

    public void setApprovedAt(LocalDateTime approvedAt) {
        this.approvedAt = approvedAt;
    }

    public LocalDateTime getStatusUpdatedAt() {
        return statusUpdatedAt;
    }

    public void setStatusUpdatedAt(LocalDateTime statusUpdatedAt) {
        this.statusUpdatedAt = statusUpdatedAt;
    }

    public LocalDateTime getSupportEmailSentAt() {
        return supportEmailSentAt;
    }

    public void setSupportEmailSentAt(LocalDateTime supportEmailSentAt) {
        this.supportEmailSentAt = supportEmailSentAt;
    }

    public String getCustomerComments() {
        return customerComments;
    }

    public void setCustomerComments(String customerComments) {
        this.customerComments = customerComments;
    }

    public String getOrderSummary() {
        return orderSummary;
    }

    public void setOrderSummary(String orderSummary) {
        this.orderSummary = orderSummary;
    }
}