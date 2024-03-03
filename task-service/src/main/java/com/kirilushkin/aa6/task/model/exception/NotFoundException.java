package com.kirilushkin.aa6.task.model.exception;

import java.util.UUID;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String entity, String id) {
        super(String.format("An object %s with id %s is not found", entity, id));
    }

    public NotFoundException(String entity, UUID id) {
        this(entity, id.toString());
    }
}
