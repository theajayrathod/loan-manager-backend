package com.myapp.backend.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardStatsDTO {
    private double totalCollectedToday;
    private double totalUnpaidToday;
    private long paidCount;
    private long unpaidCount;
}
