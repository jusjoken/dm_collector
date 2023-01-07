
package ca.admin.delivermore.collector.data.global;

import java.util.List;
import javax.annotation.Generated;

import ca.admin.delivermore.collector.data.entity.OrderDetail;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "instructions",
    "coupons",
    "tax_list",
    "missed_reason",
    "billing_details",
    "fulfillment_option",
    "table_number",
    "ready",
    "updated_at",
    "id",
    "total_price",
    "sub_total_price",
    "tax_value",
    "persons",
    "latitude",
    "longitude",
    "client_first_name",
    "client_last_name",
    "client_email",
    "client_phone",
    "restaurant_name",
    "currency",
    "type",
    "status",
    "source",
    "pin_skipped",
    "accepted_at",
    "tax_type",
    "tax_name",
    "fulfill_at",
    "client_language",
    "integration_payment_provider",
    "integration_payment_amount",
    "reference",
    "restaurant_id",
    "client_id",
    "restaurant_phone",
    "restaurant_timezone",
    "card_type",
    "used_payment_methods",
    "company_account_id",
    "pos_system_id",
    "restaurant_key",
    "restaurant_country",
    "restaurant_city",
    "restaurant_state",
    "restaurant_zipcode",
    "restaurant_street",
    "restaurant_latitude",
    "restaurant_longitude",
    "client_marketing_consent",
    "restaurant_token",
    "gateway_transaction_id",
    "gateway_type",
    "api_version",
    "payment",
    "for_later",
    "client_address",
    "client_address_parts",
    "items"
})
@Generated("jsonschema2pojo")
public class GlobalOrder {

    private OrderDetail orderDetail;

    @JsonProperty("instructions")
    private String instructions;
    @JsonProperty("coupons")
    private List<Object> coupons = null;
    @JsonProperty("tax_list")
    private List<Object> taxList = null;
    @JsonProperty("missed_reason")
    private Object missedReason;
    @JsonProperty("billing_details")
    private Object billingDetails;
    @JsonProperty("fulfillment_option")
    private Object fulfillmentOption;
    @JsonProperty("table_number")
    private Object tableNumber;
    @JsonProperty("ready")
    private Boolean ready;
    @JsonProperty("updated_at")
    private String updatedAt;
    @JsonProperty("id")
    private Long id;
    @JsonProperty("total_price")
    private Double totalPrice;
    @JsonProperty("sub_total_price")
    private Double subTotalPrice;
    @JsonProperty("tax_value")
    private Double taxValue;
    @JsonProperty("persons")
    private Long persons;
    @JsonProperty("latitude")
    private String latitude;
    @JsonProperty("longitude")
    private String longitude;
    @JsonProperty("client_first_name")
    private String clientFirstName;
    @JsonProperty("client_last_name")
    private String clientLastName;
    @JsonProperty("client_email")
    private String clientEmail;
    @JsonProperty("client_phone")
    private String clientPhone;
    @JsonProperty("restaurant_name")
    private String restaurantName;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("type")
    private String type;
    @JsonProperty("status")
    private String status;
    @JsonProperty("source")
    private String source;
    @JsonProperty("pin_skipped")
    private Boolean pinSkipped;
    @JsonProperty("accepted_at")
    private String acceptedAt;
    @JsonProperty("tax_type")
    private String taxType;
    @JsonProperty("tax_name")
    private String taxName;
    @JsonProperty("fulfill_at")
    private String fulfillAt;
    @JsonProperty("client_language")
    private String clientLanguage;
    @JsonProperty("integration_payment_provider")
    private Object integrationPaymentProvider;
    @JsonProperty("integration_payment_amount")
    private Long integrationPaymentAmount;
    @JsonProperty("reference")
    private Object reference;
    @JsonProperty("restaurant_id")
    private Long restaurantId;
    @JsonProperty("client_id")
    private Long clientId;
    @JsonProperty("restaurant_phone")
    private String restaurantPhone;
    @JsonProperty("restaurant_timezone")
    private String restaurantTimezone;
    @JsonProperty("card_type")
    private Object cardType;
    @JsonProperty("used_payment_methods")
    private List<String> usedPaymentMethods = null;
    @JsonProperty("company_account_id")
    private Long companyAccountId;
    @JsonProperty("pos_system_id")
    private Long posSystemId;
    @JsonProperty("restaurant_key")
    private String restaurantKey;
    @JsonProperty("restaurant_country")
    private String restaurantCountry;
    @JsonProperty("restaurant_city")
    private String restaurantCity;
    @JsonProperty("restaurant_state")
    private String restaurantState;
    @JsonProperty("restaurant_zipcode")
    private String restaurantZipcode;
    @JsonProperty("restaurant_street")
    private String restaurantStreet;
    @JsonProperty("restaurant_latitude")
    private String restaurantLatitude;
    @JsonProperty("restaurant_longitude")
    private String restaurantLongitude;
    @JsonProperty("client_marketing_consent")
    private Boolean clientMarketingConsent;
    @JsonProperty("restaurant_token")
    private String restaurantToken;
    @JsonProperty("gateway_transaction_id")
    private Object gatewayTransactionId;
    @JsonProperty("gateway_type")
    private Object gatewayType;
    @JsonProperty("api_version")
    private Long apiVersion;
    @JsonProperty("payment")
    private String payment;
    @JsonProperty("for_later")
    private Boolean forLater;
    @JsonProperty("client_address")
    private String clientAddress;
    @JsonProperty("client_address_parts")
    private ClientAddressParts clientAddressParts;
    @JsonProperty("items")
    private List<Item> items = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GlobalOrder() {
    }

