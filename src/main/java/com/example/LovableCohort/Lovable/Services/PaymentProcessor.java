package com.example.LovableCohort.Lovable.Services;

import com.example.LovableCohort.Lovable.DTO.subscription.CheckoutRequest;
import com.example.LovableCohort.Lovable.DTO.subscription.CheckoutResponse;
import com.example.LovableCohort.Lovable.DTO.subscription.PortalResponse;

public interface PaymentProcessor {
    CheckoutResponse createCheckoutSessionUrl(CheckoutRequest request);

    PortalResponse openPortal(Long userId);
}
