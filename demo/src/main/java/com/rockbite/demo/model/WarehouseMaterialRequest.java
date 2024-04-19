package com.rockbite.demo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WarehouseMaterialRequest {
    private long warehouseId;
    private long materialTypeId;
    private int quantity;
}
