package com.myapp.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class FullDashboardDTO {
    private DashboardStatsDTO stats;
    private List<CandidateStatusDTO> candidates;
}
