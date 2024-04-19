package com.rockbite.demo.service;

import com.rockbite.demo.entity.Material;
import com.rockbite.demo.entity.MaterialType;
import com.rockbite.demo.entity.Warehouse;
import com.rockbite.demo.exception.MaterialTypeNotFoundException;
import com.rockbite.demo.exception.WarehouseNotFoundException;
import com.rockbite.demo.model.WarehouseRegistrationRequest;
import com.rockbite.demo.repository.MaterialRepository;
import com.rockbite.demo.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final MaterialService materialService;

    @Autowired
    public WarehouseService(WarehouseRepository warehouseRepository, MaterialService materialService, MaterialRepository materialRepository) {
        this.warehouseRepository = warehouseRepository;
        this.materialService = materialService;
    }

    @Transactional
    public Warehouse createWarehouse(String name) {
        Warehouse warehouse = new Warehouse();
        warehouse.setName(name);
        Warehouse newWarehouse = warehouseRepository.save(warehouse);
        return newWarehouse;
    }

    public Warehouse getWarehouse(long id) {
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(id);
        if (optionalWarehouse.isEmpty()) {
            throw new WarehouseNotFoundException("Warehouse not found");
        }
        return optionalWarehouse.get();
    }

    /**
     * Method is used for adding material to warehouse
     * Case: warehouse already contains the material
     * */
    @Transactional
    public Warehouse addMaterialQuantityToWarehouse(long warehouseId, long materialTypeId, int quantity) throws MaterialTypeNotFoundException {
        Warehouse warehouse = getWarehouse(warehouseId);

        MaterialType materialType = materialService.getMaterialType(materialTypeId);
        int availableCapacity = availableCapacity(warehouse, materialType);
        if (quantity >= availableCapacity) {
            quantity = availableCapacity;
        }
        List<Material> materials = warehouse.getMaterials();
        for (Material m : materials) {
            if (m.getMaterialType().equals(materialType)) {
                int originalQuantity = m.getQuantity();
                m.setQuantity(originalQuantity + quantity);
            }
        }
        warehouse.setMaterials(materials);
        Warehouse updatedWarehouse = warehouseRepository.save(warehouse);
        return updatedWarehouse;
    }

    /**
     * Method is used for adding material to warehouse
     * Case: warehouse does not contain the material
     * */
    @Transactional
    public Warehouse addNewMaterialToWarehouse(long warehouseId, long materialTypeId, int quantity) throws MaterialTypeNotFoundException {
        Warehouse warehouse = getWarehouse(warehouseId);
        List<Material> materials = warehouse.getMaterials();
        MaterialType materialType = materialService.getMaterialType(materialTypeId);
        int maxCapacity = materialType.getMaxCapacity();
        if (maxCapacity < quantity) {
            quantity = maxCapacity;
        }
        Material material = materialService.saveMaterialInWarehouse(materialType, quantity, warehouse);
        materials.add(material);
        warehouse.setMaterials(materials);
        Warehouse updatedWarehouse = warehouseRepository.save(warehouse);
        return updatedWarehouse;
    }

    public int availableCapacity (Warehouse warehouse, MaterialType materialType) {
        List<Material> materials = warehouse.getMaterials();
        for (Material localMaterial : materials) {
            if (localMaterial.getMaterialType().equals(materialType)) {
                return materialType.getMaxCapacity() - localMaterial.getQuantity();
            }
        }
        return materialType.getMaxCapacity();
    }

    public boolean hasMaterial(long warehouseId, long materialTypeId) {
        Warehouse warehouse = getWarehouse(warehouseId);
        MaterialType materialType = null;
        try {
            materialType = materialService.getMaterialType(materialTypeId);
        } catch (MaterialTypeNotFoundException e) {
            throw new RuntimeException(e);
        }
        for (Material warehouseMaterial : warehouse.getMaterials()) {
            if (warehouseMaterial.getMaterialType().equals(materialType)) {
                return true;
            }
        }
        return false;
    }

    @Transactional
    public Warehouse removeMaterialFromWarehouse(long warehouseId, long materialId) {
        //todo corrections needed
        Warehouse warehouse = getWarehouse(warehouseId);
        List<Material> materials = warehouse.getMaterials();
        Material material = materialService.getMaterial(materialId);

        for (Material local : materials) {
            if (local.equals(material)) {
                int quantity = local.getQuantity() - material.getQuantity();
                local.setQuantity(quantity);
                if (quantity < 0) {
                    materials.remove(local);
                }
                break;
            }
        }
        warehouse.setMaterials(materials);
        Warehouse updatedWarehouse = warehouseRepository.save(warehouse);
        return updatedWarehouse;
    }

    public void transfer(Long sourceWarehouseId, Long destinationWarehouseId, Long materialId, int quantity) {
        Warehouse sourceWarehouse = getWarehouse(sourceWarehouseId);
        Warehouse destWarehouse = getWarehouse(destinationWarehouseId);
        Material material = materialService.getMaterial(materialId);

        Material sourceMaterial = sourceWarehouse.getMaterials().stream()
                .filter(material1 -> material1.equals(material)).findAny().get();

        if (sourceMaterial.getQuantity() < quantity) {
            throw new IllegalArgumentException("Insufficient amount of quantity");
        }

        //todo not complete



    }
}
