package com.dgu_csc.akomanager_back.service;

import com.dgu_csc.akomanager_back.model.Subject;

import java.util.List;
import java.util.Optional;

public interface SubjectService {
    public void saveSubject(Subject subject);
    public List<Subject> getAllsubject();
    public List<Subject> searchSubjectbysubjectNum(String subjectNum);
    public List<Subject> searchSubjectbysubjectName(String subjectName);
    public Optional<Subject> updateSubject(String subjectNum, Subject updateSubject);
    public boolean deleteSubject(String subjectNum, String password);

    Optional<Subject> findBySubjectNum(String subjectnum);

    Optional<Subject> findBySubjectName(String subjectname);
}
