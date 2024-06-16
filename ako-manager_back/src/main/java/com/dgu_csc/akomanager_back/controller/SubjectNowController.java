package com.dgu_csc.akomanager_back.controller;

import com.dgu_csc.akomanager_back.jwt.JWTUtil;
import com.dgu_csc.akomanager_back.model.SubjectNow;
import com.dgu_csc.akomanager_back.service.SubjectNowService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/SubjectNow")
public class SubjectNowController {

    @Autowired
    private final SubjectNowService subjectNowService;

    @Autowired
    private JWTUtil jwtUtil;

    @GetMapping("/get")
    public ResponseEntity<List<SubjectNow>> getSubjectNow(HttpServletRequest request) {
        String studentId = jwtUtil.getUsername(jwtUtil.getToken(request));
        List<SubjectNow> subjectNows = subjectNowService.getAllSubjectList(studentId);
        return ResponseEntity.ok(subjectNows);
    }



}
