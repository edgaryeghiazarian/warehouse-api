package com.rockbite.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MaterialDTO {
    private long id;
    private MaterialTypeDTO materialTypeDTO;
    private int quantity;

    @Override
    public String toString() {
        return "MaterialDTO{" +
                "id=" + id +
                ", materialTypeDTO=" + materialTypeDTO +
                ", quantity=" + quantity +
                '}';
    }
}
