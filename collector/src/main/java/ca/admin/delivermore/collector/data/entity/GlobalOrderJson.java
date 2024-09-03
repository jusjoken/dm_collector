package ca.admin.delivermore.collector.data.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class GlobalOrderJson {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime lastUpdated = LocalDateTime.now();

    @Column(columnDefinition="LONGTEXT")
    private String Json;

    public enum Status{
        NEW, INPROGRESS, COMPLETE;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJson() {
        return Json;
    }

    public void setJson(String json) {
        Json = json;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        return "GlobalOrderJson{" +
                "id=" + id +
                ", status=" + status +
                ", lastUpdated=" + lastUpdated +
                ", Json='" + Json + '\'' +
                '}';
    }
}
