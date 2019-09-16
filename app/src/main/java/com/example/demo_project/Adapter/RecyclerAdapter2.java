package com.example.demo_project.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.demo_project.Custom_CardView;
import com.example.demo_project.Custom_CardView2;
import com.example.demo_project.R;

import java.util.List;

public class RecyclerAdapter2 extends RecyclerView.Adapter<RecyclerAdapter2.ViewHolder> {

    private List<Custom_CardView2> listitems;
    private Context context;

    public RecyclerAdapter2(List<Custom_CardView2> listitems, Context context) {
        this.context = context;
        this.listitems = listitems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_custom__card_view2, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Custom_CardView2 listItem = listitems.get(position);


        holder.order_no.setText(listItem.getOrder_no());
        holder.item_name.setText(listItem.getItem_name());
        holder.amount.setText(listItem.getAmount());


    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView order_no;
        public TextView item_name;
        public TextView amount;


        public ViewHolder(View itemView) {
            super(itemView);

            order_no = (TextView) itemView.findViewById(R.id.order_no);
            item_name = (TextView) itemView.findViewById(R.id.item_name);
            amount = (TextView) itemView.findViewById(R.id.amount);
        }
    }
}
