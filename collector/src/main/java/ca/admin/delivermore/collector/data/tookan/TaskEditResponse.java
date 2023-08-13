
package ca.admin.delivermore.collector.data.tookan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "message",
        "status",
        "data"
})
@Generated("jsonschema2pojo")
public class TaskEditResponse {

    @JsonProperty("message")
    private String message;
    @JsonProperty("status")
    private Long status;
    @JsonProperty("data")
    private Object data = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public TaskEditResponse() {
    }

    /**
     *
     * @param data
     * @param message
     * @param status
     */
    public TaskEditResponse(String message, Long status, Object data) {
        super();
        this.message = message;
        this.status = status;
        this.data = data;
    }

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

    @JsonProperty("data")
    public Object getData() {
        return data;
    }

    @JsonProperty("data")
    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "TaskEditResponse{" +
                "message='" + message + '\'' +
                ", status=" + status +
                ", data=" + data +
                ", additionalProperties=" + additionalProperties +
                '}';
    }

}
