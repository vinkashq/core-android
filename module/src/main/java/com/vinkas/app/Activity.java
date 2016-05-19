package com.vinkas.app;

import android.support.v7.app.AppCompatActivity;

import com.vinkas.util.Helper;

/**
 * Created by Vinoth on 3-5-16.
 */
public class Activity extends AppCompatActivity {

    static Helper helper;
    static public Helper getHelper() {
        if(helper == null)
            helper = Helper.getInstance();
        return helper;
    }

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
