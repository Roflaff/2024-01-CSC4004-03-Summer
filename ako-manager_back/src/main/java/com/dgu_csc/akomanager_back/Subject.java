package com.dgu_csc.akomanager_back;

public class Subject {
    private final String courseNumber; // 학수 번호
    private String yearOfOffering; // 개설 학년
    private String semesterOfOffering; // 개설 학기
    private String curriculum; // 교과 과정
    private String lectureType; // 이수 구분
    private String lectureName; // 교과목명
    private int credit; // 학점

    public Subject(String courseNumber, String yearOfOffering, String semesterOfOffering, String curriculum, String lectureType, String lectureName, int credit) {
        this.courseNumber = courseNumber;
        this.yearOfOffering = yearOfOffering;
        this.semesterOfOffering = semesterOfOffering;
        this.curriculum = curriculum;
        this.lectureType = lectureType;
        this.lectureName = lectureName;
        this.credit = credit;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public String getYearOfOffering() {
        return yearOfOffering;
    }

    public String getSemesterOfOffering() {
        return semesterOfOffering;
    }
    public String getCurriculum() {
        return curriculum;
    }
    public String getLectureType() {
        return lectureType;
    }

    public String getLectureName() {
        return lectureName;
    }
    public int getCredit() {
        return credit;
    }
}