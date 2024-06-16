package com.dgu_csc.akomanager_back.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 전체 과목에 대한 정보를 저장 하는 테이블
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 학수번호
    @Column(name = "subject_num", nullable = false, length = 8)
    private String subjectNum;

    // 개설학년
    @Column(name = "established_grade", length = 8)
    private String establishedGrade;

    // 개설학기
    @Column(name = "open_semester", length = 8)
    private String openSemester;

    // 교과과정
    @Column(name = "course_of_study", length = 10)
    private String courseOfStudy;

    // 이수구분
    @Column(name = "classification_of_completion", length = 10)
    private String classificationOfCompletion;

    // 교과목명
    @Column(name = "subject_name", length = 100)
    private String subjectName;

    // 학점
    @Column(name = "grade", nullable = false)
    private Integer grade;

}