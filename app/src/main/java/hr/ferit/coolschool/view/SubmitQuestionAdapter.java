package hr.ferit.coolschool.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hr.ferit.coolschool.R;
import hr.ferit.coolschool.model.Answer;
import hr.ferit.coolschool.model.Question;
import hr.ferit.coolschool.model.QuizSolution;

public class SubmitQuestionAdapter extends RecyclerView.Adapter<SubmitQuestionAdapter.SubmitQuestionViewHolder> {

    private List<Question> mQuestions;
    private List<QuizSolution> mSolutions;

    public SubmitQuestionAdapter(List<Question> mQuestions) {
        this.mQuestions = mQuestions;
        mSolutions = new ArrayList<>();
        for(Question question : mQuestions){
            mSolutions.add(new QuizSolution(question, null, null, null));
        }
    }

    public List<QuizSolution> getmSolutions() {
        return mSolutions;
    }

    @NonNull
    @Override
    public SubmitQuestionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View quizView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.submit_question_item, viewGroup, false);
        return new SubmitQuestionViewHolder(quizView);
    }

    @Override
    public void onBindViewHolder(@NonNull SubmitQuestionViewHolder qsvh, int i) {
        Question question = mQuestions.get(i);
        List<Answer> answers = new ArrayList<>(question.getAnswers());

        qsvh.rbAnswer1.setText(answers.get(0).getAnswer());
        qsvh.rbAnswer2.setText(answers.get(1).getAnswer());
        qsvh.rbAnswer3.setText(answers.get(2).getAnswer());
        qsvh.rbAnswer4.setText(answers.get(3).getAnswer());
        qsvh.tvQuestion.setText(question.getQuestionText());

        qsvh.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            QuizSolution quizSolution = mSolutions.get(i);
            switch (checkedId) {
                case R.id.qsi_rb_answer_1:
                    quizSolution.setGivenAnswer(answers.get(0));
                    break;
                case R.id.qsi_rb_answer_2:
                    quizSolution.setGivenAnswer(answers.get(1));
                    break;
                case R.id.qsi_rb_answer_3:
                    quizSolution.setGivenAnswer(answers.get(2));
                    break;
                case R.id.qsi_rb_answer_4:
                    quizSolution.setGivenAnswer(answers.get(3));
                    break;
            }
            qsvh.rbAnswer1.setEnabled(false);
            qsvh.rbAnswer2.setEnabled(false);
            qsvh.rbAnswer3.setEnabled(false);
            qsvh.rbAnswer4.setEnabled(false);

        });
    }

    @Override
    public int getItemCount() {
        return mQuestions.size();
    }

    static class SubmitQuestionViewHolder extends RecyclerView.ViewHolder {

        private TextView tvQuestion, tvPointsEarned;
        private RadioButton rbAnswer1, rbAnswer2, rbAnswer3, rbAnswer4;
        private RadioGroup radioGroup;

        SubmitQuestionViewHolder(View view) {
            super(view);
            this.radioGroup = view.findViewById(R.id.qsi_rg_answer);
            this.tvQuestion = view.findViewById(R.id.qsi_question);
            this.tvPointsEarned = view.findViewById(R.id.qsi_tv_points);
            this.rbAnswer1 = view.findViewById(R.id.qsi_rb_answer_1);
            this.rbAnswer2 = view.findViewById(R.id.qsi_rb_answer_2);
            this.rbAnswer3 = view.findViewById(R.id.qsi_rb_answer_3);
            this.rbAnswer4 = view.findViewById(R.id.qsi_rb_answer_4);
        }
    }
}