    /**
     * 
     * @param instructions
     * @param billingDetails
     * @param fulfillAt
     * @param fulfillmentOption
     * @param source
     * @param type
     * @param clientAddress
     * @param tableNumber
     * @param companyAccountId
     * @param reference
     * @param restaurantTimezone
     * @param gatewayType
     * @param apiVersion
     * @param coupons
     * @param payment
     * @param id
     * @param restaurantCountry
     * @param taxType
     * @param updatedAt
     * @param longitude
     * @param integrationPaymentProvider
     * @param clientLastName
     * @param missedReason
     * @param clientLanguage
     * @param clientEmail
     * @param clientPhone
     * @param clientMarketingConsent
     * @param persons
     * @param forLater
     * @param restaurantZipcode
     * @param integrationPaymentAmount
     * @param restaurantPhone
     * @param items
     * @param status
     * @param totalPrice
     * @param latitude
     * @param restaurantId
     * @param subTotalPrice
     * @param taxName
     * @param gatewayTransactionId
     * @param restaurantName
     * @param ready
     * @param posSystemId
     * @param currency
     * @param restaurantLongitude
     * @param acceptedAt
     * @param clientId
     * @param clientAddressParts
     * @param cardType
     * @param restaurantState
     * @param restaurantStreet
     * @param restaurantCity
     * @param taxList
     * @param taxValue
     * @param usedPaymentMethods
     * @param pinSkipped
     * @param clientFirstName
     * @param restaurantKey
     * @param restaurantLatitude
     * @param restaurantToken
     */
    public GlobalOrder(String instructions, List<Object> coupons, List<Object> taxList, Object missedReason, Object billingDetails, Object fulfillmentOption, Object tableNumber, Boolean ready, String updatedAt, Long id, Double totalPrice, Double subTotalPrice, Double taxValue, Long persons, String latitude, String longitude, String clientFirstName, String clientLastName, String clientEmail, String clientPhone, String restaurantName, String currency, String type, String status, String source, Boolean pinSkipped, String acceptedAt, String taxType, String taxName, String fulfillAt, String clientLanguage, Object integrationPaymentProvider, Long integrationPaymentAmount, Object reference, Long restaurantId, Long clientId, String restaurantPhone, String restaurantTimezone, Object cardType, List<String> usedPaymentMethods, Long companyAccountId, Long posSystemId, String restaurantKey, String restaurantCountry, String restaurantCity, String restaurantState, String restaurantZipcode, String restaurantStreet, String restaurantLatitude, String restaurantLongitude, Boolean clientMarketingConsent, String restaurantToken, Object gatewayTransactionId, Object gatewayType, Long apiVersion, String payment, Boolean forLater, String clientAddress, ClientAddressParts clientAddressParts, List<Item> items) {
        super();
        this.instructions = instructions;
        this.coupons = coupons;
        this.taxList = taxList;
        this.missedReason = missedReason;
        this.billingDetails = billingDetails;
        this.fulfillmentOption = fulfillmentOption;
        this.tableNumber = tableNumber;
        this.ready = ready;
        this.updatedAt = updatedAt;
        this.id = id;
        this.totalPrice = totalPrice;
        this.subTotalPrice = subTotalPrice;
        this.taxValue = taxValue;
        this.persons = persons;
        this.latitude = latitude;
        this.longitude = longitude;
        this.clientFirstName = clientFirstName;
        this.clientLastName = clientLastName;
        this.clientEmail = clientEmail;
        this.clientPhone = clientPhone;
        this.restaurantName = restaurantName;
        this.currency = currency;
        this.type = type;
        this.status = status;
        this.source = source;
        this.pinSkipped = pinSkipped;
        this.acceptedAt = acceptedAt;
        this.taxType = taxType;
        this.taxName = taxName;
        this.fulfillAt = fulfillAt;
        this.clientLanguage = clientLanguage;
        this.integrationPaymentProvider = integrationPaymentProvider;
        this.integrationPaymentAmount = integrationPaymentAmount;
        this.reference = reference;
        this.restaurantId = restaurantId;
        this.clientId = clientId;
        this.restaurantPhone = restaurantPhone;
        this.restaurantTimezone = restaurantTimezone;
        this.cardType = cardType;
        this.usedPaymentMethods = usedPaymentMethods;
        this.companyAccountId = companyAccountId;
        this.posSystemId = posSystemId;
        this.restaurantKey = restaurantKey;
        this.restaurantCountry = restaurantCountry;
        this.restaurantCity = restaurantCity;
        this.restaurantState = restaurantState;
        this.restaurantZipcode = restaurantZipcode;
        this.restaurantStreet = restaurantStreet;
        this.restaurantLatitude = restaurantLatitude;
        this.restaurantLongitude = restaurantLongitude;
        this.clientMarketingConsent = clientMarketingConsent;
        this.restaurantToken = restaurantToken;
        this.gatewayTransactionId = gatewayTransactionId;
        this.gatewayType = gatewayType;
        this.apiVersion = apiVersion;
        this.payment = payment;
        this.forLater = forLater;
        this.clientAddress = clientAddress;
        this.clientAddressParts = clientAddressParts;
        this.items = items;
    }

