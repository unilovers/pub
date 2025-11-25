package com.grupo_5.pub.Config;

import com.grupo_5.pub.Model.ErroResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ErroResponse tratarErros(ResponseStatusException ex) {
        return new ErroResponse(
                ex.getReason(),
                ex.getStatusCode().value()
        );
    }
}
