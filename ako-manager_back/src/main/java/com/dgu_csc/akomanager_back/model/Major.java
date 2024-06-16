package com.dgu_csc.akomanager_back.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 전공 과목 정보
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Major {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 기준 연도
    @Column(name = "year", nullable = false, length = 10)
    private String year;

    // 전공명
    @Column(name = "major_name", nullable = false, length = 100)
    private String majorName;

    // 전공의 시작 학수번호 (ex. CSC, CSE)
    @Column(name = "majornum", nullable = false, length = 10)
    private String majornum;

    // 졸업 기준 총 학점
    @Column(name = "total_score", nullable = false)
    private Integer totalScore;

    // 졸업 기준 총 전공 학점
    @Column(name = "total_major_score", nullable = false)
    private Integer totalMajorScore;

    // 졸업 기준 총 일반 교양 학점
    @Column(name = "total_common_score")
    private Integer totalCommonScore;

    // 졸업 기준 지정 교양 학점
    @Column(name = "total_designated_score")
    private Integer totalDesignatedScore;

}