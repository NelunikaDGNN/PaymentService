package org.paymentService.paymentService.models;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PaymentMethod {
    CARD("card"),
    CASH("cash");

    private final String method;

}