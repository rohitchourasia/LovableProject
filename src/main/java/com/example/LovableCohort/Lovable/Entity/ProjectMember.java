package com.example.LovableCohort.Lovable.Entity;
import com.example.LovableCohort.Lovable.Enums.MessageRole;
import com.example.LovableCohort.Lovable.Enums.ProjectRole;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMember {


    @EmbeddedId
     private ProjectMemberId id ;
    @MapsId("projectId")
    @ManyToOne
    @JoinColumn
    Project project;
    @MapsId("userId")
    @ManyToOne
    @JoinColumn
    User user;
    @Enumerated(value=EnumType.STRING)
    ProjectRole projectRole;

    Instant invitedAt;
    Instant acceptedAt;
}


