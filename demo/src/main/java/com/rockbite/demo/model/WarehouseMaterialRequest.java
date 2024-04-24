package com.rockbite.demo.model;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WarehouseMaterialRequest {
    @NotNull
    @Positive(message = "Warehouse id is mandatory and should be positive")
    private long warehouseId;

    @NotNull
    @Positive(message = "Material type id is mandatory and should be positive")
    private long materialTypeId;

    @Positive
    private int quantity;
}
