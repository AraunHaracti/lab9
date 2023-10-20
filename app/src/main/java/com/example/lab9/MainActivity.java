package com.example.lab9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    SharedPreferences setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setting = getPreferences(MODE_PRIVATE);

        NotepadFragment notepadFragment =
                new NotepadFragment(this, setting.getString("fileName", "content"));

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_fragment, notepadFragment, "init_notepad_fragment").commit();
    }
}
