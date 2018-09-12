package hr.ferit.coolschool.model;

import java.util.Set;

public class Question {
    private Long questionId;
    private String questionText;
    private String imagePath;
    private Quiz quiz;
    private Set<Answer> answers;

    public Question(String questionText, String imagePath, Quiz quiz) {
        this.questionText = questionText;
        this.imagePath = imagePath;
        this.quiz = quiz;
    }

    public Question() {
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionId=" + questionId +
                ", questionText='" + questionText + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", quiz=" + quiz +
                ", answers=" + answers +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        if (!questionText.equals(question.questionText)) return false;
        return answers.equals(question.answers);
    }

    @Override
    public int hashCode() {
        int result = questionText.hashCode();
        result = 31 * result + answers.hashCode();
        return result;
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
}

