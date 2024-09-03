package ca.admin.delivermore.collector.data.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@IdClass(SettingEntityPk.class)
public class SettingEntity {

    @Id
    private String section;
    @Id
    private String name;

    private String description = null;

    @Column(columnDefinition="varchar(1000)")
    private String value;

    public enum ValueType{
        STRING, LIST, DOUBLE, INTEGER;
    }

    @Enumerated(EnumType.STRING)
    private ValueType valueType = ValueType.STRING;

    public SettingEntity() {
    }

    public SettingEntity(String section, String name, String description, String value, ValueType valueType) {
        this.section = section;
        this.name = name;
        this.description = description;
        this.value = value;
        this.valueType = valueType;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<String> getValueAsList(){
        if(this.value==null){
            return new ArrayList<>();
        }
        List<String> items= Stream.of(this.value.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
        return items;
    }

    public void setValue(List<String> value) {
        if(value==null){
            this.value = null;
        }else{
            this.value = String.join(",", value);;
        }
    }

    public Double getValueAsDouble(){
        if(this.value==null) return 0.0;
        return Double.valueOf(this.value);
    }

    public void setValue(Double value) {
        if(value==null){
            this.value = "0.0";
        }else{
            this.value = String.valueOf(value);
        }
    }

    public Integer getValueAsInteger(){
        if(this.value==null) return 0;
        return Integer.valueOf(this.value);
    }

    public void setValue(Integer value) {
        if(value==null){
            this.value = "0";
        }else{
            this.value = String.valueOf(value);
        }
    }

    public ValueType getValueType() {
        return valueType;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }

    @Override
    public String toString() {
        return "SettingEntity{" +
                "section='" + section + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", value='" + value + '\'' +
                ", valueType=" + valueType +
                '}';
    }
}
