package com.rockbite.demo.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class WarehouseDTO {
    private long id;

    @NotEmpty(message = "Warehouse name is mandatory")
    private String name;
    private List<MaterialDTO> materialDTOList;

    @Override
    public String toString() {
        return "Warehouse{\n" +
                "id=" + id +
                "\nname='" + name +
                "\nlist of materials=" + materialDTOList.toString() +
                '}';
    }
}
