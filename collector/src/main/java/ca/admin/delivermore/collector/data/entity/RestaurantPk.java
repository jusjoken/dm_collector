package ca.admin.delivermore.collector.data.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class RestaurantPk implements Serializable {

    private Long restaurantId;

    private LocalDate dateEffective;

    public RestaurantPk() {
    }

    public RestaurantPk(Long restaurantId, LocalDate dateEffective) {
        this.restaurantId = restaurantId;
        this.dateEffective = dateEffective;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantPk that = (RestaurantPk) o;
        return Objects.equals(restaurantId, that.restaurantId) && Objects.equals(dateEffective, that.dateEffective);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurantId, dateEffective);
    }
}
