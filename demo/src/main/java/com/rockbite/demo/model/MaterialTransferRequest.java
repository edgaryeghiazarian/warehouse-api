package com.rockbite.demo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaterialTransferRequest {
    private long sourceWarehouseId;
    private long destWarehouseId;
    private long materialTypeId;
    private int quantity;
}
