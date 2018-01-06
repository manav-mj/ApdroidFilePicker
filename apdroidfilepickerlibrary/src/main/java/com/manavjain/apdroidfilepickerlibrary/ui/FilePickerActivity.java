package com.manavjain.apdroidfilepickerlibrary.ui;

import android.Manifest;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.manavjain.apdroidfilepickerlibrary.R;

import java.io.File;
import java.util.ArrayList;

import static com.manavjain.apdroidfilepickerlibrary.utils.FilePickerUtils.RESULT_PATH_KEY;

public class FilePickerActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_READ_STORAGE = 111;
    FragmentManager manager;

    ArrayList<String> visitedPaths = new ArrayList<>();

    // Handle for long click
    ArrayList<File> clickedFiles = new ArrayList<>();
    private int mMaxSelection;

    private String startingPath = Environment.getExternalStorageDirectory().getAbsolutePath();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_picker);
        checkPermissions();
        manager = getFragmentManager();
        replaceFragment(startingPath);
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSIONS_REQUEST_READ_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    replaceFragment(startingPath);
                } else {
                    setResultAndFinish(RESULT_CANCELED, null);
                }

            }
        }
    }

    private void replaceFragment(String path) {

        FileFragment firstFragment = FileFragment.createInstance(path, file -> {
            if (file.isDirectory()) {
                String newPath = file.getAbsolutePath();
                visitedPaths.add(newPath.substring(0, newPath.lastIndexOf('/')));
                replaceFragment(file.getAbsolutePath());
            } else setResultAndFinish(RESULT_OK, file.getAbsolutePath());
        });

        manager.beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(R.id.fragment_frame_layout, firstFragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (visitedPaths.size() > 0) {
            replaceFragment(visitedPaths.get(visitedPaths.size() - 1));
            visitedPaths.remove(visitedPaths.size() - 1);
        } else super.onBackPressed();
    }

    public void setResultAndFinish(int result, String path) {
        Intent intent = new Intent();
        intent.putExtra(RESULT_PATH_KEY, path);
        setResult(RESULT_OK, intent);
        finish();
    }
}
