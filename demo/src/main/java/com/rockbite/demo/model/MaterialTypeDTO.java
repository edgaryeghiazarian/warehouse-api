package com.rockbite.demo.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MaterialTypeDTO {
    private long id;
    private String name;
    private String description;
    private String icon;
    private int maxCapacity;

    @Override
    public String toString() {
        return "MaterialType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", icon='" + icon + '\'' +
                ", maxCapacity=" + maxCapacity +
                '}';
    }
}
