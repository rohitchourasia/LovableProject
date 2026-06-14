package com.example.LovableCohort.Lovable.Controllers;

import com.example.LovableCohort.Lovable.DTO.subscription.*;
import com.example.LovableCohort.Lovable.Services.PaymentProcessor;
import com.example.LovableCohort.Lovable.Services.PlanService;
import com.example.LovableCohort.Lovable.Services.SubscriptionService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DialectOverride;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
@RestController
@RequiredArgsConstructor
@Slf4j
public class BillingController {
    private final PlanService planService ;
    private final  SubscriptionService subscriptionService ;
    private final PaymentProcessor paymentProcessor;
    @Value("${stripe.webhook.secret}")
    private String webhookSecret ;
    @GetMapping(path="/api/plans")
    public ResponseEntity<List<PlanResponse>>getAllPlans(){
        return ResponseEntity.ok(planService.getPlans());
    }
    @GetMapping(path="/api/me/subscription")
    public ResponseEntity<SubscriptionResponse>currentSubscription(){
        return  ResponseEntity.ok(subscriptionService.getCurrentSubcription()) ;

    }
    @PostMapping(path="/api/payment/checkout")
    public ResponseEntity<CheckoutResponse>createCheckoutResponse(@RequestBody CheckoutRequest  checkoutRequest){
        Long userId = 1L ;
        return ResponseEntity.ok(paymentProcessor.createCheckoutSessionUrl(checkoutRequest));
    }
    @PostMapping(path="/api/payment/portal")
    public ResponseEntity<PortalResponse>openCustomerPortal(){
        Long userId = 1L ;
        return ResponseEntity.ok(paymentProcessor.openPortal(userId));
    }
    @PostMapping("/webhooks/payment")
    public ResponseEntity<String>handlePaymentWebhook(@RequestBody String payload,@RequestHeader("Stripe-Signature")String sigHeader){
        try {
            Event event = Webhook.constructEvent(payload, sigHeader, webhookSecret);

            EventDataObjectDeserializer deserializer = event.getDataObjectDeserializer();
            StripeObject stripeObject = null;

            if (deserializer.getObject().isPresent()) { // happy case
                stripeObject = deserializer.getObject().get();
            } else {
                // Fallback: Deserialize from raw JSON
                try {
                    stripeObject = deserializer.deserializeUnsafe();
                    if (stripeObject == null) {
                        log.warn("Failed to deserialize webhook object for event: {}", event.getType());
                        return ResponseEntity.ok().build();
                    }
                } catch (Exception e) {
                    log.error("Unsafe deserialization failed for event {}: {}", event.getType(), e.getMessage());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Deserialization failed");
                }
            }

            // Now extract metadata only if it's a Checkout Session
            Map<String, String> metadata = new HashMap<>();
            if (stripeObject instanceof Session session) {
                metadata = session.getMetadata();
            }

            // Pass to your processor
            paymentProcessor.handleWebhookEvent(event.getType(), stripeObject, metadata);
            return ResponseEntity.ok().build();

        } catch (SignatureVerificationException e) {
            throw new RuntimeException(e);
        }
    }
}
*/