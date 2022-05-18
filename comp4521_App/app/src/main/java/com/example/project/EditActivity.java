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

import java.util.Calendar;

public class EditActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText noteTitle, noteDetails;
    Calendar c; //get the current time and date, then send to database
    String todaysDate;
    String currentTime;
    NoteDatabase db;
    Note note;
    long nId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent i = getIntent();
        nId = i.getLongExtra("IDName", 0);
        db = new NoteDatabase(this);
        note = db.getNote(nId);

        final String title = note.getTitle();
        String content = note.getContent();
        noteDetails = findViewById(R.id.noteDetails);
        noteTitle = findViewById(R.id.noteTitle);

        toolbar = findViewById(R.id.toolbar);
        //set UI toolbar Text color to white
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        //setSupportActionBar(toolbar);
        //go to AndroidManifext.xml to set which page is its parent Activity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        //set UI title name to the specific note
        getSupportActionBar().setTitle(note.getTitle());




        //check if the text is changed
        noteTitle.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                getSupportActionBar().setTitle(title);
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

        noteTitle.setText(title);
        noteDetails.setText(content);

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
//                note.setTitle(noteTitle.getText().toString());
//                note.setContent(noteDetails.getText().toString());
//                int id = db.editNote(note);
//                if(id == note.getID()){
//                    Toast.makeText(this,"Note Updated", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    Toast.makeText(this, "Error Updating Note.", Toast.LENGTH_SHORT).show();
//                }
//
//                Intent i = new Intent(getApplicationContext(), DetailsActivity.class);
//                i.putExtra("ID", note.getID());
//                startActivity(i);
                Note note = new Note(nId,noteTitle.getText().toString(),noteDetails.getText().toString(),todaysDate,currentTime);
                Log.d("EDITED", "edited: before saving id -> " + note.getID());
                NoteDatabase sDB = new NoteDatabase(getApplicationContext());
                long id = sDB.editNote(note);
                Log.d("EDITED", "EDIT: id " + id);
                goToMain();
                Toast.makeText(this, "Note Edited.", Toast.LENGTH_SHORT).show();

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