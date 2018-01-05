package com.manavjain.apdroidfilepickerlibrary.ui;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.manavjain.apdroidfilepickerlibrary.R;

import java.io.File;
import java.util.ArrayList;

import static com.manavjain.apdroidfilepickerlibrary.utils.FilePickerUtils.RESULT_PATH_KEY;

public class FilePickerActivity extends AppCompatActivity {

    FragmentManager manager;

    ArrayList<File> clickedFiles = new ArrayList<>();
    private int mMaxSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_picker);

        manager = getFragmentManager();
        replaceFragment(null);
    }

    private void replaceFragment(String absolutePath) {

        FileFragment firstFragment = FileFragment.createInstance(absolutePath, file -> {
            if (file.isDirectory()){
                replaceFragment(file.getAbsolutePath());
            }
            // Long Click
//            if (mMaxSelection == 1) setResultAndFinish(file.toURI().getPath());
//            if (clickedFiles.size() < mMaxSelection) clickedFiles.add(file);
//            else Toast.makeText(this, mMaxSelection + " files already selected", Toast.LENGTH_SHORT).show();
        });

        manager.beginTransaction()
                .replace(R.id.fragment_frame_layout, firstFragment)
                .commit();
    }

    public void setResultAndFinish(String path) {
        Intent intent = new Intent();
        intent.putExtra(RESULT_PATH_KEY, path);
        setResult(RESULT_OK, intent);
        finish();
    }
}
