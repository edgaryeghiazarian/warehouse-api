package com.rockbite.demo.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MaterialTypeDTO {
    private long id;

    @NotEmpty(message = "Material type name is mandatory")
    private String name;

    @NotEmpty(message = "Material description is mandatory")
    private String description;
    private String icon;

    @NotNull
    @Positive(message = "Material type max capacity is mandatory and should be positive")
    private int maxCapacity;

    @Override
    public String toString() {
        return "MaterialType{\n" +
                "id= " + id +
                "\nname= " + name +
                "\ndescription= " + description +
                "\nicon= " + icon +
                "\nmaxCapacity= " + maxCapacity +
                '}';
    }
}
