package com.example.LovableCohort.Lovable.Repository;


import com.example.LovableCohort.Lovable.Entity.ChatEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatEventRepository extends JpaRepository<ChatEvent, Long> {

}
