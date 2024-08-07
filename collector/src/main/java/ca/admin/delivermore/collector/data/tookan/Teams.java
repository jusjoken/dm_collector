
package ca.admin.delivermore.collector.data.tookan;

import java.util.List;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "message",
    "status",
    "team"
})
@Generated("jsonschema2pojo")
public class Teams {

    @JsonProperty("message")
    private String message;
    @JsonProperty("status")
    private Long status;
    @JsonProperty("data")
    private List<Team> team;

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("status")
    public Long getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(Long status) {
        this.status = status;
    }

    @JsonProperty("team")
    public List<Team> getTeam() {
        return team;
    }

    @JsonProperty("team")
    public void setTeam(List<Team> team) {
        this.team = team;
    }

    public Teams() {
    }

    public Teams(String message, Long status, List<Team> team) {
        this.message = message;
        this.status = status;
        this.team = team;
    }

    @Override
    public String toString() {
        return "Teams{" +
                "message='" + message + '\'' +
                ", status=" + status +
                ", team=" + team +
                '}';
    }
}
