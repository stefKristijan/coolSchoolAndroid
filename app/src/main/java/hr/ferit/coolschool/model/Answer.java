package hr.ferit.coolschool.model;

import java.util.Set;

public class Answer {

    private Long answerId;
    private String answer;
    private boolean correct = false;
    private Float points = (float) 0;
    private Question question;
    private Set<QuizParticipant> participants;


    public Answer(String answer, boolean correct, Float points, Question question) {
        this.answer = answer;
        this.correct = correct;
        this.points = points;
        this.question = question;
    }

    public Answer() {
    }

    @Override
    public String toString() {
        return "Answer{" +
                "answerId=" + answerId +
                ", answer='" + answer + '\'' +
                ", correct=" + correct +
                ", points=" + points +
                ", question=" + question +
                ", participants=" + participants +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer1 = (Answer) o;

        if (correct != answer1.correct) return false;
        if (!answer.equals(answer1.answer)) return false;
        return points.equals(answer1.points);
    }

    @Override
    public int hashCode() {
        int result = answer.hashCode();
        result = 31 * result + (correct ? 1 : 0);
        result = 31 * result + points.hashCode();
        return result;
    }

    public Set<QuizParticipant> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<QuizParticipant> participants) {
        this.participants = participants;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
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
