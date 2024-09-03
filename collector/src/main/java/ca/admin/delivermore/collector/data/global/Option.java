
package ca.admin.delivermore.collector.data.global;

import jakarta.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name",
    "price",
    "group_name",
    "quantity",
    "type",
    "type_id"
})
@Generated("jsonschema2pojo")
public class Option {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("price")
    private Double price;
    @JsonProperty("group_name")
    private String groupName;
    @JsonProperty("quantity")
    private Long quantity;
    @JsonProperty("type")
    private String type;
    @JsonProperty("type_id")
    private Long typeId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Option() {
    }

    /**
     * 
     * @param groupName
     * @param quantity
     * @param price
     * @param name
     * @param typeId
     * @param id
     * @param type
     */
    public Option(Long id, String name, Double price, String groupName, Long quantity, String type, Long typeId) {
        super();
        this.id = id;
        this.name = name;
        this.price = price;
        this.groupName = groupName;
        this.quantity = quantity;
        this.type = type;
        this.typeId = typeId;
    }

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("price")
    public Double getPrice() {
        return price;
    }

    @JsonProperty("price")
    public void setPrice(Double price) {
        this.price = price;
    }

    @JsonProperty("group_name")
    public String getGroupName() {
        return groupName;
    }

    @JsonProperty("group_name")
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @JsonProperty("quantity")
    public Long getQuantity() {
        return quantity;
    }

    @JsonProperty("quantity")
    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("type_id")
    public Long getTypeId() {
        return typeId;
    }

    @JsonProperty("type_id")
    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

}
