package ca.admin.delivermore.collector.data.entity;

import java.io.Serializable;

public class TaskEntityPk implements Serializable {
    private String source;
    private Long sourceId;

    public TaskEntityPk() {
    }

    public TaskEntityPk(String source, Long sourceId) {
        this.source = source;
        this.sourceId = sourceId;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
