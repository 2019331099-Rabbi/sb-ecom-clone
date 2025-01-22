package com.ecommerce.sbecom.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    String resourceName;
    String field;
    Long fieldId;

    public ResourceNotFoundException(String resourceName, String field, Long fieldId) {
        super(String.format("Resource %s not found with %s: %d", resourceName, field, fieldId));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldId = fieldId;
    }

    public ResourceNotFoundException() {
    }
}
