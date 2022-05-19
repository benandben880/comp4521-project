package com.example.project;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
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
    SimpleDateFormat showMonth=new SimpleDateFormat("MMMM");
    SimpleDateFormat showYear=new SimpleDateFormat("yyyy");
    CalendatAdapter calendarAdapter;
    DBOpenHelper helper;
    AlertDialog alertDialog;
    SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");

    public static int alarmHour, alarmMin, alarmDay, alarmYear, alarmMonth;




    public CustomCalendarView(Context context) {
        super(context);
    }

    public CustomCalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        Initialize();
        SetUp();
        PreviousButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.MONTH,-1);
                SetUp();
            }
        });
        NextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.MONTH,1);
                SetUp();
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                AlertDialog.Builder builder= new AlertDialog.Builder(context);
                builder.setCancelable(true);
                View addView= LayoutInflater.from(adapterView.getContext()).inflate(R.layout.add_event,null);
                EditText event_name=addView.findViewById(R.id.event_title);
                TextView event_time=addView.findViewById(R.id.event_time);
                Button addevent= addView.findViewById(R.id.addevent);
                TextView descript=addView.findViewById(R.id.description);
                CheckBox alarm=addView.findViewById(R.id.alarm);
                Calendar calForAlarm=Calendar.getInstance();
                calForAlarm.setTime(dates.get(position));
                alarmDay=calForAlarm.get(Calendar.DAY_OF_MONTH);
                alarmMonth=calForAlarm.get(Calendar.MONTH);
                alarmYear=calForAlarm.get(Calendar.YEAR);
                event_time.setOnClickListener(new OnClickListener() {
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
                                event_time.setText(ETime);
                                alarmMin=c.get(Calendar.MINUTE);
                                alarmHour=c.get(Calendar.HOUR_OF_DAY);
                            }
                        },h,min,false);
                        timePickerDialog.show();
                    }
                });
                String selectedd=dateFormat.format(dates.get(position));
                String selectedm=showMonth.format(dates.get(position));
                String selectedy=showYear.format(dates.get(position));


                addevent.setOnClickListener(new OnClickListener() {
                    @SuppressLint("Range")
                    @Override
                    public void onClick(View view) {
                        Log.d("MyTag3","Alarm Check");

                        if(alarm.isChecked()){
                            Log.d("MyTag3","Alarm should On");
                            save(event_name.getText().toString(),event_time.getText().toString(),selectedd,selectedm,selectedy,descript.getText().toString(),"AlarmOn");
                            Calendar CA=Calendar.getInstance();
                            CA.set(alarmYear,alarmMonth,alarmDay,alarmHour,alarmMin);
                            DBOpenHelper helper=new DBOpenHelper(context);
                            SQLiteDatabase db=helper.getReadableDatabase();
                            Cursor cursor=helper.ReadID(event_name.getText().toString(),event_time.getText().toString(),selectedd,db);
                            int id=0;
                            while (cursor.moveToNext()){
                                id=cursor.getInt(cursor.getColumnIndex(DBStructure.ID));
                            }cursor.close();
                            helper.close();
                            setAlarm(CA,event_name.getText().toString(),event_time.getText().toString(),id);
                        }else{
                            save(event_name.getText().toString(),event_time.getText().toString(),selectedd,selectedm,selectedy,descript.getText().toString(),"AlarmOff");

                        }
                        SetUp();

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
                String date = dateFormat.format(dates.get(i));
                AlertDialog.Builder ADB= new AlertDialog.Builder(context);
                ADB.setCancelable(true);
                View show=LayoutInflater.from(adapterView.getContext()).inflate(R.layout.event_list,null);
                RecyclerView tempView=show.findViewById(R.id.list);
                RecyclerView.LayoutManager manager=new LinearLayoutManager(show.getContext());
                tempView.setLayoutManager(manager);
                tempView.setHasFixedSize(true);
                recyclerview viewhelp=new recyclerview(show.getContext(),  EventListHelper(date));
                tempView.setAdapter(viewhelp);
                viewhelp.notifyDataSetChanged();
                ADB.setView(show);
                alertDialog= ADB.create();
                alertDialog.show();
                return true;
            }
        });
    }

    public void save(String eventName,String time,String date,String month,String year,String descript,String alarm){

        helper=new DBOpenHelper(context);
        SQLiteDatabase db=helper.getWritableDatabase();

        helper.SaveEvent(eventName,time,date,month,year,descript,alarm,db);
        helper.close();
    }

    public CustomCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void Initialize(){
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view =inflater.inflate(R.layout.calendar_layout,this);
        PreviousButton=view.findViewById(R.id.previous);
        CurrentDate=view.findViewById(R.id.CurrentDate);
        NextButton=view.findViewById(R.id.next);
        gridView=view.findViewById(R.id.gridview);
        calendar.add(Calendar.MONTH,1);
        calendar.add(Calendar.MONTH,-1);

    }
    public void SetUp(){
        SimpleDateFormat curMon=new SimpleDateFormat("yyyy-MM");
        String currentDate=curMon.format(calendar.getTime());
        CurrentDate.setText(currentDate);
        dates.clear();
        Calendar monthCalendar=(Calendar) calendar.clone();
        monthCalendar.set(Calendar.DAY_OF_MONTH,1);
        int FirstDayMonth=monthCalendar.get(Calendar.DAY_OF_WEEK);
        FirstDayMonth-=1;
        monthCalendar.add(Calendar.DAY_OF_MONTH,-FirstDayMonth);
        //recovered
        EventListHelperM(showMonth.format(calendar.getTime()),showYear.format(calendar.getTime()));
        Log.d("Month_and_Year", "get month year is: " + showMonth.format(calendar.getTime()) + " " + showYear.format(calendar.getTime()));
        while(dates.size()<42){
            dates.add(monthCalendar.getTime());
            monthCalendar.add(Calendar.DAY_OF_MONTH,1);
        }
        calendarAdapter=new CalendatAdapter(context,dates,calendar,eventList);
        gridView.setAdapter(calendarAdapter);
    }

    //recovered
    public void EventListHelperM(String month, String year){
        helper =new DBOpenHelper(context);
        SQLiteDatabase db= helper.getReadableDatabase();
        Cursor cursor=helper.ReadEventsperMonth(month,year,db);
        Log.d("eventSize", " yoo event size is: " + eventList.size());
        eventList.clear();
        //int count = 0;
        while(cursor.moveToNext()){
            Log.d("whileLoop", "in the while loop");
            @SuppressLint("Range") String next=cursor.getString(cursor.getColumnIndex(DBStructure.EVENT));
            @SuppressLint("Range") String next2=cursor.getString(cursor.getColumnIndex(DBStructure.TIME));
            @SuppressLint("Range") String next3=cursor.getString(cursor.getColumnIndex(DBStructure.DATE));
            @SuppressLint("Range") String next4=cursor.getString(cursor.getColumnIndex(DBStructure.MONTH));
            @SuppressLint("Range") String next5=cursor.getString(cursor.getColumnIndex(DBStructure.YEAR));
            @SuppressLint("Range") String next6=cursor.getString(cursor.getColumnIndex(DBStructure.DESCRIPTION));

            Events events=new Events(next,next2,next3,next4,next5,next6);
            eventList.add(events);
            Log.d("eventSize", " uoo event size is: " + eventList.size());
            //count = count +1;
        }
        helper.close();
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
            @SuppressLint("Range") String next6=cursor.getString(cursor.getColumnIndex(DBStructure.DESCRIPTION));

            Events events=new Events(next,next2,next3,next4,next5,next6);
            helperA.add(events);
        }
        helper.close();
        cursor.close();
        return helperA;
    }
    public void setAlarm(Calendar c, String event,String time,int Code){
        Intent intent=new Intent(context.getApplicationContext(),AlarmReciver.class);
        intent.putExtra("event",event);
        intent.putExtra("time",time);
        intent.putExtra("id",Code);
        PendingIntent pi=PendingIntent.getBroadcast(context,Code,intent,PendingIntent.FLAG_ONE_SHOT);
        AlarmManager manager=(AlarmManager) context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        manager.set(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pi);
    }
}
