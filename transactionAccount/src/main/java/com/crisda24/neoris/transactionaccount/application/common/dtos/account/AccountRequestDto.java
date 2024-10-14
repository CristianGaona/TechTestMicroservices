package com.crisda24.neoris.transactionaccount.application.common.dtos.account;

import com.crisda24.neoris.transactionaccount.domain.enums.AccountType;
import com.crisda24.neoris.transactionaccount.domain.models.Account;
import com.crisda24.neoris.transactionaccount.infrastructure.utils.ValidEnum;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountRequestDto {

    @Min(1)
    private Long idAccount;

    @NotNull
    @Size(max = 20)
    @Pattern(regexp = "\\d+", message = "the account number must contain only digits")
    private String accountNumber;

    @NotNull
    @ValidEnum(enumClass = AccountType.class, message = "invalid account type: CORRIENTE o AHORRO")
    private String accountType;

    @NotNull
    @Digits(integer = 10, fraction = 2)
    @Min(0)
    private BigDecimal initBalance;

    @NotNull
    private Boolean status;

    @NotNull
    @Min(1)
    private Long idClient;

}
