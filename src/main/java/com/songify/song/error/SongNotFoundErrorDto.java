package com.songify.song.error;

import org.springframework.http.HttpStatus;

public record SongNotFoundErrorDto(String message, HttpStatus status) {

}
