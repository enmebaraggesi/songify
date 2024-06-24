package com.songify.infrastructure.crud.song.controller.error;

import com.songify.infrastructure.crud.song.controller.SongsRestController;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Log4j2
@ControllerAdvice(assignableTypes = SongsRestController.class)
public class SongErrorHandler {
    
    @ExceptionHandler(SongNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public SongNotFoundErrorDto handleException(SongNotFoundException e) {
        log.warn("SongNotFoundException while accessing song");
        return new SongNotFoundErrorDto(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
