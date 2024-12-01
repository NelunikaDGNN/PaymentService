package org.paymentService.paymentService.confirmation;

import org.paymentService.paymentService.models.PaymentMethod;
import org.paymentService.paymentService.models.PaymentStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentConfirmation {

    private final RestTemplate restTemplate;

    public void confirmPayment(Long orderId, Long paymentId, PaymentMethod paymentMethod , PaymentStatus paymentStatus) {

        Map<String, Object> requestPayload = new HashMap<>();
        requestPayload.put("orderId", orderId);
        requestPayload.put("paymentId", paymentId);
        requestPayload.put("paymentMethod", paymentMethod);
        requestPayload.put("status", paymentStatus);

        // Order Service URL
        String orderServiceUrl = "http://localhost:8081/api/orders" + orderId +"/"+ paymentStatus;

        try {
            restTemplate.put(orderServiceUrl, requestPayload);
            log.info("Payment confirmation sent for Order ID: {}", orderId);
        } catch (Exception e) {
            log.error("Failed to confirm payment for Order ID: {}. Error: {}", orderId, e.getMessage());
        }
    }
}
