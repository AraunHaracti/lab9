package com.example.lab9;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab9.databinding.FragmentAddFileBinding;

public class AddFileFragment extends Fragment {

    Activity mainActivity;
    FragmentAddFileBinding binding;

    public AddFileFragment(Activity activity) {
        mainActivity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddFileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.agreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameFile = binding.nameFile.getText().toString();
                if (!nameFile.matches("^[A-Za-z0-9]{1,}$")) return;
                getParentFragmentManager().beginTransaction().replace(R.id.main_fragment, new NotepadFragment(mainActivity, nameFile), "").commit();
            }
        });

        binding.disagreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
    }
}