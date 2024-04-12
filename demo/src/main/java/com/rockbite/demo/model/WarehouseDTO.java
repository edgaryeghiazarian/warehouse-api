package com.rockbite.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class WarehouseDTO {
    private long id;

    private String name;
    private List<MaterialDTO> materialDTOList;

    @Override
    public String toString() {
        return "Warehouse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", list of materials=" + materialDTOList +
                '}';
    }
}
