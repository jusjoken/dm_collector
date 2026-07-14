package ca.admin.delivermore.collector.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RestaurantMenuCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "menu_version_id", nullable = false)
    private Long menuVersionId;

    @Column(name = "source_category_id")
    private Long sourceCategoryId;

    @Column(name = "source_menu_id")
    private Long sourceMenuId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "active_begin")
    private String activeBegin;

    @Column(name = "active_end")
    private String activeEnd;

    @Column(name = "active_days")
    private Integer activeDays;

    @Column(name = "picture_id")
    private Long pictureId;

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

    public Long getSourceCategoryId() {
        return sourceCategoryId;
    }

    public void setSourceCategoryId(Long sourceCategoryId) {
        this.sourceCategoryId = sourceCategoryId;
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

    public Long getPictureId() {
        return pictureId;
    }

    public void setPictureId(Long pictureId) {
        this.pictureId = pictureId;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
}