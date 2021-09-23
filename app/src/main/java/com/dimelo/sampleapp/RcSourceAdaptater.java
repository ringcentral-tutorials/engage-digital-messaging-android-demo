package com.dimelo.sampleapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import java.util.List;

public class RcSourceAdaptater extends RecyclerView.Adapter<RcSourceAdaptater.ViewHolder> {
    private List<RcSourceModel> listData;
    private Context context;
    private RcSourceModel rcSourceModel;
    private OnItemClickListener listener;

    public RcSourceAdaptater(List<RcSourceModel> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.rc_source_item, viewGroup, false);
        return new ViewHolder(view);
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

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView configName;
        TextView description;
        CheckBox checkBox;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            configName = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            checkBox = itemView.findViewById(R.id.CheckBox);
            cardView = itemView.findViewById(R.id.layoutItem);
        }

        void selectConfig(final RcSourceModel rcConf, final int position) {
            configName.setText(rcConf.name);
            description.setText(rcConf.description);
            checkBox.setChecked(rcConf.isSelected);
            checkBox.setTag(new Integer(position));

            if (rcConf.isSelected) {
                rcSourceModel = rcConf;
            }
            checkBox.setOnClickListener(null);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!rcConf.isSelected) {
                        rcSourceModel.isSelected = false;
                        rcConf.isSelected = true;
                        rcSourceModel = rcConf;
                        if (listener != null) listener.onItemClick(itemView, position, rcConf);
                        notifyDataSetChanged();
                    }
                }
            });
        }
    }
}
