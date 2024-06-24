package com.songify.infrastructure.crud.song.controller.error;

import org.springframework.http.HttpStatus;

public record SongNotFoundErrorDto(String message, HttpStatus status) {

}
