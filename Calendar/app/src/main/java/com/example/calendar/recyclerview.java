package com.example.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class recyclerview extends RecyclerView.Adapter<recyclerview.viewHolder> {
    Context context;
    ArrayList<Events> array;

    public recyclerview(Context context, ArrayList<Events> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singleevent,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Events events = array.get(position);
        holder.title.setText(events.getEVENT());
        holder.time.setText(events.getTIME());
        holder.date.setText(events.getDATE());
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView title,time,date;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.nameOfEvent);
            time=itemView.findViewById(R.id.timeOfEvent);
            date=itemView.findViewById(R.id.dateOfEvent);

        }
    }
}
