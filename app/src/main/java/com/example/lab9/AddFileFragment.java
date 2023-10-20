package com.example.lab9;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab9.databinding.FragmentAddFileBinding;

public class AddFileFragment extends Fragment {
    Activity mainActivity;
    ActivityResultLauncher<Intent> launcher;
    FragmentAddFileBinding binding;

    String nameFile;

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

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
           if (result.getResultCode() == Activity.RESULT_OK) {
               Intent data = result.getData();
               if (data != null) {
                   Uri uri = data.getData();
                   if (uri != null){
                       nameFile = uri.getPath();
                   }
               }
           }
        });

        return binding.getRoot();
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"text/plain"});
        launcher.launch(intent);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.openCreatedFile.setOnClickListener(v -> {
            openFilePicker();

            NotepadFragment notepadFragment = new NotepadFragment(mainActivity, nameFile);

            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_fragment, notepadFragment, "open_file")
                    .commit();
        });

        binding.agreeBtn.setOnClickListener(v -> {
            nameFile = binding.nameFile.getText().toString();
            if (!nameFile.matches("^[A-Za-z0-9]{1,}$")) return;

            NotepadFragment notepadFragment = new NotepadFragment(mainActivity, nameFile);

            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_fragment, notepadFragment, "create_new_file")
                    .commit();
        });

        binding.disagreeBtn.setOnClickListener(v -> getFragmentManager().popBackStack());
    }
}