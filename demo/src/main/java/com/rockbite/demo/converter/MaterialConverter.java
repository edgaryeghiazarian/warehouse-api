package com.rockbite.demo.converter;

import com.rockbite.demo.entity.Material;
import com.rockbite.demo.model.MaterialDTO;
import org.springframework.stereotype.Component;

@Component
public class MaterialConverter implements Converter<Material, MaterialDTO> {
    @Override
    public Material convertToEntity(MaterialDTO model, Material entity) {
        return entity;
    }

    @Override
    public MaterialDTO convertToModel(Material entity, MaterialDTO model) {
        return model;
    }
}
