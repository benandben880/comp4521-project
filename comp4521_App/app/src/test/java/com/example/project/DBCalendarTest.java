package com.example.project;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import junit.framework.TestCase;
import androidx.test.core.app.ApplicationProvider;



import org.junit.Before;
import org.robolectric.RobolectricTestRunner;
@RunWith(RobolectricTestRunner.class)

public class DBCalendarTest extends TestCase {
    private DBOpenHelper helper;
    private SQLiteDatabase db;
    private Cursor cursor;
    @Before
    public void setUp() throws Exception{
        super.setUp();
        Context context= ApplicationProvider.getApplicationContext();
        helper=new DBOpenHelper(context);
        db=helper.getWritableDatabase();

    }
    @Test
    public void testSaveAndReadEvent() {

        helper.SaveEvent("test", "time", "date", "month", "year", "descript", "alarm", db);
        cursor = helper.ReadEvents("date", db);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String next = cursor.getString(cursor.getColumnIndex(DBStructure.EVENT));
            @SuppressLint("Range") String next2 = cursor.getString(cursor.getColumnIndex(DBStructure.TIME));
            @SuppressLint("Range") String next3 = cursor.getString(cursor.getColumnIndex(DBStructure.DATE));
            @SuppressLint("Range") String next4 = cursor.getString(cursor.getColumnIndex(DBStructure.MONTH));
            @SuppressLint("Range") String next5 = cursor.getString(cursor.getColumnIndex(DBStructure.YEAR));
            @SuppressLint("Range") String next6 = cursor.getString(cursor.getColumnIndex(DBStructure.DESCRIPTION));

            assertEquals("test",next);
            assertEquals( "time",next2);
            assertEquals( "date",next3);
            assertEquals( "month",next4);
            assertEquals( "year",next5);
            assertEquals( "descript",next6);

        }
    }

    @After
    public void close(){
        db.close();
        helper.close();
    }

}