package com.songify.infrastructure.crud.album.error;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
class AlbumErrorHandler {
    
    @ExceptionHandler(AlbumNotFoundException.class)
    public ResponseEntity<AlbumNotFoundResponseDto> handleAlbumNotFoundException(AlbumNotFoundException e) {
        log.error(e);
        AlbumNotFoundResponseDto responseDto = new AlbumNotFoundResponseDto(HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(responseDto);
    }
}
