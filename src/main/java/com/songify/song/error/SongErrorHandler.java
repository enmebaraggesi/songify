package com.songify.song.error;

import com.songify.song.controller.SongsRestController;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
