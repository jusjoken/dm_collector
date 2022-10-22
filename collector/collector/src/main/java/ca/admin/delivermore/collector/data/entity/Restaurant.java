package ca.admin.delivermore.collector.data.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
public class Restaurant{

    public Restaurant() {
        super();
    }

    public Restaurant(Long restaurantId, String name, Double commissionRate, String authCode) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.commissionRate = commissionRate;
        this.globalAuthCode = authCode;
    }

    public Restaurant(Long restaurantId, String name, Double commissionRate, Long formId, String authCode) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.formId = formId;
        this.commissionRate = commissionRate;
        this.deliveryFeeFromVendor = deliveryFeeFromVendor;
        this.globalAuthCode = authCode;
    }

    public Restaurant(Long restaurantId, String name, Double commissionRate, Double deliveryFeeFromVendor, String authCode) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.commissionRate = commissionRate;
        this.deliveryFeeFromVendor = deliveryFeeFromVendor;
        this.globalAuthCode = authCode;
    }

    public Restaurant(Long restaurantId, String name, Double commissionRate, Double deliveryFeeFromVendor, Long formId, String authCode) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.formId = formId;
        this.commissionRate = commissionRate;
        this.deliveryFeeFromVendor = deliveryFeeFromVendor;
        this.globalAuthCode = authCode;
    }

    public Restaurant(Long restaurantId, String name, Double commissionRate, Double commissionPerDelivery, Double deliveryFeeFromVendor, Long formId, String authCode) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.formId = formId;
        this.commissionRate = commissionRate;
        this.commissionPerDelivery = commissionPerDelivery;
        this.deliveryFeeFromVendor = deliveryFeeFromVendor;
        this.globalAuthCode = authCode;
    }


    //@NotEmpty
    @Id
    private Long restaurantId;

    @NotEmpty
    private String name = "";

    private Long formId = 0L;

    private Double commissionRate = 0.0;

    private Double commissionPerDelivery = 0.0;

    private Double deliveryFeeFromVendor = 0.0;

    private String globalAuthCode = "";

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

    public Double getDeliveryFeeFromVendor() {
        return deliveryFeeFromVendor;
    }

    public void setDeliveryFeeFromVendor(Double deliveryFeeFromVendor) {
        this.deliveryFeeFromVendor = deliveryFeeFromVendor;
    }

    public Double getCommissionPerDelivery() {
        return commissionPerDelivery;
    }

    public void setCommissionPerDelivery(Double commissionPerDelivery) {
        this.commissionPerDelivery = commissionPerDelivery;
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
                ", name='" + name + '\'' +
                ", formId=" + formId +
                ", commissionRate=" + commissionRate +
                ", commissionPerDelivery=" + commissionPerDelivery +
                ", deliveryFeeFromVendor=" + deliveryFeeFromVendor +
                '}';
    }
}
