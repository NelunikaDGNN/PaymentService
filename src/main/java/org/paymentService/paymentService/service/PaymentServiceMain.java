package org.paymentService.paymentService.service;

import org.paymentService.paymentService.dto.PaymentDto;

import java.util.List;

public interface PaymentServiceMain {
    List<PaymentDto> findAll();
    PaymentDto findById(Long paymentId);
    PaymentDto create(PaymentDto paymentDto);
    PaymentDto update(PaymentDto paymentDto);
    void deleteById(Long paymentID);

}
