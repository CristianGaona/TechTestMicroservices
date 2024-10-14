package com.example.clientmanagement.infrastructure.controller;

import com.example.clientmanagement.common.dto.interfaces.ClientListResponseDto;
import com.example.clientmanagement.common.dto.ClientRequestDto;
import com.example.clientmanagement.common.dto.ClientRequestPatchDto;
import com.example.clientmanagement.common.dto.GeneralResponseDto;
import com.example.clientmanagement.application.service.ClientService;
import jakarta.annotation.Nullable;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping(value = "")
    public ResponseEntity<?> createClient(@Valid @RequestBody ClientRequestDto clientRequestDto, BindingResult bindingResult) {
        ResponseEntity<?> errors = getResponseEntity(bindingResult);
        if (errors != null) return errors;
        clientService.create(clientRequestDto);
        return new ResponseEntity<>(new GeneralResponseDto("Client successfully created"), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id){
        try {
            clientService.delete(id);
            return new ResponseEntity<>(new GeneralResponseDto("Client successfully deleted"), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new GeneralResponseDto(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("{id}")
    public ResponseEntity<?> updateClient(@PathVariable Long id, @Valid @RequestBody ClientRequestDto clientRequestDto){
        try {
            clientService.update(id, clientRequestDto);
            return new ResponseEntity<>(new GeneralResponseDto("Client successfully updated"), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new GeneralResponseDto(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> updateClientPatch(@PathVariable Long id, @Valid @RequestBody ClientRequestPatchDto updateFields, BindingResult bindingResult){

        ResponseEntity<?> errors = getResponseEntity(bindingResult);
        if (errors != null) return errors;
        try {
            clientService.updatePatch(id, updateFields);
            return new ResponseEntity<>(new GeneralResponseDto("Client successfully updated"), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new GeneralResponseDto(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("")
    public ResponseEntity<?> findAllClients(){
        try {
            List<ClientListResponseDto> clientList = clientService.findAll();
            return new ResponseEntity<>(clientList, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new GeneralResponseDto(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    @Nullable
    private ResponseEntity<?> getResponseEntity(BindingResult bindingResult) {
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
