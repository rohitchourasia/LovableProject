package com.example.LovableCohort.Lovable.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Getter
@Setter
@FieldDefaults(level= AccessLevel.PRIVATE)
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
        @Index(name="deletetAt_null_update_At_desc",columnList = "deleted_at,updated_at DESC")
})
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id ;
    String name ;


    Boolean isPublic=false ;
    @CreationTimestamp
    Instant createdAt ;
    @UpdateTimestamp
    Instant updatedAt ;
    Instant deletedAt ;
}
