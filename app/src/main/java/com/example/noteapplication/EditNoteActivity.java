package com.example.noteapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class EditNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        final Intent intent = getIntent();
        final String noteName = intent.getStringExtra(NoteActivity.NOTE_NAME);
        try {
            FileInputStream fileInputStream = openFileInput(noteName + ".txt");
            BufferedReader reader = new BufferedReader( new InputStreamReader( fileInputStream ) );
            String line;
            String noteText = "";
            while ( (line = reader.readLine()) != null ) {
                if(noteText == "") {
                    noteText = noteText + line;
                }else{
                    noteText = noteText + "\n" + line;
                }
            }
            reader.close();
            final EditText editText = (EditText) findViewById(R.id.contentfield);
            editText.setText(noteText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        final EditText editText = (EditText) findViewById(R.id.contentfield);
        TextView textView = (TextView) findViewById(R.id.nameEdit);
        textView.setText(noteName);
        Button saveBtn = (Button) findViewById(R.id.savebutton);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filename = noteName + ".txt";
                if(!editText.getText().toString().trim().isEmpty()){
                    File dir = getFilesDir();
                    File file = new File(dir, filename);
                    file.delete();
                    try {
                        FileOutputStream fOut = openFileOutput(filename, Context.MODE_PRIVATE);
                        fOut.write(editText.getText().toString().trim().getBytes());
                        fOut.close();
                        TextView status_view = (TextView) findViewById(R.id.savebutton);
                        status_view.setText("SAVED!");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    editText.setError("Content can't be empty");
                }
            }
        });
        Button button = (Button) findViewById(R.id.Return);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent1);
            }
        });
    }
}
