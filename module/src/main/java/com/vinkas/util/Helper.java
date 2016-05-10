package com.vinkas.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.firebase.client.FirebaseError;
import com.firebase.client.FirebaseException;
import com.vinkas.library.Application;

import java.util.Calendar;
import java.util.UUID;

import com.vinkas.library.R;

/**
 * Created by Vinoth on 3-5-16.
 */
public class Helper {

    private static Helper helper;
    public static Helper getInstance() {
        if(helper == null)
            helper = new Helper();
        return helper;
    }

    private static Application application;
    private static String rootUrl, userUrl;

    public static String getUserId() {
        return getApplication().getUserId();
    }

    public static void setUserId(String value) {
        userUrl = rootUrl + "/users/" + value;
        getApplication().setUserId(value);
    }

    public static void setApplication(Application app) {
        application = app;
        rootUrl = "https://" + getApplication().getString(R.string.firebase_hostname);
    }

    public static Application getApplication() {
        return application;
    }

    public Long toTimeStamp(int day, int month, int year, int hour, int min) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, min);
        return calendar.getTimeInMillis();
    }

    public static final int RESULT_OK = 1001;
    public static final int RESULT_CANCEL = 1002;
    public static final int RESULT_ERROR = 1003;

    public static void onException(Exception exception) {
        exception.printStackTrace();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void onError(FirebaseError error) {
        onFirebaseException(error.toException());
    }

    public static void onFirebaseException(FirebaseException firebaseException) {
        onException(firebaseException);
    }

    public static String generateId() {
        return UUID.randomUUID().toString();
    }

    public static String getRootUrl(String childPath) {
        return rootUrl + "/" + childPath;
    }

    public static String getUserUrl(String childPath) {
        return userUrl + "/" + childPath;
    }

}
