package ca.admin.delivermore.collector.data.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
        name = "restaurant_menu_image_association",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_restaurant_menu_image_association_restaurant_key",
                columnNames = { "restaurant_id", "mapping_key" }))
public class RestaurantMenuImageAssociation {

    public enum ScopeType {
        HEADER,
        CATEGORY,
        ITEM
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;

    @Enumerated(EnumType.STRING)
    @Column(name = "scope_type", nullable = false)
    private ScopeType scopeType;

    @Column(name = "mapping_key", nullable = false)
    private String mappingKey;

    @Column(name = "source_menu_id")
    private Long sourceMenuId;

    @Column(name = "source_category_id")
    private Long sourceCategoryId;

    @Column(name = "source_item_id")
    private Long sourceItemId;

    @Column(name = "image_asset_id", nullable = false)
    private Long imageAssetId;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

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

    public ScopeType getScopeType() {
        return scopeType;
    }

    public void setScopeType(ScopeType scopeType) {
        this.scopeType = scopeType;
    }

    public String getMappingKey() {
        return mappingKey;
    }

    public void setMappingKey(String mappingKey) {
        this.mappingKey = mappingKey;
    }

    public Long getSourceMenuId() {
        return sourceMenuId;
    }

    public void setSourceMenuId(Long sourceMenuId) {
        this.sourceMenuId = sourceMenuId;
    }

    public Long getSourceCategoryId() {
        return sourceCategoryId;
    }

    public void setSourceCategoryId(Long sourceCategoryId) {
        this.sourceCategoryId = sourceCategoryId;
    }

    public Long getSourceItemId() {
        return sourceItemId;
    }

    public void setSourceItemId(Long sourceItemId) {
        this.sourceItemId = sourceItemId;
    }

    public Long getImageAssetId() {
        return imageAssetId;
    }

    public void setImageAssetId(Long imageAssetId) {
        this.imageAssetId = imageAssetId;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}