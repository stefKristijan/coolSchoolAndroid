package hr.ferit.coolschool.model;

import java.time.LocalDateTime;
import java.util.Set;

public class Quiz {
    private Long quizId;
    private LocalDateTime creationTime;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int classNum;
    private SchoolType schoolType;
    private Subject subject;
    private Float maxPoints;
    private int difficulty;
    private boolean enabled = true;

    private Set<Question> questions;
    private Set<QuizParticipant> quizParticipants;

    public Quiz(LocalDateTime creationTime, LocalDateTime startTime, LocalDateTime endTime, Integer classNum, SchoolType schoolType, Subject subject, Float maxPoints, int difficulty, boolean enabled) {
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
        if (creationTime != null ? !creationTime.equals(quiz.creationTime) : quiz.creationTime != null) return false;
        if (startTime != null ? !startTime.equals(quiz.startTime) : quiz.startTime != null) return false;
        if (endTime != null ? !endTime.equals(quiz.endTime) : quiz.endTime != null) return false;
        if (classNum != quiz.classNum) return false;
        if (schoolType != quiz.schoolType) return false;
        if (subject != quiz.subject) return false;
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
        result = 31 * result + subject.hashCode();
        result = 31 * result + maxPoints.hashCode();
        result = 31 * result + difficulty;
        result = 31 * result + (enabled ? 1 : 0);
        return result;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
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
}
