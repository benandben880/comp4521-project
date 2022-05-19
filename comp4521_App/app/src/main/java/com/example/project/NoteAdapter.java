package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//need a custom layout for this adapter
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    LayoutInflater inflater;
    List<Note> notes;

    NoteAdapter(Context context, List<Note> notes){
        this.inflater = LayoutInflater.from(context);
        this.notes = notes;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.custom_list_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position) {
        String title = notes.get(position).getTitle();
        String date = notes.get(position).getDate();
        String time = notes.get(position).getTime();
        long id = notes.get(position).getID();

        holder.nTitle.setText(title);
        holder.nTime.setText(time);
        holder.nDate.setText(date);
        holder.nID.setText(String.valueOf(notes.get(position).getID()));

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        //binding data, retrieve the data from these nodes of list view and save the data to our title date and time field using this viewholder
        TextView nTitle, nDetails, nDate, nTime, nID;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            //no need bind details as it is not gonna to display
            nTitle = itemView.findViewById(R.id.nTitle);
            nDate = itemView.findViewById(R.id.nDate);
            nTime = itemView.findViewById(R.id.nTime);
            nID = itemView.findViewById(R.id.listId);
            //then we need to set these three data to our onbindviewholder


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Debug check if the item is clicked
                    //Toast.makeText(view.getContext(), "Item clicked", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(view.getContext(), DetailsActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putLong("IDName", notes.get(getAdapterPosition()).getID());
//                    i.putExtras(bundle);
                    i.putExtra("IDName", notes.get(getAdapterPosition()).getID());
                    //Log.d("ApaterID", "ApaterID is: " + notes.get(getAdapterPosition()).getID());
                    view.getContext().startActivity(i);
                }
            });
        }
    }

}
