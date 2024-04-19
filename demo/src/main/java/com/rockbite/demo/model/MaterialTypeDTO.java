package com.rockbite.demo.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MaterialTypeDTO {
    private long id;
    private String name;
    private String description;
    private String icon;
    private int maxCapacity;
}
