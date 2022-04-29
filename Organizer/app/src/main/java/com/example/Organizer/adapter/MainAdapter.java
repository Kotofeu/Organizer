package com.example.Organizer.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Organizer.Edit;
import com.example.Organizer.R;
import com.example.Organizer.db.Constant;
import com.example.Organizer.db.DBManager;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {
    private Context context;
    private List<link> Array;
    public MainAdapter(Context context){
        this.context = context;
        Array = new ArrayList<>();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.item_list_layout, parent,false);
        return new MyViewHolder(view, context, Array);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setData(Array.get(position).getTitle(), Array.get(position).getDate(), Array.get(position).getImage() );
    }

    @Override
    public int getItemCount() {
        return Array.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tv, td;
        private ImageView ti;
        private Context context;
        private List<link> Array;
        public MyViewHolder(@NonNull View itemView, Context context, List<link> array) {
            super(itemView);
            this.context = context;
            Array = array;
            tv = itemView.findViewById(R.id.TVTitle);
            td = itemView.findViewById(R.id.Time);
            ti = itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);
        }
        public void setData(String title, String date, String image){
            tv.setText(title);
            td.setText(date);

            if (Integer.parseInt(image) == 1){
                ti.setImageResource(R.drawable.warming);
            }
            else if(Integer.parseInt(image) == 2){
                ti.setImageResource(R.drawable.sport);

            }
            else  if(Integer.parseInt(image) == 3){
                ti.setImageResource(R.drawable.party);

            }
            else{
                ti.setImageResource(R.drawable.defeult);

            }
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(context, Edit.class);
            i.putExtra(Constant.LIST_ITEM_INTENT, Array.get(getAdapterPosition()));
            i.putExtra(Constant.EDIT_STATE, false);
            context.startActivity(i);
        }
    }
    public void updAdapter(List<link> newList){
        Array.clear();
        Array.addAll(newList);
        notifyDataSetChanged();
    }
    public void remove(int pos, DBManager DBM){
        DBM.delete(Array.get(pos).getId());
        Array.remove(pos);
        notifyItemRangeChanged(0, Array.size());
        notifyItemRemoved(pos);
    }

}
