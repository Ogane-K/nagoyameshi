package com.example.nagoyameshi.exception;

public class DuplicateRestaurantNameException extends RuntimeException {
    public DuplicateRestaurantNameException(String message) {
        super(message);
    }
}
