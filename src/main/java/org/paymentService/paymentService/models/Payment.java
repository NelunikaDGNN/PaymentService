package org.paymentService.paymentService.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor


public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "payment_id", unique = true ,nullable = false,updatable = false)
    private Long paymentId;

    @Column(name="order_id")
    private Long orderId;
    @Column(name = "is_payed")
    private Boolean isPayed;

    @Enumerated(EnumType.STRING)
    @Column(name="payment_status")
    private PaymentStatus paymentStatus;

}

