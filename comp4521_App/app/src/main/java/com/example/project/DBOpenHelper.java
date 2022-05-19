package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBOpenHelper extends SQLiteOpenHelper {
    public final static String CREATE_EVENTS_TABLE="create table "+DBStructure.EVENT_TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT, "+DBStructure.EVENT+" TEXT, "+DBStructure.TIME+" TEXT, "+DBStructure.DATE+" TEXT, "+DBStructure.MONTH+" TEXT, "+DBStructure.YEAR+" TEXT, "+DBStructure.DESCRIPTION+" TEXT, "+DBStructure.ALARM+" TEXT) ";
    public static final String DROP_EVENT_TABLE="DROP TABLE IF EXISTS "+DBStructure.EVENT_TABLE_NAME;
    public DBOpenHelper(@Nullable Context context) {
        super(context,DBStructure.DB_NAME,null, DBStructure.DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_EVENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(DROP_EVENT_TABLE);
        onCreate(db);
    }
    public void DeleteEvent(String event,String date,String time,String des,SQLiteDatabase db){
        String Selection=DBStructure.EVENT+"=? and "+DBStructure.DATE+"=? and "+DBStructure.TIME+"=? and "+DBStructure.DESCRIPTION+"=?";
        String [] SelectionArgs={event,date,time,des};
        db.delete(DBStructure.EVENT_TABLE_NAME,Selection,SelectionArgs);
    }


    public void SaveEvent(String event,String time,String date,String month,String year,String description,String alarm,SQLiteDatabase db){
        ContentValues values=new ContentValues();
        values.put(DBStructure.EVENT,event);
        values.put(DBStructure.TIME,time);
        values.put(DBStructure.DATE,date);
        values.put(DBStructure.MONTH,month);
        values.put(DBStructure.YEAR,year);
        values.put(DBStructure.DESCRIPTION,description);
        values.put(DBStructure.ALARM,alarm);
        Log.d("Tag",description);
        db.insert(DBStructure.EVENT_TABLE_NAME,null,values);
    }
    public Cursor ReadEvents(String date,SQLiteDatabase db){
        String [] Projections={DBStructure.EVENT,DBStructure.TIME,DBStructure.DATE,DBStructure.MONTH,DBStructure.YEAR,DBStructure.DESCRIPTION};
        String Selection=DBStructure.DATE+"=?";
        String [] SelectionArgs={date};

        return db.query(DBStructure.EVENT_TABLE_NAME,Projections,Selection,SelectionArgs,null,null,null);
    }
    public Cursor ReadID(String event,String time,String date,SQLiteDatabase db){
        String [] Projections={DBStructure.ID,DBStructure.ALARM};
        String Selection=DBStructure.DATE+"=? and "+DBStructure.EVENT+"=? and "+DBStructure.TIME+"=?";
        String [] SelectionArgs={event,time,date};

        return db.query(DBStructure.EVENT_TABLE_NAME,Projections,Selection,SelectionArgs,null,null,null);
    }

    //recovered
    public Cursor ReadEventsperMonth(String month,String year,SQLiteDatabase db) {
        String[] Projections = {DBStructure.EVENT, DBStructure.TIME, DBStructure.DATE, DBStructure.MONTH, DBStructure.YEAR,DBStructure.DESCRIPTION};
        String Selection = DBStructure.MONTH + "=? and " + DBStructure.YEAR + "=?";
        String[] SelectionArgs = {month, year};

        return db.query(DBStructure.EVENT_TABLE_NAME, Projections, Selection, SelectionArgs, null, null, null);
    }
}
