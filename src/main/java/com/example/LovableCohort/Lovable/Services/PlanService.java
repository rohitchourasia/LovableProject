package com.example.LovableCohort.Lovable.Services;

import com.example.LovableCohort.Lovable.DTO.subscription.PlanResponse;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface PlanService {
    List<PlanResponse> getPlans() ;

}
