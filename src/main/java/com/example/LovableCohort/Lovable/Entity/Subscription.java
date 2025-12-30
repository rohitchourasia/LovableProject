package com.example.LovableCohort.Lovable.Entity;

import com.example.LovableCohort.Lovable.Enums.SubscriptionStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
@Getter
@Setter
@FieldDefaults(level= AccessLevel.PRIVATE)

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id ;
    @ManyToOne
    @JoinColumn
    User user ;
    @OneToOne
    @JoinColumn
    Plan plan ;
    @Column(unique = true)
    String stripeSubscriptionId ;
    String stripeCustomerId ;
    SubscriptionStatus status ;
    Instant currentPeriodStart;
    Instant currentPeriodEnd ;
    Instant createdAt ;
    Instant  updatedAt ;



}
