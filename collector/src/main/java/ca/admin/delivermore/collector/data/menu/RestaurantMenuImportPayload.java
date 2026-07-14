package ca.admin.delivermore.collector.data.menu;

public class RestaurantMenuImportPayload {

    private final Long restaurantId;
    private final String restaurantName;
    private final String rawJson;

    public RestaurantMenuImportPayload(Long restaurantId, String restaurantName, String rawJson) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.rawJson = rawJson;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getRawJson() {
        return rawJson;
    }
}