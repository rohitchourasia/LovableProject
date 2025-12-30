package com.example.LovableCohort.Lovable.Services;

import com.example.LovableCohort.Lovable.DTO.subscription.CheckoutResponse;
import com.example.LovableCohort.Lovable.DTO.subscription.PortalResponse;
import com.example.LovableCohort.Lovable.DTO.subscription.SubscriptionResponse;
import org.jspecify.annotations.Nullable;

public interface SubscriptionService {

    SubscriptionResponse getCurrentSubcription(Long userId);

     CheckoutResponse createCheckoutSessionUrl(Long userId);

     PortalResponse openPortal(Long userId);
}
