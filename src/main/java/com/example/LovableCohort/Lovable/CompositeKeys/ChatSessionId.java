package com.example.LovableCohort.Lovable.CompositeKeys;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@Builder
@ToString
@AllArgsConstructor
public class ChatSessionId implements Serializable {
    private Long projectId ;
    private Long userId ;


}
