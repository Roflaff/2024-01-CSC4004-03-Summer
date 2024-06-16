package com.dgu_csc.akomanager_back.controller;

import com.dgu_csc.akomanager_back.jwt.JWTUtil;
import com.dgu_csc.akomanager_back.model.Subject;
import com.dgu_csc.akomanager_back.model.SubjectFinished;
import com.dgu_csc.akomanager_back.model.SubjectNow;
import com.dgu_csc.akomanager_back.model.User;
import com.dgu_csc.akomanager_back.service.SubjectFinishedService;
import com.dgu_csc.akomanager_back.service.SubjectNowService;
import com.dgu_csc.akomanager_back.service.SubjectService;
import com.dgu_csc.akomanager_back.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/excel")
public class ExcelUploadController {

    @Autowired
    private SubjectService subjectService;
    @Autowired
    private SubjectFinishedService subjectFinishedService;
    @Autowired
    private SubjectNowService subjectNowService;
    @Autowired
    private UserService userService;
    @Autowired
    private JWTUtil jwtUtil;

    // POST : [/excel/uploadS]
    @PostMapping("/uploadS")
    public ResponseEntity<String> uploadExcelFileSubject(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is empty");
        }

        List<Subject> subjects = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) { // Skip header row
                    continue;
                }
                Subject subject = new Subject();
                for (Cell cell : row) {
                    // 엑셀 열을 기준으로 집어넣을 항목
                    switch (cell.getColumnIndex()) {
                        case 0:
                            break;
                        case 1:
                            // 개설 학년
                            subject.setEstablishedGrade(cell.toString());
                            break;
                        case 2:
                            // 개설 학기
                            subject.setOpenSemester(cell.toString());
                            break;
                        case 3:
                            // 교과 과정
                            subject.setCourseOfStudy(cell.toString());
                            break;
                        case 4:
                            break;
                        case 5:
                            // 이수 구분
                            subject.setClassificationOfCompletion(cell.toString());
                            break;
                        case 6:
                            // 학수 번호
                            subject.setSubjectNum(cell.toString());
                            break;
                        case 7:
                            // 과목명
                            subject.setSubjectName(cell.toString());
                            break;
                        case 8:
                            break;
                        case 9:
                            // 학점
                            subject.setGrade(Integer.parseInt(cell.toString()));
                            break;
                    }
                }
                subjects.add(subject);
            }
        }

        for (Subject subject : subjects) {
            try {
                subjectService.saveSubject(subject);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(409).body(e.getMessage());
            }
        }

        return ResponseEntity.ok("Subjects added successfully");
    }

    // 이수가 끝난 과목에 대한 엑셀 파일 업로드
    // POST : [/excel/uploadF]
    @PostMapping("/uploadF")
    public ResponseEntity<String> uploadExcelFileSubjectFinished(@RequestParam("file") MultipartFile file,
                                                                 HttpServletRequest request) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is empty");
        }

        String studentId = jwtUtil.getUsername(jwtUtil.getToken(request));
        User user = userService.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + studentId));

        List<SubjectFinished> subjectFinishedList = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) { // Skip header row
                    continue;
                }
                SubjectFinished subjectFinished = new SubjectFinished();
                subjectFinished.setSfStudentid(user); // Set the User object

                for (Cell cell : row) {
                    switch (cell.getColumnIndex()) {
                        case 1:
                            subjectFinished.setFinishedY(cell.toString());
                            break;
                        case 2:
                            subjectFinished.setFinishedS(cell.toString());
                            break;
                        case 5:
                            String subjectNum = cell.toString();
                            subjectNum = subjectNum.substring(0, Math.min(subjectNum.length(), 3));
                            subjectFinished.setSubjectNum(subjectNum);
                            break;
                        case 7:
                            subjectFinished.setSfSubjectname(cell.toString());
                            break;
                        case 9:
                            subjectFinished.setGrade(cell.toString());
                            break;
                        case 10:
                            // 학점(성적) 설정
                            subjectFinished.setScore(cell.toString());
                            break;
                        case 12:
                            // 재수강 여부 설정
                            subjectFinished.setReClass(Boolean.parseBoolean(cell.toString()));
                            break;
                    }
                }
                subjectFinishedList.add(subjectFinished);
            }
        }

        for (SubjectFinished sf : subjectFinishedList) {
            try {
                subjectFinishedService.saveSubjectFinished(sf);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(409).body(e.getMessage());
            }
        }

        return ResponseEntity.ok("SubjectFinished records added successfully");
    }

    // 현재 수강 중인 과목에 대한 엑셀 파일 업로드
    // POST : [/excel/uploadN]
    @PostMapping("/uploadN")
    public ResponseEntity<String> uploadExcelFileSubjectNow(@RequestParam("file") MultipartFile file,
                                                            HttpServletRequest request) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is empty");
        }

        String studentId = jwtUtil.getUsername(jwtUtil.getToken(request));
        User user = userService.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + studentId));

        // TODO :
        List<SubjectNow> SubjectNowlist = new ArrayList<SubjectNow>();
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) { // Skip header row
                    continue;
                }
                SubjectNow subjectNow = new SubjectNow();
                subjectNow.setSnStudent(user); // Set the User object

                for (Cell cell : row) {
                    switch (cell.getColumnIndex()) {
                        case 4:
                            subjectNow.setSnSubjectName(cell.toString());
                            break;

                        case 6:
                            String temp = cell.toString();
                            Map<String, List<String>> parsedSchedule = parseSchedule(temp);

                            StringBuilder dayTemp = new StringBuilder();
                            StringBuilder timeTemp = new StringBuilder();

                            for (Map.Entry<String, List<String>> entry : parsedSchedule.entrySet()) {
                                dayTemp.append(entry.getKey()).append(" ");
                                System.out.println(String.join(" ", convertToTimeRanges(entry.getValue())));
                                timeTemp.append(String.join(" ", convertToTimeRanges(entry.getValue()))).append(" ");
                            }

                            subjectNow.setDateInfo(dayTemp.toString().trim());
                            subjectNow.setTimeInfo(timeTemp.toString().trim());
                            break;

                        case 7:
                            String classroom = cell.toString();
                            classroom = extractString(classroom);
                            subjectNow.setClassroom(classroom);
                            break;

                    }
                }
                SubjectNowlist.add(subjectNow);
            }
        }

        for (SubjectNow sn : SubjectNowlist) {
            try {
                subjectNowService.saveSubjectNow(sn);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(409).body(e.getMessage());
            }
        }

        return ResponseEntity.ok("SubjectNow records added successfully");
    }

    // 시간, 요일 파싱 (hash)
    public static Map<String, List<String>> parseSchedule(String schedule) {
        Map<String, List<String>> parsedSchedule = new HashMap<>();

        if (schedule.isEmpty()) {
            return parsedSchedule; // 빈 문자열인 경우 그대로 반환
        }

        String[] entries = schedule.split(",");
        for (String entry : entries) {
            String day = entry.substring(0, 1); // 첫 번째 글자는 요일
            String timeRange = entry.substring(1); // 나머지는 시간 범위

            List<String> times = parsedSchedule.getOrDefault(day, new ArrayList<>());
            times.add(timeRange);
            parsedSchedule.put(day, times);
        }

        return parsedSchedule;
    }


    // 강의실 파싱
    public static String extractString(String input) {
        int startIndex = input.indexOf("(") + 1;
        if (startIndex == 0) {
            return "";
        }

        int endIndex = startIndex;
        while (endIndex < input.length() && input.charAt(endIndex) != ' ' && input.charAt(endIndex) != '(') {
            endIndex++;
        }

        return input.substring(startIndex, endIndex);
    }

    public static List<String> convertToTimeRanges(List<String> timeRanges) {
        List<String> formattedRanges = new ArrayList<>();
        for (String range : timeRanges) {
            String[] times = range.split("-");
            if (times.length == 2) {
                String startTime = convertToTime(Double.parseDouble(times[0]));
                String endTime = convertToTime(Double.parseDouble(times[1]));
                formattedRanges.add(startTime + "~" + endTime);
            }
        }
        return formattedRanges;
    }





    public static String convertToTime(double num) {
        // 기준 시간 설정 (1.0을 오전 8시로 변환)
        int baseHour = 8; // 오전 8시로
        int baseMinute = 0;

        // 입력된 숫자에 따라 시간과 분 계산
        int totalMinutes = (int) (num * 60);
        int hours = baseHour + totalMinutes / 60;
        int minutes = baseMinute + totalMinutes % 60;

        // 시간과 분을 포맷팅하여 반환
        return String.format("%02d:%02d", hours, minutes);
    }

}