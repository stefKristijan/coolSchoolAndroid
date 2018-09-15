package hr.ferit.coolschool.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import hr.ferit.coolschool.R;
import hr.ferit.coolschool.model.UserSchool;

public class SchoolsAdapter extends RecyclerView.Adapter<SchoolsAdapter.SchoolViewHolder> {

    private List<UserSchool> mSchools;
    private boolean mShowDeleteBtn;

    public SchoolsAdapter(List<UserSchool> mSchools, boolean showDeleteBtn) {
        this.mSchools = mSchools;
        this.mShowDeleteBtn = showDeleteBtn;
    }

    public void setmShowDeleteBtn(boolean mShowDeleteBtn){
        this.mShowDeleteBtn = mShowDeleteBtn;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SchoolViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View schoolView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.school_item, viewGroup, false);
        return new SchoolViewHolder(schoolView);
    }

    @Override
    public void onBindViewHolder(@NonNull final SchoolViewHolder schoolViewHolder, int i) {
        UserSchool userSchool = this.mSchools.get(i);

        schoolViewHolder.tvName.setText(userSchool.getSchool().getName());
        schoolViewHolder.tvAddress.setText(
                String.format(
                        "%s, %s",
                        userSchool.getSchool().getAddress(),
                        userSchool.getSchool().getCity()
                )
        );
        String classSubText =
                userSchool.getClassNum() == null ?
                        "Učitelj/ica predmeta: %s" :
                        "Učenik/ca %s. razreda";
        schoolViewHolder.tvClassSubject.setText(
                String.format(
                        classSubText, userSchool.getClassNum() == null ?
                                userSchool.getSubject() : userSchool.getClassNum()
                )
        );
        schoolViewHolder.btnDelete.setVisibility(mShowDeleteBtn ? View.VISIBLE : View.GONE);
        schoolViewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSchools.remove(schoolViewHolder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSchools.size();
    }

    public static class SchoolViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvAddress, tvClassSubject;
        Button btnDelete;

        SchoolViewHolder(View view) {
            super(view);
            this.tvAddress = view.findViewById(R.id.rv_si_tv_address);
            this.tvName = view.findViewById(R.id.rv_si_tv_name);
            this.btnDelete = view.findViewById(R.id.rv_si_btn_delete);
            this.tvClassSubject = view.findViewById(R.id.rv_si_class_sub);
        }
    }

}
