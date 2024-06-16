package com.dgu_csc.akomanager_back.service;

import com.dgu_csc.akomanager_back.dto.MajorDto;
import com.dgu_csc.akomanager_back.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public void saveUser(User users);
    public List<User> getAllUsers(String massterPassword);
    public Optional<User> searchUser(String studentId, String password);
    public Optional<User> updateUser(String studentId, String password, User updatedUsers);
    public boolean deleteUser(String studentId, String password);
    Optional<User> findByStudentId(String string);

    Optional<MajorDto> getMajorDto(String studentId);
}
