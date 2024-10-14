package com.crisda24.neoris.transactionaccount.application.common.dtos.account.messages;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClientIdListMessage {
    private List<Long> Ids;

}
