package com.rockbite.demo.observer;

import com.rockbite.demo.entity.Material;
import com.rockbite.demo.entity.MaterialType;
import com.rockbite.demo.observer.WarehouseObserver;
import org.springframework.stereotype.Component;


@Component
public class MaterialTransferObserver implements WarehouseObserver {
    @Override
    public void update(Material material) {

    }

    @Override
    public void updateMaterialTransfer(MaterialType materialType, String sourceWarehouse, String destinationWarehouse, int quantity) {
        System.out.println("From " + sourceWarehouse + " to " + destinationWarehouse +
                ":\n quantity: " + quantity);
    }
}
