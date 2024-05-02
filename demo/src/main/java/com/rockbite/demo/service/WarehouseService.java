package com.rockbite.demo.service;

import com.rockbite.demo.entity.Material;
import com.rockbite.demo.entity.MaterialType;
import com.rockbite.demo.entity.Warehouse;
import com.rockbite.demo.exception.MaterialNotFoundException;
import com.rockbite.demo.exception.MaterialTypeNotFoundException;
import com.rockbite.demo.exception.WarehouseNotFoundException;
import com.rockbite.demo.observer.WarehouseObserver;
import com.rockbite.demo.observer.WarehouseSubject;
import com.rockbite.demo.repository.MaterialRepository;
import com.rockbite.demo.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WarehouseService implements WarehouseSubject {
    private List<WarehouseObserver> observers = new ArrayList<>();
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
                notifyObserver(m);
                break;
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
        notifyObserver(material);
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
    public Warehouse removeMaterialFromWarehouse(long warehouseId, long materialTypeId, int quantity) throws MaterialTypeNotFoundException {
        Warehouse warehouse = getWarehouse(warehouseId);
        List<Material> materials = warehouse.getMaterials();
        MaterialType materialType = materialService.getMaterialType(materialTypeId);

        for (Material localMaterial : materials) {
            if (localMaterial.getMaterialType().equals(materialType)) {
                int initialQuantity = localMaterial.getQuantity();
                localMaterial.setQuantity(initialQuantity-quantity);
                if (localMaterial.getQuantity() < 0) {
                    localMaterial.setQuantity(0);
                }
                notifyObserver(localMaterial);
                break;
            }
        }

        warehouse.setMaterials(materials);
        Warehouse updatedWarehouse = warehouseRepository.save(warehouse);
        return updatedWarehouse;
    }

    /**
     * This method transfers materials from on warehouse to another.
     * If source warehouse has less material quantity it will transfer the rest.
     * If destination warehouse has less available capacity than the transfer amount,
     * available amount for destination warehouse will be transferred from source warehouse,
     * and same amount will be removed from source warehouse.
     * */
    @Transactional
    public Warehouse transfer(Long sourceWarehouseId, Long destinationWarehouseId, Long materialTypeId, int quantity) throws MaterialTypeNotFoundException {
        Warehouse sourceWarehouse = getWarehouse(sourceWarehouseId);
        Warehouse destWarehouse = getWarehouse(destinationWarehouseId);
        MaterialType materialType = materialService.getMaterialType(materialTypeId);

        Material sourceWHmaterial = sourceWarehouse.getMaterials().stream()
                .filter(m -> m.getMaterialType().equals(materialType)).findFirst().orElseThrow(MaterialNotFoundException::new);

        if (sourceWHmaterial.getQuantity() < quantity) {
            quantity = sourceWHmaterial.getQuantity();
        }

        Warehouse updatedDestWarehouse = null;

        if (hasMaterial(destinationWarehouseId, materialTypeId)) {
            int availableCapacity = availableCapacity(destWarehouse, materialType);
            quantity = Math.min(availableCapacity, quantity);
            updatedDestWarehouse = addMaterialQuantityToWarehouse(destinationWarehouseId, materialTypeId, quantity);
        } else {
            updatedDestWarehouse = addNewMaterialToWarehouse(destinationWarehouseId, materialTypeId, quantity);
        }
        removeMaterialFromWarehouse(sourceWarehouseId, materialTypeId, quantity);
        notifyMaterialTransfer(materialType, sourceWarehouse.getName(), destWarehouse.getName(), quantity);
        return updatedDestWarehouse;
    }

    @Override
    public void registerObserver(WarehouseObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(WarehouseObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObserver(Material material) {
        for (WarehouseObserver observer : observers) {
            observer.update(material);
        }
    }

    @Override
    public void notifyMaterialTransfer(MaterialType materialType, String sourceWarehouse, String destinationWarehouse, int quantity) {
        for (WarehouseObserver observer : observers) {
            observer.updateMaterialTransfer(materialType, sourceWarehouse, destinationWarehouse, quantity);
        }
    }
}
