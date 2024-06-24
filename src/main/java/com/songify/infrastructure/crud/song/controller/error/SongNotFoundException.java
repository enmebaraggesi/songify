package com.songify.infrastructure.crud.song.controller.error;

public class SongNotFoundException extends RuntimeException {
    
    public SongNotFoundException(String message) {
        super(message);
    }
}
