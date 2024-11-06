
package ca.admin.delivermore.collector.data.global;

import java.util.List;
import jakarta.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
    "id",
    "name",
    "total_item_price",
    "price",
    "quantity",
    "instructions",
    "type",
    "type_id",
    "tax_rate",
    "tax_value",
    "parent_id",
    "item_discount",
    "cart_discount_rate",
    "cart_discount",
    "tax_type",
    "options"
})
@Generated("jsonschema2pojo")
public class Item {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("total_item_price")
    private Double totalItemPrice;
    @JsonProperty("price")
    private Double price;
    @JsonProperty("quantity")
    private Long quantity;
    @JsonProperty("instructions")
    private String instructions;
    @JsonProperty("type")
    private String type;
    @JsonProperty("type_id")
    private Long typeId;
    @JsonProperty("tax_rate")
    private Double taxRate;
    @JsonProperty("tax_value")
    private Double taxValue;
    @JsonProperty("parent_id")
    private Object parentId;
    @JsonProperty("item_discount")
    private Double itemDiscount;
    @JsonProperty("cart_discount_rate")
    private Double cartDiscountRate;
    @JsonProperty("cart_discount")
    private Double cartDiscount;
    @JsonProperty("tax_type")
    private String taxType;
    @JsonProperty("options")
    private List<Option> options = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Item() {
    }

    /**
     * 
     * @param instructions
     * @param quantity
     * @param itemDiscount
     * @param type
     * @param parentId
     * @param taxRate
     * @param taxValue
     * @param cartDiscount
     * @param price
     * @param name
     * @param options
     * @param typeId
     * @param id
     * @param cartDiscountRate
     * @param totalItemPrice
     * @param taxType
     */
    public Item(Long id, String name, Double totalItemPrice, Double price, Long quantity, String instructions, String type, Long typeId, Double taxRate, Double taxValue, Object parentId, Double itemDiscount, Double cartDiscountRate, Double cartDiscount, String taxType, List<Option> options) {
        super();
        this.id = id;
        this.name = name;
        this.totalItemPrice = totalItemPrice;
        this.price = price;
        this.quantity = quantity;
        this.instructions = instructions;
        this.type = type;
        this.typeId = typeId;
        this.taxRate = taxRate;
        this.taxValue = taxValue;
        this.parentId = parentId;
        this.itemDiscount = itemDiscount;
        this.cartDiscountRate = cartDiscountRate;
        this.cartDiscount = cartDiscount;
        this.taxType = taxType;
        this.options = options;
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

    @JsonProperty("total_item_price")
    public Double getTotalItemPrice() {
        return totalItemPrice;
    }

    @JsonProperty("total_item_price")
    public void setTotalItemPrice(Double totalItemPrice) {
        this.totalItemPrice = totalItemPrice;
    }

    @JsonProperty("price")
    public Double getPrice() {
        return price;
    }

    @JsonProperty("price")
    public void setPrice(Double price) {
        this.price = price;
    }

    @JsonProperty("quantity")
    public Long getQuantity() {
        return quantity;
    }

    @JsonProperty("quantity")
    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @JsonProperty("instructions")
    public String getInstructions() {
        return instructions;
    }

    @JsonProperty("instructions")
    public void setInstructions(String instructions) {
        this.instructions = instructions;
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

    @JsonProperty("tax_rate")
    public Double getTaxRate() {
        return taxRate;
    }

    @JsonProperty("tax_rate")
    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    @JsonProperty("tax_value")
    public Double getTaxValue() {
        return taxValue;
    }

    @JsonProperty("tax_value")
    public void setTaxValue(Double taxValue) {
        this.taxValue = taxValue;
    }

    @JsonProperty("parent_id")
    public Object getParentId() {
        return parentId;
    }

    @JsonProperty("parent_id")
    public void setParentId(Object parentId) {
        this.parentId = parentId;
    }

    @JsonProperty("item_discount")
    public Double getItemDiscount() {
        return itemDiscount;
    }

    @JsonProperty("item_discount")
    public void setItemDiscount(Double itemDiscount) {
        this.itemDiscount = itemDiscount;
    }

    @JsonProperty("cart_discount_rate")
    public Double getCartDiscountRate() {
        return cartDiscountRate;
    }

    @JsonProperty("cart_discount_rate")
    public void setCartDiscountRate(Double cartDiscountRate) {
        this.cartDiscountRate = cartDiscountRate;
    }

    @JsonProperty("cart_discount")
    public Double getCartDiscount() {
        return cartDiscount;
    }

    @JsonProperty("cart_discount")
    public void setCartDiscount(Double cartDiscount) {
        this.cartDiscount = cartDiscount;
    }

    @JsonProperty("tax_type")
    public String getTaxType() {
        return taxType;
    }

    @JsonProperty("tax_type")
    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    @JsonProperty("options")
    public List<Option> getOptions() {
        return options;
    }

    @JsonProperty("options")
    public void setOptions(List<Option> options) {
        this.options = options;
    }

}
