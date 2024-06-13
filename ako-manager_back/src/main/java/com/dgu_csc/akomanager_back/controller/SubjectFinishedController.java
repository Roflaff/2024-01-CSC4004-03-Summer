package com.dgu_csc.akomanager_back.controller;

import com.dgu_csc.akomanager_back.dto.PasswordRequest;
import com.dgu_csc.akomanager_back.jwt.JWTUtil;
import com.dgu_csc.akomanager_back.model.SubjectFinished;
import com.dgu_csc.akomanager_back.service.SubjectFinishedService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/SubjectFinished")
public class SubjectFinishedController {

    @Autowired
    private final SubjectFinishedService subjectFinishedService;

    @Autowired
    private JWTUtil jwtUtil;

    // TODO : 수정 필요
    // POST : [/SubjectFinished/get] url의 studentId와 body의 password 정보로 해당 유저의 들은 과목 정보 출력
    @PostMapping("/get")
    public ResponseEntity<List<SubjectFinished>> getSubjectsByStudentId(HttpServletRequest request) {
        try {
            String studentId = jwtUtil.getUsername(jwtUtil.getToken(request));
            List<SubjectFinished> subjects = subjectFinishedService.searchByStudentId(studentId);
            return ResponseEntity.ok(subjects);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    // GET : [/SubjectFinished/getUserTotalScore] 지금 까지 들은 총 학점
    @GetMapping("/getUserTotalScore")
    public int getUserTotalScore(HttpServletRequest request) {
        String studentId = jwtUtil.getUsername(jwtUtil.getToken(request));
        return subjectFinishedService.getUserTotalScore(studentId);
    }

    // GET : [/SubjectFinished/getUserTotalMajorScore] 지금 까지 들은 총 전공 학점
    @GetMapping("/getUserTotalMajorScore")
    public int getUserTotalMajorScore(HttpServletRequest request) {
        String studentId = jwtUtil.getUsername(jwtUtil.getToken(request));
        return subjectFinishedService.getUserTotalMajorScore(studentId);
    }

    // GET : [/SubjectFinished/getUserTotalMajorScore] 지금 까지 들은 총 전공 학점
    @GetMapping("/getUserTotalCommonScore")
    public int getUserTotalCommonScore(HttpServletRequest request) {
        String studentId = jwtUtil.getUsername(jwtUtil.getToken(request));
        return subjectFinishedService.getUserTotalCommonScore(studentId);
    }
}

  