package hr.ferit.coolschool.model;

import android.annotation.SuppressLint;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.Set;

public class Quiz {
    private Long quizId;
    @JsonFormat(pattern = "dd.MM.yyyy.")
    private Date creationTime;
    @JsonFormat(pattern = "dd.MM.yyyy.")
    private Date startTime;
    @JsonFormat(pattern = "dd.MM.yyyy.")
    private Date endTime;
    private String name;
    private String description;
    private int classNum;
    private SchoolType schoolType;
    private Subject subject;
    private Float maxPoints;
    private int difficulty;
    private boolean enabled = true;

    private Set<Question> questions;
    private Set<QuizParticipant> quizParticipants;

    public Quiz(Date creationTime, Date startTime, Date endTime, Integer classNum, SchoolType schoolType, Subject subject, Float maxPoints, int difficulty, boolean enabled) {
        this.creationTime = creationTime;
        this.startTime = startTime;
        this.endTime = endTime;
        this.classNum = classNum;
        this.schoolType = schoolType;
        this.subject = subject;
        this.maxPoints = maxPoints;
        this.difficulty = difficulty;
        this.enabled = enabled;
    }

    public Quiz() {
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "quizId=" + quizId +
                ", creationTime=" + creationTime +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", classNum=" + classNum +
                ", name=" + name +
                ", description=" + description +
                ", schoolType=" + schoolType +
                ", subject=" + subject +
                ", maxPoints=" + maxPoints +
                ", difficulty=" + difficulty +
                ", enabled=" + enabled +
                ", questions=" + questions +
                ", quizParticipants=" + quizParticipants +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quiz quiz = (Quiz) o;

        if (difficulty != quiz.difficulty) return false;
        if (enabled != quiz.enabled) return false;
        if (!quizId.equals(quiz.quizId)) return false;
        if (creationTime != null ? !creationTime.equals(quiz.creationTime) : quiz.creationTime != null)
            return false;
        if (startTime != null ? !startTime.equals(quiz.startTime) : quiz.startTime != null)
            return false;
        if (endTime != null ? !endTime.equals(quiz.endTime) : quiz.endTime != null) return false;
        if (classNum != quiz.classNum) return false;
        if (schoolType != quiz.schoolType) return false;
        if (subject != quiz.subject) return false;
        if (name != quiz.name) return false;
        if (description != quiz.description) return false;
        return maxPoints.equals(quiz.maxPoints);
    }

    @Override
    public int hashCode() {
        int result = quizId.hashCode();
        result = 31 * result + (creationTime != null ? creationTime.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + classNum;
        result = 31 * result + schoolType.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + subject.hashCode();
        result = 31 * result + maxPoints.hashCode();
        result = 31 * result + difficulty;
        result = 31 * result + (enabled ? 1 : 0);
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setClassNum(int classNum) {
        this.classNum = classNum;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getClassNum() {
        return classNum;
    }

    public void setClassNum(Integer classNum) {
        this.classNum = classNum;
    }

    public SchoolType getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(SchoolType schoolType) {
        this.schoolType = schoolType;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Float getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(Float maxPoints) {
        this.maxPoints = maxPoints;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    public Set<QuizParticipant> getQuizParticipants() {
        return quizParticipants;
    }

    public void setQuizParticipants(Set<QuizParticipant> quizParticipants) {
        this.quizParticipants = quizParticipants;
    }

    @SuppressLint("DefaultLocale")
    public String getDifficultyText() {
        switch (difficulty) {
            case 1:
                return String.format("Najjednostavnija (%d)", difficulty);
            case 2:
                return String.format("Vrlo jednostavna (%d)", difficulty);
            case 3:
                return String.format("Jednostavna (%d)", difficulty);
            case 4:
                return String.format("Umjerena (%d)", difficulty);
            case 5:
                return String.format("Srednja (%d)", difficulty);
            case 6:
                return String.format("Teška (%d)", difficulty);
            case 7:
                return String.format("Vrlo teška (%d)", difficulty);
            case 8:
                return String.format("Stručna (%d)", difficulty);
            case 9:
                return String.format("Profesionalna (%d)", difficulty);
            case 10:
                return String.format("Doktorska (%d)", difficulty);
            default:
                return String.format("Nepoznata (%d)", difficulty);
        }
    }

}
