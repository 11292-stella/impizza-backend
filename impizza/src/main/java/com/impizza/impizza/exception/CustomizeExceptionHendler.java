package com.impizza.impizza.exception;





import com.impizza.impizza.model.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice  // gestisce le eccezioni è un controller per errori
public class CustomizeExceptionHendler {

    @ExceptionHandler(NotFoundException.class)//serve per mappare il metodo
    //che gestisce questa eccezione
    //quando in Spring si genera una eccezione di tipo NotFoundException
    //spring passerà l'oonere della risposta al client a questo metodo
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError notFoundExceptionHandler(NotFoundException e){

        ApiError error = new ApiError();
        error.setMessage(e.getMessage());
        error.setDataErrore(LocalDateTime.now());
        return error;

    }

    @ExceptionHandler(ValidationException.class)//serve per mappare il metodo
    //che gestisce questa eccezione
    //quando in Spring si genera una eccezione di tipo NotFoundException
    //spring passerà l'oonere della risposta al client a questo metodo
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError ValidationExceptionHandler(ValidationException e){

        ApiError error = new ApiError();
        error.setMessage(e.getMessage());
        error.setDataErrore(LocalDateTime.now());
        return error;

    }

    @ExceptionHandler(UnAuthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiError unAuthorizedExceptionHandler(UnAuthorizedException e) {
        ApiError error = new ApiError();
        error.setMessage(e.getMessage());
        error.setDataErrore(LocalDateTime.now());
        return error;
    }
}
