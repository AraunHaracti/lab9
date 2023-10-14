package com.example.lab9;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.lab9.databinding.FragmentNotepadBinding;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class NotepadFragment extends Fragment {

    Activity mainActivity;
    FragmentNotepadBinding binding;

    private String fileName;

    SharedPreferences setting;
    SharedPreferences.Editor editSetting;

    public NotepadFragment(Activity activity, String fileName) {
        mainActivity = activity;
        this.fileName = fileName;
        setting = activity.getPreferences(Context.MODE_PRIVATE);
        editSetting = setting.edit();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNotepadBinding.inflate(inflater, container, false);

        FileInputStream fin = null;
        EditText textView = binding.notepadField;
        try {
            fin = mainActivity.getBaseContext().openFileInput(fileName);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String (bytes);
            textView.setText(text);
        }
        catch(IOException ex) {
            Log.e("IOException", ex.getMessage().toString());
        }
        finally{

            try{
                if(fin!=null)
                    fin.close();
            }
            catch(IOException ex){
                Log.e("IOException", ex.getMessage().toString());
            }
        }

        binding.nameFile.setText(fileName);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.notepadField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                FileOutputStream fos = null;
                try {

                    String text = binding.notepadField.getText().toString();

                    fos = mainActivity.getBaseContext().openFileOutput(fileName, Context.MODE_PRIVATE);

                    fos.write(text.getBytes());

                    editSetting.putString("fileName", fileName);
                    editSetting.apply();

                } catch (IOException e) {
                    Log.e("IOException", e.getMessage().toString());
                    throw new RuntimeException(e);
                } finally{
                    try{
                        if(fos!=null)
                            fos.close();
                    }
                    catch(IOException ex){
                        Log.e("IOException", ex.getMessage().toString());
                    }
                }
            }
        });

        binding.addFile.setOnClickListener((v) -> {
            AddFileFragment listFilesFragment = new AddFileFragment(mainActivity);
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.main_fragment, listFilesFragment, "replace_notepad_to_add_file")
                    .addToBackStack("addFile")
                    .commit();
        });

        binding.openFile.setOnClickListener((v) -> {
            ListFilesFragment listFilesFragment = new ListFilesFragment(mainActivity);
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.main_fragment, listFilesFragment, "replace_notepad_to_list_files")
                    .addToBackStack("openFiles")
                    .commit();
        });
    }
}