package com.vegetableshoppingbe.exception;

public class ResourceNotFoundException extends RuntimeException{

    private String fieldValue;
    private String resourceName;
    private String fieldName;

    public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
        this.fieldValue = fieldValue;
        this.resourceName = resourceName;
        this.fieldName = fieldName;
    }
}
