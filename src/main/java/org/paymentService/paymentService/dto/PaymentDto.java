package org.paymentService.paymentService.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.paymentService.paymentService.models.PaymentMethod;
import org.paymentService.paymentService.models.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDto{

    private Long paymentId;
    private Boolean isPayed;
    private PaymentStatus paymentStatus;
    private Long orderId;
    private PaymentMethod paymentMethod;


//    @JsonProperty("order")
//    @JsonInclude(Include.NON_NULL)
//    private OrderDto orderDto;


}

