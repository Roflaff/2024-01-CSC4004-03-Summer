package com.dgu_csc.akomanager_back.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class SubjectNow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 강의실
    @Column(name = "classroom", length = 100)
    private String classroom;

    // 요일 정보
    @Column(name = "date_info", length = 20)
    private String dateInfo;

    // 시간 정보 (요일과 쌍 맞추어야 한다)
    @Column(name = "time_info", length = 20)
    private String timeInfo;

    // 유저 정보
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sn_student_id", nullable = false, referencedColumnName = "student_id")
    private User snStudent;

    // 과목 이름 정보
    @Column(name = "sn_subject_name", nullable = false, length = 100)
    private String snSubjectName;

}