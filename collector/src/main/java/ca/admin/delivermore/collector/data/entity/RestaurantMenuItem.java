package ca.admin.delivermore.collector.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RestaurantMenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "menu_version_id", nullable = false)
    private Long menuVersionId;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "source_item_id")
    private Long sourceItemId;

    @Column(name = "source_category_id")
    private Long sourceCategoryId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "LONGTEXT")
    private String description;

    @Column(name = "base_price")
    private Double basePrice;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "active_begin")
    private String activeBegin;

    @Column(name = "active_end")
    private String activeEnd;

    @Column(name = "active_days")
    private Integer activeDays;

    @Column(name = "out_of_stock")
    private Boolean outOfStock;

    @Column(name = "ingredients", columnDefinition = "LONGTEXT")
    private String ingredients;

    @Column(name = "additives", columnDefinition = "LONGTEXT")
    private String additives;

    @Column(name = "nutritional_values_size")
    private String nutritionalValuesSize;

    @Column(name = "tags_json", columnDefinition = "LONGTEXT")
    private String tagsJson;

    @Column(name = "extras_json", columnDefinition = "LONGTEXT")
    private String extrasJson;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder = 0;

    @Column(name = "image_asset_id")
    private Long imageAssetId;

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

    public Long getSourceItemId() {
        return sourceItemId;
    }

    public void setSourceItemId(Long sourceItemId) {
        this.sourceItemId = sourceItemId;
    }

    public Long getSourceCategoryId() {
        return sourceCategoryId;
    }

    public void setSourceCategoryId(Long sourceCategoryId) {
        this.sourceCategoryId = sourceCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getActiveBegin() {
        return activeBegin;
    }

    public void setActiveBegin(String activeBegin) {
        this.activeBegin = activeBegin;
    }

    public String getActiveEnd() {
        return activeEnd;
    }

    public void setActiveEnd(String activeEnd) {
        this.activeEnd = activeEnd;
    }

    public Integer getActiveDays() {
        return activeDays;
    }

    public void setActiveDays(Integer activeDays) {
        this.activeDays = activeDays;
    }

    public Boolean getOutOfStock() {
        return outOfStock;
    }

    public void setOutOfStock(Boolean outOfStock) {
        this.outOfStock = outOfStock;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getAdditives() {
        return additives;
    }

    public void setAdditives(String additives) {
        this.additives = additives;
    }

    public String getNutritionalValuesSize() {
        return nutritionalValuesSize;
    }

    public void setNutritionalValuesSize(String nutritionalValuesSize) {
        this.nutritionalValuesSize = nutritionalValuesSize;
    }

    public String getTagsJson() {
        return tagsJson;
    }

    public void setTagsJson(String tagsJson) {
        this.tagsJson = tagsJson;
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

    public Long getImageAssetId() {
        return imageAssetId;
    }

    public void setImageAssetId(Long imageAssetId) {
        this.imageAssetId = imageAssetId;
    }
}