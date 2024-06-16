package com.dgu_csc.akomanager_back.service;

import com.dgu_csc.akomanager_back.model.ChatBot;

import java.util.List;

public interface ChatBotService {
    public void saveLog(ChatBot chatBot);

    List<ChatBot> findByCStudent_StudentId(String studentId);
}
