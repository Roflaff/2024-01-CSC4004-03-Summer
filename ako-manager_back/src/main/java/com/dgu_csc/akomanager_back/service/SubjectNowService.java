package com.dgu_csc.akomanager_back.service;

import com.dgu_csc.akomanager_back.model.SubjectNow;

import java.util.List;

public interface SubjectNowService {
    public List<SubjectNow> getAllSubjectList(String studentId);
    public void saveSubjectNow(SubjectNow subjectNow);
}
