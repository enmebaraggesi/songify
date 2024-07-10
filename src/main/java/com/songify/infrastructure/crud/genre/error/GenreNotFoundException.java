package com.songify.infrastructure.crud.genre.error;

public class GenreNotFoundException extends RuntimeException {
    
    public GenreNotFoundException() {
        super("Genre not found");
    }
}
