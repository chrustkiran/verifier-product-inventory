package org.example.exceptions;

public class DuplicateIdException extends InventoryException {
    public DuplicateIdException(String message) {
        super(message);
    }
}
