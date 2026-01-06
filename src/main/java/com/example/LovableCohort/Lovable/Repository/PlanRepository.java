package com.example.LovableCohort.Lovable.Repository;

import com.example.LovableCohort.Lovable.Entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.authentication.jaas.JaasPasswordCallbackHandler;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepository extends JpaRepository<Plan,Long> {

}
