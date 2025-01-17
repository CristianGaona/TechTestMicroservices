package com.crisda24.neoris.transactionaccount.infrastructure.input.adapter.rest.impl;

import com.crisda24.neoris.transactionaccount.domain.common.dtos.GeneralResponseDto;
import com.crisda24.neoris.transactionaccount.domain.common.dtos.movement.MovementRangeDatesDto;
import com.crisda24.neoris.transactionaccount.domain.common.dtos.movement.MovementRequestDto;
import com.crisda24.neoris.transactionaccount.application.input.port.MovementService;
import com.crisda24.neoris.transactionaccount.infrastructure.input.adapter.exceptions.GeneralException;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/movements")
public class MovementController {

    private Logger logger = LoggerFactory.getLogger(MovementController.class);
    private final MovementService movementService;


    public MovementController(MovementService movementService) {
        this.movementService = movementService;
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createMovement(@Valid @RequestBody MovementRequestDto movementRequestDto, BindingResult bindingResult) throws Exception {
        ResponseEntity<?> errors = getResponseEntity(bindingResult);
        if (errors != null) return errors;
        movementService.create(movementRequestDto);
        return new ResponseEntity<>(new GeneralResponseDto("Movement successfully created"), HttpStatus.OK);
    }

    @GetMapping(value = "/reports")
    public ResponseEntity<?> reportsByDates(
            @RequestParam(required = false) String accountNumber,
            @RequestParam(required = false) Long clientId,
            @RequestParam String startDate,
            @RequestParam String endDate){
        if(accountNumber == null && clientId == null){
            throw new GeneralException("Account number or clientId must be set", HttpStatus.BAD_REQUEST);
        }
        List<MovementRangeDatesDto> reportsRange= movementService.retrieveMovemenstByDates(startDate, endDate, accountNumber, clientId);
        return new ResponseEntity<>(reportsRange, HttpStatus.OK);
    }

    @Nullable
    public static ResponseEntity<?> getResponseEntity(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> {
                String fieldName = error.getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            return ResponseEntity.badRequest().body(errors);
        }
        return null;
    }
}
