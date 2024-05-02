package com.rockbite.demo.observer;

import com.rockbite.demo.entity.Material;
import com.rockbite.demo.entity.MaterialType;

public interface WarehouseObserver {
    void update(Material material);
    void updateMaterialTransfer(MaterialType materialType, String sourceWarehouse, String destinationWarehouse, int quantity);
}
