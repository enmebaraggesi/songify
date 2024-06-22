package com.songify.infrastructure.controller.error;

import org.springframework.http.HttpStatus;

public record SongNotFoundErrorDto(String message, HttpStatus status) {

}
