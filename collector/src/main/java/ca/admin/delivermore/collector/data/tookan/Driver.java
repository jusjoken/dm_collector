
package ca.admin.delivermore.collector.data.tookan;

import javax.annotation.Generated;
import javax.persistence.*;

import ca.admin.delivermore.collector.data.Role;
import ca.admin.delivermore.collector.data.entity.TaskEntity;
import com.fasterxml.jackson.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "fleet_id",
    "device_type",
    "total_rating",
    "total_rated_tasks",
    "is_active",
    "has_gps_accuracy",
    "username",
    "name",
    "login_id",
    "transport_type",
    "transport_desc",
    "license",
    "email",
    "phone",
    "battery_level",
    "registration_status",
    "latitude",
    "longitude",
    "tags",
    "fleet_thumb_image",
    "fleet_image",
    "status",
    "timezone",
    "fleet_type",
    "block_reason",
    "is_available"
})
@Generated("jsonschema2pojo")
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Driver {

    @JsonProperty("fleet_id")
    @Id
    private Long fleetId;
    @JsonProperty("device_type")
    private Long deviceType;
    @JsonProperty("total_rating")
    private Long totalRating;
    @JsonProperty("total_rated_tasks")
    private Long totalRatedTasks;
    @JsonProperty("is_active")
    private Long isActive;
    @JsonProperty("has_gps_accuracy")
    private Long hasGpsAccuracy;
    @JsonProperty("username")
    private String username;
    @JsonProperty("name")
    private String name;
    @JsonProperty("login_id")
    private String loginId;
    @JsonProperty("transport_type")
    private Long transportType;
    @JsonProperty("transport_desc")
    private String transportDesc;
    @JsonProperty("license")
    private String license;
    @JsonProperty("email")
    private String email;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("battery_level")
    private Long batteryLevel;
    @JsonProperty("registration_status")
    private Long registrationStatus;
    @JsonProperty("latitude")
    private String latitude;
    @JsonProperty("longitude")
    private String longitude;
    @JsonProperty("tags")
    private String tags;
    @JsonProperty("fleet_thumb_image")
    private String fleetThumbImage;
    @JsonProperty("fleet_image")
    private String fleetImage;
    @JsonProperty("status")
    private Long status;
    @JsonProperty("timezone")
    private String timezone;
    @JsonProperty("fleet_type")
    private Long fleetType;
    /*
    @JsonProperty("block_reason")
    @OneToOne(targetEntity = BlockReason.class)
    private BlockReason blockReason;
     */
    @JsonProperty("is_available")
    private Long isAvailable;
    /*
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
     */

    @JsonIgnore
    private String hashedPassword;

    @JsonIgnore
    private Boolean loginAllowed = Boolean.FALSE;
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Role> roles;

    @Transient
    private Logger log = LoggerFactory.getLogger(Driver.class);

    /**
     * No args constructor for use in serialization
     * 
     */
    public Driver() {
    }

    /**
     * 
     * @param isAvailable
     * @param registrationStatus
     * @param loginId
     * @param timezone
     * @param latitude
     * @param totalRatedTasks
     * @param transportDesc
     * @param fleetId
     * @param isActive
     * @param fleetType
     * @param transportType
     * @param fleetImage
     * @param blockReason
     * @param email
     * @param batteryLevel
     * @param longitude
     * @param deviceType
     * @param totalRating
     * @param hasGpsAccuracy
     * @param tags
     * @param license
     * @param phone
     * @param name
     * @param fleetThumbImage
     * @param username
     * @param status
     */
    public Driver(Long fleetId, Long deviceType, Long totalRating, Long totalRatedTasks, Long isActive, Long hasGpsAccuracy, String username, String name, String loginId, Long transportType, String transportDesc, String license, String email, String phone, Long batteryLevel, Long registrationStatus, String latitude, String longitude, String tags, String fleetThumbImage, String fleetImage, Long status, String timezone, Long fleetType, Long isAvailable) {
        super();
        this.fleetId = fleetId;
        this.deviceType = deviceType;
        this.totalRating = totalRating;
        this.totalRatedTasks = totalRatedTasks;
        this.isActive = isActive;
        this.hasGpsAccuracy = hasGpsAccuracy;
        this.username = username;
        this.name = name;
        this.loginId = loginId;
        this.transportType = transportType;
        this.transportDesc = transportDesc;
        this.license = license;
        this.email = email;
        this.phone = phone;
        this.batteryLevel = batteryLevel;
        this.registrationStatus = registrationStatus;
        this.latitude = latitude;
        this.longitude = longitude;
        this.tags = tags;
        this.fleetThumbImage = fleetThumbImage;
        this.fleetImage = fleetImage;
        this.status = status;
        this.timezone = timezone;
        this.fleetType = fleetType;
        this.isAvailable = isAvailable;
    }

    @JsonProperty("fleet_id")
    public Long getFleetId() {
        return fleetId;
    }

    @JsonProperty("fleet_id")
    public void setFleetId(Long fleetId) {
        this.fleetId = fleetId;
    }

    @JsonProperty("device_type")
    public Long getDeviceType() {
        return deviceType;
    }

    @JsonProperty("device_type")
    public void setDeviceType(Long deviceType) {
        this.deviceType = deviceType;
    }

    @JsonProperty("total_rating")
    public Long getTotalRating() {
        return totalRating;
    }

    @JsonProperty("total_rating")
    public void setTotalRating(Long totalRating) {
        this.totalRating = totalRating;
    }

    @JsonProperty("total_rated_tasks")
    public Long getTotalRatedTasks() {
        return totalRatedTasks;
    }

    @JsonProperty("total_rated_tasks")
    public void setTotalRatedTasks(Long totalRatedTasks) {
        this.totalRatedTasks = totalRatedTasks;
    }

    @JsonProperty("is_active")
    public Long getIsActive() {
        return isActive;
    }

    @JsonProperty("is_active")
    public void setIsActive(Long isActive) {
        this.isActive = isActive;
    }

    @JsonProperty("has_gps_accuracy")
    public Long getHasGpsAccuracy() {
        return hasGpsAccuracy;
    }

    @JsonProperty("has_gps_accuracy")
    public void setHasGpsAccuracy(Long hasGpsAccuracy) {
        this.hasGpsAccuracy = hasGpsAccuracy;
    }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("login_id")
    public String getLoginId() {
        return loginId;
    }

    @JsonProperty("login_id")
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    @JsonProperty("transport_type")
    public Long getTransportType() {
        return transportType;
    }

    @JsonProperty("transport_type")
    public void setTransportType(Long transportType) {
        this.transportType = transportType;
    }

    @JsonProperty("transport_desc")
    public String getTransportDesc() {
        return transportDesc;
    }

    @JsonProperty("transport_desc")
    public void setTransportDesc(String transportDesc) {
        this.transportDesc = transportDesc;
    }

    @JsonProperty("license")
    public String getLicense() {
        return license;
    }

    @JsonProperty("license")
    public void setLicense(String license) {
        this.license = license;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    @JsonProperty("phone")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonProperty("battery_level")
    public Long getBatteryLevel() {
        return batteryLevel;
    }

    @JsonProperty("battery_level")
    public void setBatteryLevel(Long batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    @JsonProperty("registration_status")
    public Long getRegistrationStatus() {
        return registrationStatus;
    }

    @JsonProperty("registration_status")
    public void setRegistrationStatus(Long registrationStatus) {
        this.registrationStatus = registrationStatus;
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

    @JsonProperty("tags")
    public String getTags() {
        return tags;
    }

    @JsonProperty("tags")
    public void setTags(String tags) {
        this.tags = tags;
    }

    @JsonProperty("fleet_thumb_image")
    public String getFleetThumbImage() {
        return fleetThumbImage;
    }

    @JsonProperty("fleet_thumb_image")
    public void setFleetThumbImage(String fleetThumbImage) {
        this.fleetThumbImage = fleetThumbImage;
    }

    @JsonProperty("fleet_image")
    public String getFleetImage() {
        return fleetImage;
    }

    @JsonProperty("fleet_image")
    public void setFleetImage(String fleetImage) {
        this.fleetImage = fleetImage;
    }

    @JsonProperty("status")
    public Long getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(Long status) {
        this.status = status;
    }

    @JsonProperty("timezone")
    public String getTimezone() {
        return timezone;
    }

    @JsonProperty("timezone")
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    @JsonProperty("fleet_type")
    public Long getFleetType() {
        return fleetType;
    }

    @JsonProperty("fleet_type")
    public void setFleetType(Long fleetType) {
        this.fleetType = fleetType;
    }

    /*
    @JsonProperty("block_reason")
    public BlockReason getBlockReason() {
        return blockReason;
    }

    @JsonProperty("block_reason")
    public void setBlockReason(BlockReason blockReason) {
        this.blockReason = blockReason;
    }

     */

    @JsonProperty("is_available")
    public Long getIsAvailable() {
        return isAvailable;
    }

    @JsonProperty("is_available")
    public void setIsAvailable(Long isAvailable) {
        this.isAvailable = isAvailable;
    }

    /*
    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
     */

    public String getHashedPassword() {
        if(hashedPassword==null){
            return "";
        }
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Boolean isUser(){
        return roles.contains(Role.USER);
    }
    public Boolean isManager(){
        return roles.contains(Role.MANAGER);
    }
    public Boolean isAdmin(){
        return roles.contains(Role.ADMIN);
    }

    public Boolean getLoginAllowed() {
        if(loginAllowed==null) return Boolean.FALSE;
        return loginAllowed;
    }

    public void setLoginAllowed(Boolean loginAllowed) {
        this.loginAllowed = loginAllowed;
    }

    public Boolean hasPassword(){
        if(this.hashedPassword==null || this.hashedPassword.isEmpty()) return Boolean.FALSE;
        return Boolean.TRUE;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "fleetId=" + fleetId +
                ", deviceType=" + deviceType +
                ", totalRating=" + totalRating +
                ", totalRatedTasks=" + totalRatedTasks +
                ", isActive=" + isActive +
                ", hasGpsAccuracy=" + hasGpsAccuracy +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", loginId='" + loginId + '\'' +
                ", transportType=" + transportType +
                ", transportDesc='" + transportDesc + '\'' +
                ", license='" + license + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", batteryLevel=" + batteryLevel +
                ", registrationStatus=" + registrationStatus +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", tags='" + tags + '\'' +
                ", fleetThumbImage='" + fleetThumbImage + '\'' +
                ", fleetImage='" + fleetImage + '\'' +
                ", status=" + status +
                ", timezone='" + timezone + '\'' +
                ", fleetType=" + fleetType +
                ", isAvailable=" + isAvailable +
                ", hashedPassword='" + hashedPassword + '\'' +
                ", loginAllowed=" + loginAllowed +
                ", roles=" + roles +
                ", user=" + isUser() +
                ", manager=" + isManager() +
                ", admin=" + isAdmin() +
                ", hasPassword=" + hasPassword() +
                ", isActivePresentation='" + getIsActivePresentation() + '\'' +
                '}';
    }

    public String getIsActivePresentation(){
        if(isActive.equals(0L)) return "Blocked";
        return "Active";
    }

    public Driver updateDriverIsActive(Driver checkDriver, List<Driver> validDrivers){
        for (Driver driver: validDrivers) {
            if (driver.getFleetId().equals(checkDriver.getFleetId())) {
                log.info("updateDriverIsActive: setting existing driver to active:" + driver.getName());
                checkDriver.setIsActive(1L);
                return checkDriver;
            }
        }
        log.info("updateDriverIsActive: driver no longer active:" + checkDriver.getName());
        checkDriver.setIsActive(0L);
        //do not disable login for the Local Admin user that will never be in Tookan
        if(checkDriver.fleetId>10L){ //allow 0 to 10 for local Ids Admin, Manager etc for testing
            //disable login if the user have been blocked in Tookan
            log.info("updateDriverIsActive: disabling login for:" + checkDriver.getName());
            checkDriver.setLoginAllowed(Boolean.FALSE);
        }
        return checkDriver;
    }

    public void updateDriverTookanOnly(Driver updated){
        this.deviceType = updated.deviceType;
        this.totalRating = updated.totalRating;
        this.totalRatedTasks = updated.totalRatedTasks;
        this.isActive = updated.isActive;
        this.hasGpsAccuracy = updated.hasGpsAccuracy;
        this.username = updated.username;
        this.name = updated.name;
        this.loginId = updated.loginId;
        this.transportType = updated.transportType;
        this.transportDesc = updated.transportDesc;
        this.license = updated.license;
        this.email = updated.email;
        this.phone = updated.phone;
        this.batteryLevel = updated.batteryLevel;
        this.registrationStatus = updated.registrationStatus;
        this.latitude = updated.latitude;
        this.longitude = updated.longitude;
        this.tags = updated.tags;
        this.fleetThumbImage = updated.fleetThumbImage;
        this.fleetImage = updated.fleetImage;
        this.status = updated.status;
        this.timezone = updated.timezone;
        this.fleetType = updated.fleetType;
        this.isAvailable = updated.isAvailable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return fleetId.equals(driver.fleetId) && Objects.equals(deviceType, driver.deviceType) && Objects.equals(totalRating, driver.totalRating) && Objects.equals(totalRatedTasks, driver.totalRatedTasks) && Objects.equals(isActive, driver.isActive) && Objects.equals(hasGpsAccuracy, driver.hasGpsAccuracy) && Objects.equals(username, driver.username) && Objects.equals(name, driver.name) && Objects.equals(loginId, driver.loginId) && Objects.equals(transportType, driver.transportType) && Objects.equals(transportDesc, driver.transportDesc) && Objects.equals(license, driver.license) && Objects.equals(email, driver.email) && Objects.equals(phone, driver.phone) && Objects.equals(batteryLevel, driver.batteryLevel) && Objects.equals(registrationStatus, driver.registrationStatus) && Objects.equals(latitude, driver.latitude) && Objects.equals(longitude, driver.longitude) && Objects.equals(tags, driver.tags) && Objects.equals(fleetThumbImage, driver.fleetThumbImage) && Objects.equals(fleetImage, driver.fleetImage) && Objects.equals(status, driver.status) && Objects.equals(timezone, driver.timezone) && Objects.equals(fleetType, driver.fleetType) && Objects.equals(isAvailable, driver.isAvailable) && Objects.equals(hashedPassword, driver.hashedPassword) && Objects.equals(loginAllowed, driver.loginAllowed) && Objects.equals(roles, driver.roles) && Objects.equals(log, driver.log);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fleetId, deviceType, totalRating, totalRatedTasks, isActive, hasGpsAccuracy, username, name, loginId, transportType, transportDesc, license, email, phone, batteryLevel, registrationStatus, latitude, longitude, tags, fleetThumbImage, fleetImage, status, timezone, fleetType, isAvailable, hashedPassword, loginAllowed, roles, log);
    }
}
