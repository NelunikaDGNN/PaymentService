package org.paymentService.paymentService.mapper;

import org.paymentService.paymentService.models.Payment;
import org.paymentService.paymentService.dto.PaymentDto;
import org.paymentService.paymentService.dto.OrderDto;
public class PaymentMapper {

    public static PaymentDto map(Payment payment){
        return PaymentDto.builder()
                .paymentId(payment.getPaymentId())
                .isPayed(payment.getIsPayed())
               .paymentStatus(payment.getPaymentStatus())
                .orderDto(
                            OrderDto.builder()
                                    .orderID(payment.getOrderId())
                                    .build()
                )
                .build();

    }
    public static Payment map(PaymentDto paymentDto){
        return Payment.builder()
                .paymentId(paymentDto.getPaymentId())
                .orderId(paymentDto.getOrderId())
                .isPayed(paymentDto.getIsPayed())
                .paymentStatus(paymentDto.getPaymentStatus())
                .build();
    }
}

