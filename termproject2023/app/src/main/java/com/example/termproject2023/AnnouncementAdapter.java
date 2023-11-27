package com.example.termproject2023;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.MyViewHolder > {
    Context context;
    ArrayList<Announcement> list;
    public AnnouncementAdapter(Context context, ArrayList<Announcement> list){
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public AnnouncementAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.announcement_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnouncementAdapter.MyViewHolder holder, int position) {
        Announcement announcement = list.get(position);
        holder.announcementText.setText(announcement.getText());
    }
    @Override
    public int getItemCount() {return list.size();}

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView announcementText;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            announcementText = itemView.findViewById(R.id.announcementText);
        }
    }
}
