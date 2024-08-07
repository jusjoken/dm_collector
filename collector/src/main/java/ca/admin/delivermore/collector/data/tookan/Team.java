
package ca.admin.delivermore.collector.data.tookan;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.*;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "tags",
    "battery_usage",
    "team_id",
    "team_name",
    "address",
    "latitude",
    "longitude"
})
@Generated("jsonschema2pojo")
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Team {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(teamId, team.teamId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamId);
    }

    @JsonProperty("team_id")
    @Id
    private Long teamId;
    @JsonProperty("tags")
    private String tags;
    @JsonProperty("battery_usage")
    private Long batteryUsage;
    @JsonProperty("team_name")
    private String teamName;
    @JsonProperty("address")
    private String address;
    @JsonProperty("latitude")
    private String latitude;
    @JsonProperty("longitude")
    private String longitude;

    @JsonIgnore
    private Boolean active = Boolean.TRUE;

    @JsonProperty("tags")
    public String getTags() {
        return tags;
    }

    @JsonProperty("tags")
    public void setTags(String tags) {
        this.tags = tags;
    }

    @JsonProperty("battery_usage")
    public Long getBatteryUsage() {
        return batteryUsage;
    }

    @JsonProperty("battery_usage")
    public void setBatteryUsage(Long batteryUsage) {
        this.batteryUsage = batteryUsage;
    }

    @JsonProperty("team_id")
    public Long getTeamId() {
        return teamId;
    }

    @JsonProperty("team_id")
    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    @JsonProperty("team_name")
    public String getTeamName() {
        return teamName;
    }

    @JsonProperty("team_name")
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @JsonProperty("address")
    public String getAddress() {
        return address;
    }

    @JsonProperty("address")
    public void setAddress(String address) {
        this.address = address;
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

    public Team() {
    }

    public Team(Long teamId, String tags, Long batteryUsage, String teamName, String address, String latitude, String longitude) {
        this.teamId = teamId;
        this.tags = tags;
        this.batteryUsage = batteryUsage;
        this.teamName = teamName;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Boolean getActive() {
        if(active==null) return Boolean.TRUE;
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void updateTeamTookanOnly(Team updated){
        this.address = updated.address;
        this.tags = updated.tags;
        this.teamName = updated.teamName;
        this.batteryUsage = updated.batteryUsage;
        this.latitude = updated.latitude;
        this.longitude = updated.longitude;

    }

    @Override
    public String toString() {
        return "Team{" +
                "teamId=" + teamId +
                ", tags='" + tags + '\'' +
                ", batteryUsage=" + batteryUsage +
                ", teamName='" + teamName + '\'' +
                ", address='" + address + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", active=" + active +
                '}';
    }
}
