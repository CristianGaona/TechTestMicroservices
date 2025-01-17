package com.crisda24.neoris.transactionaccount.infrastructure.input.adapter.exceptions.rest.impl;

import com.crisda24.neoris.transactionaccount.domain.common.dtos.GeneralResponseDto;
import com.crisda24.neoris.transactionaccount.domain.common.dtos.account.AccountRequestDto;
import com.crisda24.neoris.transactionaccount.domain.common.dtos.account.AccountRequestPatchDto;
import com.crisda24.neoris.transactionaccount.domain.common.dtos.account.MergedAccountInfoDto;
import com.crisda24.neoris.transactionaccount.application.input.port.AccountService;
import com.crisda24.neoris.transactionaccount.infrastructure.input.adapter.exceptions.GeneralException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(value = "")
    public ResponseEntity<?> createAccount(@Valid @RequestBody AccountRequestDto accountRequestDto, BindingResult bindingResult) throws GeneralException{

        ResponseEntity<?> errors = MovementController.getResponseEntity(bindingResult);
        if (errors != null) return errors;
        accountService.createAccount(accountRequestDto);
        return new ResponseEntity<GeneralResponseDto>(new GeneralResponseDto("Account successfully created") , HttpStatus.CREATED);
    }

    @GetMapping(value = "")
    public ResponseEntity<?> retrieveAllAccounts(){
            List<MergedAccountInfoDto> accountList = accountService.retrieveAccounts();
            return new ResponseEntity<List<MergedAccountInfoDto>>(accountList, HttpStatus.OK);
    }

    @GetMapping(value = "/{accountNumber}")
    public ResponseEntity<?> retrieveByAccountNumber(@PathVariable String accountNumber){
        MergedAccountInfoDto accountInfoDto = accountService.retrieveAccountByAccountNumber(accountNumber);
        return new ResponseEntity<MergedAccountInfoDto>(accountInfoDto, HttpStatus.OK);
    }


    @DeleteMapping("{accountNumber}")
    public ResponseEntity<?> delete(@PathVariable String accountNumber){
        try {
            accountService.delete(accountNumber);
            return new ResponseEntity<GeneralResponseDto>(new GeneralResponseDto("Account successfully deleted"), HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<GeneralResponseDto>(new GeneralResponseDto(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("{accountNumber}")
    public ResponseEntity<?> updateAccountPatch(@PathVariable String accountNumber, @Valid @RequestBody AccountRequestPatchDto updateFields, BindingResult bindingResult ){
        ResponseEntity<?> errors = MovementController.getResponseEntity(bindingResult);
        if (errors != null) return errors;
        try{
            accountService.accountPatch(accountNumber, updateFields);
            return new ResponseEntity<>(new GeneralResponseDto("Account successfully updated"), HttpStatus.OK);
        }catch(EntityNotFoundException e){
            return new ResponseEntity<>(new GeneralResponseDto(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}
