package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;

public class AddNote extends AppCompatActivity {
    Toolbar toolbar;
    EditText noteTitle, noteDetails;
    Calendar c; //get the current time and date, then send to database
    String todaysDate;
    String currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        toolbar = findViewById(R.id.toolbar);
        //set UI toolbar Text color to white
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        //setSupportActionBar(toolbar);
        //set UI title name to "New Note"
        getSupportActionBar().setTitle("New Note");
        //go to AndroidManifext.xml to set which page is its parent Activity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        noteTitle = findViewById(R.id.noteTitle);
        noteDetails = findViewById(R.id.noteDetails);

        //check if the text is changed
        noteTitle.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0){
                    getSupportActionBar().setTitle(s); //set the Note title to UI title
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        //get current date and time
        c = Calendar.getInstance();
        todaysDate = c.get(Calendar.YEAR)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DAY_OF_MONTH); // YY/MM/DD
        currentTime = pad(c.get(Calendar.HOUR))+":"+pad(c.get(Calendar.MINUTE));

        //debug
        Log.d("calendar", "Date and Time: " + todaysDate + " and " + currentTime);
    }

    private String pad(int i){
        if(i<10){
            return "0"+i;
        }
        return String.valueOf(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.delete){
            //click the add btn and a gray text pop up in the bottom of UI
            //Toast.makeText(this, "Delete btn is Clicked", Toast.LENGTH_SHORT).show();

            onBackPressed();

        }

        if(item.getItemId() == R.id.save){
            //click the add btn and a gray text pop up in the bottom of UI
            //Toast.makeText(this, "Save btn is Clicked", Toast.LENGTH_SHORT).show();

            if(noteTitle.getText().length() != 0){
                Note note = new Note(noteTitle.getText().toString(), noteDetails.getText().toString(), todaysDate, currentTime); //create without ID --> after inserting, it will return an ID
                //insert into database
                NoteDatabase db = new NoteDatabase(this);
                db.addNote(note);

                //onBackPressed();
                goToMain(); //call the Activity instead of just goBack --> show the new added note
            }
            else{
                noteTitle.setError("Title can not be blank.");
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void goToMain() {
        Intent i = new Intent(this, MemoActivity.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}