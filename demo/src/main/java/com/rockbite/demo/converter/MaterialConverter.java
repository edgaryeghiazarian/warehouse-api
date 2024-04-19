package com.rockbite.demo.converter;

import com.rockbite.demo.entity.Material;
import com.rockbite.demo.model.MaterialDTO;
import com.rockbite.demo.model.MaterialTypeDTO;
import org.springframework.stereotype.Component;

@Component
public class MaterialConverter implements Converter<Material, MaterialDTO> {
    @Override
    public Material convertToEntity(MaterialDTO model, Material entity) {
        return entity;
    }

    @Override
    public MaterialDTO convertToModel(Material entity, MaterialDTO model) {
        MaterialTypeConverter materialTypeConverter = new MaterialTypeConverter();
        MaterialTypeDTO materialTypeDTO = materialTypeConverter.convertToModel(entity.getMaterialType(), new MaterialTypeDTO());
        model.setMaterialTypeDTO(materialTypeDTO);
        model.setId(entity.getId());
        model.setQuantity(entity.getQuantity());
        return model;
    }
}
