package com.example.LovableCohort.Lovable.Services;


import com.example.LovableCohort.Lovable.DTO.deploy.DeployResponse;

public interface DeploymentService {

    DeployResponse deploy(Long projectId);
}
