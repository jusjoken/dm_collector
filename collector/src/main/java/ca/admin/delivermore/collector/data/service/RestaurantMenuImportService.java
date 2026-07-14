package ca.admin.delivermore.collector.data.service;

import java.io.UncheckedIOException;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ca.admin.delivermore.collector.data.entity.RestaurantMenuCategory;
import ca.admin.delivermore.collector.data.entity.RestaurantMenuItem;
import ca.admin.delivermore.collector.data.entity.RestaurantMenuItemSize;
import ca.admin.delivermore.collector.data.entity.RestaurantMenuOption;
import ca.admin.delivermore.collector.data.entity.RestaurantMenuOptionGroup;
import ca.admin.delivermore.collector.data.entity.RestaurantMenuVersion;
import ca.admin.delivermore.collector.data.menu.RestaurantMenuImportPayload;

@Service
public class RestaurantMenuImportService {

    private static final Logger log = LoggerFactory.getLogger(RestaurantMenuImportService.class);
    private static final int MAX_VERSIONS_PER_RESTAURANT = 3;

    private final RestaurantMenuVersionRepository restaurantMenuVersionRepository;
    private final RestaurantMenuCategoryRepository restaurantMenuCategoryRepository;
    private final RestaurantMenuItemRepository restaurantMenuItemRepository;
    private final RestaurantMenuItemSizeRepository restaurantMenuItemSizeRepository;
    private final RestaurantMenuOptionGroupRepository restaurantMenuOptionGroupRepository;
    private final RestaurantMenuOptionRepository restaurantMenuOptionRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RestaurantMenuImportService(
            RestaurantMenuVersionRepository restaurantMenuVersionRepository,
            RestaurantMenuCategoryRepository restaurantMenuCategoryRepository,
            RestaurantMenuItemRepository restaurantMenuItemRepository,
            RestaurantMenuItemSizeRepository restaurantMenuItemSizeRepository,
            RestaurantMenuOptionGroupRepository restaurantMenuOptionGroupRepository,
            RestaurantMenuOptionRepository restaurantMenuOptionRepository) {
        this.restaurantMenuVersionRepository = restaurantMenuVersionRepository;
        this.restaurantMenuCategoryRepository = restaurantMenuCategoryRepository;
        this.restaurantMenuItemRepository = restaurantMenuItemRepository;
        this.restaurantMenuItemSizeRepository = restaurantMenuItemSizeRepository;
        this.restaurantMenuOptionGroupRepository = restaurantMenuOptionGroupRepository;
        this.restaurantMenuOptionRepository = restaurantMenuOptionRepository;
    }

    @Transactional
    public void importMenu(RestaurantMenuImportPayload payload) {
        JsonNode menuRoot = readMenuRoot(payload.getRawJson());
        RestaurantMenuVersion currentActiveVersion = restaurantMenuVersionRepository.findByRestaurantIdAndActiveTrue(payload.getRestaurantId());
        RestaurantMenuVersion latestVersion = restaurantMenuVersionRepository.findTopByRestaurantIdOrderByVersionNumberDesc(payload.getRestaurantId());

        if(currentActiveVersion != null){
            currentActiveVersion.setActive(Boolean.FALSE);
            restaurantMenuVersionRepository.save(currentActiveVersion);
        }

        RestaurantMenuVersion menuVersion = new RestaurantMenuVersion();
        menuVersion.setRestaurantId(payload.getRestaurantId());
        menuVersion.setSourceMenuId(getLongValue(menuRoot, "id"));
        menuVersion.setCurrency(getTextValue(menuRoot, "currency"));
        menuVersion.setVersionNumber(latestVersion == null ? 1 : latestVersion.getVersionNumber() + 1);
        menuVersion.setFetchedAt(LocalDateTime.now());
        menuVersion.setRawJson(payload.getRawJson());
        menuVersion.setActive(Boolean.TRUE);
        menuVersion = restaurantMenuVersionRepository.save(menuVersion);

        persistCategories(menuVersion.getId(), menuRoot.path("categories"), menuVersion.getSourceMenuId());
        trimOldVersions(payload.getRestaurantId());
        log.info("importMenu: imported menu version {} for restaurant {} ({})", menuVersion.getVersionNumber(), payload.getRestaurantName(), payload.getRestaurantId());
    }

