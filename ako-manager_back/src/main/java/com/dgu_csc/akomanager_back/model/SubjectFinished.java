package com.dgu_csc.akomanager_back.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

// 수강 완료된 과목에 대한 정보 저장 : 성적 파일
@NoArgsConstructor
@Getter
@Setter
@Entity
public class SubjectFinished {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Subject의 subjectName을 외래키로 가지는 항목
    @Column(name = "sf_subjectname", nullable = false, length = 100)
    private String sfSubjectname;

    // 학점(성적)
    @Column(name = "score", nullable = false, length = 4)
    private String score;

    // User 정보
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sf_studentid", nullable = false, referencedColumnName = "student_id")
    private User sfStudentid;


    // Subject 정보
    @ColumnDefault("0")
    @Column(name = "re_class", nullable = false)
    private Boolean reClass = false;

    // 수강 완료 연도
    @Column(name = "finishedY", length = 4)
    private String finishedY;

    // 수강 완료 학기 (계절학기 및 군학기 고려 필요)
    @Column(name = "finishedS", length = 5)
    private String finishedS;

    // 학점 (ex. 3학점)
    @Column(name = "grade", nullable = false, length = 3)
    private String grade;

    // 학수번호 앞에 3개
    @Column(name = "subject_num", nullable = false, length = 3)
    private String subjectNum;

}