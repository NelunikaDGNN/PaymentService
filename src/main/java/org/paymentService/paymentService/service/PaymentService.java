package org.paymentService.paymentService.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.paymentService.paymentService.models.Payment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.paymentService.paymentService.dto.PaymentDto;
import org.paymentService.paymentService.exception.PaymentNotFoundException;
import org.paymentService.paymentService.mapper.PaymentMapper;
import org.paymentService.paymentService.repository.PaymentRepository;
import org.paymentService.paymentService.confirmation.PaymentConfirmation;
import org.paymentService.paymentService.models.PaymentStatus;
import org.paymentService.paymentService.config.AppConfig;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class PaymentService implements PaymentServiceMain{

    private final PaymentRepository paymentRepository;
    private final RestTemplate restTemplate;
    private final PaymentConfirmation confirmationPayment;

    @Override
    public List<PaymentDto> findAll() {

        return this.paymentRepository.findAll()
                .stream()
                .map(PaymentMapper::map)
                .distinct()
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public PaymentDto findById(Long paymentId) {
        return this.paymentRepository.findById(paymentId)
                .map(PaymentMapper::map)
                .orElseThrow(() -> new PaymentNotFoundException(String.format("Payment with id: %d not found", paymentId)));
    }


    @Override
    public PaymentDto create(PaymentDto paymentDto) {

        Payment payment = PaymentMapper.map(paymentDto);
        Payment savedPayment = paymentRepository.save(payment);

        // Trigger payment confirmation after saving

            confirmationPayment.confirmPayment(
                savedPayment.getOrderId(),
                savedPayment.getPaymentId(),
                    paymentDto.getPaymentMethod(),
                    paymentDto.getPaymentStatus()

        );

        return PaymentMapper.map(savedPayment);
    }
//    public PaymentDto create(PaymentDto paymentDto) {
//        return PaymentMapper.map(this.paymentRepository.save(PaymentMapper.map(paymentDto)));
//    }

    @Override
    public PaymentDto update(PaymentDto paymentDto) {
        return PaymentMapper.map(this.paymentRepository.save(PaymentMapper.map(paymentDto)));
    }

    @Override
    public void deleteById(Long paymentId) {
        this.paymentRepository.deleteById(paymentId);
    }


}
