package com.example.danhbadienthoai;

import androidx.recyclerview.widget.RecyclerView;


import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.util.List;

public class AlphabetAdapter extends RecyclerView.Adapter<AlphabetAdapter.MyViewHolder> {

    List<String> alphalist;
    public AlphabetAdapter(List<String> alphalist){
        this.alphalist = alphalist;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alphabet_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlphabetAdapter.MyViewHolder holder, int position) {
        TextDrawable drawable;
        final  int alphabet_available = Common.alphabet_available.indexOf(alphalist.get(position));
        if(alphabet_available != -1){
            drawable = TextDrawable.builder().buildRound(alphalist.get(position), Color.GREEN);
        }else {
            drawable = TextDrawable.builder().buildRound(alphalist.get(position), Color.GRAY);
        }
        holder.avatar.setImageDrawable(drawable);
    }

    @Override
    public int getItemCount() {
        return alphalist.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView avatar;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            avatar  = (ImageView) itemView.findViewById(R.id.avatar_alphabet);
        }
    }
}