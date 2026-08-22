package com.database.model;

public class Score {
    private int studentId;
    private int totalScore;
    private String grade;

    public Score() {}

    public Score(int studentId, int totalScore, String grade) {
        this.studentId = studentId;
        this.totalScore = totalScore;
        this.grade = grade;
    }

    // Getters and Setters
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public int getTotalScore() { return totalScore; }
    public void setTotalScore(int totalScore) { this.totalScore = totalScore; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
}
