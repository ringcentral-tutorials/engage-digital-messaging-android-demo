package com.dimelo.sampleapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import java.util.List;

public class RcSourceAdapter extends RecyclerView.Adapter<RcSourceAdapter.ViewHolder>  {
    private List<RcSourceModel> listData;
    private Context context;
    private OnItemClickListener listener;
    private int selectedStarPosition = -1;

    public RcSourceAdapter(List<RcSourceModel> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.rc_source_item, viewGroup, false);
        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder singleViewHolder, int i) {
        singleViewHolder.selectConfig(listData.get(i), i);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position, RcSourceModel rcConf);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.listener = onItemClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView configName;
        TextView description;
        RadioButton sourceChecked;
        CardView cardView;
        int position;
        RcSourceModel rcsSourceSelected;

        public ViewHolder(@NonNull View itemView, RcSourceAdapter mAdapter) {
            super(itemView);
            configName = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            sourceChecked = itemView.findViewById(R.id.SourceChecked);
            cardView = itemView.findViewById(R.id.layoutItem);
            sourceChecked.setOnClickListener(this);
        }

        void selectConfig(final RcSourceModel rcConf, final int position) {
            configName.setText(rcConf.name);
            description.setText(rcConf.description);
            if (rcConf.isSelected) {
                selectedStarPosition = position;
            }
            sourceChecked.setChecked(position == selectedStarPosition);

            this.position = position;
            rcsSourceSelected = rcConf;
            sourceChecked.setOnClickListener(this);
            cardView.setOnClickListener(this);
            description.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listData.get(selectedStarPosition).isSelected = false;
            selectedStarPosition = getAdapterPosition();
            notifyDataSetChanged();
            listener.onItemClick(itemView, position, rcsSourceSelected);
        }

    }
}
