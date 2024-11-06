
package ca.admin.delivermore.collector.data.global;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
    "street",
    "city",
    "more_address"
})
@Generated("jsonschema2pojo")
public class ClientAddressParts {

    @JsonProperty("street")
    private String street;
    @JsonProperty("city")
    private String city;
    @JsonProperty("more_address")
    private String moreAddress;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ClientAddressParts() {
    }

    /**
     * 
     * @param city
     * @param street
     * @param moreAddress
     */
    public ClientAddressParts(String street, String city, String moreAddress) {
        super();
        this.street = street;
        this.city = city;
        this.moreAddress = moreAddress;
    }

    @JsonProperty("street")
    public String getStreet() {
        return street;
    }

    @JsonProperty("street")
    public void setStreet(String street) {
        this.street = street;
    }

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty("more_address")
    public String getMoreAddress() {
        return moreAddress;
    }

    @JsonProperty("more_address")
    public void setMoreAddress(String moreAddress) {
        this.moreAddress = moreAddress;
    }

}
