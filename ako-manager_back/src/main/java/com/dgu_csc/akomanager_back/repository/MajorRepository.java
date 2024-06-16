package com.dgu_csc.akomanager_back.repository;

import com.dgu_csc.akomanager_back.dto.MajorDto;
import com.dgu_csc.akomanager_back.model.Major;
import com.dgu_csc.akomanager_back.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MajorRepository extends JpaRepository<Major, Long> {
    List<Major> findByMajorName(String MajorName);
    Optional<Major> findByYear(String yesr);
    Optional<Major> findByMajorNameAndYear(String MajorName, String yesr);
}