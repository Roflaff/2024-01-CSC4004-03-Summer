    package com.dgu_csc.akomanager_back.model;

    import com.fasterxml.jackson.annotation.JsonFormat;
    import jakarta.persistence.*;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;
    import org.hibernate.annotations.ColumnDefault;

    import java.time.LocalDate;

    // 유저 개인 정보를 저장 하는 테이블
    @NoArgsConstructor
    @Getter
    @Setter
    @Entity
    public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        // 학번
        @Column(name = "student_id", nullable = false, length = 10)
        private String studentId;

        // 대학교
        @Column(name = "`university`", nullable = false, length = 30)
        private String university;

        // 이름
        @Column(name = "`name`", nullable = false, length = 20)
        private String name;

        // 생년월일
        @Column(name = "`date_of_birth`")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private LocalDate date_of_birth;

        // 단과대학
        @Column(name = "`college`", nullable = false, length = 40)
        private String college;

        // 전공
        @Column(name = "`major`", nullable = false, length = 100)
        private String major;

        // 부전공
        @Column(name = "`minor`" , length = 100)
        private String minor;

        // 닉네임
        @Column(name = "`username`", nullable = false, length = 10)
        private String username;

        // 비밀번호
        @Column(name = "`password`", nullable = false, length = 10)
        private String password;

        // 권한
        @ColumnDefault("0")
        @Column(name = "`role`")
        private String role;


    }
