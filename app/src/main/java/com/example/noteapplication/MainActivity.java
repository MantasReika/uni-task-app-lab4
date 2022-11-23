package com.example.noteapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    public static final String EXTRA_MESSAGE = "com.example.notes.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File files = getFilesDir();
        String[] array = files.list();
        ArrayList<String> arrayList = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        for (String filename : array) {
            filename = filename.replace(".txt", "");
            adapter.add(filename);
        }
        final ListView noteListView = (ListView) findViewById(R.id.listView);
        noteListView.setAdapter(adapter);
        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = noteListView.getItemAtPosition(position).toString();
                Intent intent = new Intent(getApplicationContext(), NoteActivity.class);
                intent.putExtra(EXTRA_MESSAGE, item);
                startActivity(intent);
            }
        });

        Button button = (Button) findViewById(R.id.AddNoteButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextHeading = (EditText) findViewById(R.id.NoteName);
                EditText editTextContent = (EditText) findViewById(R.id.NoteContent);
                String noteName = editTextHeading.getText().toString().trim();
                String noteContent = editTextContent.getText().toString().trim();
                if (!noteName.isEmpty()) {
                    if(!noteContent.isEmpty()) {
                        try {
                            FileOutputStream fileOutputStream = openFileOutput(noteName + ".txt", Context.MODE_PRIVATE);
                            fileOutputStream.write(noteContent.getBytes());
                            fileOutputStream.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        adapter.add(noteName);
                        noteListView.setAdapter(adapter);
                    }else {
                        editTextContent.setError("Note content can not be empty!");
                    }
                }else{
                    editTextHeading.setError("Note name can not be empty!");
                }
                editTextContent.setText("");
                editTextHeading.setText("");
            }
        });



    }
}
