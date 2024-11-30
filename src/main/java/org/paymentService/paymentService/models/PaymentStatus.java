package org.paymentService.paymentService.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PaymentStatus {

    NOT_STARTED("not_started"),
    INPROGRESS("inprogress"),
    COMPLETED("completed");

    private final String status;

}
