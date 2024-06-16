package com.dgu_csc.akomanager_back.service;

import com.dgu_csc.akomanager_back.model.SubjectNow;
import com.dgu_csc.akomanager_back.repository.SubjectNowRepository;
import com.dgu_csc.akomanager_back.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectNowServiceImpl implements SubjectNowService{

    private final SubjectNowRepository subjectNowRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void saveSubjectNow(SubjectNow subjectNow) {
        subjectNowRepository.save(subjectNow);
    }

    // 유저 시간표 정보 반환
    @Override
    public List<SubjectNow> getAllSubjectList(String studentId) {
        return subjectNowRepository.findAll();
    }
}
