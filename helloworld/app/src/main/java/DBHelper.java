import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    //input event data
    private static final String CREATE_SQL="CREATE TABLE "+Database.event_table+"(ID INTEGER PRIMARY KEY, "+
            Database.event+" TEXT, "+Database.time+" TEXT, "+Database.date+" TEXT, "+Database.month+" TEXT, "+Database.year+" TEXT)";

    private static final String DROP_SQL="DROP TABLE IF EXISTS "+Database.event_table;
    public DBHelper(@Nullable Context context) {
        super(context,Database.db_name,null,Database.version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    sqLiteDatabase.execSQL(CREATE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    sqLiteDatabase.execSQL(DROP_SQL);
    onCreate(sqLiteDatabase);
    }

    public void SAVE(String event,String time,String date,String month,String year,SQLiteDatabase sqLiteDatabase){
        ContentValues values=new ContentValues();
        values.put(Database.event,event);
        values.put(Database.time,time);
        values.put(Database.date,date);
        values.put(Database.month,month);
        values.put(Database.year,year);
        sqLiteDatabase.insert(Database.event_table,null,values);
    }
public Cursor READDATE(String date, SQLiteDatabase sqLiteDatabase){
        String [] projection ={
                Database.event,
                Database.time,
                Database.date,
                Database.month,
                Database.year};
        String Selection=Database.date+"=?";
        String [] SelectionArgs={date};

        return sqLiteDatabase.query(Database.event_table,projection,Selection,SelectionArgs,null,null,null);
        }
    public Cursor READMONTH(String month,String year, SQLiteDatabase sqLiteDatabase){
        String [] projection ={
                Database.event,
                Database.time,
                Database.date,
                Database.month,
                Database.year};
        String Selection=Database.month+"=? and "+Database.year+"=?";
        String [] SelectionArgs={month,year};

        return sqLiteDatabase.query(Database.event_table,projection,Selection,SelectionArgs,null,null,null);
    }

}
