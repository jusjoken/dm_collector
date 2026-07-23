package ca.admin.delivermore.collector.data.service;

import java.io.UncheckedIOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ca.admin.delivermore.collector.data.entity.RestaurantMenuCategory;
import ca.admin.delivermore.collector.data.entity.RestaurantMenuItem;
import ca.admin.delivermore.collector.data.entity.RestaurantMenuItemAllergen;
import ca.admin.delivermore.collector.data.entity.RestaurantMenuItemNutrition;
import ca.admin.delivermore.collector.data.entity.RestaurantMenuItemSize;
import ca.admin.delivermore.collector.data.entity.RestaurantMenuItemTag;
import ca.admin.delivermore.collector.data.entity.RestaurantMenuOption;
import ca.admin.delivermore.collector.data.entity.RestaurantMenuOptionAllergen;
import ca.admin.delivermore.collector.data.entity.RestaurantMenuOptionGroup;
import ca.admin.delivermore.collector.data.entity.RestaurantMenuOptionNutrition;
import ca.admin.delivermore.collector.data.entity.RestaurantMenuOptionTag;
import ca.admin.delivermore.collector.data.entity.RestaurantMenuVersion;
import ca.admin.delivermore.collector.data.entity.RestaurantMenuVersion.WorkflowStatus;
import ca.admin.delivermore.collector.data.entity.SettingEntity;
import ca.admin.delivermore.collector.data.menu.RestaurantMenuImportPayload;

@Service
public class RestaurantMenuImportService {

    private static final Logger log = LoggerFactory.getLogger(RestaurantMenuImportService.class);
    private static final int MAX_VERSIONS_PER_RESTAURANT = 3;
    private static final String MENU_EDITOR_SETTINGS_SECTION = "menu_editor";
    private static final String ITEM_TAG_OPTIONS_SETTING = "item_tag_options";
    private static final String ITEM_ALLERGEN_OPTIONS_SETTING = "item_allergen_options";
    private static final String OPTION_GROUP_MAJOR_GROUP_SETTING_PREFIX = "option_group_major_group_";
    private static final String OPTION_GROUP_TAXATION_CATEGORY_SETTING_PREFIX = "option_group_taxation_category_";
    private static final String DEFAULT_IMPORTED_MAJOR_GROUP = "Food";
    private static final String DEFAULT_IMPORTED_TAXATION_CATEGORY = "Food";

    private final RestaurantMenuVersionRepository restaurantMenuVersionRepository;
    private final RestaurantMenuCategoryRepository restaurantMenuCategoryRepository;
    private final RestaurantMenuItemRepository restaurantMenuItemRepository;
    private final RestaurantMenuItemTagRepository restaurantMenuItemTagRepository;
    private final RestaurantMenuItemAllergenRepository restaurantMenuItemAllergenRepository;
    private final RestaurantMenuItemNutritionRepository restaurantMenuItemNutritionRepository;
    private final RestaurantMenuItemSizeRepository restaurantMenuItemSizeRepository;
    private final RestaurantMenuOptionGroupRepository restaurantMenuOptionGroupRepository;
    private final RestaurantMenuOptionRepository restaurantMenuOptionRepository;
    private final RestaurantMenuOptionTagRepository restaurantMenuOptionTagRepository;
    private final RestaurantMenuOptionAllergenRepository restaurantMenuOptionAllergenRepository;
    private final RestaurantMenuOptionNutritionRepository restaurantMenuOptionNutritionRepository;
    private final SettingRepository settingRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RestaurantMenuImportService(
            RestaurantMenuVersionRepository restaurantMenuVersionRepository,
            RestaurantMenuCategoryRepository restaurantMenuCategoryRepository,
            RestaurantMenuItemRepository restaurantMenuItemRepository,
            RestaurantMenuItemTagRepository restaurantMenuItemTagRepository,
            RestaurantMenuItemAllergenRepository restaurantMenuItemAllergenRepository,
            RestaurantMenuItemNutritionRepository restaurantMenuItemNutritionRepository,
            RestaurantMenuItemSizeRepository restaurantMenuItemSizeRepository,
            RestaurantMenuOptionGroupRepository restaurantMenuOptionGroupRepository,
            RestaurantMenuOptionRepository restaurantMenuOptionRepository,
            RestaurantMenuOptionTagRepository restaurantMenuOptionTagRepository,
            RestaurantMenuOptionAllergenRepository restaurantMenuOptionAllergenRepository,
            RestaurantMenuOptionNutritionRepository restaurantMenuOptionNutritionRepository,
            SettingRepository settingRepository) {
        this.restaurantMenuVersionRepository = restaurantMenuVersionRepository;
        this.restaurantMenuCategoryRepository = restaurantMenuCategoryRepository;
        this.restaurantMenuItemRepository = restaurantMenuItemRepository;
        this.restaurantMenuItemTagRepository = restaurantMenuItemTagRepository;
        this.restaurantMenuItemAllergenRepository = restaurantMenuItemAllergenRepository;
        this.restaurantMenuItemNutritionRepository = restaurantMenuItemNutritionRepository;
        this.restaurantMenuItemSizeRepository = restaurantMenuItemSizeRepository;
        this.restaurantMenuOptionGroupRepository = restaurantMenuOptionGroupRepository;
        this.restaurantMenuOptionRepository = restaurantMenuOptionRepository;
        this.restaurantMenuOptionTagRepository = restaurantMenuOptionTagRepository;
        this.restaurantMenuOptionAllergenRepository = restaurantMenuOptionAllergenRepository;
        this.restaurantMenuOptionNutritionRepository = restaurantMenuOptionNutritionRepository;
        this.settingRepository = settingRepository;
    }

