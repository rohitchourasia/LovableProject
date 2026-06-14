package com.example.LovableCohort.Lovable.ServiceImpl;
/*
import com.example.LovableCohort.Lovable.DTO.subscription.CheckoutResponse;
import com.example.LovableCohort.Lovable.DTO.subscription.PortalResponse;
import com.example.LovableCohort.Lovable.DTO.subscription.SubscriptionResponse;
import com.example.LovableCohort.Lovable.Entity.Plan;
import com.example.LovableCohort.Lovable.Entity.Subscription;
import com.example.LovableCohort.Lovable.Entity.User;
import com.example.LovableCohort.Lovable.Enums.SubscriptionStatus;
import com.example.LovableCohort.Lovable.Jwt.JwtAuthImpl;
import com.example.LovableCohort.Lovable.Repository.PlanRepository;
import com.example.LovableCohort.Lovable.Repository.SubscriptionRepository;
import com.example.LovableCohort.Lovable.Repository.UserRepository;
import com.example.LovableCohort.Lovable.Services.SubscriptionService;
import com.example.LovableCohort.Lovable.errors.ResourceNotFoundException;
import com.example.LovableCohort.Lovable.mapper.SubscriptionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final JwtAuthImpl jwtAuth;
    private  final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;
    private final UserRepository userRepository;
    private final PlanRepository planRepository ;
    @Override
    public void activateSubscription(Long userId, Long planId, String subscriptionId, String customerId) {
        if(subscriptionRepository.existsByStripeSubscriptionId()){
            return ;
        }

        User user =userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User",userId.toString()));
        Plan plan = planRepository.findById(planId).orElseThrow(()->new ResourceNotFoundException("Plan",planId.toString()));
        Subscription subscription = Subscription.builder()
                .user(user)
                .plan(plan)
                .stripeSubscriptionId(subscriptionId)
                .stripeCustomerId(customerId)
                .status(SubscriptionStatus.INCOMPLETE)
                .build();
        subscriptionRepository.save(subscription);

    }

    @Override
    public SubscriptionResponse getCurrentSubcription() {
        Long userId = jwtAuth.getUserId();
        Subscription subscription= subscriptionRepository.findByUserIdAndStatusIn(userId, Set.of(
                SubscriptionStatus.ACTIVE,SubscriptionStatus.PAST_DUE,SubscriptionStatus.INCOMPLETE
        )).orElse(new Subscription());
        return subscriptionMapper.fromSubscriptiontoSubscriptionResponse(subscription);
    }

    @Override
    public void updateSubscription(String id, SubscriptionStatus status, Instant periodStart, Instant periodEnd, Boolean cancelAtPeriodEnd, Long planId) {

    }

    @Override
    public void cancelSubscription(String id) {

    }

    @Override
    public void renewSubscriptionPeriod(String gatewaySubscriptionId, Instant periodStart, Instant periodEnd) {
        Subscription subscription = getSubscription(gatewaySubscriptionId);
        Instant newStart = periodStart != null ? periodStart : subscription.getCurrentPeriodEnd();
        subscription.setCurrentPeriodStart(newStart);
        subscription.setCurrentPeriodEnd(periodEnd);

        if(subscription.getStatus() == SubscriptionStatus.PAST_DUE || subscription.getStatus() == SubscriptionStatus.INCOMPLETE) {
            subscription.setStatus(SubscriptionStatus.ACTIVE);
        }

        subscriptionRepository.save(subscription);

    }

    @Override
    public void markSubscriptionPastDue(String subId) {

    }
    public Subscription getSubscription(String gatewaySubscriptionId){
        return subscriptionRepository.findByStripeSubscriptionId(gatewaySubscriptionId).orElseThrow(()->new ResourceNotFoundException("Subscription",gatewaySubscriptionId));
    }


}
*/