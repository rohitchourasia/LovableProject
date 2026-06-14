package com.example.LovableCohort.Lovable.Services;

import com.example.LovableCohort.Lovable.DTO.subscription.CheckoutResponse;
import com.example.LovableCohort.Lovable.DTO.subscription.PortalResponse;
import com.example.LovableCohort.Lovable.DTO.subscription.SubscriptionResponse;
import com.example.LovableCohort.Lovable.Enums.SubscriptionStatus;
import org.jspecify.annotations.Nullable;

import java.time.Instant;

public interface SubscriptionService {

     void activateSubscription(Long userId, Long planId, String subscriptionId, String customerId) ;

    SubscriptionResponse getCurrentSubcription();


    void updateSubscription(String id, SubscriptionStatus status, Instant periodStart, Instant periodEnd, Boolean cancelAtPeriodEnd, Long planId);

    void cancelSubscription(String id);

    void renewSubscriptionPeriod(String subId, Instant periodStart, Instant periodEnd);

    void markSubscriptionPastDue(String subId);
}
