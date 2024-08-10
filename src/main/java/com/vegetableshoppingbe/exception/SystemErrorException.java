package com.vegetableshoppingbe.exception;

public class SystemErrorException extends RuntimeException {

    private String fieldValue;
    private String resourceName;
    private String fieldName;
    
    public SystemErrorException(String message) {
        super(message);
    }

    public SystemErrorException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("Server error occurred while processing %s with %s : '%s'",
                resourceName, fieldName, fieldValue));
        this.fieldValue = fieldValue;
        this.resourceName = resourceName;
        this.fieldName = fieldName;
    }
}
