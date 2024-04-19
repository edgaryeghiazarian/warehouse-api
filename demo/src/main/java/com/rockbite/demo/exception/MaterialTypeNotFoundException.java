package com.rockbite.demo.exception;

public class MaterialTypeNotFoundException extends Throwable {
    public MaterialTypeNotFoundException(String message) {
        super(message);
    }

    public MaterialTypeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
