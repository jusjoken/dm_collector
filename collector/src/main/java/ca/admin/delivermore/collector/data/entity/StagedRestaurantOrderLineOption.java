package ca.admin.delivermore.collector.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class StagedRestaurantOrderLineOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "staged_order_id", nullable = false)
    private Long stagedOrderId;

    @Column(name = "staged_order_line_id", nullable = false)
    private Long stagedOrderLineId;

    @Column(name = "line_number", nullable = false)
    private Integer lineNumber;

    @Column(name = "group_name", nullable = false)
    private String groupName;

    @Column(name = "option_name", nullable = false)
    private String optionName;

    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "option_subtotal", nullable = false)
    private Double optionSubtotal;

    @Column(name = "taxation_category")
    private String taxationCategory;

    @Column(name = "taxation_rate", nullable = false)
    private Double taxationRate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStagedOrderId() {
        return stagedOrderId;
    }

    public void setStagedOrderId(Long stagedOrderId) {
        this.stagedOrderId = stagedOrderId;
    }

    public Long getStagedOrderLineId() {
        return stagedOrderLineId;
    }

    public void setStagedOrderLineId(Long stagedOrderLineId) {
        this.stagedOrderLineId = stagedOrderLineId;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getOptionSubtotal() {
        return optionSubtotal;
    }

    public void setOptionSubtotal(Double optionSubtotal) {
        this.optionSubtotal = optionSubtotal;
    }

    public String getTaxationCategory() {
        return taxationCategory;
    }

    public void setTaxationCategory(String taxationCategory) {
        this.taxationCategory = taxationCategory;
    }

    public Double getTaxationRate() {
        return taxationRate;
    }

    public void setTaxationRate(Double taxationRate) {
        this.taxationRate = taxationRate;
    }
}