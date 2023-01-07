package ca.admin.delivermore.collector.data.entity;

import java.io.Serializable;
import java.util.Objects;

public class SettingEntityPk implements Serializable {
    private String section;
    private String name;

    public SettingEntityPk(String section, String name) {
        section = section;
        name = name;
    }

    public SettingEntityPk() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SettingEntityPk that = (SettingEntityPk) o;
        return Objects.equals(section, that.section) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(section, name);
    }
}
