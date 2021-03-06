package com.example.project;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendatAdapter extends ArrayAdapter {
    List<Date> dates;
    LayoutInflater inflater;
    Calendar tempcalendar;
    List<Events> events;
    public CalendatAdapter(@NonNull Context context,List<Date> dates,Calendar calendar,List<Events> events) {
        super(context,R.layout.calendar_cell);
        this.dates=dates;
        this.events=events;
        inflater=LayoutInflater.from(context);
        this.tempcalendar=calendar;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view=convertView;
        Date monthDate=dates.get(position);
        Calendar dateCalendar=Calendar.getInstance();
        dateCalendar.setTime(monthDate);
        int DateNo= dateCalendar.get(Calendar.DAY_OF_MONTH);
        int curMonth=tempcalendar.get(Calendar.MONTH);
        curMonth+=1;
        int curYear=tempcalendar.get(Calendar.YEAR);
        int disMonth=dateCalendar.get(Calendar.MONTH);
        disMonth+=1;
        int disYear =dateCalendar.get(Calendar.YEAR);
        if(view==null){
            view=inflater.inflate(R.layout.calendar_cell,parent,false);
        }
        if(disYear==curYear&&disMonth==curMonth){
            view.setBackgroundColor(getContext().getResources().getColor(R.color.calendarHightlight)); //highlight color

        }else {
            view.setBackgroundColor(Color.parseColor("#FFFFFF")); //white

        }
        TextView dnumber=view.findViewById(R.id.calendar_date);
        dnumber.setText(String.valueOf(DateNo));

        TextView Title=view.findViewById(R.id.event_id);
        Calendar event_Cal=Calendar.getInstance();
        ArrayList<String> array=new ArrayList<>();
        Log.d("call", "this get view is called");
        Log.d("eventsize", "event size is: " + DateNo + " " + disMonth + " " + disYear + " " + events.size());
        for(int i=0;i< events.size();i++){
            //event_Cal.setTime(StrToDate(events.get(i).getDATE()));
            Log.d("log to see", "log to see: " + events.get(i).getDATE());
            //int date = Integer.valueOf(StrToDate(events.get(i).getDATE()));
            String substring = events.get(i).getDATE().length() > 2 ? events.get(i).getDATE().substring(events.get(i).getDATE().length() - 2) : events.get(i).getDATE();
            int dateint = Integer.parseInt(substring);
            int date = 18;
            if(DateNo==dateint && disMonth==event_Cal.get(Calendar.MONTH)+1&&disYear==event_Cal.get(Calendar.YEAR)){
                array.add(events.get(i).getEVENT());
                Log.d("arraysize", "arraySize is: " + array.size());
                Title.setText(array.size()  +" Events");
            }
        }
        return view;
    }


    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public int getPosition(@Nullable Object item) {
        return dates.indexOf(item);
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return dates.get(position);
    }
}
