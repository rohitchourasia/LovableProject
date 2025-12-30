package com.example.LovableCohort.Lovable.ServiceImpl;

import com.example.LovableCohort.Lovable.DTO.subscription.PlanResponse;
import com.example.LovableCohort.Lovable.Services.PlanService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PlanServiceImpl implements PlanService {
    @Override
    public List<PlanResponse> getPlans() {
        return List.of();
    }
}