    @JsonProperty("instructions")
    public String getInstructions() {
        return instructions;
    }

    @JsonProperty("instructions")
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    @JsonProperty("coupons")
    public List<Object> getCoupons() {
        return coupons;
    }

    @JsonProperty("coupons")
    public void setCoupons(List<Object> coupons) {
        this.coupons = coupons;
    }

    @JsonProperty("tax_list")
    public List<Object> getTaxList() {
        return taxList;
    }

    @JsonProperty("tax_list")
    public void setTaxList(List<Object> taxList) {
        this.taxList = taxList;
    }

    @JsonProperty("missed_reason")
    public Object getMissedReason() {
        return missedReason;
    }

    @JsonProperty("missed_reason")
    public void setMissedReason(Object missedReason) {
        this.missedReason = missedReason;
    }

    @JsonProperty("billing_details")
    public Object getBillingDetails() {
        return billingDetails;
    }

    @JsonProperty("billing_details")
    public void setBillingDetails(Object billingDetails) {
        this.billingDetails = billingDetails;
    }

    @JsonProperty("fulfillment_option")
    public Object getFulfillmentOption() {
        return fulfillmentOption;
    }

    @JsonProperty("fulfillment_option")
    public void setFulfillmentOption(Object fulfillmentOption) {
        this.fulfillmentOption = fulfillmentOption;
    }

