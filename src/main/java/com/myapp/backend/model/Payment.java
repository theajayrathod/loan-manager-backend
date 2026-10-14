package com.myapp.backend.model;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Data
@Document(collection = "payments")
public class Payment {
    @Id private String id;
    private String candidateId;
    private double amount;
    private LocalDate paymentDate;
    private String paymentMode; // CASH, UPI, CARD
    private String collectedBy;
}
