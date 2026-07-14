package ca.admin.delivermore.collector.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RestaurantMenuOption {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "menu_version_id", nullable = false)
    private Long menuVersionId;

    @Column(name = "option_group_id", nullable = false)
    private Long optionGroupId;

    @Column(name = "source_option_id")
    private Long sourceOptionId;

    @Column(name = "source_group_id")
    private Long sourceGroupId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "default_option")
    private Boolean defaultOption;

    @Column(name = "extras_json", columnDefinition = "LONGTEXT")
    private String extrasJson;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMenuVersionId() {
        return menuVersionId;
    }

    public void setMenuVersionId(Long menuVersionId) {
        this.menuVersionId = menuVersionId;
    }

    public Long getOptionGroupId() {
        return optionGroupId;
    }

    public void setOptionGroupId(Long optionGroupId) {
        this.optionGroupId = optionGroupId;
    }

    public Long getSourceOptionId() {
        return sourceOptionId;
    }

    public void setSourceOptionId(Long sourceOptionId) {
        this.sourceOptionId = sourceOptionId;
    }

    public Long getSourceGroupId() {
        return sourceGroupId;
    }

    public void setSourceGroupId(Long sourceGroupId) {
        this.sourceGroupId = sourceGroupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getDefaultOption() {
        return defaultOption;
    }

    public void setDefaultOption(Boolean defaultOption) {
        this.defaultOption = defaultOption;
    }

    public String getExtrasJson() {
        return extrasJson;
    }

    public void setExtrasJson(String extrasJson) {
        this.extrasJson = extrasJson;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
}