    @JsonProperty("table_number")
    public Object getTableNumber() {
        return tableNumber;
    }

    @JsonProperty("table_number")
    public void setTableNumber(Object tableNumber) {
        this.tableNumber = tableNumber;
    }

    @JsonProperty("ready")
    public Boolean getReady() {
        return ready;
    }

    @JsonProperty("ready")
    public void setReady(Boolean ready) {
        this.ready = ready;
    }

    @JsonProperty("updated_at")
    public String getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty("updated_at")
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("total_price")
    public Double getTotalPrice() {
        return totalPrice;
    }

    @JsonProperty("total_price")
    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @JsonProperty("sub_total_price")
    public Double getSubTotalPrice() {
        return subTotalPrice;
    }

    @JsonProperty("sub_total_price")
    public void setSubTotalPrice(Double subTotalPrice) {
        this.subTotalPrice = subTotalPrice;
    }

    @JsonProperty("tax_value")
    public Double getTaxValue() {
        return taxValue;
    }

    @JsonProperty("tax_value")
    public void setTaxValue(Double taxValue) {
        this.taxValue = taxValue;
    }

    @JsonProperty("persons")
    public Long getPersons() {
        return persons;
    }

    @JsonProperty("persons")
    public void setPersons(Long persons) {
        this.persons = persons;
    }

    @JsonProperty("latitude")
    public String getLatitude() {
        return latitude;
    }

    @JsonProperty("latitude")
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @JsonProperty("longitude")
    public String getLongitude() {
        return longitude;
    }

    @JsonProperty("longitude")
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @JsonProperty("client_first_name")
    public String getClientFirstName() {
        return clientFirstName;
    }

    @JsonProperty("client_first_name")
    public void setClientFirstName(String clientFirstName) {
        this.clientFirstName = clientFirstName;
    }

    @JsonProperty("client_last_name")
    public String getClientLastName() {
        return clientLastName;
    }

