package com.manavjain.apdroidfilepickerlibrary.ui;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.manavjain.apdroidfilepickerlibrary.R;

import java.io.File;
import java.util.ArrayList;

import static com.manavjain.apdroidfilepickerlibrary.utils.FilePickerUtils.RESULT_PATH_KEY;

public class FilePickerActivity extends AppCompatActivity {

    FragmentManager manager;

    ArrayList<String> visitedPaths = new ArrayList<>();

    // Handle for long click
    ArrayList<File> clickedFiles = new ArrayList<>();
    private int mMaxSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_picker);

        manager = getFragmentManager();
        replaceFragment(Environment.getExternalStorageDirectory().getAbsolutePath());
    }

    private void replaceFragment(String path) {

        FileFragment firstFragment = FileFragment.createInstance(path, file -> {
            if (file.isDirectory()) {
                String newPath = file.getAbsolutePath();
                visitedPaths.add(newPath.substring(0, newPath.lastIndexOf('/')));
                replaceFragment(file.getAbsolutePath());
            } else setResultAndFinish(file.getAbsolutePath());
        });

        manager.beginTransaction()
                .replace(R.id.fragment_frame_layout, firstFragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (visitedPaths.size()>0) {
            replaceFragment(visitedPaths.get(visitedPaths.size() - 1));
            visitedPaths.remove(visitedPaths.size() - 1);
        }else super.onBackPressed();
    }

    public void setResultAndFinish(String path) {
        Intent intent = new Intent();
        intent.putExtra(RESULT_PATH_KEY, path);
        setResult(RESULT_OK, intent);
        finish();
    }
}
