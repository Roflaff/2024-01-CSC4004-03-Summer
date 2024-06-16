package com.dgu_csc.akomanager_back.dto;

import com.dgu_csc.akomanager_back.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {
    private User updatedUsers;
    private String password;
}
