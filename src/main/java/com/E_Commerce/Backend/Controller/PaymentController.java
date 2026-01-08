package com.E_Commerce.Backend.Controller;

import com.E_Commerce.Backend.DTO.request.PaymobCallbackRequest;
import com.E_Commerce.Backend.Service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/pay/{orderId}")
    public ResponseEntity<String> pay(@PathVariable Long orderId) {
        return ResponseEntity.ok(paymentService.initiatePayment(orderId));
    }

    @PostMapping("/callback")
    public ResponseEntity<Void> callback(@RequestBody PaymobCallbackRequest request) {
        paymentService.handleCallback(
                request.getTransactionId(),
                request.isSuccess()
        );
        return ResponseEntity.ok().build();
    }

}
