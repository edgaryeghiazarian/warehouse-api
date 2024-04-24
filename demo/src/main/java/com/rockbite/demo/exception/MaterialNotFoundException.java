package com.rockbite.demo.exception;

public class MaterialNotFoundException extends RuntimeException {

    public MaterialNotFoundException(String message) {
        super(message);
    }

    public MaterialNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MaterialNotFoundException() {

    }
}
