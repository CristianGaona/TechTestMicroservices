package com.crisda24.neoris.transactionaccount.application.common.dtos.account.interfaces;

import com.crisda24.neoris.transactionaccount.domain.enums.AccountType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountListDtoI {

    private String accountNumber;
    private AccountType accountType;
    private BigDecimal initBalance;
    private Boolean status;
    private Long idClient;

}
