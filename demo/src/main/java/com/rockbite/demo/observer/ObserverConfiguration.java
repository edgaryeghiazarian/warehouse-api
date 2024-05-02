package com.rockbite.demo.observer;

import com.rockbite.demo.service.WarehouseService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObserverConfiguration {
    @Autowired
    private MaterialInventoryObserver materialInventoryObserver;

    @Autowired
    private MaterialTransferObserver materialTransferObserver;

    @Autowired
    private WarehouseService warehouseService;

    @PostConstruct
    public void registerObservers() {
        warehouseService.registerObserver(materialInventoryObserver);
        warehouseService.registerObserver(materialTransferObserver);
    }
}
