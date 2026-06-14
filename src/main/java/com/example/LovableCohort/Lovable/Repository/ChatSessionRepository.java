package com.example.LovableCohort.Lovable.Repository;


import com.example.LovableCohort.Lovable.CompositeKeys.ChatSessionId;
import com.example.LovableCohort.Lovable.Entity.ChatSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatSessionRepository extends JpaRepository<ChatSession, ChatSessionId> {
}