    @Transactional
    public boolean importMenu(RestaurantMenuImportPayload payload) {
        if(isPullLocked(payload.getRestaurantId())){
            log.info("importMenu: pull skipped for restaurant {} ({}) because a published menu exists", payload.getRestaurantName(), payload.getRestaurantId());
            return false;
        }

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
        menuVersion.setWorkflowStatus(WorkflowStatus.PULLED);
        menuVersion = restaurantMenuVersionRepository.save(menuVersion);

        Set<String> discoveredItemTags = new LinkedHashSet<>();
        Set<String> discoveredItemAllergens = new LinkedHashSet<>();
        persistCategories(
            menuVersion.getId(),
            menuRoot.path("categories"),
            menuVersion.getSourceMenuId(),
            discoveredItemTags,
            discoveredItemAllergens);
        mergeMenuEditorLookupValues(ITEM_TAG_OPTIONS_SETTING, discoveredItemTags);
        mergeMenuEditorLookupValues(ITEM_ALLERGEN_OPTIONS_SETTING, discoveredItemAllergens);
        trimOldVersions(payload.getRestaurantId());
        log.info("importMenu: imported menu version {} for restaurant {} ({})", menuVersion.getVersionNumber(), payload.getRestaurantName(), payload.getRestaurantId());
        return true;
    }

    @Transactional
    public RestaurantMenuVersion createDraftFromLatestPulledVersion(Long restaurantId) {
        if(isPullLocked(restaurantId)){
            throw new IllegalStateException("A published menu exists for restaurant id:" + restaurantId + ". Reset menu pulls before creating a new draft.");
        }

        if(restaurantMenuVersionRepository.existsByRestaurantIdAndWorkflowStatus(restaurantId, WorkflowStatus.DRAFT)){
            throw new IllegalStateException("A draft menu already exists for restaurant id:" + restaurantId);
        }

        RestaurantMenuVersion latestPulledVersion = restaurantMenuVersionRepository
                .findTopByRestaurantIdAndWorkflowStatusOrderByVersionNumberDesc(restaurantId, WorkflowStatus.PULLED);

        if(latestPulledVersion == null){
            throw new IllegalStateException("No pulled menu version exists for restaurant id:" + restaurantId);
        }

        JsonNode menuRoot = readMenuRoot(latestPulledVersion.getRawJson());
        RestaurantMenuVersion latestVersion = restaurantMenuVersionRepository.findTopByRestaurantIdOrderByVersionNumberDesc(restaurantId);

        RestaurantMenuVersion draftVersion = new RestaurantMenuVersion();
        draftVersion.setRestaurantId(restaurantId);
        draftVersion.setSourceMenuId(getLongValue(menuRoot, "id"));
        draftVersion.setCurrency(getTextValue(menuRoot, "currency"));
        draftVersion.setVersionNumber(latestVersion == null ? 1 : latestVersion.getVersionNumber() + 1);
        draftVersion.setFetchedAt(LocalDateTime.now());
        draftVersion.setRawJson(latestPulledVersion.getRawJson());
        draftVersion.setActive(Boolean.FALSE);
        draftVersion.setWorkflowStatus(WorkflowStatus.DRAFT);
        draftVersion = restaurantMenuVersionRepository.save(draftVersion);

        persistCategories(
            draftVersion.getId(),
            menuRoot.path("categories"),
            draftVersion.getSourceMenuId(),
            new LinkedHashSet<>(),
            new LinkedHashSet<>());
        trimOldVersions(restaurantId);
        log.info("createDraftFromLatestPulledVersion: created draft version {} for restaurant {}", draftVersion.getVersionNumber(), restaurantId);
        return draftVersion;
    }

