package com.dgu_csc.akomanager_back.repository;

import com.dgu_csc.akomanager_back.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 학번을 매개변수로 객체 반환
    Optional<User> findByStudentId(String studentId);
//    Users findByStudentId(String studentId);
}

