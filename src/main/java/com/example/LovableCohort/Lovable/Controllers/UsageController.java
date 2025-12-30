package com.example.LovableCohort.Lovable.Controllers;

import com.example.LovableCohort.Lovable.DTO.subscription.PlanLimitsResponse;
import com.example.LovableCohort.Lovable.DTO.subscription.UsageTodayResponse;
import com.example.LovableCohort.Lovable.Services.UsageServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api/usage")
@RequiredArgsConstructor
public class UsageController {
    private final UsageServices usageService;

    @GetMapping("/today")
    public ResponseEntity<UsageTodayResponse> getTodayUsage() {
        Long userId = 1L;
        return ResponseEntity.ok(usageService.getTodayUsageOfUser(userId));
    }

    @GetMapping("/limits")
    public ResponseEntity<PlanLimitsResponse> getPlanLimits() {
        Long userId = 1L;
        return ResponseEntity.ok(usageService.getCurrentSubscriptionLimitsOfUser(userId));
    }

}
