package com.dgu_csc.akomanager_back.service;

import com.dgu_csc.akomanager_back.model.Major;
import com.dgu_csc.akomanager_back.model.SubjectFinished;
import com.dgu_csc.akomanager_back.model.User;
import com.dgu_csc.akomanager_back.repository.MajorRepository;
import com.dgu_csc.akomanager_back.repository.SubjectFinishedRepository;
import com.dgu_csc.akomanager_back.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubjectFinishedServiceImpl implements SubjectFinishedService {
    private static final String MASTER_PASSWORD = "SUMMER";
    private final SubjectFinishedRepository subjectFinishedRepository;
    private final UserRepository userRepository;
    private final MajorRepository majorRepository;

    // POST :  [/SubjectFinished/add] 들은 과목 추가 (학수번호 중복 확인)
    @Override
    @Transactional
    public void saveSubjectFinished(SubjectFinished subjectFinished) {
        subjectFinishedRepository.save(subjectFinished);
    }

    // GET : [/SubjectFinished/getAll] DB 전체 내용 출력
    @Override
    public List<SubjectFinished> getAllSubjectFinished(String masterPassword) {
        if (MASTER_PASSWORD.equals(masterPassword)) {
            return subjectFinishedRepository.findAll();
        } else {
            throw new SecurityException("마스터 비밀번호 오류");
        }
    }

    // POST : [/SubjectFinished/{studentId}/get] url의 studentId와 body의 password 정보로 해당 유저의 들은 과목 정보 출력
    public List<SubjectFinished> searchByStudentId(String studentId) {
        Optional<User> userOpt = userRepository.findByStudentId(studentId);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        User users = userOpt.get();

        return subjectFinishedRepository.findBySfStudentid(users);
    }

    // TODO : 이수한 총 학점 수
    @Override
    public int getUserTotalScore(String studentId) {
        Optional<User> user = userRepository.findByStudentId(studentId);
        List<SubjectFinished> finishedSubjects = subjectFinishedRepository.findBySfStudentid(user.get());
        int totalScore = 0;

        for (SubjectFinished subject : finishedSubjects) {
            try {
                totalScore += (int) Double.parseDouble(subject.getGrade());
            } catch (NumberFormatException e) {
            }
        }
        return totalScore;
    }

    // TODO : 이수한 총 전공 학점 수
    @Override
    public int getUserTotalMajorScore(String studentId) {
        Optional<User> user = userRepository.findByStudentId(studentId);
        List<SubjectFinished> finishedSubjects = subjectFinishedRepository.findBySfStudentid(user.get());
        int totalMajorScore = 0;
        // 입학 연도 파싱
        String enterYear = studentId.substring(0, Math.min(studentId.length(), 4));
        Major major = majorRepository.findByMajorNameAndYear(user.get().getMajor(), enterYear).get();
        String userMajor = major.getMajorName();
        String subjectMNum = major.getMajornum();
        System.out.println(userMajor);

        for (SubjectFinished subject : finishedSubjects) {
            try {
                String subjectUserNum = subject.getSubjectNum();
                // 과목 DB에서 학수번호로 찾아서 나오고 나온 과목이 만약 전공 학수 번호로 시작되면 해당 학점 더한다.
                // 컴공 인 경우
                if (subjectMNum.equals("CSC")) {
                    if (subjectUserNum.equals("CSE") || subjectUserNum.equals("DAI") || subjectUserNum.equals("CSC") || subjectUserNum.equals("ASW")) {
                        totalMajorScore += (int) Double.parseDouble(subject.getGrade());
                    }
                }
            } catch (NumberFormatException e) {
            }
        }
        return totalMajorScore;
    }

    // TODO : 이수한 총 일반교양 학점 수
    @Override
    public int getUserTotalCommonScore(String studentId) {
        return 0;
    }
}