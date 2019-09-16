package com.example.demo_project.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.demo_project.Custom_CardView;
import com.example.demo_project.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<Custom_CardView> listitems;
    private Context context;

    public RecyclerAdapter(List<Custom_CardView> listitems, Context context) {
        this.context = context;
        this.listitems = listitems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_custom__card_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Custom_CardView listItem = listitems.get(position);


        holder.group_name.setText(listItem.getGroup_name());
        holder.item_name.setText(listItem.getItem_name());
        holder.oty.setText(listItem.getOty());
        holder.rate.setText(listItem.getRate());
        holder.amount.setText(listItem.getAmount());


    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView group_name;
        public TextView item_name;
        public TextView oty;
        public TextView rate;
        public TextView amount;


        public ViewHolder(View itemView) {
            super(itemView);

            group_name = (TextView) itemView.findViewById(R.id.group_name);
            item_name = (TextView) itemView.findViewById(R.id.item_name);
            oty = (TextView) itemView.findViewById(R.id.oty);
            rate = (TextView) itemView.findViewById(R.id.rate);
            amount = (TextView) itemView.findViewById(R.id.amount);
        }
    }
}
