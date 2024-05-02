package com.rockbite.demo.observer;

import com.rockbite.demo.entity.Material;
import com.rockbite.demo.entity.MaterialType;
import com.rockbite.demo.observer.WarehouseObserver;
import org.springframework.stereotype.Component;

@Component
public class MaterialInventoryObserver implements WarehouseObserver {
    @Override
    public void update(Material material) {
        System.out.println("Inventory updated: Material quantity " + material.getQuantity() +
                "\n Warehouse id: " + material.getWarehouse().getId());
    }

    @Override
    public void updateMaterialTransfer(MaterialType materialType, String sourceWarehouse,
                                       String destinationWarehouse, int quantity) {

    }
}
