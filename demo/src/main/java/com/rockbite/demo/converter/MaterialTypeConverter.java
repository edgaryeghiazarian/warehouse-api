package com.rockbite.demo.converter;

import com.rockbite.demo.entity.MaterialType;
import com.rockbite.demo.model.MaterialTypeDTO;
import org.springframework.stereotype.Component;

@Component
public class MaterialTypeConverter implements Converter<MaterialType, MaterialTypeDTO> {
    @Override
    public MaterialType convertToEntity(MaterialTypeDTO model, MaterialType entity) {
        entity.setMaxCapacity(model.getMaxCapacity());
        entity.setIcon(model.getIcon());
        entity.setName(model.getName());
        entity.setDescription(model.getDescription());
        return entity;
    }

    @Override
    public MaterialTypeDTO convertToModel(MaterialType entity, MaterialTypeDTO model) {
        model.setDescription(entity.getDescription());
        model.setIcon(entity.getIcon());
        model.setName(entity.getName());
        model.setMaxCapacity(entity.getMaxCapacity());
        model.setId(entity.getId());
        return model;
    }
}
