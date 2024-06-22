package com.songify.infrastructure.controller.error;

public class SongNotFoundException extends RuntimeException {
    
    public SongNotFoundException(String message) {
        super(message);
    }
}
