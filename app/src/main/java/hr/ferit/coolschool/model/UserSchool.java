package hr.ferit.coolschool.model;

import java.io.Serializable;

public class UserSchool implements Serializable {

    private Long userSchoolId;
    private User user;
    private School school;
    private Integer classNum;

    public UserSchool(User user, School school, Integer classNum) {
        this.user = user;
        this.school = school;
        this.classNum = classNum;
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
                '}';
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
}
