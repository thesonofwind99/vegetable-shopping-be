package com.vegetableshoppingbe.exception;

public class ValidationException extends RuntimeException{

        private String fieldValue;
        private String resourceName;
        private String fieldName;

        public ValidationException(String resourceName, String fieldName, String fieldValue) {
            super(String.format("%s: %s is invalid with value: '%s'", resourceName, fieldName, fieldValue));
            this.fieldValue = fieldValue;
            this.resourceName = resourceName;
            this.fieldName = fieldName;
        }
}
