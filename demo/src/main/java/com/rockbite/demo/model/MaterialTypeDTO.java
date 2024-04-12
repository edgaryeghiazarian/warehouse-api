package com.rockbite.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
