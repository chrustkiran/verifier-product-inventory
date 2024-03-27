package org.example.exceptions;

public class NoRecordFoundException extends InventoryException {
    public NoRecordFoundException(String message) {
        super(message);
    }
}
