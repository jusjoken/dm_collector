package ca.admin.delivermore.collector.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class StagedRestaurantOrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "staged_order_id", nullable = false)
    private Long stagedOrderId;

    @Column(name = "line_number", nullable = false)
    private Integer lineNumber;

    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "selected_size_name")
    private String selectedSizeName;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;

    @Column(name = "line_subtotal", nullable = false)
    private Double lineSubtotal;

    @Column(name = "instructions", length = 2000)
    private String instructions;

    @Column(name = "primary_taxation_category")
    private String primaryTaxationCategory;

    @Column(name = "primary_tax_rate", nullable = false)
    private Double primaryTaxRate;

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

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getSelectedSizeName() {
        return selectedSizeName;
    }

    public void setSelectedSizeName(String selectedSizeName) {
        this.selectedSizeName = selectedSizeName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getLineSubtotal() {
        return lineSubtotal;
    }

    public void setLineSubtotal(Double lineSubtotal) {
        this.lineSubtotal = lineSubtotal;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getPrimaryTaxationCategory() {
        return primaryTaxationCategory;
    }

    public void setPrimaryTaxationCategory(String primaryTaxationCategory) {
        this.primaryTaxationCategory = primaryTaxationCategory;
    }

    public Double getPrimaryTaxRate() {
        return primaryTaxRate;
    }

    public void setPrimaryTaxRate(Double primaryTaxRate) {
        this.primaryTaxRate = primaryTaxRate;
    }
}