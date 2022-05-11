package com.example.calendar;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class CustomCalendarView extends LinearLayout {
    Button NextButton,PreviousButton;
    TextView CurrentDate;
    GridView gridView;
    Calendar calendar=Calendar.getInstance();
    Context context;
    List<Date> dates=new ArrayList<>();
    List<Events> eventList=new ArrayList<>();
    SimpleDateFormat monthFormat=new SimpleDateFormat("MMMM");
    SimpleDateFormat yearFormat=new SimpleDateFormat("yyyy");
    CalendatAdapter calendarAdapter;
    DBOpenHelper helper;
    AlertDialog alertDialog;
    SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");




    public CustomCalendarView(Context context) {
        super(context);
    }

    public CustomCalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        InitializeLayout();
        SetUpCalendar();
        PreviousButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.MONTH,-1);
                SetUpCalendar();
            }
        });
        NextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.MONTH,1);
                SetUpCalendar();
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                AlertDialog.Builder builder= new AlertDialog.Builder(context);
                builder.setCancelable(true);
                View addView= LayoutInflater.from(adapterView.getContext()).inflate(R.layout.add_event,null);
                EditText event_name=addView.findViewById(R.id.event_title);
                TextView eventtime=addView.findViewById(R.id.eventtime);
                Button addevent= addView.findViewById(R.id.addevent);
                eventtime.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar c=Calendar.getInstance();
                        int min=c.get(Calendar.MINUTE);
                        int h=c.get(Calendar.HOUR_OF_DAY);
                        TimePickerDialog timePickerDialog=new TimePickerDialog(addView.getContext(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                Calendar c=Calendar.getInstance();
                                c.set(Calendar.HOUR_OF_DAY,i);
                                c.set(Calendar.MINUTE,i1);
                                c.setTimeZone(TimeZone.getDefault());
                                SimpleDateFormat format=new SimpleDateFormat("K:mm a");
                                String ETime = format.format(c.getTime());
                                eventtime.setText(ETime);

                            }
                        },h,min,false);
                        timePickerDialog.show();
                    }
                });
                String selectedd=dateFormat.format(dates.get(position));
                String selectedm=monthFormat.format(dates.get(position));
                String selectedy=yearFormat.format(dates.get(position));


                addevent.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        save(event_name.getText().toString(),eventtime.getText().toString(),selectedd,selectedm,selectedy);
                        SetUpCalendar();
                        alertDialog.dismiss();
                    }
                });
                builder.setView(addView);
                alertDialog=builder.create();
                alertDialog.show();
            }
        });
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder= new AlertDialog.Builder(context);
                String date = dateFormat.format(dates.get(i));
                builder.setCancelable(true);
                View show=LayoutInflater.from(adapterView.getContext()).inflate(R.layout.event_list,null);
                RecyclerView tempView=show.findViewById(R.id.list);
                RecyclerView.LayoutManager manager=new LinearLayoutManager(show.getContext());
                tempView.setLayoutManager(manager);
                tempView.setHasFixedSize(true);
                recyclerview viewhelp=new recyclerview(show.getContext(),EventListHelper(date));
                tempView.setAdapter(viewhelp);
                viewhelp.notifyDataSetChanged();
                builder.setView(show);
                alertDialog= builder.create();
                alertDialog.show();
                return true;
            }
        });
    }
    public void save(String eventName,String time,String date,String month,String year){

        helper=new DBOpenHelper(context);
        SQLiteDatabase db=helper.getWritableDatabase();

        helper.SaveEvent(eventName,time,date,month,year,db);
        helper.close();
    }

    public CustomCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void InitializeLayout(){
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view =inflater.inflate(R.layout.calendar_layout,this);
        NextButton=view.findViewById(R.id.next);
        PreviousButton=view.findViewById(R.id.previous);
        CurrentDate=view.findViewById(R.id.CurrentDate);
        gridView=view.findViewById(R.id.gridview);
        calendar.add(Calendar.MONTH,1);
        calendar.add(Calendar.MONTH,-1);


    }
    private void SetUpCalendar(){
        String currentDate=dateFormat.format(calendar.getTime());
        CurrentDate.setText(currentDate);
        dates.clear();
        Calendar monthCalendar=(Calendar) calendar.clone();
        monthCalendar.set(Calendar.DAY_OF_MONTH,1);
        int FirstDayMonth=monthCalendar.get(Calendar.DAY_OF_WEEK)-1;
        monthCalendar.add(Calendar.DAY_OF_MONTH,-FirstDayMonth);
        EventListHelperM(monthFormat.format(calendar.getTime()),yearFormat.format(calendar.getTime()));
        while(dates.size()<42){
            dates.add(monthCalendar.getTime());
            monthCalendar.add(Calendar.DAY_OF_MONTH,1);
        }
        calendarAdapter=new CalendatAdapter(context,dates,calendar,eventList);
        gridView.setAdapter(calendarAdapter);
    }

    public void EventListHelperM(String month, String year){
        helper =new DBOpenHelper(context);
        SQLiteDatabase db= helper.getReadableDatabase();
        Cursor cursor=helper.ReadEventsperMonth(month,year,db);
        eventList.clear();
        while(cursor.moveToNext()){
            @SuppressLint("Range") String next=cursor.getString(cursor.getColumnIndex(DBStructure.EVENT));
            @SuppressLint("Range") String next2=cursor.getString(cursor.getColumnIndex(DBStructure.TIME));
            @SuppressLint("Range") String next3=cursor.getString(cursor.getColumnIndex(DBStructure.DATE));
            @SuppressLint("Range") String next4=cursor.getString(cursor.getColumnIndex(DBStructure.MONTH));
            @SuppressLint("Range") String next5=cursor.getString(cursor.getColumnIndex(DBStructure.YEAR));
            Events events=new Events(next,next2,next3,next4,next5);
            eventList.add(events);
        }helper.close();
        cursor.close();
    }
    public ArrayList<Events> EventListHelper(String date){
        ArrayList<Events> helperA=new ArrayList<>();
        helper=new DBOpenHelper(context);
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor=helper.ReadEvents(date,db);
        while (cursor.moveToNext()){
            @SuppressLint("Range") String next=cursor.getString(cursor.getColumnIndex(DBStructure.EVENT));
            @SuppressLint("Range") String next2=cursor.getString(cursor.getColumnIndex(DBStructure.TIME));
            @SuppressLint("Range") String next3=cursor.getString(cursor.getColumnIndex(DBStructure.DATE));
            @SuppressLint("Range") String next4=cursor.getString(cursor.getColumnIndex(DBStructure.MONTH));
            @SuppressLint("Range") String next5=cursor.getString(cursor.getColumnIndex(DBStructure.YEAR));
            Events events=new Events(next,next2,next3,next4,next5);
            helperA.add(events);
        }
        helper.close();
        cursor.close();
        return helperA;
    }
}
