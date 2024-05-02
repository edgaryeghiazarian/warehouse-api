package com.rockbite.demo.observer;

import com.rockbite.demo.entity.Material;
import com.rockbite.demo.entity.MaterialType;

public interface WarehouseSubject {
    void registerObserver(WarehouseObserver observer);
    void removeObserver(WarehouseObserver observer);
    void notifyObserver(Material material);
    void notifyMaterialTransfer(MaterialType materialType, String sourceWarehouse, String destinationWarehouse, int quantity);
}
