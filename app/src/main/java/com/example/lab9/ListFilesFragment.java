package com.example.lab9;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.lab9.databinding.FragmentListFilesBinding;

public class ListFilesFragment extends Fragment {
    Activity mainActivity;
    FragmentListFilesBinding binding;

    public ListFilesFragment(Activity activity) {
        mainActivity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListFilesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] listFiles = mainActivity.getBaseContext().fileList();
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, listFiles);
        binding.listFiles.setAdapter(adapter);

        binding.listFiles.setOnItemClickListener((parent, view1, position, id) -> {
            NotepadFragment notepadFragment = new NotepadFragment(mainActivity, listFiles[position]);
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_fragment, notepadFragment, "")
                    .commit();
        });
    }
}