    @JsonProperty("client_last_name")
    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }

    @JsonProperty("client_email")
    public String getClientEmail() {
        return clientEmail;
    }

    @JsonProperty("client_email")
    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    @JsonProperty("client_phone")
    public String getClientPhone() {
        return clientPhone;
    }

    @JsonProperty("client_phone")
    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    @JsonProperty("restaurant_name")
    public String getRestaurantName() {
        return restaurantName;
    }

    @JsonProperty("restaurant_name")
    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    @JsonProperty("currency")
    public String getCurrency() {
        return currency;
    }

    @JsonProperty("currency")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("source")
    public String getSource() {
        return source;
    }

    @JsonProperty("source")
    public void setSource(String source) {
        this.source = source;
    }

    @JsonProperty("pin_skipped")
    public Boolean getPinSkipped() {
        return pinSkipped;
    }

    @JsonProperty("pin_skipped")
    public void setPinSkipped(Boolean pinSkipped) {
        this.pinSkipped = pinSkipped;
    }

    @JsonProperty("accepted_at")
    public String getAcceptedAt() {
        return acceptedAt;
    }

    @JsonProperty("accepted_at")
    public void setAcceptedAt(String acceptedAt) {
        this.acceptedAt = acceptedAt;
    }

    @JsonProperty("tax_type")
    public String getTaxType() {
        return taxType;
    }

    @JsonProperty("tax_type")
    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    @JsonProperty("tax_name")
    public String getTaxName() {
        return taxName;
    }

    @JsonProperty("tax_name")
    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    @JsonProperty("fulfill_at")
    public String getFulfillAt() {
        return fulfillAt;
    }

    @JsonProperty("fulfill_at")
    public void setFulfillAt(String fulfillAt) {
        this.fulfillAt = fulfillAt;
    }

    @JsonProperty("client_language")
    public String getClientLanguage() {
        return clientLanguage;
    }

    @JsonProperty("client_language")
    public void setClientLanguage(String clientLanguage) {
        this.clientLanguage = clientLanguage;
    }

    @JsonProperty("integration_payment_provider")
    public Object getIntegrationPaymentProvider() {
        return integrationPaymentProvider;
    }

    @JsonProperty("integration_payment_provider")
    public void setIntegrationPaymentProvider(Object integrationPaymentProvider) {
        this.integrationPaymentProvider = integrationPaymentProvider;
    }

    @JsonProperty("integration_payment_amount")
    public Long getIntegrationPaymentAmount() {
        return integrationPaymentAmount;
    }

    @JsonProperty("integration_payment_amount")
    public void setIntegrationPaymentAmount(Long integrationPaymentAmount) {
        this.integrationPaymentAmount = integrationPaymentAmount;
    }

    @JsonProperty("reference")
    public Object getReference() {
        return reference;
    }

    @JsonProperty("reference")
    public void setReference(Object reference) {
        this.reference = reference;
    }

    @JsonProperty("restaurant_id")
    public Long getRestaurantId() {
        return restaurantId;
    }

    @JsonProperty("restaurant_id")
    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    @JsonProperty("client_id")
    public Long getClientId() {
        return clientId;
    }

    @JsonProperty("client_id")
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    @JsonProperty("restaurant_phone")
    public String getRestaurantPhone() {
        return restaurantPhone;
    }

    @JsonProperty("restaurant_phone")
    public void setRestaurantPhone(String restaurantPhone) {
        this.restaurantPhone = restaurantPhone;
    }

    @JsonProperty("restaurant_timezone")
    public String getRestaurantTimezone() {
        return restaurantTimezone;
    }

    @JsonProperty("restaurant_timezone")
    public void setRestaurantTimezone(String restaurantTimezone) {
        this.restaurantTimezone = restaurantTimezone;
    }

    @JsonProperty("card_type")
    public Object getCardType() {
        return cardType;
    }

    @JsonProperty("card_type")
    public void setCardType(Object cardType) {
        this.cardType = cardType;
    }

    @JsonProperty("used_payment_methods")
    public List<String> getUsedPaymentMethods() {
        return usedPaymentMethods;
    }

    @JsonProperty("used_payment_methods")
    public void setUsedPaymentMethods(List<String> usedPaymentMethods) {
        this.usedPaymentMethods = usedPaymentMethods;
    }

    @JsonProperty("company_account_id")
    public Long getCompanyAccountId() {
        return companyAccountId;
    }

    @JsonProperty("company_account_id")
    public void setCompanyAccountId(Long companyAccountId) {
        this.companyAccountId = companyAccountId;
    }

    @JsonProperty("pos_system_id")
    public Long getPosSystemId() {
        return posSystemId;
    }

    @JsonProperty("pos_system_id")
    public void setPosSystemId(Long posSystemId) {
        this.posSystemId = posSystemId;
    }

    @JsonProperty("restaurant_key")
    public String getRestaurantKey() {
        return restaurantKey;
    }

    @JsonProperty("restaurant_key")
    public void setRestaurantKey(String restaurantKey) {
        this.restaurantKey = restaurantKey;
    }

    @JsonProperty("restaurant_country")
    public String getRestaurantCountry() {
        return restaurantCountry;
    }

    @JsonProperty("restaurant_country")
    public void setRestaurantCountry(String restaurantCountry) {
        this.restaurantCountry = restaurantCountry;
    }

    @JsonProperty("restaurant_city")
    public String getRestaurantCity() {
        return restaurantCity;
    }

    @JsonProperty("restaurant_city")
    public void setRestaurantCity(String restaurantCity) {
        this.restaurantCity = restaurantCity;
    }

    @JsonProperty("restaurant_state")
    public String getRestaurantState() {
        return restaurantState;
    }

    @JsonProperty("restaurant_state")
    public void setRestaurantState(String restaurantState) {
        this.restaurantState = restaurantState;
    }

    @JsonProperty("restaurant_zipcode")
    public String getRestaurantZipcode() {
        return restaurantZipcode;
    }

    @JsonProperty("restaurant_zipcode")
    public void setRestaurantZipcode(String restaurantZipcode) {
        this.restaurantZipcode = restaurantZipcode;
    }

    @JsonProperty("restaurant_street")
    public String getRestaurantStreet() {
        return restaurantStreet;
    }

    @JsonProperty("restaurant_street")
    public void setRestaurantStreet(String restaurantStreet) {
        this.restaurantStreet = restaurantStreet;
    }

    @JsonProperty("restaurant_latitude")
    public String getRestaurantLatitude() {
        return restaurantLatitude;
    }

    @JsonProperty("restaurant_latitude")
    public void setRestaurantLatitude(String restaurantLatitude) {
        this.restaurantLatitude = restaurantLatitude;
    }

    @JsonProperty("restaurant_longitude")
    public String getRestaurantLongitude() {
        return restaurantLongitude;
    }

    @JsonProperty("restaurant_longitude")
    public void setRestaurantLongitude(String restaurantLongitude) {
        this.restaurantLongitude = restaurantLongitude;
    }

    @JsonProperty("client_marketing_consent")
    public Boolean getClientMarketingConsent() {
        return clientMarketingConsent;
    }

    @JsonProperty("client_marketing_consent")
    public void setClientMarketingConsent(Boolean clientMarketingConsent) {
        this.clientMarketingConsent = clientMarketingConsent;
    }

    @JsonProperty("restaurant_token")
    public String getRestaurantToken() {
        return restaurantToken;
    }

    @JsonProperty("restaurant_token")
    public void setRestaurantToken(String restaurantToken) {
        this.restaurantToken = restaurantToken;
    }

    @JsonProperty("gateway_transaction_id")
    public Object getGatewayTransactionId() {
        return gatewayTransactionId;
    }

    @JsonProperty("gateway_transaction_id")
    public void setGatewayTransactionId(Object gatewayTransactionId) {
        this.gatewayTransactionId = gatewayTransactionId;
    }

    @JsonProperty("gateway_type")
    public Object getGatewayType() {
        return gatewayType;
    }

    @JsonProperty("gateway_type")
    public void setGatewayType(Object gatewayType) {
        this.gatewayType = gatewayType;
    }

    @JsonProperty("api_version")
    public Long getApiVersion() {
        return apiVersion;
    }

    @JsonProperty("api_version")
    public void setApiVersion(Long apiVersion) {
        this.apiVersion = apiVersion;
    }

    @JsonProperty("payment")
    public String getPayment() {
        return payment;
    }

    @JsonProperty("payment")
    public void setPayment(String payment) {
        this.payment = payment;
    }

    @JsonProperty("for_later")
    public Boolean getForLater() {
        return forLater;
    }

    @JsonProperty("for_later")
    public void setForLater(Boolean forLater) {
        this.forLater = forLater;
    }

    @JsonProperty("client_address")
    public String getClientAddress() {
        return clientAddress;
    }

    @JsonProperty("client_address")
    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    @JsonProperty("client_address_parts")
    public ClientAddressParts getClientAddressParts() {
        return clientAddressParts;
    }

    @JsonProperty("client_address_parts")
    public void setClientAddressParts(ClientAddressParts clientAddressParts) {
        this.clientAddressParts = clientAddressParts;
    }

    @JsonProperty("items")
    public List<Item> getItems() {
        return items;
    }

    @JsonProperty("items")
    public void setItems(List<Item> items) {
        this.items = items;
    }

    public OrderDetail getOrderDetail(){
        orderDetail = new OrderDetail();
        orderDetail.setOrderId(id);
        orderDetail.setSource(OrderDetail.Source.JSON);
        orderDetail.setRestaurantId(restaurantId);
        orderDetail.setSubtotal(subTotalPrice);
        orderDetail.setTotal(totalPrice);
        orderDetail.setPaymentMethod(payment);
        orderDetail.setTotalTaxes(taxValue);
        orderDetail.setOrderType(type);
        orderDetail.setClientName(clientFirstName + " " + clientLastName);
        for (Item item:items) {
            if(item.getType().equals("delivery_fee")){
                //log.info("***getOrderDetail***: order:" + id + " getPrice:" + item.getPrice() + " getItemDiscount:" + item.getItemDiscount() + " getTotalItemPrice:" + item.getTotalItemPrice());
                orderDetail.setDeliveryFee(item.getTotalItemPrice());
                //log.info("***getOrderDetail***: order:" + id + " setDeliveryFee after:" + orderDetail.getDeliveryFee());
            }else if(item.getType().equals("service_fee_subtotal")){
                orderDetail.setServiceFee(item.getPrice());
            }else if(item.getType().equals("tip")){
                orderDetail.setTip(item.getTotalItemPrice());
            }
        }
        return orderDetail;
    }

    @Override
    public String toString() {
        return "GlobalOrder{" +
                "instructions='" + instructions + '\'' +
                ", coupons=" + coupons +
                ", taxList=" + taxList +
                ", missedReason=" + missedReason +
                ", billingDetails=" + billingDetails +
                ", fulfillmentOption=" + fulfillmentOption +
                ", tableNumber=" + tableNumber +
                ", ready=" + ready +
                ", updatedAt='" + updatedAt + '\'' +
                ", id=" + id +
                ", totalPrice=" + totalPrice +
                ", subTotalPrice=" + subTotalPrice +
                ", taxValue=" + taxValue +
                ", persons=" + persons +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", clientFirstName='" + clientFirstName + '\'' +
                ", clientLastName='" + clientLastName + '\'' +
                ", clientEmail='" + clientEmail + '\'' +
                ", clientPhone='" + clientPhone + '\'' +
                ", restaurantName='" + restaurantName + '\'' +
                ", currency='" + currency + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", source='" + source + '\'' +
                ", pinSkipped=" + pinSkipped +
                ", acceptedAt='" + acceptedAt + '\'' +
                ", taxType='" + taxType + '\'' +
                ", taxName='" + taxName + '\'' +
                ", fulfillAt='" + fulfillAt + '\'' +
                ", clientLanguage='" + clientLanguage + '\'' +
                ", integrationPaymentProvider=" + integrationPaymentProvider +
                ", integrationPaymentAmount=" + integrationPaymentAmount +
                ", reference=" + reference +
                ", restaurantId=" + restaurantId +
                ", clientId=" + clientId +
                ", restaurantPhone='" + restaurantPhone + '\'' +
                ", restaurantTimezone='" + restaurantTimezone + '\'' +
                ", cardType=" + cardType +
                ", usedPaymentMethods=" + usedPaymentMethods +
                ", companyAccountId=" + companyAccountId +
                ", posSystemId=" + posSystemId +
                ", restaurantKey='" + restaurantKey + '\'' +
                ", restaurantCountry='" + restaurantCountry + '\'' +
                ", restaurantCity='" + restaurantCity + '\'' +
                ", restaurantState='" + restaurantState + '\'' +
                ", restaurantZipcode='" + restaurantZipcode + '\'' +
                ", restaurantStreet='" + restaurantStreet + '\'' +
                ", restaurantLatitude='" + restaurantLatitude + '\'' +
                ", restaurantLongitude='" + restaurantLongitude + '\'' +
                ", clientMarketingConsent=" + clientMarketingConsent +
                ", restaurantToken='" + restaurantToken + '\'' +
                ", gatewayTransactionId=" + gatewayTransactionId +
                ", gatewayType=" + gatewayType +
                ", apiVersion=" + apiVersion +
                ", payment='" + payment + '\'' +
                ", forLater=" + forLater +
                ", clientAddress='" + clientAddress + '\'' +
                ", clientAddressParts=" + clientAddressParts +
                ", items=" + items +
                '}';
    }
}
