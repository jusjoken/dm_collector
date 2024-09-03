package ca.admin.delivermore.collector.data.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class JobEntity {

    @Id
    private String Id;

    @Column(name = "name")
    private String name;

    @Column(name = "enabled")
    private Boolean enabled = Boolean.TRUE;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Column(name = "last_run")
    private LocalDateTime lastRun;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getLastRun() {
        return lastRun;
    }

    public void setLastRun(LocalDateTime lastRun) {
        this.lastRun = lastRun;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    @Override
    public String toString() {
        return "JobEntity{" +
                "Id='" + Id + '\'' +
                ", name='" + name + '\'' +
                ", enabled=" + enabled +
                ", lastRun=" + lastRun +
                ", id='" + getId() + '\'' +
                '}';
    }
}
