package com.myapp.backend.repository;
import com.myapp.backend.model.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDate;
import java.util.List;

public interface PaymentRepository extends MongoRepository<Payment, String> {
    List<Payment> findByCandidateId(String candidateId);
    List<Payment> findByCandidateIdAndPaymentDate(String candidateId, LocalDate date);
    List<Payment> findByPaymentDate(LocalDate date);
}
