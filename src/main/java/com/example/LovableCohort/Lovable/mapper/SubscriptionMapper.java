package com.example.LovableCohort.Lovable.mapper;

import com.example.LovableCohort.Lovable.DTO.subscription.PlanResponse;
import com.example.LovableCohort.Lovable.DTO.subscription.SubscriptionResponse;
import com.example.LovableCohort.Lovable.Entity.Plan;
import com.example.LovableCohort.Lovable.Entity.Subscription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    SubscriptionResponse fromSubscriptiontoSubscriptionResponse(Subscription subscription);
    PlanResponse toPlanResponse(Plan plan);
}
