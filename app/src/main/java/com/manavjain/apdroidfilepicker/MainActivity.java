package com.manavjain.apdroidfilepicker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.manavjain.apdroidfilepickerlibrary.ApdroidFilePicker;

import static com.manavjain.apdroidfilepickerlibrary.utils.FilePickerUtils.RESULT_PATH_KEY;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openFilePicker(View view){
        new ApdroidFilePicker()
                .withActivity(this)
                .withRequestCode(121)
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 121 && resultCode == RESULT_OK){
            Toast.makeText(this, data.getStringExtra(RESULT_PATH_KEY), Toast.LENGTH_SHORT).show();
        }
    }
}
