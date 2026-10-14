package com.myapp.backend.dto;
import com.myapp.backend.model.Candidate;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CandidateStatusDTO {
    private Candidate candidate;
    private boolean isPaidToday;
    private int dayNumber;
}
