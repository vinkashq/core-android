package com.vinkas.activity;

import android.support.v7.app.AppCompatActivity;

import com.vinkas.library.Application;
import com.vinkas.util.Helper;

/**
 * Created by Vinoth on 3-5-16.
 */
public class Activity extends AppCompatActivity {

    public Application getApp() {
        return (Application) getApplication();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Helper.RESULT_CANCEL);
    }

    public void sendResult(int resultCode) {
        setResult(resultCode);
        finish();
    }

}
