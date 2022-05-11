package com.example.calendar;

import android.content.Context;
import android.graphics.Color;
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
        int DayNo= dateCalendar.get(Calendar.DAY_OF_MONTH);
        int curMonth=tempcalendar.get(Calendar.MONTH)+1;
        int curYear=tempcalendar.get(Calendar.YEAR);
        int disMonth=dateCalendar.get(Calendar.MONTH)+1;
        int disYear =dateCalendar.get(Calendar.YEAR);
        if(view==null){
            view=inflater.inflate(R.layout.calendar_cell,parent,false);

        }
        if(disYear==curYear&&disMonth==curMonth){
            view.setBackgroundColor(getContext().getResources().getColor(R.color.teal_200));
        }else {
            view.setBackgroundColor(Color.parseColor("#FFFFFF"));

        }
        TextView dnumber=view.findViewById(R.id.calendar_date);
        dnumber.setText(String.valueOf(DayNo));
        TextView Title=view.findViewById(R.id.event_id);
        Calendar event_Cal=Calendar.getInstance();
        ArrayList<String> array=new ArrayList<>();
        for(int i=0;i< events.size();i++){
            event_Cal.setTime(StrToDate(events.get(i).getDATE()));
            if(DayNo==event_Cal.get(Calendar.DAY_OF_MONTH)&&disMonth==event_Cal.get(Calendar.MONTH)+1&&disYear==event_Cal.get(Calendar.YEAR)){
                array.add(events.get(i).getEVENT());
                Title.setText(array.size()+" Events");
            }
        }
        return view;
    }
    private Date StrToDate(String date){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MMMM-dd");
        Date d = null;
        try {
            d = format.parse(date);

        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return d;
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