    private void persistCategories(Long menuVersionId, JsonNode categoriesNode, Long sourceMenuId) {
        if(!categoriesNode.isArray()){
            return;
        }

        int categoryOrder = 0;
        for (JsonNode categoryNode : categoriesNode) {
            RestaurantMenuCategory category = new RestaurantMenuCategory();
            category.setMenuVersionId(menuVersionId);
            category.setSourceMenuId(sourceMenuId);
            category.setSourceCategoryId(getLongValue(categoryNode, "id"));
            category.setName(getTextValue(categoryNode, "name"));
            category.setActive(getBooleanValue(categoryNode, "active"));
            category.setActiveBegin(getTextValue(categoryNode, "active_begin"));
            category.setActiveEnd(getTextValue(categoryNode, "active_end"));
            category.setActiveDays(getIntegerValue(categoryNode, "active_days"));
            category.setPictureId(getLongValue(categoryNode, "picture_id"));
            category.setDisplayOrder(categoryOrder++);
            category = restaurantMenuCategoryRepository.save(category);

            persistOptionGroups(menuVersionId, category.getId(), null, null, sourceMenuId, categoryNode.path("groups"));
            persistItems(menuVersionId, category, categoryNode.path("items"));
        }
    }

    private void persistItems(Long menuVersionId, RestaurantMenuCategory category, JsonNode itemsNode) {
        if(!itemsNode.isArray()){
            return;
        }

        int itemOrder = 0;
        for (JsonNode itemNode : itemsNode) {
            RestaurantMenuItem item = new RestaurantMenuItem();
            item.setMenuVersionId(menuVersionId);
            item.setCategoryId(category.getId());
            item.setSourceCategoryId(category.getSourceCategoryId());
            item.setSourceItemId(getLongValue(itemNode, "id"));
            item.setName(getTextValue(itemNode, "name"));
            item.setDescription(getTextValue(itemNode, "description"));
            item.setBasePrice(getDoubleValue(itemNode, "price"));
            item.setActive(getBooleanValue(itemNode, "active"));
            item.setActiveBegin(getTextValue(itemNode, "active_begin"));
            item.setActiveEnd(getTextValue(itemNode, "active_end"));
            item.setActiveDays(getIntegerValue(itemNode, "active_days"));
            item.setTagsJson(writeJson(itemNode.path("tags")));
            item.setExtrasJson(writeJson(itemNode.path("extras")));
            item.setDisplayOrder(itemOrder++);
            item = restaurantMenuItemRepository.save(item);

            persistOptionGroups(menuVersionId, null, item.getId(), null, category.getSourceMenuId(), itemNode.path("groups"));
            persistItemSizes(menuVersionId, item, category.getSourceMenuId(), itemNode.path("sizes"));
        }
    }

    private void persistItemSizes(Long menuVersionId, RestaurantMenuItem item, Long sourceMenuId, JsonNode sizesNode) {
        if(!sizesNode.isArray()){
            return;
        }

        int sizeOrder = 0;
        for (JsonNode sizeNode : sizesNode) {
            RestaurantMenuItemSize itemSize = new RestaurantMenuItemSize();
            itemSize.setMenuVersionId(menuVersionId);
            itemSize.setItemId(item.getId());
            itemSize.setSourceItemId(item.getSourceItemId());
            itemSize.setSourceSizeId(getLongValue(sizeNode, "id"));
            itemSize.setName(getTextValue(sizeNode, "name"));
            itemSize.setPrice(getDoubleValue(sizeNode, "price"));
            itemSize.setDefaultSize(getBooleanValue(sizeNode, "default"));
            itemSize.setExtrasJson(writeJson(sizeNode.path("extras")));
            itemSize.setDisplayOrder(sizeOrder++);
            itemSize = restaurantMenuItemSizeRepository.save(itemSize);

            persistOptionGroups(menuVersionId, null, null, itemSize.getId(), sourceMenuId, sizeNode.path("groups"));
        }
    }

