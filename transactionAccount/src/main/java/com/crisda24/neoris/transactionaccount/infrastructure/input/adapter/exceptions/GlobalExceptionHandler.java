package com.crisda24.neoris.transactionaccount.infrastructure.input.adapter.exceptions;

import com.crisda24.neoris.transactionaccount.domain.common.dtos.GeneralResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<GeneralResponseDto> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        return new ResponseEntity<GeneralResponseDto>(new GeneralResponseDto(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<GeneralResponseDto> handleHttpGeneral(GeneralException ex) {
        return new ResponseEntity<GeneralResponseDto>(new GeneralResponseDto(ex.getMessage()), ex.getStatus()
        );
    }

}
