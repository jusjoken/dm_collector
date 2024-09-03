package ca.admin.delivermore.collector.data.tookan;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "job_id",
        "job_description"
})
@Generated("jsonschema2pojo")
public class TaskByOrderDetail {

    @JsonProperty("job_id")
    private Long jobId;
    @JsonProperty("job_description")
    private String jobDescription;

    @JsonProperty("job_id")
    public Long getJobId() {
        return jobId;
    }

    @JsonProperty("job_id")
    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    @JsonProperty("job_description")
    public String getJobDescription() {
        return jobDescription;
    }

    @JsonProperty("job_description")
    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

}