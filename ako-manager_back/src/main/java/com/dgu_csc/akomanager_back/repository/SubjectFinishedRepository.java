package com.dgu_csc.akomanager_back.repository;

import com.dgu_csc.akomanager_back.model.SubjectFinished;
import com.dgu_csc.akomanager_back.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectFinishedRepository extends JpaRepository<SubjectFinished, Long> {
    // 사용자가 들은 과목의 정보를 찾아서 반환
    List<SubjectFinished> findBySfStudentid(User users);
    // 과목에 대한 정보 반환
//    List<SubjectFinished> findBySfSubjectnum(Subject subject);

    Optional<SubjectFinished> findBySfSubjectname(String sfSubjectname);
}
