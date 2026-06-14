package com.example.LovableCohort.Lovable.Services;

import com.example.LovableCohort.Lovable.DTO.subscription.CheckoutRequest;
import com.example.LovableCohort.Lovable.DTO.subscription.CheckoutResponse;
import com.example.LovableCohort.Lovable.DTO.subscription.PortalResponse;
import com.stripe.model.StripeObject;

import java.util.Map;

public interface PaymentProcessor {
    CheckoutResponse createCheckoutSessionUrl(CheckoutRequest request);

    PortalResponse openPortal(Long userId);

    void handleWebhookEvent(String type, StripeObject stripeObject, Map<String, String> metadata);
}
