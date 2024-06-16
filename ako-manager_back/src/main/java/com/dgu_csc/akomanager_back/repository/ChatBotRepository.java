package com.dgu_csc.akomanager_back.repository;

import com.dgu_csc.akomanager_back.model.ChatBot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatBotRepository  extends JpaRepository<ChatBot, Long> {
    List<ChatBot> findByCstudentId_StudentId(String studentId);
}
