package com.example.LovableCohort.Lovable.Services;

import com.example.LovableCohort.Lovable.DTO.subscription.PlanLimitsResponse;
import com.example.LovableCohort.Lovable.DTO.subscription.UsageTodayResponse;
import org.jspecify.annotations.Nullable;

public interface UsageServices {
     UsageTodayResponse getTodayUsageOfUser(Long userId);

     PlanLimitsResponse getCurrentSubscriptionLimitsOfUser(Long userId);
}
