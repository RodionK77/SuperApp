package com.example.superapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.CarsViewHolder> implements Serializable{

    private List<GameCard> items;
    Context context;
    private static int viewHolderCount;

    public RecycleAdapter(List<GameCard> items){
        this.items = items;
    }

    @NonNull
    @Override
    public CarsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        int layoutIdForListCars = R.layout.rv_game_card;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListCars, parent, false);
        CarsViewHolder viewHolder = new CarsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CarsViewHolder holder, int position) {
        GameCard item = items.get(position);
        holder.desc1.setText(item.getDescription1());
        holder.desc2.setText(item.getDescription2());
        holder.date.setText(item.getDate());
        Picasso.get().load(item.getImg()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class CarsViewHolder extends RecyclerView.ViewHolder implements Serializable {

        public TextView desc1, desc2;
        public TextView date;
        public ImageView image;

        public CarsViewHolder(@NonNull View itemView) {
            super(itemView);

            desc1 = (TextView)itemView.findViewById(R.id.tv_game_description1);
            desc2 = (TextView)itemView.findViewById(R.id.tv_game_description2);
            date = (TextView)itemView.findViewById(R.id.tv_game_date);
            image = (ImageView) itemView.findViewById(R.id.iv_game_card);

        }

    }
}