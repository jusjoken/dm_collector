package ca.admin.delivermore.collector.data.service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.admin.delivermore.collector.data.entity.RestaurantMenuCategory;
import ca.admin.delivermore.collector.data.entity.RestaurantMenuImageAssociation;
import ca.admin.delivermore.collector.data.entity.RestaurantMenuImageAssociation.ScopeType;
import ca.admin.delivermore.collector.data.entity.RestaurantMenuItem;
import ca.admin.delivermore.collector.data.entity.RestaurantMenuVersion;

@Service
public class RestaurantMenuImageAssociationService {

    private static final String HEADER_KEY = "HEADER";

    private final RestaurantMenuImageAssociationRepository restaurantMenuImageAssociationRepository;
    private final RestaurantMenuVersionRepository restaurantMenuVersionRepository;
    private final RestaurantMenuCategoryRepository restaurantMenuCategoryRepository;
    private final RestaurantMenuItemRepository restaurantMenuItemRepository;

    public RestaurantMenuImageAssociationService(
            RestaurantMenuImageAssociationRepository restaurantMenuImageAssociationRepository,
            RestaurantMenuVersionRepository restaurantMenuVersionRepository,
            RestaurantMenuCategoryRepository restaurantMenuCategoryRepository,
            RestaurantMenuItemRepository restaurantMenuItemRepository) {
        this.restaurantMenuImageAssociationRepository = restaurantMenuImageAssociationRepository;
        this.restaurantMenuVersionRepository = restaurantMenuVersionRepository;
        this.restaurantMenuCategoryRepository = restaurantMenuCategoryRepository;
        this.restaurantMenuItemRepository = restaurantMenuItemRepository;
    }

    @Transactional
    public void applyAssociationsToMenuVersion(RestaurantMenuVersion menuVersion) {
        if (menuVersion == null || menuVersion.getId() == null || menuVersion.getRestaurantId() == null) {
            return;
        }

        Map<String, RestaurantMenuImageAssociation> associationsByKey = new LinkedHashMap<>();
        for (RestaurantMenuImageAssociation association : restaurantMenuImageAssociationRepository
                .findByRestaurantId(menuVersion.getRestaurantId())) {
            associationsByKey.put(association.getMappingKey(), association);
        }

        RestaurantMenuImageAssociation headerAssociation = associationsByKey.get(HEADER_KEY);
        if (headerAssociation != null
                && !equalsLong(menuVersion.getHeaderImageAssetId(), headerAssociation.getImageAssetId())) {
            menuVersion.setHeaderImageAssetId(headerAssociation.getImageAssetId());
            restaurantMenuVersionRepository.save(menuVersion);
        }

        List<RestaurantMenuCategory> categories = restaurantMenuCategoryRepository.findByMenuVersionId(menuVersion.getId());
        boolean categoriesChanged = false;
        for (RestaurantMenuCategory category : categories) {
            RestaurantMenuImageAssociation association = associationsByKey.get(categoryKey(category));
            if (association == null || equalsLong(category.getImageAssetId(), association.getImageAssetId())) {
                continue;
            }
            category.setImageAssetId(association.getImageAssetId());
            categoriesChanged = true;
        }
        if (categoriesChanged) {
            restaurantMenuCategoryRepository.saveAll(categories);
        }

        List<RestaurantMenuItem> items = restaurantMenuItemRepository.findByMenuVersionId(menuVersion.getId());
        boolean itemsChanged = false;
        for (RestaurantMenuItem item : items) {
            RestaurantMenuImageAssociation association = associationsByKey.get(itemKey(item));
            if (association == null || equalsLong(item.getImageAssetId(), association.getImageAssetId())) {
                continue;
            }
            item.setImageAssetId(association.getImageAssetId());
            itemsChanged = true;
        }
        if (itemsChanged) {
            restaurantMenuItemRepository.saveAll(items);
        }
    }

    @Transactional
    public void syncMenuVersionImage(RestaurantMenuVersion menuVersion) {
        if (menuVersion == null || menuVersion.getRestaurantId() == null) {
            return;
        }

        syncAssociation(
                menuVersion.getRestaurantId(),
                ScopeType.HEADER,
                HEADER_KEY,
                menuVersion.getSourceMenuId(),
                null,
                null,
                menuVersion.getHeaderImageAssetId());
    }

