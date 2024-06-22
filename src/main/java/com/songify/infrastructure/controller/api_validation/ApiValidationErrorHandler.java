package com.songify.infrastructure.controller.api_validation;

import com.songify.infrastructure.controller.SongsRestController;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice(assignableTypes = SongsRestController.class)
class ApiValidationErrorHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody // so it will send body properly
    @ResponseStatus(HttpStatus.BAD_REQUEST) // so it will send error instead of ok
    public ApiValidationErrorResponseDto handleValidationException(MethodArgumentNotValidException e) {
        return new ApiValidationErrorResponseDto(getErrorsFromException(e));
    }
    
    // method to collect all messages from validation error
    private List<String> getErrorsFromException(MethodArgumentNotValidException e) {
        return e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
    }
}
