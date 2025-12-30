package com.example.LovableCohort.Lovable.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level= AccessLevel.PRIVATE)

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id ;
    String name ;
    String stripePriceId ;
    Integer maxProjects ;
    Integer maxTokensPerDay;
    Integer  maxPreview ;
    Boolean unlimetedAi  ;
    Boolean active;


}
