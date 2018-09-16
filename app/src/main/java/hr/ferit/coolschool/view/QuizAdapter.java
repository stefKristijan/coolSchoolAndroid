package hr.ferit.coolschool.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.abdularis.civ.CircleImageView;

import java.util.List;

import hr.ferit.coolschool.R;
import hr.ferit.coolschool.model.Quiz;
import hr.ferit.coolschool.model.Subject;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.QuizViewHolder> {

    private List<Quiz> mQuizzes;
    private Context mContext;

    public QuizAdapter(List<Quiz> quizzes, Context context) {
        this.mQuizzes = quizzes;
        this.mContext = context;
    }

    @NonNull
    @Override
    public QuizViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View quizView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.quiz_item, viewGroup, false);
        return new QuizViewHolder(quizView);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizViewHolder quizViewHolder, int i) {
        Quiz quiz = mQuizzes.get(i);
        quizViewHolder.tvTitle.setText(quiz.getName());
        quizViewHolder.tvDescription.setText(quiz.getDescription());
        quizViewHolder.tvPoints.setText(String.valueOf(quiz.getMaxPoints()));
        quizViewHolder.tvDifficulty.setText(quiz.getDifficultyText());
        setSubjectPicture(quiz.getSubject(), quizViewHolder);
        setDifficultyColor(quiz.getDifficulty(), quizViewHolder);
    }

    private void setSubjectPicture(Subject subject, QuizViewHolder quizViewHolder) {
        switch (subject){
            case Biologija:
                quizViewHolder.civSubject.setImageResource(R.mipmap.biology);
                break;
            case Fizika:
                quizViewHolder.civSubject.setImageResource(R.mipmap.physics);
                break;
            case Matematika:
                quizViewHolder.civSubject.setImageResource(R.mipmap.math);
                break;
            case Hrvatski:
                quizViewHolder.civSubject.setImageResource(R.mipmap.croatian);
                break;
            case Geografija:
                quizViewHolder.civSubject.setImageResource(R.mipmap.geography);
                break;
            case Glazbeni:
                quizViewHolder.civSubject.setImageResource(R.mipmap.music);
                break;
            case Kemija:
                quizViewHolder.civSubject.setImageResource(R.mipmap.chemistry);
                break;
        }
    }

    private void setDifficultyColor(int difficulty, QuizViewHolder quizViewHolder) {
        switch (difficulty) {
            case 1:
                quizViewHolder.tvDifficulty.setTextColor(
                        mContext.getResources().getColor(R.color.difficulty_1));
                break;
            case 2:
                quizViewHolder.tvDifficulty.setTextColor(
                        mContext.getResources().getColor(R.color.difficulty_2));
                break;
            case 3:
                quizViewHolder.tvDifficulty.setTextColor(
                        mContext.getResources().getColor(R.color.difficulty_3));
                break;
            case 4:
                quizViewHolder.tvDifficulty.setTextColor(
                        mContext.getResources().getColor(R.color.difficulty_4));
                break;
            case 5:
                quizViewHolder.tvDifficulty.setTextColor(
                        mContext.getResources().getColor(R.color.difficulty_5));
                break;
            case 6:
                quizViewHolder.tvDifficulty.setTextColor(
                        mContext.getResources().getColor(R.color.difficulty_6));
                break;
            case 7:
                quizViewHolder.tvDifficulty.setTextColor(
                        mContext.getResources().getColor(R.color.difficulty_7));
                break;
            case 8:
                quizViewHolder.tvDifficulty.setTextColor(
                        mContext.getResources().getColor(R.color.difficulty_8));
                break;
            case 9:
                quizViewHolder.tvDifficulty.setTextColor(
                        mContext.getResources().getColor(R.color.difficulty_9));
                break;
            case 10:
                quizViewHolder.tvDifficulty.setTextColor(
                        mContext.getResources().getColor(R.color.difficulty_10));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mQuizzes.size();
    }

    public static class QuizViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView civSubject;
        private TextView tvTitle, tvDescription, tvPoints, tvDifficulty;

        QuizViewHolder(View view) {
            super(view);
            this.civSubject = view.findViewById(R.id.qi_image);
            this.tvDescription = view.findViewById(R.id.qi_tv_description);
            this.tvDifficulty = view.findViewById(R.id.qi_tv_difficulty);
            this.tvPoints = view.findViewById(R.id.qi_tv_points);
            this.tvTitle = view.findViewById(R.id.qi_tv_title);
        }
    }
}
