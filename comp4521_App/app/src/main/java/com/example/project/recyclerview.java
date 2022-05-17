package com.example.project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class recyclerview extends RecyclerView.Adapter<recyclerview.viewHolder> {
    Context context;
    ArrayList<Events> array;
    DBOpenHelper helper;

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
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {
        Events events = array.get(position);
        holder.title.setText("Event: "+events.getEVENT());
        holder.time.setText("Time: "+events.getTIME());
        holder.date.setText("Date: "+events.getDATE());
        holder.des.setText(("Description: "+events.getDESCRIPTION()));
        holder.deleteevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helper=new DBOpenHelper(context);
                SQLiteDatabase db=helper.getWritableDatabase();
                helper.DeleteEvent(events.getEVENT(),events.getDATE(),events.getTIME(),events.getDESCRIPTION(),db);
                db.close();
                array.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView title,time,date,des;
        Button deleteevent;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.nameOfEvent);
            time=itemView.findViewById(R.id.timeOfEvent);
            date=itemView.findViewById(R.id.dateOfEvent);
            des=itemView.findViewById(R.id.description2);
            deleteevent=itemView.findViewById(R.id.deleteevent);

        }
    }
}
