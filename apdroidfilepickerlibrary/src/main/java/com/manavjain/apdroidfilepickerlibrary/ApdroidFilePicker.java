package com.manavjain.apdroidfilepickerlibrary;

import android.app.Activity;
import android.content.Intent;

import com.manavjain.apdroidfilepickerlibrary.ui.FilePickerActivity;
import com.manavjain.apdroidfilepickerlibrary.ui.FilePickerActivity;

/**
 * Created by YourFather on 01-01-2018.
 */

public class ApdroidFilePicker {

    private Activity mActivity;

    private Integer mMaxSelection = 1;
    private Integer mRequestCode;

    private Intent intent;

    public ApdroidFilePicker withActivity(Activity activity) {
        mActivity = activity;
        return this;
    }

    public ApdroidFilePicker withRequestCode(Integer requestCode) {
        mRequestCode = requestCode;
        return this;
    }

    public ApdroidFilePicker setMaxSelection(Integer maxSelection) {
        mMaxSelection = maxSelection;
        return this;
    }

    public void start() {
        Intent intent = getIntent();
        mActivity.startActivityForResult(intent, mRequestCode);
    }

    public Intent getIntent() {

        Activity activity = mActivity;

        Intent intent = new Intent(activity, FilePickerActivity.class);

        return intent;
    }
}
