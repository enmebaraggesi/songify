package com.songify.infrastructure.crud.album.error;

import org.springframework.http.HttpStatus;

public record AlbumNotFoundResponseDto(HttpStatus status, String message) {

}
