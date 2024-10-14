package com.crisda24.neoris.transactionaccount.application.common.mappers.account;

import com.crisda24.neoris.transactionaccount.application.common.dtos.account.AccountRequestDto;
import com.crisda24.neoris.transactionaccount.application.common.dtos.account.AccountRequestPatchDto;
import com.crisda24.neoris.transactionaccount.application.common.dtos.account.MergedAccountInfoDto;
import com.crisda24.neoris.transactionaccount.application.common.dtos.account.interfaces.AccountListDtoI;
import com.crisda24.neoris.transactionaccount.domain.enums.AccountType;
import com.crisda24.neoris.transactionaccount.domain.models.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    @Mapping(target = "idAccount", expression = "java(dto.getIdAccount() != null ? dto.getIdAccount() : null)")
    Account dtoToEntity(AccountRequestDto dto);

    @Mapping(target = "balance", source = "account.initBalance")
    @Mapping(target = "name", source = "clientName")
    MergedAccountInfoDto entityToDto(Account account, String clientName);

    @Mapping(target = "balance", source = "account.initBalance")
    @Mapping(target = "name", source = "clientName")
    MergedAccountInfoDto entityIToDto(Account account, String clientName);

    @Mapping(target ="idAccount", expression = "java(dto.getIdAccount() != null ? dto.getIdAccount(): account.getIdAccount())")
    @Mapping(target ="accountNumber", expression = "java(dto.getAccountNumber() != null ? dto.getAccountNumber(): account.getAccountNumber())")
    @Mapping(target ="accountType", expression = "java(mapAccountType(dto.getAccountType(), account.getAccountType()))")
    @Mapping(target ="idClient", expression = "java(dto.getIdClient() != null ? dto.getIdClient(): account.getIdClient())")
    @Mapping(target ="status", expression = "java(dto.getStatus() != null ? dto.getStatus(): account.getStatus())")
    Account dtoPatchToEntity(Account account, AccountRequestPatchDto dto);

    default AccountType mapAccountType(String accountTypeString, AccountType currentAccountType) {
        return accountTypeString != null ? AccountType.valueOf(accountTypeString) : currentAccountType;
    }

}
