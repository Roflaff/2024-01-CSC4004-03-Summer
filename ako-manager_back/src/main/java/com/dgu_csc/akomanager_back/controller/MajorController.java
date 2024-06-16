package com.dgu_csc.akomanager_back.controller;


import com.dgu_csc.akomanager_back.dto.MajorDto;
import com.dgu_csc.akomanager_back.jwt.JWTUtil;
import com.dgu_csc.akomanager_back.model.Major;
import com.dgu_csc.akomanager_back.model.User;
import com.dgu_csc.akomanager_back.service.MajorService;
import com.dgu_csc.akomanager_back.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Major")
public class MajorController {

    @Autowired
    private final MajorService majorService;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserService userService;


    // POST : [/Major/add] 과목 추가
    @PostMapping("/add")
    public ResponseEntity<String> addMajor(@RequestBody Major major, HttpServletRequest request) {
        try {
            String studentId = jwtUtil.getUsername(jwtUtil.getToken(request));
            Optional<User> masteruser = userService.findByStudentId(studentId);

            // 권한 확인 후 충족시만 저장
            if(masteruser.get().getRole().equals("11")) {
                majorService.saveMajor(major);
                return ResponseEntity.ok("Major added  successfully");
            }

            // 권한 없을 시 오류
            return ResponseEntity.status(409).build();

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    // 졸업 기준 총 학점
    // POST : [/Major/getTotalScore]
    @PostMapping("/getTotalScore")
    public Integer getTotalScore(@RequestBody MajorDto majorDto, HttpServletRequest request) {
        String studentId = jwtUtil.getUsername(jwtUtil.getToken(request));
        Optional<User> user = userService.findByStudentId(studentId);
        if(user.get().getMajor().equals(majorDto.getMajorName()))
            return majorService.getTotalScore(majorDto);
        else {
            System.out.println("can;t found!");
            return 0;
        }
    }

    // 졸업 기준 총 전공 학점
    // POST : [/Major/getTotalMajorScore]
    @PostMapping("/getTotalMajorScore")
    public Integer getTotalMajorScore(@RequestBody MajorDto majorDto, HttpServletRequest request) {
        String studentId = jwtUtil.getUsername(jwtUtil.getToken(request));
        Optional<User> user = userService.findByStudentId(studentId);
        if(user.get().getMajor().equals(majorDto.getMajorName()))
            return majorService.getTotalMajorScore(majorDto);
        else return 0;
    }

    // 졸업 기준 총 일반 교양 학점
    // POST : [/Major/getTotalCommonScore]
        @PostMapping("/getTotalCommonScore")
    public Integer getTotalCommonScore(@RequestBody MajorDto majorDto, HttpServletRequest request) {
            String studentId = jwtUtil.getUsername(jwtUtil.getToken(request));
            Optional<User> user = userService.findByStudentId(studentId);
        if(user.get().getMajor().equals(majorDto.getMajorName()))
            return majorService.getTotalCommonScore(majorDto);
        else return 0;
    }

    // 졸업 기준 지정 교양 학점
    // POST : [/Major/getTotalDesignatedScore]
    @PostMapping("/getTotalDesignatedScore")
    public Integer getTotalDesignatedScore(@RequestBody MajorDto majorDto, HttpServletRequest request) {
        String studentId = jwtUtil.getUsername(jwtUtil.getToken(request));
        Optional<User> user = userService.findByStudentId(studentId);
        if(user.get().getMajor().equals(majorDto.getMajorName()))
            return majorService.getTotalDesignatedScore(majorDto);
        else return 0;
    }

    // 졸업 기준 남는 일반 학점 (ex. 총 학점 - 전공,지정,일반 = 남는 학점 ) 2022까지 입학생 적용
    // POST : [/Major/getTotalExtraScore]
    @PostMapping("/getTotalExtraScore")
    public Integer getTotalExtraScore(@RequestBody MajorDto majorDto, HttpServletRequest request) {
        String studentId = jwtUtil.getUsername(jwtUtil.getToken(request));
        Optional<User> user = userService.findByStudentId(studentId);
        if(user.get().getMajor().equals(majorDto.getMajorName())) {
            int extra = majorService.getTotalScore(majorDto); // 총 학점 저장 후
            // 각각을 빼줌
            extra -= majorService.getTotalMajorScore(majorDto);
            extra -= majorService.getTotalDesignatedScore(majorDto);
            extra -= majorService.getTotalCommonScore(majorDto);

            // 0보다 클 때만 반환
            return Math.max(extra, 0);
        }
        else return 0;
    }

}