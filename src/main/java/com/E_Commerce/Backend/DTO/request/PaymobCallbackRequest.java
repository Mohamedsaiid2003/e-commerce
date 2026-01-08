package com.E_Commerce.Backend.DTO.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymobCallbackRequest {
    private String transactionId;
    private boolean success;

}
