package com.example.clientmanagement.domain.common.dto.messages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientIdListMessage {
    private List<Long> Ids;
}
