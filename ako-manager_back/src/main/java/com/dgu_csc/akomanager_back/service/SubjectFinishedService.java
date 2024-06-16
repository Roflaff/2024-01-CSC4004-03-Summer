package com.dgu_csc.akomanager_back.service;

import com.dgu_csc.akomanager_back.model.SubjectFinished;

import java.util.List;

public interface SubjectFinishedService {
    // DB 저장
    public void saveSubjectFinished(SubjectFinished subjectFinished);
    // 모든 DB 내용 출력
    public List<SubjectFinished> getAllSubjectFinished(String masterPassword);
    // 유저 아이디 매개 변수로 검색해 해당 유저의 수강한 과목들 출력
    public List<SubjectFinished> searchByStudentId(String studentId);
    // 총 들은 학점 계산
    public int getUserTotalScore(String studentId);
    // 총 들은 전공 학점 계산
    public int getUserTotalMajorScore(String studentId);

    public int getUserTotalCommonScore(String studentId);

}
