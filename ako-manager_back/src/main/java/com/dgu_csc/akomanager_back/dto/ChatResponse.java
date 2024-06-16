package com.dgu_csc.akomanager_back.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ChatResponse {
    private String studentId;
    private String userinput;
    private String botoutput;
    private Instant timelog;
}
