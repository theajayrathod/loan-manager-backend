package com.myapp.backend.model;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Data
@Document(collection = "candidates")
public class Candidate {
    @Id private String id;
    private String name;
    private String phone;
    private double loanAmount;
    private double emiAmount;
    private int totalEmisExpected;
    private LocalDate startDate;
}