    @Transactional
    public RestaurantMenuVersion publishDraftVersion(Long restaurantId, Long draftVersionId) {
        RestaurantMenuVersion draftVersion = restaurantMenuVersionRepository.findByIdAndRestaurantId(draftVersionId, restaurantId);
        if(draftVersion == null){
            throw new IllegalStateException("Draft version not found for restaurant id:" + restaurantId + " draft id:" + draftVersionId);
        }
        if(draftVersion.getWorkflowStatus() != WorkflowStatus.DRAFT){
            throw new IllegalStateException("Version id:" + draftVersionId + " is not a draft");
        }

        RestaurantMenuVersion currentActiveVersion = restaurantMenuVersionRepository.findByRestaurantIdAndActiveTrue(restaurantId);
        if(currentActiveVersion != null && !currentActiveVersion.getId().equals(draftVersion.getId())){
            currentActiveVersion.setActive(Boolean.FALSE);
            restaurantMenuVersionRepository.save(currentActiveVersion);
        }

        draftVersion.setActive(Boolean.TRUE);
        draftVersion.setWorkflowStatus(WorkflowStatus.PUBLISHED);
        restaurantMenuVersionRepository.save(draftVersion);
        log.info("publishDraftVersion: published version {} for restaurant {}", draftVersion.getVersionNumber(), restaurantId);
        return draftVersion;
    }

    public boolean isPullLocked(Long restaurantId) {
        return restaurantMenuVersionRepository.existsByRestaurantIdAndWorkflowStatus(restaurantId, WorkflowStatus.PUBLISHED);
    }

    @Transactional
    public int resetMenuVersions(Long restaurantId) {
        List<RestaurantMenuVersion> versions = restaurantMenuVersionRepository.findByRestaurantIdOrderByVersionNumberDesc(restaurantId);
        int deletedCount = 0;
        for (RestaurantMenuVersion version : versions) {
            if(version.getWorkflowStatus() == WorkflowStatus.PULLED){
                continue;
            }
            deleteMenuVersion(version.getId());
            deletedCount++;
        }
        log.info("resetMenuVersions: removed {} draft/published menu versions for restaurant {}", deletedCount, restaurantId);
        return deletedCount;
    }

    private void persistCategories(
            Long menuVersionId,
            JsonNode categoriesNode,
            Long sourceMenuId,
            Set<String> discoveredItemTags,
            Set<String> discoveredItemAllergens) {
        if(!categoriesNode.isArray()){
            return;
        }

        int categoryOrder = 0;
        Set<Integer> usedCategoryOrders = new LinkedHashSet<>();
        for (JsonNode categoryNode : categoriesNode) {
            RestaurantMenuCategory category = new RestaurantMenuCategory();
            category.setMenuVersionId(menuVersionId);
            category.setSourceMenuId(sourceMenuId);
            category.setSourceCategoryId(getLongValue(categoryNode, "id"));
            category.setName(getTextValue(categoryNode, "name"));
            category.setDescription(getTextValue(categoryNode, "description"));
            category.setActive(getBooleanValue(categoryNode, "active"));
            category.setActiveBegin(getTextValue(categoryNode, "active_begin"));
            category.setActiveEnd(getTextValue(categoryNode, "active_end"));
            category.setActiveDays(getIntegerValue(categoryNode, "active_days"));
            category.setPictureId(getLongValue(categoryNode, "picture_id"));
            category.setDisplayOrder(resolveDisplayOrder(categoryNode, categoryOrder++, usedCategoryOrders));
            category = restaurantMenuCategoryRepository.save(category);

                persistOptionGroups(
                    menuVersionId,
                    category.getId(),
                    null,
                    null,
                    sourceMenuId,
                        categoryNode.path("groups"));
                persistItems(
                    menuVersionId,
                    category,
                    categoryNode.path("items"),
                    discoveredItemTags,
                        discoveredItemAllergens);
        }
    }

