package ca.admin.delivermore.collector.data.service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.admin.delivermore.collector.data.entity.RestaurantMenuItem;
import ca.admin.delivermore.collector.data.entity.RestaurantMenuItemSpotlightPreference;
import ca.admin.delivermore.collector.data.entity.RestaurantMenuVersion;

@Service
public class RestaurantMenuItemSpotlightPreferenceService {

    private final RestaurantMenuItemSpotlightPreferenceRepository restaurantMenuItemSpotlightPreferenceRepository;
    private final RestaurantMenuVersionRepository restaurantMenuVersionRepository;
    private final RestaurantMenuItemRepository restaurantMenuItemRepository;

    public RestaurantMenuItemSpotlightPreferenceService(
            RestaurantMenuItemSpotlightPreferenceRepository restaurantMenuItemSpotlightPreferenceRepository,
            RestaurantMenuVersionRepository restaurantMenuVersionRepository,
            RestaurantMenuItemRepository restaurantMenuItemRepository) {
        this.restaurantMenuItemSpotlightPreferenceRepository = restaurantMenuItemSpotlightPreferenceRepository;
        this.restaurantMenuVersionRepository = restaurantMenuVersionRepository;
        this.restaurantMenuItemRepository = restaurantMenuItemRepository;
    }

    @Transactional
    public void applyPreferencesToMenuVersion(RestaurantMenuVersion menuVersion) {
        if (menuVersion == null || menuVersion.getId() == null || menuVersion.getRestaurantId() == null) {
            return;
        }

        Map<String, RestaurantMenuItemSpotlightPreference> preferencesByKey = new LinkedHashMap<>();
        for (RestaurantMenuItemSpotlightPreference preference : restaurantMenuItemSpotlightPreferenceRepository
                .findByRestaurantIdAndPreferredTrue(menuVersion.getRestaurantId())) {
            preferencesByKey.put(preference.getMappingKey(), preference);
        }

        List<RestaurantMenuItem> items = restaurantMenuItemRepository.findByMenuVersionId(menuVersion.getId());
        boolean changed = false;
        for (RestaurantMenuItem item : items) {
            boolean preferred = preferencesByKey.containsKey(itemKey(item));
            if (Boolean.valueOf(preferred).equals(item.getSpotlightPreferred())) {
                continue;
            }
            item.setSpotlightPreferred(preferred);
            changed = true;
        }

        if (changed) {
            restaurantMenuItemRepository.saveAll(items);
        }
    }

    @Transactional
    public void syncItemPreference(RestaurantMenuItem item) {
        RestaurantMenuVersion menuVersion = loadVersion(item == null ? null : item.getMenuVersionId());
        if (menuVersion == null || menuVersion.getRestaurantId() == null || item == null) {
            return;
        }

        String mappingKey = itemKey(item);
        if (mappingKey == null) {
            return;
        }

        if (!Boolean.TRUE.equals(item.getSpotlightPreferred())) {
            restaurantMenuItemSpotlightPreferenceRepository.deleteByRestaurantIdAndMappingKey(menuVersion.getRestaurantId(), mappingKey);
            return;
        }

        RestaurantMenuItemSpotlightPreference preference = restaurantMenuItemSpotlightPreferenceRepository
                .findByRestaurantIdAndMappingKey(menuVersion.getRestaurantId(), mappingKey)
                .orElseGet(RestaurantMenuItemSpotlightPreference::new);
        preference.setRestaurantId(menuVersion.getRestaurantId());
        preference.setMappingKey(mappingKey);
        preference.setSourceCategoryId(item.getSourceCategoryId());
        preference.setSourceItemId(item.getSourceItemId());
        preference.setPreferred(Boolean.TRUE);
        preference.setUpdatedAt(LocalDateTime.now());
        restaurantMenuItemSpotlightPreferenceRepository.save(preference);
    }

    private RestaurantMenuVersion loadVersion(Long menuVersionId) {
        if (menuVersionId == null) {
            return null;
        }
        return restaurantMenuVersionRepository.findById(menuVersionId).orElse(null);
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
}