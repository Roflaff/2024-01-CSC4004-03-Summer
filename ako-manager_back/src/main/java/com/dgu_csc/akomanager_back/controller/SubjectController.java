package com.dgu_csc.akomanager_back.controller;

import com.dgu_csc.akomanager_back.dto.PasswordRequest;
import com.dgu_csc.akomanager_back.model.Subject;
import com.dgu_csc.akomanager_back.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Subject")
public class SubjectController {

    @Autowired
    private final SubjectService subjectService;

    // POST :  [/Subject/add] 과목 추가 (학수번호 중복 확인)
    @PostMapping("/add")
    public ResponseEntity<String> addSubject(@RequestBody Subject subject) {
        try {
            subjectService.saveSubject(subject);
            return ResponseEntity.ok("Subject added successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    // TODO: 같은 포맷의 api 요청이 있을 때 구별법 분류
    // GET : [/Subject/{subjectNum}/get] url의 subjectNum으로 과목 정보 반환 (학수번호 일부만 검색 해도 나오게 구현)
    @GetMapping("/getByNum/{subjectNum}")
    public ResponseEntity<List<Subject>> getSubjectbySubjectNum(@PathVariable String subjectNum) {
        List<Subject> subjects = subjectService.searchSubjectbysubjectNum(subjectNum);
        if (subjects.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(subjects);
        }
    }

    // GET : [/Subject/{subjectName}/get] url의 subjectName으로 과목 정보 반환 (과목명 일부만 검색 해도 나오게 구현)
    @GetMapping("/getByName/{subjectName}")
    public ResponseEntity<List<Subject>> getSubjectbySubjectName(@PathVariable String subjectName) {
        List<Subject> subjects = subjectService.searchSubjectbysubjectName(subjectName);
        if (subjects.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(subjects);
        }
    }

    // GET : [/Subject/getAll] / user와 다르게 별도의 마스터 비밀 번호 필요 없다.
    @GetMapping("/getAll")
    public List<Subject> allSubjectList() {
        return subjectService.getAllsubject();
    }

    // DELETE : [/Subject/{subjectNum}/delete] url의 subjectNum와 body의 마스터 비밀번호로 과목 정보 삭제
    @DeleteMapping("/{subjectNum}/delete")
    public ResponseEntity<Void> deleteUser(@PathVariable String subjectNum, @RequestBody PasswordRequest request) {
        boolean isDeleted = subjectService.deleteSubject(subjectNum, request.getPassword());
        if (isDeleted) {
            System.out.println("delete complete!");
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // PUT : [/Subject/{subjectNum}/update] url의 subjectNum와 body의 Subject 정보로 업데이트
    @PutMapping("/{subjectNum}/update")
    public ResponseEntity<Subject> updateSubject(@PathVariable String subjectNum, @RequestBody Subject updatesubject) {
        Optional<Subject> subject = subjectService.updateSubject(subjectNum, updatesubject);
        return subject.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
