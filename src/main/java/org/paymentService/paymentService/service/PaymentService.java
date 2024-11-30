package org.paymentService.paymentService.service;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.paymentService.paymentService.dto.OrderDto;
import org.paymentService.paymentService.dto.PaymentDto;
import org.paymentService.paymentService.exception.PaymentNotFoundException;
import org.paymentService.paymentService.mapper.PaymentMapper;
import org.paymentService.paymentService.repository.PaymentRepository;
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

    @Value("${order.service.api.url}")
    private String orderServiceApiUrl; // Injected from application properties

    @Override
//    public List<PaymentDto> findAll() {
//
//        return this.paymentRepository.findAll()
//                .stream()
//                .map(PaymentMapper::map)
//                .map(p -> {
//                    String url = String.format("%s/%d", orderServiceApiUrl,p.getOrderDto().getOrderID());
//                    p.setOrderDto(this.restTemplate.getForObject(url, OrderDto.class));
//                    return p;
//                })
//                .distinct()
//                .collect(Collectors.toUnmodifiableList());
//    }

    public List<PaymentDto> findAll() {
        return this.paymentRepository.findAll()
                .stream()
                .map(PaymentMapper::map)
                .map(p -> {
                    try {
                        // Attempt to fetch OrderDto from the Order Service
                        String url = String.format("%s/%d", orderServiceApiUrl, p.getOrderDto().getOrderID());
                        p.setOrderDto(this.restTemplate.getForObject(url, OrderDto.class));
                    } catch (Exception e) {
                        // If the service is not reachable, set a fallback OrderDto
                        log.warn("Order Service not reachable. Using hardcoded OrderDto. Error: {}", e.getMessage());
                        p.setOrderDto(new OrderDto(999L, 0L)); // Fallback OrderDto
                    }
                    return p;
                })
                .distinct()
                .collect(Collectors.toUnmodifiableList());
    }


    @Override
    public PaymentDto findById(Long paymentId) {
        return this.paymentRepository.findById(paymentId)
                .map(PaymentMapper::map)
                .map(p-> {
                    String url = String.format("%s/%d", orderServiceApiUrl,p.getOrderDto().getOrderID());
                    p.setOrderDto(this.restTemplate.getForObject(url, OrderDto.class));
                    return p;
                })
                .orElseThrow(() -> new PaymentNotFoundException(String.format("Payment with id: %d not found", paymentId)));
    }

    @Override
    public PaymentDto create(PaymentDto paymentDto) {
        return PaymentMapper.map(this.paymentRepository.save(PaymentMapper.map(paymentDto)));
    }

    @Override
    public PaymentDto update(PaymentDto paymentDto) {
        return PaymentMapper.map(this.paymentRepository.save(PaymentMapper.map(paymentDto)));
    }

    @Override
    public void deleteById(Long paymentId) {
        this.paymentRepository.deleteById(paymentId);
    }
}
