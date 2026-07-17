package ca.admin.delivermore.collector.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class StagedRestaurantOrderTaxRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "staged_order_id", nullable = false)
    private Long stagedOrderId;

    @Column(name = "taxation_category", nullable = false)
    private String taxationCategory;

    @Column(name = "tax_scope", nullable = false)
    private String taxScope = "CATEGORY";

    @Column(name = "tax_name")
    private String taxName;

    @Column(name = "rate_percent", nullable = false)
    private Double ratePercent;

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

    public String getTaxationCategory() {
        return taxationCategory;
    }

    public void setTaxationCategory(String taxationCategory) {
        this.taxationCategory = taxationCategory;
    }

    public String getTaxScope() {
        return taxScope;
    }

    public void setTaxScope(String taxScope) {
        this.taxScope = taxScope;
    }

    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    public Double getRatePercent() {
        return ratePercent;
    }

    public void setRatePercent(Double ratePercent) {
        this.ratePercent = ratePercent;
    }
}