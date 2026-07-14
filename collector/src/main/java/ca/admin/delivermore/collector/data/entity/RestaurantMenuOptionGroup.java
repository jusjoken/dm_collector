package ca.admin.delivermore.collector.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RestaurantMenuOptionGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "menu_version_id", nullable = false)
    private Long menuVersionId;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "item_size_id")
    private Long itemSizeId;

    @Column(name = "source_group_id")
    private Long sourceGroupId;

    @Column(name = "source_menu_id")
    private Long sourceMenuId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "required_selection")
    private Boolean requiredSelection;

    @Column(name = "allow_quantity")
    private Boolean allowQuantity;

    @Column(name = "force_min")
    private Integer forceMin;

    @Column(name = "force_max")
    private Integer forceMax;

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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getItemSizeId() {
        return itemSizeId;
    }

    public void setItemSizeId(Long itemSizeId) {
        this.itemSizeId = itemSizeId;
    }

    public Long getSourceGroupId() {
        return sourceGroupId;
    }

    public void setSourceGroupId(Long sourceGroupId) {
        this.sourceGroupId = sourceGroupId;
    }

    public Long getSourceMenuId() {
        return sourceMenuId;
    }

    public void setSourceMenuId(Long sourceMenuId) {
        this.sourceMenuId = sourceMenuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getRequiredSelection() {
        return requiredSelection;
    }

    public void setRequiredSelection(Boolean requiredSelection) {
        this.requiredSelection = requiredSelection;
    }

    public Boolean getAllowQuantity() {
        return allowQuantity;
    }

    public void setAllowQuantity(Boolean allowQuantity) {
        this.allowQuantity = allowQuantity;
    }

    public Integer getForceMin() {
        return forceMin;
    }

    public void setForceMin(Integer forceMin) {
        this.forceMin = forceMin;
    }

    public Integer getForceMax() {
        return forceMax;
    }

    public void setForceMax(Integer forceMax) {
        this.forceMax = forceMax;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
}