package com.rockbite.demo.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaterialTransferRequest {
    @NotNull
    @Positive(message = "Source warehouse id is mandatory and should be positive")
    private long sourceWarehouseId;

    @NotNull
    @Positive(message = "Destination warehouse id is mandatory and should be positive")
    private long destWarehouseId;

    @NotNull
    @Positive(message = "Material type id is mandatory and should be positive")
    private long materialTypeId;

    @Positive(message = "Quantity should be a positive number")
    private int quantity;
}
