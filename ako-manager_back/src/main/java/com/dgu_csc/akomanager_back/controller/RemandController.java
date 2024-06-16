//package com.dgu_csc.akomanager_back.controller;
//import com.dgu_csc.akomanager_back.jwt.JWTUtil;
//import com.dgu_csc.akomanager_back.model.Subject;
//import com.dgu_csc.akomanager_back.model.SubjectFinished;
//import com.dgu_csc.akomanager_back.model.User;
//import com.dgu_csc.akomanager_back.repository.UserRepository;
//import com.dgu_csc.akomanager_back.service.SubjectFinishedService;
//import com.dgu_csc.akomanager_back.service.UserService;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.*;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/RecommendSubjectController")
//public class RecommendSubjectController {
//
//    @Autowired
//    private JWTUtil jwtUtil;
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private SubjectFinishedService subjectFinishedService;
//
//    @GetMapping("/get")
//    public List<Subject> getRecommendSubject(HttpServletRequest request) {
//        // 헤더 에서 유저 정보 추출
//        String studentId = jwtUtil.getUsername(jwtUtil.getToken(request));
//        // 해당 유저 가져옴
//        Optional<User> user = userService.findByStudentId(studentId);
//        // 해당 유저의 전공 정보 가졍옴
//        String userMajor = user.get().getMajor();
//        List<SubjectFinished> subjectFinisheds = subjectFinishedService.searchByStudentId(studentId);
//
//
//
//
//    }
//
//            // 추천 과목을 요청하는 유저 A의 정보, 여기 DB에서 가져오는 걸로 수정해야 합니다.
//            // 함수 초기 매개변수: User 또는 User의 ID + Major
//            String A_ID = "2020113456";
//            String A_major = "컴퓨터공학전공";
//
//            // 유저 A의 수강한 과목 리스트, 여기 DB에서 가져오는걸로 수정해야합니다
//            List<List<String>> A_Database = Arrays.asList(
//                    Arrays.asList("2023", "2학기", "심화프로그래밍"),
//                    Arrays.asList("2023", "2학기", "어드벤처디자인"),
//                    Arrays.asList("2023", "1학기", "화학개론"),
//                    Arrays.asList("2023", "1학기", "컴퓨터구성")
//            );
//
//            // 현재 행의 과목 이름을 배열 A_Attended에 저장한다
//            List<String> A_Attended = new ArrayList<>();
//            for (List<String> list : A_Database) {
//                A_Attended.add(list.get(2));
//            }
//
//            // A의 이수학기
//            int A_Semester = calculateSemester(A_Database);
//
//            // 기존에 존재한 다른 유저들(B)의 정보, 여기 DB에서 수정해야 합니다.
//            String B1_ID = "2019115678";
//            String B1_Major = "컴퓨터공학전공";
//
//            String B2_ID = "2019117891";
//            String B2_Major = "컴퓨터공학전공";
//
//            String B3_ID = "2021113456";
//            String B3_Major = "컴퓨터공학전공";
//
//            List<List<String>> B1_Database = Arrays.asList(
//                    Arrays.asList("2024", "2학기", "심화프로그래밍"),
//                    Arrays.asList("2024", "2학기", "알고리즘"),
//                    Arrays.asList("2024", "1학기", "운영체제"),
//                    Arrays.asList("2023", "2학기", "심화프로그래밍")
//            );
//            List<List<String>> B2_Database = Arrays.asList(
//                    Arrays.asList("2024", "2학기", "알고리즘"),
//                    Arrays.asList("2023", "2학기", "데이터베이스"),
//                    Arrays.asList("2023", "겨울학기", "개별연구1"),
//                    Arrays.asList("2023", "1학기", "컴퓨터구성"),
//                    Arrays.asList("2022", "2학기", "기초프로그래밍")
//            );
//            List<List<String>> B3_Database = Arrays.asList(
//                    Arrays.asList("2024", "2학기", "인공지능"),
//                    Arrays.asList("2023", "2학기", "데이터베이스")
//            );
//
//            // 추천과목을 저장할 자료구조: B_Attended
//            Map<String, Integer> B_Attended = new HashMap<>();
//
//            System.out.println("A 이수학기: " + A_Semester);
//            System.out.println("B1 이수학기: " + calculateSemester(B1_Database));
//            System.out.println("B2 이수학기: " + calculateSemester(B2_Database));
//            System.out.println("B3 이수학기: " + calculateSemester(B3_Database));
//
//            // 각각의 B 유저에 대해 학기 수를 비교하고, A가 들을만한 과목들을 B_Attended에 저장한다.
//            if (calculateSemester(B1_Database) - A_Semester > 0) {
//                int k = calculateSemester(B1_Database) - A_Semester;
//                List<String> temp = returnSemester(B1_Database, k);
//
//                for (String lectureName : temp) {
//                    B_Attended.put(lectureName, B_Attended.getOrDefault(lectureName, 0) + 1);
//                }
//            }
//
//            if (calculateSemester(B2_Database) - A_Semester > 0) {
//                int k = calculateSemester(B2_Database) - A_Semester;
//                List<String> temp = returnSemester(B2_Database, k);
//
//                for (String lectureName : temp) {
//                    B_Attended.put(lectureName, B_Attended.getOrDefault(lectureName, 0) + 1);
//                }
//            }
//
//            if (calculateSemester(B3_Database) - A_Semester > 0) {
//                int k = calculateSemester(B3_Database) - A_Semester;
//                List<String> temp = returnSemester(B3_Database, k);
//
//                for (String lectureName : temp) {
//                    B_Attended.put(lectureName, B_Attended.getOrDefault(lectureName, 0) + 1);
//                }
//            }
//
//            // B_Attended와 A_Attended의 중복 항목을 삭제한다
//            for (String lecture : A_Attended) {
//                B_Attended.remove(lecture);
//            }
//
//            // B_Attended를 정렬한다.
//            List<Map.Entry<String, Integer>> sorted_B_Attended = new ArrayList<>(B_Attended.entrySet());
//            sorted_B_Attended.sort((a, b) -> b.getValue().compareTo(a.getValue()));
//
//            for (Map.Entry<String, Integer> entry : sorted_B_Attended) {
//                System.out.println(entry.getKey() + ": " + entry.getValue());
//            }
//        }
//
//        // User DB의 정보를 이용해 이수 학기를 계산하는 함수
//        public static int calculateSemester(List<List<String>> User_Database) {
//            int i = 1;
//            String yearSemester = User_Database.get(0).get(0) + User_Database.get(0).get(1);
//            for (List<String> lecture : User_Database) {
//                String currentYearSemester = lecture.get(0) + lecture.get(1);
//                if (!yearSemester.equals(currentYearSemester)) {
//                    if (!currentYearSemester.contains("겨울학기") && !currentYearSemester.contains("여름학기")) {
//                        i++;
//                        yearSemester = currentYearSemester;
//                    }
//                }
//            }
//            return i;
//        }
//
//        // 유저의 DB와 학기 차이 수를 이용해서 A가 다음 학기에 들을만한 과목들을 배열로 만들어 반환
//        public static List<String> returnSemester(List<List<String>> User_Database, int k) {
//            int i = 1;
//            List<String> result = new ArrayList<>();
//            String yearSemester = User_Database.get(0).get(0) + User_Database.get(0).get(1);
//            for (List<String> lecture : User_Database) {
//                if (!yearSemester.equals(lecture.get(0) + lecture.get(1))) {
//                    i++;
//                    yearSemester = lecture.get(0) + lecture.get(1);
//                }
//                if (i == k) {
//                    result.add(lecture.get(2));
//                } else if (i > k) {
//                    break; // 이미 k번째 학기를 지난 경우 루프 종료
//                }
//            }
//            return result;
//        }
//    }
//
//}
