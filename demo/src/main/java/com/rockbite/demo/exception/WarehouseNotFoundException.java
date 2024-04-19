package com.rockbite.demo.exception;

public class WarehouseNotFoundException extends RuntimeException {
    public WarehouseNotFoundException(String message) {
        super(message);
    }

    public WarehouseNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
