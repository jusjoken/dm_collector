
package ca.admin.delivermore.collector.data.global;

import java.util.List;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "count",
    "orders"
})
@Generated("jsonschema2pojo")
public class GlobalOrderList {

    @JsonProperty("count")
    private Long count;
    @JsonProperty("orders")
    private List<GlobalOrder> orders = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GlobalOrderList() {
    }

    /**
     * 
     * @param count
     * @param orders
     */
    public GlobalOrderList(Long count, List<GlobalOrder> orders) {
        super();
        this.count = count;
        this.orders = orders;
    }

    @JsonProperty("count")
    public Long getCount() {
        return count;
    }

    @JsonProperty("count")
    public void setCount(Long count) {
        this.count = count;
    }

    @JsonProperty("orders")
    public List<GlobalOrder> getOrders() {
        return orders;
    }

    @JsonProperty("orders")
    public void setOrders(List<GlobalOrder> orders) {
        this.orders = orders;
    }

}