    private void persistItems(
            Long menuVersionId,
            RestaurantMenuCategory category,
            JsonNode itemsNode,
            Set<String> discoveredItemTags,
            Set<String> discoveredItemAllergens) {
        if(!itemsNode.isArray()){
            return;
        }

        int itemOrder = 0;
        Set<Integer> usedItemOrders = new LinkedHashSet<>();
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
            item.setOutOfStock(readBooleanWithFallback(itemNode, "is_out_of_stock", "is_out_of_stock"));
            item.setIngredients(readTextWithFallback(itemNode, "ingredients", "menu_item_ingredients"));
            item.setAdditives(readTextWithFallback(itemNode, "additives", "menu_item_additives"));
            item.setNutritionalValuesSize(readNutritionSize(itemNode));
            // New normalized fields are the source of truth; keep legacy JSON columns empty.
            item.setTagsJson(null);
            item.setExtrasJson(null);
            item.setDisplayOrder(resolveDisplayOrder(itemNode, itemOrder++, usedItemOrders));
            item = restaurantMenuItemRepository.save(item);

            List<String> itemTags = readStringArrayWithFallback(itemNode, "tags");
            discoveredItemTags.addAll(itemTags);
            List<String> itemAllergens = readStringArrayWithFallback(itemNode, "menu_item_allergens_values");
            discoveredItemAllergens.addAll(itemAllergens);

            persistItemTags(item, itemTags);
            persistItemAllergens(item, itemAllergens);
            persistItemNutrition(item, readNutritionValues(itemNode));

                persistOptionGroups(
                    menuVersionId,
                    null,
                    item.getId(),
                    null,
                    category.getSourceMenuId(),
                        itemNode.path("groups"));
                persistItemSizes(
                    menuVersionId,
                    item,
                    category.getSourceMenuId(),
                        itemNode.path("sizes"));
        }
    }

    private void persistItemTags(RestaurantMenuItem item, List<String> tags) {
        int order = 0;
        for (String tag : tags) {
            RestaurantMenuItemTag itemTag = new RestaurantMenuItemTag();
            itemTag.setMenuVersionId(item.getMenuVersionId());
            itemTag.setItemId(item.getId());
            itemTag.setTag(tag);
            itemTag.setDisplayOrder(order++);
            restaurantMenuItemTagRepository.save(itemTag);
        }
    }

    private void persistItemAllergens(RestaurantMenuItem item, List<String> allergens) {
        int order = 0;
        for (String allergen : allergens) {
            RestaurantMenuItemAllergen itemAllergen = new RestaurantMenuItemAllergen();
            itemAllergen.setMenuVersionId(item.getMenuVersionId());
            itemAllergen.setItemId(item.getId());
            itemAllergen.setAllergen(allergen);
            itemAllergen.setDisplayOrder(order++);
            restaurantMenuItemAllergenRepository.save(itemAllergen);
        }
    }

    private void persistItemNutrition(RestaurantMenuItem item, Map<String, String> nutritionValues) {
        int order = 0;
        for (Map.Entry<String, String> entry : nutritionValues.entrySet()) {
            RestaurantMenuItemNutrition itemNutrition = new RestaurantMenuItemNutrition();
            itemNutrition.setMenuVersionId(item.getMenuVersionId());
            itemNutrition.setItemId(item.getId());
            itemNutrition.setNutritionName(entry.getKey());
            itemNutrition.setNutritionValue(entry.getValue());
            itemNutrition.setDisplayOrder(order++);
            restaurantMenuItemNutritionRepository.save(itemNutrition);
        }
    }

        private void persistItemSizes(
            Long menuVersionId,
            RestaurantMenuItem item,
            Long sourceMenuId,
                JsonNode sizesNode) {
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

            persistOptionGroups(
                    menuVersionId,
                    null,
                    null,
                    itemSize.getId(),
                    sourceMenuId,
                    sizeNode.path("groups"));
        }
    }

    private void persistOptionGroups(
            Long menuVersionId,
            Long categoryId,
            Long itemId,
            Long itemSizeId,
            Long sourceMenuId,
            JsonNode groupsNode) {
        if(!groupsNode.isArray()){
            return;
        }

        int groupOrder = 0;
        Set<Integer> usedGroupOrders = new LinkedHashSet<>();
        for (JsonNode groupNode : groupsNode) {
            RestaurantMenuOptionGroup optionGroup = new RestaurantMenuOptionGroup();
            optionGroup.setMenuVersionId(menuVersionId);
            optionGroup.setCategoryId(categoryId);
            optionGroup.setItemId(itemId);
            optionGroup.setItemSizeId(itemSizeId);
            Long sourceGroupId = getLongValue(groupNode, "id");
            optionGroup.setSourceGroupId(sourceGroupId);
            optionGroup.setSourceMenuId(sourceMenuId);
            optionGroup.setName(getTextValue(groupNode, "name"));
            optionGroup.setRequiredSelection(getBooleanValue(groupNode, "required"));
            optionGroup.setAllowQuantity(getBooleanValue(groupNode, "allow_quantity"));
            optionGroup.setForceMin(getIntegerValue(groupNode, "force_min"));
            optionGroup.setForceMax(getIntegerValue(groupNode, "force_max"));
            optionGroup.setDisplayOrder(resolveDisplayOrder(
                    groupNode,
                    preferredOrderFromSourceId(sourceGroupId),
                    groupOrder++,
                    usedGroupOrders));
            optionGroup = restaurantMenuOptionGroupRepository.save(optionGroup);

            saveStringSetting(optionGroupMajorGroupSettingName(optionGroup.getId()), DEFAULT_IMPORTED_MAJOR_GROUP);
            saveStringSetting(optionGroupTaxationCategorySettingName(optionGroup.getId()), DEFAULT_IMPORTED_TAXATION_CATEGORY);

            persistOptions(menuVersionId, optionGroup, groupNode.path("options"));
        }
    }

    private void persistOptions(Long menuVersionId, RestaurantMenuOptionGroup optionGroup, JsonNode optionsNode) {
        if(!optionsNode.isArray()){
            return;
        }

        int optionOrder = 0;
        Set<Integer> usedOptionOrders = new LinkedHashSet<>();
        for (JsonNode optionNode : optionsNode) {
            RestaurantMenuOption option = new RestaurantMenuOption();
            option.setMenuVersionId(menuVersionId);
            option.setOptionGroupId(optionGroup.getId());
            option.setSourceGroupId(optionGroup.getSourceGroupId());
            Long sourceOptionId = getLongValue(optionNode, "id");
            option.setSourceOptionId(sourceOptionId);
            option.setName(getTextValue(optionNode, "name"));
            option.setPrice(getDoubleValue(optionNode, "price"));
            option.setDefaultOption(getBooleanValue(optionNode, "default"));
            option.setOutOfStock(readBooleanWithFallback(optionNode, "is_out_of_stock", "is_out_of_stock"));
            option.setIngredients(readTextWithFallback(optionNode, "ingredients", "menu_item_ingredients"));
            option.setAdditives(readTextWithFallback(optionNode, "additives", "menu_item_additives"));
            option.setNutritionalValuesSize(readNutritionSize(optionNode));
            option.setExtrasJson(null);
            option.setDisplayOrder(resolveDisplayOrder(
                    optionNode,
                    preferredOrderFromSourceId(sourceOptionId),
                    optionOrder++,
                    usedOptionOrders));
            option = restaurantMenuOptionRepository.save(option);

            persistOptionTags(option, readStringArrayWithFallback(optionNode, "tags"));
            persistOptionAllergens(option, readStringArrayWithFallback(optionNode, "menu_item_allergens_values"));
            persistOptionNutrition(option, readNutritionValues(optionNode));
        }
    }

    private void persistOptionTags(RestaurantMenuOption option, List<String> tags) {
        int order = 0;
        for (String tag : tags) {
            RestaurantMenuOptionTag optionTag = new RestaurantMenuOptionTag();
            optionTag.setMenuVersionId(option.getMenuVersionId());
            optionTag.setOptionId(option.getId());
            optionTag.setTag(tag);
            optionTag.setDisplayOrder(order++);
            restaurantMenuOptionTagRepository.save(optionTag);
        }
    }

    private void persistOptionAllergens(RestaurantMenuOption option, List<String> allergens) {
        int order = 0;
        for (String allergen : allergens) {
            RestaurantMenuOptionAllergen optionAllergen = new RestaurantMenuOptionAllergen();
            optionAllergen.setMenuVersionId(option.getMenuVersionId());
            optionAllergen.setOptionId(option.getId());
            optionAllergen.setAllergen(allergen);
            optionAllergen.setDisplayOrder(order++);
            restaurantMenuOptionAllergenRepository.save(optionAllergen);
        }
    }

    private void persistOptionNutrition(RestaurantMenuOption option, Map<String, String> nutritionValues) {
        int order = 0;
        for (Map.Entry<String, String> entry : nutritionValues.entrySet()) {
            RestaurantMenuOptionNutrition optionNutrition = new RestaurantMenuOptionNutrition();
            optionNutrition.setMenuVersionId(option.getMenuVersionId());
            optionNutrition.setOptionId(option.getId());
            optionNutrition.setNutritionName(entry.getKey());
            optionNutrition.setNutritionValue(entry.getValue());
            optionNutrition.setDisplayOrder(order++);
            restaurantMenuOptionNutritionRepository.save(optionNutrition);
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
        restaurantMenuOptionNutritionRepository.deleteByMenuVersionId(menuVersionId);
        restaurantMenuOptionAllergenRepository.deleteByMenuVersionId(menuVersionId);
        restaurantMenuOptionTagRepository.deleteByMenuVersionId(menuVersionId);
        restaurantMenuOptionRepository.deleteByMenuVersionId(menuVersionId);
        restaurantMenuOptionGroupRepository.deleteByMenuVersionId(menuVersionId);
        restaurantMenuItemSizeRepository.deleteByMenuVersionId(menuVersionId);
        restaurantMenuItemNutritionRepository.deleteByMenuVersionId(menuVersionId);
        restaurantMenuItemAllergenRepository.deleteByMenuVersionId(menuVersionId);
        restaurantMenuItemTagRepository.deleteByMenuVersionId(menuVersionId);
        restaurantMenuItemRepository.deleteByMenuVersionId(menuVersionId);
        restaurantMenuCategoryRepository.deleteByMenuVersionId(menuVersionId);
        restaurantMenuVersionRepository.deleteById(menuVersionId);
    }

    private List<String> readStringArrayWithFallback(JsonNode itemNode, String fieldName) {
        Set<String> values = new LinkedHashSet<>();
        addArrayNodeValues(values, itemNode.path(fieldName));
        JsonNode extrasNode = itemNode.path("extras");
        if (extrasNode.isObject()) {
            addArrayNodeValues(values, extrasNode.path(fieldName));
        }
        return new ArrayList<>(values);
    }

    private void addArrayNodeValues(Set<String> output, JsonNode node) {
        if (!node.isArray()) {
            return;
        }
        for (JsonNode valueNode : node) {
            String value = trimToNull(valueNode.asText());
            if (value != null) {
                output.add(value);
            }
        }
    }

    private String readTextWithFallback(JsonNode itemNode, String topLevelField, String extrasField) {
        String topLevel = trimToNull(getTextValue(itemNode, topLevelField));
        if (topLevel != null) {
            return topLevel;
        }
        JsonNode extrasNode = itemNode.path("extras");
        if (!extrasNode.isObject()) {
            return null;
        }
        return trimToNull(getTextValue(extrasNode, extrasField));
    }

    private Boolean readBooleanWithFallback(JsonNode itemNode, String topLevelField, String extrasField) {
        Boolean topLevel = getBooleanValue(itemNode, topLevelField);
        if (topLevel != null) {
            return topLevel;
        }
        JsonNode extrasNode = itemNode.path("extras");
        if (!extrasNode.isObject()) {
            return null;
        }
        return getBooleanValue(extrasNode, extrasField);
    }

    private String readNutritionSize(JsonNode itemNode) {
        String topLevel = trimToNull(getTextValue(itemNode, "menu_item_nutritional_values_size"));
        if (topLevel != null) {
            return topLevel;
        }
        JsonNode extrasNode = itemNode.path("extras");
        if (!extrasNode.isObject()) {
            return null;
        }
        return trimToNull(getTextValue(extrasNode, "menu_item_nutritional_values_size"));
    }

    private Map<String, String> readNutritionValues(JsonNode itemNode) {
        Map<String, String> values = new LinkedHashMap<>();
        JsonNode nutritionValuesNode = itemNode.path("menu_item_nutritional_values");
        if (!nutritionValuesNode.isArray()) {
            JsonNode extrasNode = itemNode.path("extras");
            if (extrasNode.isObject()) {
                nutritionValuesNode = extrasNode.path("menu_item_nutritional_values");
            }
        }
        if (!nutritionValuesNode.isArray()) {
            return values;
        }

        for (JsonNode valueNode : nutritionValuesNode) {
            String key = trimToNull(getTextValue(valueNode, "name"));
            if (key == null) {
                key = trimToNull(getTextValue(valueNode, "label"));
            }
            if (key == null) {
                key = trimToNull(getTextValue(valueNode, "key"));
            }
            String value = trimToNull(getTextValue(valueNode, "value"));
            if (key != null && value != null) {
                values.put(key, value);
            }
        }
        return values;
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private void mergeMenuEditorLookupValues(String settingName, Set<String> discoveredValues) {
        if (discoveredValues == null || discoveredValues.isEmpty()) {
            return;
        }

        SettingEntity setting = settingRepository.findBySectionAndName(MENU_EDITOR_SETTINGS_SECTION, settingName);
        if (setting == null) {
            setting = new SettingEntity();
            setting.setSection(MENU_EDITOR_SETTINGS_SECTION);
            setting.setName(settingName);
            setting.setDescription("Auto-populated from menu imports");
            setting.setValueType(SettingEntity.ValueType.LIST);
        }

        Set<String> merged = new LinkedHashSet<>();
        if (setting.getValueAsList() != null) {
            for (String value : setting.getValueAsList()) {
                String cleaned = trimToNull(value);
                if (cleaned != null) {
                    merged.add(cleaned);
                }
            }
        }
        for (String discovered : discoveredValues) {
            String cleaned = trimToNull(discovered);
            if (cleaned != null) {
                merged.add(cleaned);
            }
        }

        setting.setValue(new ArrayList<>(merged));
        settingRepository.save(setting);
    }

    private String optionGroupMajorGroupSettingName(Long optionGroupId) {
        return OPTION_GROUP_MAJOR_GROUP_SETTING_PREFIX + optionGroupId;
    }

    private String optionGroupTaxationCategorySettingName(Long optionGroupId) {
        return OPTION_GROUP_TAXATION_CATEGORY_SETTING_PREFIX + optionGroupId;
    }

    private void saveStringSetting(String settingName, String value) {
        SettingEntity setting = settingRepository.findBySectionAndName(MENU_EDITOR_SETTINGS_SECTION, settingName);
        if (setting == null) {
            setting = new SettingEntity();
            setting.setSection(MENU_EDITOR_SETTINGS_SECTION);
            setting.setName(settingName);
            setting.setDescription("Menu editor choice-group imported metadata");
            setting.setValueType(SettingEntity.ValueType.STRING);
        }

        String cleaned = trimToNull(value);
        if (cleaned == null) {
            settingRepository.delete(setting);
            return;
        }

        setting.setValue(cleaned);
        settingRepository.save(setting);
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

    private Integer resolveDisplayOrder(JsonNode node, int fallbackIndex, Set<Integer> usedOrders) {
        return resolveDisplayOrder(node, null, fallbackIndex, usedOrders);
    }

    private Integer resolveDisplayOrder(JsonNode node, Integer preferredOrder, int fallbackIndex, Set<Integer> usedOrders) {
        Integer sourceSort = getStrictIntegerValue(node, "sort");
        if (sourceSort != null && (usedOrders == null || !usedOrders.contains(sourceSort))) {
            if (usedOrders != null) {
                usedOrders.add(sourceSort);
            }
            return sourceSort;
        }

        if (preferredOrder != null && (usedOrders == null || !usedOrders.contains(preferredOrder))) {
            if (usedOrders != null) {
                usedOrders.add(preferredOrder);
            }
            return preferredOrder;
        }

        int resolved = fallbackIndex;
        if (usedOrders != null) {
            while (usedOrders.contains(resolved)) {
                resolved++;
            }
            usedOrders.add(resolved);
        }
        return resolved;
    }

    private Integer preferredOrderFromSourceId(Long sourceId) {
        if (sourceId == null || sourceId <= 0L || sourceId > Integer.MAX_VALUE) {
            return null;
        }
        return sourceId.intValue();
    }

    private Integer getStrictIntegerValue(JsonNode node, String fieldName) {
        JsonNode value = node.path(fieldName);
        if (value.isMissingNode() || value.isNull()) {
            return null;
        }
        if (value.isInt() || value.isLong()) {
            return value.intValue();
        }
        if (value.isTextual()) {
            String text = trimToNull(value.asText());
            if (text == null) {
                return null;
            }
            try {
                return Integer.valueOf(text);
            } catch (NumberFormatException ignored) {
                return null;
            }
        }
        return null;
    }
}