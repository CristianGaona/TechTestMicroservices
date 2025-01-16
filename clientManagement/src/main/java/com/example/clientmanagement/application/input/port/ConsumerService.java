package com.example.clientmanagement.application.input.port;

public interface ConsumerService {
    public String sendAndReceiveClientsInfo(String message);
    public Boolean sendAndReceiveClientExists(Long id);
}
