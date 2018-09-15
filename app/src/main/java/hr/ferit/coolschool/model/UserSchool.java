package hr.ferit.coolschool.model;

import java.io.Serializable;
import java.util.Objects;

public class UserSchool implements Serializable {

    private Long userSchoolId;
    private User user;
    private School school;
    private Integer classNum;
    private Subject subject;

    public UserSchool(User user, School school, Integer classNum, Subject subject) {
        this.user = user;
        this.school = school;
        this.classNum = classNum;
        this.subject = subject;
    }

    public UserSchool() {
    }

    @Override
    public String toString() {
        return "UserSchool{" +
                "userSchoolId=" + userSchoolId +
                ", user=" + user +
                ", school=" + school +
                ", classNum=" + classNum +
                ", subject=" + subject +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSchool that = (UserSchool) o;
        return Objects.equals(school, that.school) &&
                Objects.equals(classNum, that.classNum) &&
                subject == that.subject;
    }

    @Override
    public int hashCode() {

        return Objects.hash(school, classNum, subject);
    }

    public Long getUserSchoolId() {
        return userSchoolId;
    }

    public void setUserSchoolId(Long userSchoolId) {
        this.userSchoolId = userSchoolId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Integer getClassNum() {
        return classNum;
    }

    public void setClassNum(Integer classNum) {
        this.classNum = classNum;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
