package org.paymentService.paymentService.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.paymentService.paymentService.dto.PaymentDto;
import org.paymentService.paymentService.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<PaymentDto>> findAll()
        {
            return ResponseEntity.ok(this.paymentService.findAll());
        }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentDto> findById(
            @PathVariable ("paymentId")
            @NotNull(message = "Input cannot be blank")
            @Valid final Long paymentId)
            {
                return ResponseEntity.ok(this.paymentService.findById(paymentId));
            }

    @PostMapping
    public ResponseEntity<PaymentDto> create(
            @RequestBody
            @NotNull(message = "Input must not be null")
            @Valid final PaymentDto paymentDto)
            {
                return ResponseEntity.ok(this.paymentService.create(paymentDto));
             }

    @PutMapping("/{paymentId}")
    public ResponseEntity<PaymentDto> update(
            @RequestBody
            @NotNull(message = "Input must not be null")
            @Valid final PaymentDto paymentDto)
             {
                 return ResponseEntity.ok(this.paymentService.update(paymentDto));
             }

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<Boolean> deleteById(
            @PathVariable("paymentId")
            final String paymentId)
            {
                this.paymentService.deleteById(Long.parseLong(paymentId));
                return ResponseEntity.ok(true);
            }
}
