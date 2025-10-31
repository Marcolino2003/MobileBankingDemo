package com.example.bank.controller;

import lombok.Data;

@Data
public class TransferRequest {
    private String senderIban;
    private String receiverIban;
    private String receiverName;
    private String receiverSurname;
    private double amount;
    private String description;
}
