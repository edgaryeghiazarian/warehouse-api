package com.rockbite.demo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaterialRegistrationRequest {
    private long materialTypeId;
    private int quantity;
}
