package com.example.LovableCohort.Lovable.Controllers;

import com.example.LovableCohort.Lovable.DTO.subscription.*;
import com.example.LovableCohort.Lovable.Services.PaymentProcessor;
import com.example.LovableCohort.Lovable.Services.PlanService;
import com.example.LovableCohort.Lovable.Services.SubscriptionService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.DialectOverride;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BillingController {
    private final PlanService planService ;
    private final  SubscriptionService subscriptionService ;
    private final PaymentProcessor paymentProcessor;
    @GetMapping(path="/api/plans")
    public ResponseEntity<List<PlanResponse>>getAllPlans(){
        return ResponseEntity.ok(planService.getPlans());
    }
    @GetMapping(path="/api/me/subscription/{userId}")
    public ResponseEntity<SubscriptionResponse>currentSubscription(@PathVariable Long userId){
        return  ResponseEntity.ok(subscriptionService.getCurrentSubcription(userId)) ;

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
}