    private void persistOptionGroups(Long menuVersionId, Long categoryId, Long itemId, Long itemSizeId, Long sourceMenuId, JsonNode groupsNode) {
        if(!groupsNode.isArray()){
            return;
        }

        int groupOrder = 0;
        for (JsonNode groupNode : groupsNode) {
            RestaurantMenuOptionGroup optionGroup = new RestaurantMenuOptionGroup();
            optionGroup.setMenuVersionId(menuVersionId);
            optionGroup.setCategoryId(categoryId);
            optionGroup.setItemId(itemId);
            optionGroup.setItemSizeId(itemSizeId);
            optionGroup.setSourceGroupId(getLongValue(groupNode, "id"));
            optionGroup.setSourceMenuId(sourceMenuId);
            optionGroup.setName(getTextValue(groupNode, "name"));
            optionGroup.setRequiredSelection(getBooleanValue(groupNode, "required"));
            optionGroup.setAllowQuantity(getBooleanValue(groupNode, "allow_quantity"));
            optionGroup.setForceMin(getIntegerValue(groupNode, "force_min"));
            optionGroup.setForceMax(getIntegerValue(groupNode, "force_max"));
            optionGroup.setDisplayOrder(groupOrder++);
            optionGroup = restaurantMenuOptionGroupRepository.save(optionGroup);

            persistOptions(menuVersionId, optionGroup, groupNode.path("options"));
        }
    }

    private void persistOptions(Long menuVersionId, RestaurantMenuOptionGroup optionGroup, JsonNode optionsNode) {
        if(!optionsNode.isArray()){
            return;
        }

        int optionOrder = 0;
        for (JsonNode optionNode : optionsNode) {
            RestaurantMenuOption option = new RestaurantMenuOption();
            option.setMenuVersionId(menuVersionId);
            option.setOptionGroupId(optionGroup.getId());
            option.setSourceGroupId(optionGroup.getSourceGroupId());
            option.setSourceOptionId(getLongValue(optionNode, "id"));
            option.setName(getTextValue(optionNode, "name"));
            option.setPrice(getDoubleValue(optionNode, "price"));
            option.setDefaultOption(getBooleanValue(optionNode, "default"));
            option.setExtrasJson(writeJson(optionNode.path("extras")));
            option.setDisplayOrder(optionOrder++);
            restaurantMenuOptionRepository.save(option);
        }
    }

    private void trimOldVersions(Long restaurantId) {
        List<RestaurantMenuVersion> versions = restaurantMenuVersionRepository.findByRestaurantIdOrderByVersionNumberDesc(restaurantId);
        if(versions.size() <= MAX_VERSIONS_PER_RESTAURANT){
            return;
        }

        for (int index = MAX_VERSIONS_PER_RESTAURANT; index < versions.size(); index++) {
            deleteMenuVersion(versions.get(index).getId());
        }
    }

    private void deleteMenuVersion(Long menuVersionId) {
        restaurantMenuOptionRepository.deleteByMenuVersionId(menuVersionId);
        restaurantMenuOptionGroupRepository.deleteByMenuVersionId(menuVersionId);
        restaurantMenuItemSizeRepository.deleteByMenuVersionId(menuVersionId);
        restaurantMenuItemRepository.deleteByMenuVersionId(menuVersionId);
        restaurantMenuCategoryRepository.deleteByMenuVersionId(menuVersionId);
        restaurantMenuVersionRepository.deleteById(menuVersionId);
    }

    private JsonNode readMenuRoot(String rawJson) {
        try {
            return objectMapper.readTree(rawJson);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Unable to parse menu JSON", e);
        }
    }

    private String writeJson(JsonNode node) {
        if(node == null || node.isMissingNode() || node.isNull()){
            return null;
        }
        try {
            return objectMapper.writeValueAsString(node);
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }

    private String getTextValue(JsonNode node, String fieldName) {
        JsonNode value = node.path(fieldName);
        if(value.isMissingNode() || value.isNull()){
            return null;
        }
        return value.asText();
    }

    private Long getLongValue(JsonNode node, String fieldName) {
        JsonNode value = node.path(fieldName);
        if(value.isMissingNode() || value.isNull()){
            return null;
        }
        return value.asLong();
    }

    private Integer getIntegerValue(JsonNode node, String fieldName) {
        JsonNode value = node.path(fieldName);
        if(value.isMissingNode() || value.isNull()){
            return null;
        }
        return value.asInt();
    }

    private Double getDoubleValue(JsonNode node, String fieldName) {
        JsonNode value = node.path(fieldName);
        if(value.isMissingNode() || value.isNull()){
            return null;
        }
        return value.asDouble();
    }

    private Boolean getBooleanValue(JsonNode node, String fieldName) {
        JsonNode value = node.path(fieldName);
        if(value.isMissingNode() || value.isNull()){
            return null;
        }
        return value.asBoolean();
    }
}