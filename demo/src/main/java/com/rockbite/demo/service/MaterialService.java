package com.rockbite.demo.service;

import com.rockbite.demo.entity.Material;
import com.rockbite.demo.entity.MaterialType;
import com.rockbite.demo.entity.Warehouse;
import com.rockbite.demo.exception.MaterialNotFoundException;
import com.rockbite.demo.exception.MaterialTypeNotFoundException;
import com.rockbite.demo.model.MaterialRegistrationRequest;
import com.rockbite.demo.repository.MaterialRepository;
import com.rockbite.demo.repository.MaterialTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MaterialService {
    private final MaterialRepository materialRepository;
    private final MaterialTypeRepository materialTypeRepository;

    @Autowired
    public MaterialService(MaterialRepository materialRepository, MaterialTypeRepository materialTypeRepository) {
        this.materialRepository = materialRepository;
        this.materialTypeRepository = materialTypeRepository;
    }

    @Transactional
    public Material saveMaterial(MaterialRegistrationRequest materialRegistry) throws MaterialTypeNotFoundException {
        Material newMaterial = new Material();
        MaterialType materialType = getMaterialType(materialRegistry.getMaterialTypeId());
        newMaterial.setMaterialType(materialType);
        newMaterial.setQuantity(materialRegistry.getQuantity());
        Material savedMaterial = materialRepository.save(newMaterial);
        return savedMaterial;
    }

    @Transactional
    public Material saveMaterialInWarehouse(MaterialType materialType, int quantity, Warehouse warehouse) {
        Material material = new Material();
        material.setMaterialType(materialType);
        material.setWarehouse(warehouse);
        material.setQuantity(quantity);
        Material savedMaterial = materialRepository.save(material);
        return savedMaterial;
    }

    public Material getMaterial(long id) {
        Optional<Material> optionalMaterial = materialRepository.findById(id);
        if (optionalMaterial.isEmpty()) {
            throw new MaterialNotFoundException("Material not found");
        }
        return optionalMaterial.get();
    }

    public MaterialType getMaterialType(long id) throws MaterialTypeNotFoundException {
        Optional<MaterialType> optional = materialTypeRepository.findById(id);
        if (optional.isEmpty()) {
            throw new MaterialTypeNotFoundException("Material type not found");
        }
        return optional.get();
    }

    @Transactional
    public MaterialType createMaterialType(MaterialType materialType) {
        MaterialType newMaterialType = new MaterialType();
        newMaterialType.setDescription(materialType.getDescription());
        newMaterialType.setIcon(materialType.getIcon());
        newMaterialType.setName(materialType.getName());
        newMaterialType.setMaxCapacity(materialType.getMaxCapacity());
        MaterialType savedMaterialType = materialTypeRepository.save(newMaterialType);
        return savedMaterialType;
    }
}
