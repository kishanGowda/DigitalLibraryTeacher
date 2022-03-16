package com.example.digitallibraryteacher.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitallibraryteacher.Model.CountModel;
import com.example.digitallibraryteacher.R;

import java.util.ArrayList;


public class CountAdapter extends RecyclerView.Adapter<CountAdapter.CardViewHolder> {

    ArrayList<CountModel> returned;
    Context context;

    public CountAdapter(ArrayList<CountModel> returned) {
        this.returned = returned;
    }


    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.count_item ,parent,false);
        CardViewHolder cvh = new CardViewHolder(view);
        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        CountModel currentCards = this.returned.get(position);
        holder.imageForCard.setImageResource(currentCards.getImageForCard());
        holder.zeroText.setText(currentCards.getZeroText());
        holder.subjectText.setText(currentCards.getSubjectText());
        holder.lastWeek.setVisibility(View.VISIBLE);
        if (currentCards.getLastWeek().charAt(0) == '-'){
            holder.lastWeek.setTextColor(Color.parseColor("#FF414D"));
        }
//        else {
//            holder.lastWeek.setTextColor(Color.parseColor(""));
//        }
        holder.lastWeek.setText(currentCards.getLastWeek());
    }

    @Override
    public int getItemCount() {
        return returned.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {

        ImageView imageForCard;
        TextView zeroText,subjectText;
        TextView lastWeek;



        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            imageForCard=itemView.findViewById(R.id.imageForCard);
            zeroText=itemView.findViewById(R.id.zeroText);
            subjectText=itemView.findViewById(R.id.subjectText);
            lastWeek=itemView.findViewById(R.id.last_count_textv);

        }
    }
}



