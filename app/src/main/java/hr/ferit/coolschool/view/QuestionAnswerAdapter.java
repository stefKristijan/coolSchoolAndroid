package hr.ferit.coolschool.view;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import hr.ferit.coolschool.R;
import hr.ferit.coolschool.model.Answer;
import hr.ferit.coolschool.model.Question;

public class QuestionAnswerAdapter extends RecyclerView.Adapter<QuestionAnswerAdapter.QuestionAnswerViewHolder> {

    private List<Question> mQuestions;

    public QuestionAnswerAdapter(List<Question> questions) {
        this.mQuestions = questions;
    }

    @NonNull
    @Override
    public QuestionAnswerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View quizView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.question_item, viewGroup, false);
        return new QuestionAnswerViewHolder(quizView);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionAnswerViewHolder qavh, int i) {
        Question question = mQuestions.get(i);
        List<Answer> answers = new ArrayList<>(question.getAnswers());
        float points = 0;
        for (Answer answer : answers) {
            if (answer.isCorrect()) {
                points = answer.getPoints();
                break;
            }
        }
        qavh.etQuestion.setText(question.getQuestionText());
        setOnTextChangeListener(qavh.etQuestion, qavh.tilQuestion);
        qavh.etAnswer1.setText(answers.get(0) != null ? answers.get(0).getAnswer() : "");
        setOnTextChangeListener(qavh.etAnswer1, qavh.tilAnswer1);
        qavh.etAnswer2.setText(answers.get(1) != null ? answers.get(1).getAnswer() : "");
        setOnTextChangeListener(qavh.etAnswer2, qavh.tilAnswer2);
        qavh.etAnswer3.setText(answers.get(2) != null ? answers.get(2).getAnswer() : "");
        setOnTextChangeListener(qavh.etAnswer3, qavh.tilAnswer3);
        qavh.etAnswer4.setText(answers.get(3) != null ? answers.get(3).getAnswer() : "");
        setOnTextChangeListener(qavh.etAnswer4, qavh.tilAnswer4);
        qavh.etPoints.setText(String.valueOf(points));
        qavh.etPoints.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                qavh.tilPoints.setErrorEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    float points = Float.parseFloat((String) s);
                    if(points <= 0){
                        qavh.tilPoints.setError("Unesite broj veći od 0");
                    }
                }catch (Exception e){
                    qavh.tilPoints.setError("Unesite broj");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        if (answers.get(0) != null && answers.get(0).isCorrect()) {
            qavh.rbAnswer1.setChecked(true);
        } else if (answers.get(1) != null && answers.get(1).isCorrect()) {
            qavh.rbAnswer2.setChecked(true);
        } else if (answers.get(2) != null && answers.get(2).isCorrect()) {
            qavh.rbAnswer3.setChecked(true);
        } else if (answers.get(3) != null && answers.get(3).isCorrect()) {
            qavh.rbAnswer4.setChecked(true);
        } else {
            qavh.rbAnswer1.setChecked(true);
        }
    }

    private void setOnTextChangeListener(TextInputEditText et, TextInputLayout til) {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                til.setErrorEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    til.setError("Unesite pitanje!");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mQuestions.size();
    }

    static class QuestionAnswerViewHolder extends RecyclerView.ViewHolder {

        TextInputLayout tilQuestion, tilAnswer1, tilAnswer2, tilAnswer3, tilAnswer4, tilPoints;
        TextInputEditText etQuestion, etAnswer1, etAnswer2, etAnswer3, etAnswer4, etPoints;
        Button btnDelete;
        RadioGroup radioGroup;
        RadioButton rbAnswer1, rbAnswer2, rbAnswer3, rbAnswer4;

        QuestionAnswerViewHolder(View view) {
            super(view);
            tilPoints = view.findViewById(R.id.qie_til_points);
            tilQuestion = view.findViewById(R.id.qie_til_question);
            tilAnswer1 = view.findViewById(R.id.qie_til_answer_1);
            tilAnswer2 = view.findViewById(R.id.qie_til_answer_2);
            tilAnswer3 = view.findViewById(R.id.qie_til_answer_3);
            tilAnswer4 = view.findViewById(R.id.qie_til_answer_4);

            etPoints = view.findViewById(R.id.qie_et_points);
            etQuestion = view.findViewById(R.id.qie_et_question);
            etAnswer1 = view.findViewById(R.id.qie_et_answer_1);
            etAnswer2 = view.findViewById(R.id.qie_et_answer_2);
            etAnswer3 = view.findViewById(R.id.qie_et_answer_3);
            etAnswer4 = view.findViewById(R.id.qie_et_answer_4);

            btnDelete = view.findViewById(R.id.qie_btn_delete);
            radioGroup = view.findViewById(R.id.qie_radio_group);
            rbAnswer1 = view.findViewById(R.id.qie_rb_answer_1);
            rbAnswer2 = view.findViewById(R.id.qie_rb_answer_2);
            rbAnswer3 = view.findViewById(R.id.qie_rb_answer_3);
            rbAnswer4 = view.findViewById(R.id.qie_rb_answer_4);
        }
    }
}
