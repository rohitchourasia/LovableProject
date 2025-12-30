package com.example.LovableCohort.Lovable.DTO.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProjectRequest(
      @NotBlank @Size(min=4) String name
) {
}
