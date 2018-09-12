package hr.ferit.coolschool.model;

import java.util.List;

public class QuizReport {

    private List<QuizSolution> solutions;
    private Float points;
    private Integer timeToFinish;

    public QuizReport(List<QuizSolution> solutions, Float points, Integer timeToFinish) {
        this.solutions = solutions;
        this.points = points;
        this.timeToFinish = timeToFinish;
    }

    public QuizReport() {
    }

    @Override
    public String toString() {
        return "QuizReport{" +
                "solutions=" + solutions +
                ", points=" + points +
                ", timeToFinish=" + timeToFinish +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuizReport that = (QuizReport) o;

        if (solutions != null ? !solutions.equals(that.solutions) : that.solutions != null) return false;
        if (points != null ? !points.equals(that.points) : that.points != null) return false;
        return timeToFinish != null ? timeToFinish.equals(that.timeToFinish) : that.timeToFinish == null;
    }

    @Override
    public int hashCode() {
        int result = solutions != null ? solutions.hashCode() : 0;
        result = 31 * result + (points != null ? points.hashCode() : 0);
        result = 31 * result + (timeToFinish != null ? timeToFinish.hashCode() : 0);
        return result;
    }

    public List<QuizSolution> getSolutions() {
        return solutions;
    }

    public void setSolutions(List<QuizSolution> solutions) {
        this.solutions = solutions;
    }

    public Float getPoints() {
        return points;
    }

    public void setPoints(Float points) {
        this.points = points;
    }

    public Integer getTimeToFinish() {
        return timeToFinish;
    }

    public void setTimeToFinish(Integer timeToFinish) {
        this.timeToFinish = timeToFinish;
    }
}
