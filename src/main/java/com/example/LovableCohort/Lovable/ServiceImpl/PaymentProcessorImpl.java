package com.example.LovableCohort.Lovable.ServiceImpl;

import com.example.LovableCohort.Lovable.DTO.subscription.CheckoutRequest;
import com.example.LovableCohort.Lovable.DTO.subscription.CheckoutResponse;
import com.example.LovableCohort.Lovable.DTO.subscription.PortalResponse;
import com.example.LovableCohort.Lovable.Entity.Plan;
import com.example.LovableCohort.Lovable.Entity.User;
import com.example.LovableCohort.Lovable.Jwt.JwtAuthImpl;
import com.example.LovableCohort.Lovable.Repository.PlanRepository;
import com.example.LovableCohort.Lovable.Repository.UserRepository;
import com.example.LovableCohort.Lovable.Services.PaymentProcessor;
import com.example.LovableCohort.Lovable.errors.ResourceNotFoundException;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentProcessorImpl implements PaymentProcessor {
    private  final JwtAuthImpl jwtAuth;
    private final PlanRepository planRepository;
    private final UserRepository userRepository ;
    @Value("${client.url}")
    private String frontendUrl;
    @Override
    public CheckoutResponse createCheckoutSessionUrl(CheckoutRequest request) {


        Long userId = jwtAuth.getUserId();
        User user =  userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user",userId.toString()));
        Plan plan = planRepository.findById(request.planId()).orElseThrow(()->new ResourceNotFoundException("Plan",request.planId().toString()));
        var params = SessionCreateParams.builder()
                .addLineItem(
                        SessionCreateParams.LineItem.builder().setPrice(plan.getStripePriceId()).setQuantity(1L).build())
                .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                .setSubscriptionData(
                        new SessionCreateParams.SubscriptionData.Builder()
                                .setBillingMode(SessionCreateParams.SubscriptionData.BillingMode.builder()
                                        .setType(SessionCreateParams.SubscriptionData.BillingMode.Type.FLEXIBLE)
                                        .build())
                                .build()
                )
                .setSuccessUrl(frontendUrl + "/success.html?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl(frontendUrl + "/cancel.html")
                .putMetadata("user_id", userId.toString())
                .putMetadata("plan_id", plan.getId().toString());

        try{
            String stripeId = user.getStripeCustomerId();
            if(stripeId==null || stripeId.isEmpty()){
                params.setCustomerEmail(user.getEmail());
            }
            else {
                params.setCustomer(stripeId);
            }
            Session session= Session.create(params.build());
            return new CheckoutResponse(session.getUrl());
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public PortalResponse openPortal(Long userId) {
        return null;
    }
}
