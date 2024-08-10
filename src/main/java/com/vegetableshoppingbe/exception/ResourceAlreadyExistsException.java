package com.vegetableshoppingbe.exception;

public class ResourceAlreadyExistsException extends RuntimeException{
    private String fieldValue;
    private String resourceName;
    private String fieldName;

    public ResourceAlreadyExistsException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s with %s : '%s' already exists", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
