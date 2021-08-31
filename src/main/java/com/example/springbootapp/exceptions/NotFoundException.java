package com.example.springbootapp.exceptions;

public class NotFoundException extends RuntimeException {
    public<T> NotFoundException(Long id, T item ) {
        super(String.format("%s with id:%d is not found", item, id));
    }
}
