package com.example.lab9;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab9.databinding.FragmentAddFileBinding;

public class AddFileFragment extends Fragment {

    FragmentAddFileBinding binding;

    public AddFileFragment() {
        // Required empty public constructor
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
}