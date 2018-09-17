package hr.ferit.coolschool.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import hr.ferit.coolschool.R;
import hr.ferit.coolschool.model.Rank;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.RankViewHolder> {

    private List<Rank> mRanks;
    private Context mContext;

    public RankAdapter(List<Rank> mRanks, Context mContext) {
        this.mRanks = mRanks;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RankViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View quizView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rank_item, viewGroup, false);
        return new RankViewHolder(quizView);
    }

    @Override
    public void onBindViewHolder(@NonNull RankViewHolder rankViewHolder, int i) {
        Rank rank = mRanks.get(i);
        rankViewHolder.tvTitle.setText(rank.getUsername());
        rankViewHolder.tvPoints.setText(String.valueOf(rank.getPoints()));
        rankViewHolder.tvPosition.setText(String.valueOf(i + 1));
    }

    @Override
    public int getItemCount() {
        return mRanks.size();
    }

    public static class RankViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvPoints, tvPosition;

        RankViewHolder(View view) {
            super(view);
            this.tvPosition = view.findViewById(R.id.ri_tv_position);
            this.tvPoints = view.findViewById(R.id.ri_tv_points);
            this.tvTitle = view.findViewById(R.id.ri_tv_title);
        }
    }
}
