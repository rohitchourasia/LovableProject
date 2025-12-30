package com.example.LovableCohort.Lovable.CompositeKeys;

import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
public class ChatSessionId implements Serializable {
    private Long projectId ;
    private Long userId ;


}
