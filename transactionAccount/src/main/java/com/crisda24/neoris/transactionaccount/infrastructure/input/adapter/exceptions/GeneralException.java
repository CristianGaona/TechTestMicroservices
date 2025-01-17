package com.crisda24.neoris.transactionaccount.infrastructure.input.adapter.exceptions;

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
