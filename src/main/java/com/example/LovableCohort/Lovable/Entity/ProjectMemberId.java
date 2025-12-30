package com.example.LovableCohort.Lovable.Entity;

import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProjectMemberId implements Serializable {
    Long projectId;
    Long userId;
}

