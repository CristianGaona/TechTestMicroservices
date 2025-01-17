package com.crisda24.neoris.transactionaccount.domain.common.dtos.account;

import com.crisda24.neoris.transactionaccount.domain.enums.AccountType;
import com.crisda24.neoris.transactionaccount.infrastructure.utils.ValidEnum;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountRequestPatchDto {
    @Min(1)
    private Long idAccount;

    @Size(max = 20)
    @Pattern(regexp = "\\d+", message = "the account number must contain only digits")
    private String accountNumber;

    @ValidEnum(enumClass = AccountType.class, message = "invalid account type: CORRIENTE o AHORRO")
    private String accountType;

    private Boolean status;

    @Min(1)
    private Long idClient;
}
