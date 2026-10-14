package com.myapp.backend.dto;
import lombok.Data;

@Data
public class PaymentRequest {
    private String candidateId;
    private double amount;
    private String paymentMode;
    private String userRole;
}
