package hr.ferit.coolschool.model;

public class QuizSolution {
    private Question question;
    private Answer givenAnswer;
    private Answer correctAnswer;
    private Float points = (float) 0;
    private Integer time;

    public QuizSolution(Question question, Answer givenAnswer, Answer correctAnswer, Float points) {
        this.question = question;
        this.givenAnswer = givenAnswer;
        this.correctAnswer = correctAnswer;
        this.points = points;
    }

    public QuizSolution() {
    }

    public void checkAnswer(Answer correctAnswer) {
        this.correctAnswer = correctAnswer;
        if (this.givenAnswer.getAnswer().equals(correctAnswer.getAnswer())) {
            this.points += correctAnswer.getPoints();
            this.givenAnswer.setCorrect(true);
        }else{
            this.givenAnswer.setCorrect(false);
            this.givenAnswer.setPoints((float) 0);
        }
    }

    @Override
    public String toString() {
        return "QuizSolution{" +
                "question=" + question +
                ", givenAnswer=" + givenAnswer +
                ", correctAnswer=" + correctAnswer +
                ", points=" + points +
                ", time=" + time +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuizSolution that = (QuizSolution) o;

        if (question != null ? !question.equals(that.question) : that.question != null) return false;
        if (givenAnswer != null ? !givenAnswer.equals(that.givenAnswer) : that.givenAnswer != null) return false;
        if (correctAnswer != null ? !correctAnswer.equals(that.correctAnswer) : that.correctAnswer != null)
            return false;
        return points != null ? points.equals(that.points) : that.points == null;
    }

    @Override
    public int hashCode() {
        int result = question != null ? question.hashCode() : 0;
        result = 31 * result + (givenAnswer != null ? givenAnswer.hashCode() : 0);
        result = 31 * result + (correctAnswer != null ? correctAnswer.hashCode() : 0);
        result = 31 * result + (points != null ? points.hashCode() : 0);
        return result;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Answer getGivenAnswer() {
        return givenAnswer;
    }

    public void setGivenAnswer(Answer givenAnswer) {
        this.givenAnswer = givenAnswer;
    }

    public Answer getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Answer correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Float getPoints() {
        return points;
    }

    public void setPoints(Float points) {
        this.points = points;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
