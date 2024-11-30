package org.paymentService.paymentService.repository;


import org.paymentService.paymentService.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
