package com.rockbite.demo.converter;

import com.rockbite.demo.entity.Material;
import com.rockbite.demo.entity.Warehouse;
import com.rockbite.demo.model.MaterialDTO;
import com.rockbite.demo.model.WarehouseDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WarehouseConverter implements Converter<Warehouse, WarehouseDTO>{
    @Override
    public Warehouse convertToEntity(WarehouseDTO model, Warehouse entity) {
        entity.setName(model.getName());
        return entity;
    }

    @Override
    public WarehouseDTO convertToModel(Warehouse entity, WarehouseDTO model) {
        model.setName(entity.getName());

        MaterialConverter materialConverter = new MaterialConverter();
        List<MaterialDTO> materialDTOList = new ArrayList<>();
        for (Material material : entity.getMaterials()) {
            MaterialDTO materialDTO = materialConverter.convertToModel(material, new MaterialDTO());
            materialDTOList.add(materialDTO);
        }
        model.setMaterialDTOList(materialDTOList);

        return model;
    }
}
