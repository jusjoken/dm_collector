
package ca.admin.delivermore.collector.data.tookan;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "job_status",
    "job_count",
    "job_status_value"
})
@Generated("jsonschema2pojo")
public class TaskCount {

    @JsonProperty("job_status")
    private Integer jobStatus;
    @JsonProperty("job_count")
    private Integer jobCount;
    @JsonProperty("job_status_value")
    private String jobStatusValue;

    @JsonProperty("job_status")
    public Integer getJobStatus() {
        return jobStatus;
    }

    @JsonProperty("job_status")
    public void setJobStatus(Integer jobStatus) {
        this.jobStatus = jobStatus;
    }

    @JsonProperty("job_count")
    public Integer getJobCount() {
        return jobCount;
    }

    @JsonProperty("job_count")
    public void setJobCount(Integer jobCount) {
        this.jobCount = jobCount;
    }

    @JsonProperty("job_status_value")
    public String getJobStatusValue() {
        return jobStatusValue;
    }

    @JsonProperty("job_status_value")
    public void setJobStatusValue(String jobStatusValue) {
        this.jobStatusValue = jobStatusValue;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(TaskCount.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("jobStatus");
        sb.append('=');
        sb.append(((this.jobStatus == null)?"<null>":this.jobStatus));
        sb.append(',');
        sb.append("jobCount");
        sb.append('=');
        sb.append(((this.jobCount == null)?"<null>":this.jobCount));
        sb.append(',');
        sb.append("jobStatusValue");
        sb.append('=');
        sb.append(((this.jobStatusValue == null)?"<null>":this.jobStatusValue));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
