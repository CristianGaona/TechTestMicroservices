package com.example.clientmanagement.domain.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class GeneralException extends RuntimeException{
    private String message;
    private HttpStatus status;

    public GeneralException(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }
}
