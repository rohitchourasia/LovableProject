package com.example.LovableCohort.Lovable.Entity;

import com.example.LovableCohort.Lovable.Enums.SubscriptionStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
@Getter
@Setter
@FieldDefaults(level= AccessLevel.PRIVATE)
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id ;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    User user ;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    Plan plan ;
    @Column(unique = true)
    String stripeSubscriptionId ;
    String stripeCustomerId ;
    @Enumerated(value=EnumType.STRING)
    SubscriptionStatus status ;
    Instant currentPeriodStart;
    Instant currentPeriodEnd ;
    @CreationTimestamp
    Instant createdAt ;
    @UpdateTimestamp
    Instant  updatedAt ;



}
