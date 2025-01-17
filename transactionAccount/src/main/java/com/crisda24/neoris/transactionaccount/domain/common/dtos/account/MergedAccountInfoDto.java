package com.crisda24.neoris.transactionaccount.domain.common.dtos.account;

import com.crisda24.neoris.transactionaccount.domain.enums.AccountType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MergedAccountInfoDto {
    private Boolean status;
    private String accountNumber;
    private BigDecimal balance;
    private AccountType accountType;
    private String name;

}
