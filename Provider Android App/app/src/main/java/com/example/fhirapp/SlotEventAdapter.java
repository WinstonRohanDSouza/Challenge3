package com.example.fhirapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;



class SlotEventAdapter extends RecyclerView.Adapter<SlotEventAdapter.ViewHolder>{

    private static final String TAG = "SlotEventAdapter";

    Context context;
    public List<Event> SlotEventList;

    public SlotEventAdapter(Context context, List<Event> slotEventList) {
        this.context = context;
        SlotEventList = slotEventList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_layout_event,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(SlotEventAdapter.ViewHolder holder, int position) {

        String StartTime,EndTime;
        long duration;
        StartTime = SlotEventList.get(position).getStartTime();
        EndTime = SlotEventList.get(position).getEndTime();
        duration = SlotEventList.get(position).getDuration();
        holder.slotTiming.setText(StartTime+" to "+EndTime);//StartTime+EndTime
        holder.duration.setText((int) duration+" Mins");




        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String unformattedStartTime,unformattedEndTime;
                unformattedStartTime = SlotEventList.get(position).getUnformattedStartTime();
                unformattedEndTime = SlotEventList.get(position).getUnformattedEndTime();


            }
        });
    }

    @Override
    public int getItemCount() {
        return SlotEventList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView slotTiming;
        TextView duration;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            slotTiming = itemView.findViewById(R.id.slotTiming);
            duration = itemView.findViewById(R.id.slotDuration);
            cardView = itemView.findViewById(R.id.cardviewevent);
        }
    }
}