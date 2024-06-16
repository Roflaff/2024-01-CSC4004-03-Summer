package com.dgu_csc.akomanager_back.service;

import com.dgu_csc.akomanager_back.model.ChatBot;
import com.dgu_csc.akomanager_back.repository.ChatBotRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatBotServiceImpl implements ChatBotService {

    private final ChatBotRepository chatBotRepository;

    @Override
    @Transactional
    public void saveLog(ChatBot chatBot) {
        chatBotRepository.save(chatBot);
    }

    @Override
    public List<ChatBot> findByCStudent_StudentId(String studentId) {
        return chatBotRepository.findByCstudentId_StudentId(studentId);
    }
}
