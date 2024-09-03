package ca.admin.delivermore.collector.data.entity;

import ca.admin.delivermore.collector.data.Utility;
import com.opencsv.bean.CsvBindByName;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class OrderDetail {

    public enum Source{
        CSV, JSON;
    }

    @NotNull
    @CsvBindByName(column = "Order ID")
    @Id
    private Long orderId;

    @NotNull
    @CsvBindByName(column = "Restaurant ID")
    private Long restaurantId;

    @CsvBindByName(column = "Subtotal")
    private Double subtotal;
    @CsvBindByName(column = "Delivery fee")
    private Double deliveryFee = 0.0;

    @CsvBindByName(column = "Service fees on subtotal")
    private Double serviceFee;

    @CsvBindByName(column = "Total taxes")
    private Double totalTaxes;
    @CsvBindByName(column = "Total")
    private Double total;
    @CsvBindByName(column = "Payment method")
    private String paymentMethod = "";

    @CsvBindByName(column = "Type")
    private String orderType = "delivery";

    @Enumerated(EnumType.STRING)
    private Source source = Source.CSV;

    @Column(name = "tip")
    private Double tip;

    @Column(name = "tax_on_fees")
    private Double taxOnFees = 0.0;

    private Long jsonSourceId = 0L; //used while loading from GlobalOrderJson to use to delete/mark complete the source json record

    @Column(name = "order_text", length = 10000)
    private String orderText;

    public Double getTip() {
        return tip;
    }

    public void setTip(Double tip) {
        this.tip = tip;
    }

    @Column(name = "client_name")
    private String clientName;

    public String getOrderText() {
        return orderText;
    }

    public void setOrderText(String orderText) {
        this.orderText = orderText;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
//Restaurant ID,Restaurant name,Order ID,Subtotal,Delivery fee,Total taxes,Total,Payment method,Fulfillment date (YYYY-MM-DD),Fulfillment time


    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(Double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public Double getTotalTaxes() {
        return totalTaxes;
    }

    public void setTotalTaxes(Double totalTaxes) {
        this.totalTaxes = totalTaxes;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Double getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(Double serviceFee) {
        this.serviceFee = serviceFee;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Long getJsonSourceId() {
        return jsonSourceId;
    }

    public void setJsonSourceId(Long jsonSourceId) {
        this.jsonSourceId = jsonSourceId;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Double getTaxOnFees() {
        return Utility.getInstance().round(taxOnFees,2);
    }

    public void setTaxOnFees(Double taxOnFees) {
        this.taxOnFees = Utility.getInstance().round(taxOnFees,2);
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderId=" + orderId +
                ", restaurantId=" + restaurantId +
                ", subtotal=" + subtotal +
                ", deliveryFee=" + deliveryFee +
                ", serviceFee=" + serviceFee +
                ", totalTaxes=" + totalTaxes +
                ", total=" + total +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", orderType='" + orderType + '\'' +
                ", source=" + source +
                ", tip=" + tip +
                ", taxOnFees=" + taxOnFees +
                ", jsonSourceId=" + jsonSourceId +
                ", orderText='" + orderText + '\'' +
                ", clientName='" + clientName + '\'' +
                '}';
    }
}
