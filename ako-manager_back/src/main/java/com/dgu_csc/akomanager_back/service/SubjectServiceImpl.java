package com.dgu_csc.akomanager_back.service;

import com.dgu_csc.akomanager_back.model.Subject;
import com.dgu_csc.akomanager_back.repository.SubjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private static final String MASTER_PASSWORD = "SUMMER";

    // POST :  [/Subject/add] 과목 추가 (학수번호 중복 확인)
    @Override
    @Transactional
    public void saveSubject(Subject subject) {
        if (!subjectRepository.findBysubjectNumContaining(subject.getSubjectNum()).isEmpty()) {
            throw new IllegalArgumentException("Subject with subjectNum already exists");
        }
        subjectRepository.save(subject);
    }

    // GET : [/Subject/getAll] / user와 다르게 별도의 마스터 비밀 번호 필요 없다.
    @Override
    public List<Subject> getAllsubject() {
        return subjectRepository.findAll();
    }

    // 해당 문자열이 포함된 학수번호 검색시 관련 항목 뜨게
    // 학수번호와 과목명으로 검색 해서 subject 반환 하는 기능
    // 검색은 포함된 으로 변경 (Containing)

    // GET : [/Subject/{subjectNum}/get] url의 subjectNum으로 과목 정보 반환 (학수번호 일부만 검색 해도 나오게 구현)
    @Override
    public List<Subject> searchSubjectbysubjectNum(String subjectNum) {
        return subjectRepository.findBysubjectNumContaining(subjectNum);
    }

    // GET : [/Subject/{subjectName}/get] url의 subjectName으로 과목 정보 반환 (과목명 일부만 검색 해도 나오게 구현)
    @Override
    public List<Subject> searchSubjectbysubjectName(String subjectName) {
        return subjectRepository.findBysubjectNameContaining(subjectName);
    }

    // PUT : [/Subject/{subjectNum}/update] url의 subjectNum와 body의 Subject 정보로 업데이트
    public Optional<Subject> updateSubject(String subjectNum, Subject updateSubject) {
        List<Subject> subjects = subjectRepository.findBysubjectNumContaining(subjectNum);
        if (subjects.size() != 1) {
            return Optional.empty(); // Return empty if there are no matches or multiple matches
        }
        Subject subject = subjects.get(0);
        if (!subject.getSubjectNum().equals(subjectNum)) {
            return Optional.empty(); // Return empty if the subjectNum does not match
        }
        subject.setSubjectNum(updateSubject.getSubjectNum());
        subject.setSubjectName(updateSubject.getSubjectName());
        subject.setEstablishedGrade(updateSubject.getEstablishedGrade());
        subject.setOpenSemester(updateSubject.getOpenSemester());
        subject.setGrade(updateSubject.getGrade());
        subject.setCourseOfStudy(updateSubject.getCourseOfStudy());
        subject.setClassificationOfCompletion(updateSubject.getClassificationOfCompletion());

        return Optional.of(subjectRepository.save(subject));
    }

    // DELETE : [/User/{subjectNum}/delete] url의 studentId와 body의 마스터 비밀번호로 과목 정보 삭제
    @Override
    public boolean deleteSubject(String subjectNum, String password) {
        if(password.equals(MASTER_PASSWORD)) {
            return true;
        } else
            return false;
    }

    @Override
    public Optional<Subject> findBySubjectNum(String subjectnum) {
        return subjectRepository.findBysubjectNum(subjectnum);
    }

    @Override
    public Optional<Subject> findBySubjectName(String subjectname) {
        return subjectRepository.findBysubjectName(subjectname);
    }
}

