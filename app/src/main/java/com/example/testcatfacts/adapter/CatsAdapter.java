package com.example.testcatfacts.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.testcatfacts.R;
import com.example.testcatfacts.pojo.Cat;
import java.util.List;

public class CatsAdapter extends RecyclerView.Adapter<CatsAdapter.CatsViewHolder> {

    List<Cat> cats;

    public List<Cat> getCats() {
        return cats;
    }

    public void setCats(List<Cat> cats) {
        this.cats = cats;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cats_item, parent, false);
        return new CatsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatsViewHolder holder, int position) {
        holder.bind(cats.get(position));
    }

    @Override
    public int getItemCount() {
        return cats.size();
    }

    class CatsViewHolder extends RecyclerView.ViewHolder {

        TextView textViewFact;

        public CatsViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewFact = itemView.findViewById(R.id.textViewFact);

        }

        void bind(Cat cats) {
            textViewFact.setText(cats.getText());
        }
    }
}
