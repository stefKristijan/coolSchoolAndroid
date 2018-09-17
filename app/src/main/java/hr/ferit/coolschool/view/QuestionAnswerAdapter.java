package hr.ferit.coolschool.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hr.ferit.coolschool.R;
import hr.ferit.coolschool.model.Answer;
import hr.ferit.coolschool.model.Question;

import static hr.ferit.coolschool.R.color.btn_add_center;

public class QuestionAnswerAdapter extends RecyclerView.Adapter<QuestionAnswerAdapter.QuestionAnswerViewHolder> {

    private List<Question> mQuestions;
    private Context mContext;

    public QuestionAnswerAdapter(List<Question> questions) {
        this.mQuestions = questions;
    }

    @NonNull
    @Override
    public QuestionAnswerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        View quizView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.question_item, viewGroup, false);
        return new QuestionAnswerViewHolder(quizView);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionAnswerViewHolder qavh, int i) {
        Question question = mQuestions.get(i);
        List<Answer> answers = new ArrayList<>(question.getAnswers());
        float points = 0;
        for (int j = 0; j < answers.size(); j++) {
            if (answers.get(j).isCorrect()) {
                points = answers.get(j).getPoints();
                switch (j) {
                    case 0:
                        qavh.tvAnswer1.setTextColor(mContext.getResources().getColor(btn_add_center));
                        break;
                    case 1:
                        qavh.tvAnswer2.setTextColor(mContext.getResources().getColor(btn_add_center));
                        break;
                    case 2:
                        qavh.tvAnswer3.setTextColor(mContext.getResources().getColor(btn_add_center));
                        break;
                    case 3:
                        qavh.tvAnswer4.setTextColor(mContext.getResources().getColor(btn_add_center));
                        break;
                }
                break;
            }
        }

        qavh.tvPoints.setText(String.valueOf(points));
        qavh.tvAnswer1.setText(answers.get(0).getAnswer());
        qavh.tvAnswer2.setText(answers.get(1).getAnswer());
        qavh.tvAnswer3.setText(answers.get(2).getAnswer());
        qavh.tvAnswer4.setText(answers.get(3).getAnswer());
        qavh.tvQuestion.setText(question.getQuestionText());

        qavh.btnDelete.setOnClickListener(v -> {
            mQuestions.remove(i);
            notifyDataSetChanged();
        });


    }

    @Override
    public int getItemCount() {
        return mQuestions.size();
    }

    static class QuestionAnswerViewHolder extends RecyclerView.ViewHolder {

        Button btnDelete;
        TextView tvQuestion, tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4, tvPoints;

        QuestionAnswerViewHolder(View view) {
            super(view);
            btnDelete = view.findViewById(R.id.qie_btn_delete);
            tvQuestion = view.findViewById(R.id.qie_tv_question);
            tvAnswer1 = view.findViewById(R.id.qie_tv_answer1);
            tvAnswer2 = view.findViewById(R.id.qie_tv_answer2);
            tvAnswer3 = view.findViewById(R.id.qie_tv_answer3);
            tvAnswer4 = view.findViewById(R.id.qie_tv_answer4);
            tvPoints = view.findViewById(R.id.qie_tv_points);
        }
    }
}
