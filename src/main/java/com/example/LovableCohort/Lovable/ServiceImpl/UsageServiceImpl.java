package com.example.LovableCohort.Lovable.ServiceImpl;

import com.example.LovableCohort.Lovable.DTO.subscription.PlanLimitsResponse;
import com.example.LovableCohort.Lovable.DTO.subscription.UsageTodayResponse;
import com.example.LovableCohort.Lovable.Services.UsageServices;
import org.springframework.stereotype.Service;

@Service
public class UsageServiceImpl implements UsageServices {
    @Override
    public UsageTodayResponse getTodayUsageOfUser(Long userId) {
        return null;
    }

    @Override
    public PlanLimitsResponse getCurrentSubscriptionLimitsOfUser(Long userId) {
        return null;
    }
}
