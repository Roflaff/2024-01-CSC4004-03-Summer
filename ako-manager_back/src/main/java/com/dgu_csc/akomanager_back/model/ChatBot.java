package com.dgu_csc.akomanager_back.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class ChatBot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 질문내용
    @Column(name = "userinput")
    private String userinput;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cstudent_id", nullable = false, referencedColumnName = "student_id")
    private User cstudentId;

    @Column(name = "botinput", length = 4000)
    private String botinput;

    @Column(name = "timelog", nullable = false)
    private Instant timelog;

}

