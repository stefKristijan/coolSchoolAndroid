package hr.ferit.coolschool.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class QuestionAnswerAdapter extends RecyclerView.Adapter<QuestionAnswerAdapter.QuestionAnswerViewHolder>{

    @NonNull
    @Override
    public QuestionAnswerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionAnswerViewHolder questionAnswerViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class QuestionAnswerViewHolder extends RecyclerView.ViewHolder {

        QuestionAnswerViewHolder(View view) {
            super(view);
        }
    }
}