    @Transactional
    public void syncCategoryImage(RestaurantMenuCategory category) {
        RestaurantMenuVersion menuVersion = loadVersion(category == null ? null : category.getMenuVersionId());
        if (menuVersion == null || menuVersion.getRestaurantId() == null || category == null) {
            return;
        }

        String mappingKey = categoryKey(category);
        if (mappingKey == null) {
            return;
        }

        syncAssociation(
                menuVersion.getRestaurantId(),
                ScopeType.CATEGORY,
                mappingKey,
                category.getSourceMenuId(),
                category.getSourceCategoryId(),
                null,
                category.getImageAssetId());
    }

    @Transactional
    public void syncItemImage(RestaurantMenuItem item) {
        RestaurantMenuVersion menuVersion = loadVersion(item == null ? null : item.getMenuVersionId());
        if (menuVersion == null || menuVersion.getRestaurantId() == null || item == null) {
            return;
        }

        String mappingKey = itemKey(item);
        if (mappingKey == null) {
            return;
        }

        syncAssociation(
                menuVersion.getRestaurantId(),
                ScopeType.ITEM,
                mappingKey,
                null,
                item.getSourceCategoryId(),
                item.getSourceItemId(),
                item.getImageAssetId());
    }

    private void syncAssociation(
            Long restaurantId,
            ScopeType scopeType,
            String mappingKey,
            Long sourceMenuId,
            Long sourceCategoryId,
            Long sourceItemId,
            Long imageAssetId) {
        if (restaurantId == null || mappingKey == null || mappingKey.isBlank()) {
            return;
        }

        if (imageAssetId == null) {
            restaurantMenuImageAssociationRepository.deleteByRestaurantIdAndMappingKey(restaurantId, mappingKey);
            return;
        }

        RestaurantMenuImageAssociation association = restaurantMenuImageAssociationRepository
                .findByRestaurantIdAndMappingKey(restaurantId, mappingKey)
                .orElseGet(RestaurantMenuImageAssociation::new);
        association.setRestaurantId(restaurantId);
        association.setScopeType(scopeType);
        association.setMappingKey(mappingKey);
        association.setSourceMenuId(sourceMenuId);
        association.setSourceCategoryId(sourceCategoryId);
        association.setSourceItemId(sourceItemId);
        association.setImageAssetId(imageAssetId);
        association.setUpdatedAt(LocalDateTime.now());
        restaurantMenuImageAssociationRepository.save(association);
    }

    private RestaurantMenuVersion loadVersion(Long menuVersionId) {
        if (menuVersionId == null) {
            return null;
        }
        return restaurantMenuVersionRepository.findById(menuVersionId).orElse(null);
    }

    private String categoryKey(RestaurantMenuCategory category) {
        if (category == null) {
            return null;
        }
        if (category.getSourceCategoryId() != null) {
            return "CATEGORY:SRC:" + category.getSourceCategoryId();
        }
        String normalizedName = normalize(category.getName());
        return normalizedName == null ? null : "CATEGORY:NAME:" + normalizedName;
    }

    private String itemKey(RestaurantMenuItem item) {
        if (item == null) {
            return null;
        }
        if (item.getSourceItemId() != null) {
            return "ITEM:SRC:" + item.getSourceItemId();
        }
        String normalizedName = normalize(item.getName());
        if (normalizedName == null) {
            return null;
        }
        String categoryPart = item.getSourceCategoryId() == null ? "unknown" : String.valueOf(item.getSourceCategoryId());
        return "ITEM:CATEGORY:" + categoryPart + ":NAME:" + normalizedName;
    }

    private String normalize(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim().toLowerCase(Locale.ENGLISH);
        if (trimmed.isEmpty()) {
            return null;
        }
        return trimmed.replaceAll("\\s+", " ");
    }

    private boolean equalsLong(Long left, Long right) {
        if (left == null) {
            return right == null;
        }
        return left.equals(right);
    }
}