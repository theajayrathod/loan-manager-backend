package com.myapp.backend.controller;

import com.myapp.backend.dto.*;
import com.myapp.backend.model.*;
import com.myapp.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/candidates")
@CrossOrigin(origins = "*")
public class CandidateController {

    @Autowired private CandidateRepository candidateRepository;
    @Autowired private PaymentRepository paymentRepository;

    @GetMapping("/full-dashboard")
    public FullDashboardDTO getFullDashboard() {
        LocalDate today = LocalDate.now();
        System.out.println("--- DASHBOARD CALCULATION STARTING ---");
        System.out.println("Today's Date: " + today);

        List<Candidate> allCandidates = candidateRepository.findAll();
        List<CandidateStatusDTO> statusList = new ArrayList<>();
        double totalCollected = 0;
        double totalUnpaidAmount = 0;
        long paidCount = 0;

        for (Candidate c : allCandidates) {
            // 1. Get ALL payments for this candidate ever
            List<Payment> allPayments = paymentRepository.findByCandidateId(c.getId());

            boolean isPaidToday = false;
            for (Payment p : allPayments) {
                // Log every payment to see why it's not matching
                System.out.println("Checking payment for " + c.getName() + ": Date=" + p.getPaymentDate() + " Today=" + today);

                if (p.getPaymentDate() != null && p.getPaymentDate().equals(today)) {
                    isPaidToday = true;
                    break;
                }
            }

            if (isPaidToday) {
                paidCount++;
                // Sum up only today's payments for the stats
                for (Payment p : allPayments) {
                    if (p.getPaymentDate() != null && p.getPaymentDate().equals(today)) {
                        totalCollected += p.getAmount();
                    }
                }
            } else {
                totalUnpaidAmount += c.getEmiAmount();
            }

            long days = java.time.temporal.ChronoUnit.DAYS.between(c.getStartDate(), today) + 1;
            statusList.add(new CandidateStatusDTO(c, isPaidToday, (int) days));
            System.out.println("Result for " + c.getName() + ": " + (isPaidToday ? "GREEN" : "RED"));
        }

        System.out.println("Final Stats -> Paid: " + paidCount + ", Unpaid: " + (allCandidates.size() - paidCount));
        System.out.println("--- DASHBOARD CALCULATION END ---");

        DashboardStatsDTO stats = new DashboardStatsDTO(totalCollected, totalUnpaidAmount, paidCount, allCandidates.size() - paidCount);
        return new FullDashboardDTO(stats, statusList);
    }

    @PostMapping("/pay")
    public Payment collectPayment(@RequestBody PaymentRequest request) {
        if ("STAFF".equals(request.getUserRole()) && "CASH".equals(request.getPaymentMode())) {
            throw new RuntimeException("Unauthorized: Only the Owner can record Cash payments!");
        }
        Payment payment = new Payment();
        payment.setCandidateId(request.getCandidateId());
        payment.setAmount(request.getAmount());
        payment.setPaymentDate(LocalDate.now());
        payment.setPaymentMode(request.getPaymentMode());
        payment.setCollectedBy(request.getUserRole());
        return paymentRepository.save(payment);
    }

    @GetMapping("/{id}/history")
    public List<Payment> getCandidateHistory(@PathVariable String id) {
        return paymentRepository.findByCandidateId(id);
    }

    @PostMapping("/add")
    public Candidate addCandidate(@RequestBody Candidate candidate) {
        return candidateRepository.save(candidate);
    }
}
