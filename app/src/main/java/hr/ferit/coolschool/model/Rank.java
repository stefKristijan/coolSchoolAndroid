package hr.ferit.coolschool.model;

public class Rank {
    private Long id;
    private String username;
    private float points;
    private String schoolName;
    private int classNum;

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "Rank{" +
                "username='" + username + '\'' +
                ", points=" + points +
                ", schoolName='" + schoolName + '\'' +
                ", classNum=" + classNum +
                '}';
    }

    public Rank(String username, float points, String schoolName, int classNum) {
        this.username = username;
        this.points = points;
        this.schoolName = schoolName;
        this.classNum = classNum;
    }

    public Rank() {
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public int getClassNum() {
        return classNum;
    }

    public void setClassNum(int classNum) {
        this.classNum = classNum;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public float getPoints() {
        return points;
    }

    public void setPoints(float points) {
        this.points = points;
    }
}
