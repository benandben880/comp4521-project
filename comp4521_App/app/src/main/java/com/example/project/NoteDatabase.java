package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

//public class NoteDatabase extends SQLiteOpenHelper {
//
//    private static final int DATABASE_VERSION = 2;
//    private static final String DATABASE_NAME = "notedb";
//    private static final String DATABASE_TABLE = "notestable";
//
//    public NoteDatabase(Context context){
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//
//    }
//
//    //columns name for database table
//    private static final String KEY_ID = "id";
//    private static final String KEY_TITLE = "title";
//    private static final String KEY_CONTENT = "content";
//    private static final String KEY_DATE = "date";
//    private static final String KEY_TIME = "time";
//
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        // create table nametame (id INT PRIMART KEY, title TEXT, content text, date TEXT, time TEXT);
//        String query ="CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ID + " INT PRIMARY KEY," +
//                KEY_TITLE + " TEXT," +
//                KEY_CONTENT + " TEXT," +
//                KEY_DATE + " TEXT," +
//                KEY_TIME + " TEXT"+")";
//
//        db.execSQL(query);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        if(oldVersion >= newVersion)
//            return;
//        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
//        onCreate(db);
//    }
//
//    //handle add sth to database
//    public long addNote(Note note){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues c = new ContentValues();
//        c.put(KEY_TITLE, note.getTitle());
//        c.put(KEY_CONTENT, note.getContent());
//        c.put(KEY_DATE, note.getDate());
//        c.put(KEY_TIME, note.getTime());
//
//        long ID = db.insert(DATABASE_TABLE, null, c); // ID is primary key
//        Log.d("Inseted", "ID -> " + ID);
//        return ID;
//    }
//
//    public Note getNote(long id){
//        // select * from databaseTable where id=1
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query(DATABASE_TABLE, new String[]{KEY_ID, KEY_TITLE, KEY_CONTENT, KEY_DATE, KEY_TIME}, KEY_ID+"=?",
//                new String[]{String.valueOf(id)}, null, null, null,null); //cursor is pointer that point to our current data in the table
//
//        if(cursor != null)
//            cursor.moveToFirst();
//
//        Note note = new Note(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
//        //Note note = new Note(Long.parseLong(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
//        return note;
//    } //return one note
//
//    public List<Note> getNotes(){ //no need parameter as we are not selecting based on any criteria
//        SQLiteDatabase db = this.getReadableDatabase();
//        List<Note> allNotes = new ArrayList<>();
//        //select * from databaseName
//
//        String query = "SELECT * FROM " + DATABASE_TABLE +" ORDER BY "+KEY_ID+" DESC";
//        Cursor cursor = db.rawQuery(query, null);
//        if(cursor.moveToFirst()){ //if there are some data in our cursor. btw cursor is starting from -1 --> use moveToFirst to ensure it is the first
//            do{
//                Note note = new Note();
//                note.setID(cursor.getLong(0));
//                //note.setID(Long.parseLong(cursor.getString(0)));
//                note.setTitle(cursor.getString(1));
//                note.setContent(cursor.getString(2));
//                note.setDate(cursor.getString(3));
//                note.setTime(cursor.getString(4));
//
//                allNotes.add(note);
//            }while(cursor.moveToNext());
//        }
//        return allNotes;
//    } //return a list of notes
//}
public class NoteDatabase extends SQLiteOpenHelper {
    // declare require values
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "SimpleDB";
    private static final String TABLE_NAME = "SimpleTable";

    public NoteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // declare table column names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";


    // creating tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createDb = "CREATE TABLE " + TABLE_NAME + " (" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_TITLE + " TEXT," +
                KEY_CONTENT + " TEXT," +
                KEY_DATE + " TEXT," +
                KEY_TIME + " TEXT"
                + " )";
        db.execSQL(createDb);
    }

    // upgrade db if older version exists
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion >= newVersion)
            return;

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long addNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(KEY_TITLE, note.getTitle());
        v.put(KEY_CONTENT, note.getContent());
        v.put(KEY_DATE, note.getDate());
        v.put(KEY_TIME, note.getTime());

        // inserting data into db
        long ID = db.insert(TABLE_NAME, null, v);
        return ID;
    }

    public Note getNote(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] query = new String[]{KEY_ID, KEY_TITLE, KEY_CONTENT, KEY_DATE, KEY_TIME};
        Cursor cursor = db.query(TABLE_NAME, query, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        return new Note(
                cursor.getLong(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4));
    }

    public List<Note> getAllNotes() {
        List<Note> allNotes = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + KEY_ID + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setID(cursor.getLong(0));
                note.setTitle(cursor.getString(1));
                note.setContent(cursor.getString(2));
                note.setDate(cursor.getString(3));
                note.setTime(cursor.getString(4));
                allNotes.add(note);
            } while (cursor.moveToNext());
        }

        return allNotes;

    }

    public int editNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        Log.d("Edited", "Edited Title: -> " + note.getTitle() + "\n ID -> " + note.getID());
        c.put(KEY_TITLE, note.getTitle());
        c.put(KEY_CONTENT, note.getContent());
        c.put(KEY_DATE, note.getDate());
        c.put(KEY_TIME, note.getTime());
        return db.update(TABLE_NAME, c, KEY_ID + "=?", new String[]{String.valueOf(note.getID())});
    }


    void deleteNote(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }


}