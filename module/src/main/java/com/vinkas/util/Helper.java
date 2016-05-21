package com.vinkas.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.FirebaseDatabase;
import com.vinkas.app.Application;
import com.vinkas.app.R;

import java.util.Calendar;
import java.util.UUID;

/**
 * Created by Vinoth on 3-5-16.
 */
public class Helper {

    public Helper() {
        database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(getApplication().getResources().getBoolean(R.bool.firebase_persistence_enabled));
    }

    public static Helper getInstance() {
        return getApplication().getHelper();
    }

    private boolean ready = false;
    public void onReady(String userId) {
        setReady(true);
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    protected static Application application;

    public static void setApplication(Application app) {
        application = app;
    }

    public static Application getApplication() {
        return application;
    }

    private FirebaseUser user;

    public FirebaseUser getUser() {
        return user;
    }

    public void setUser(FirebaseUser value) {
        user = value;
        onReady(user.getUid());
    }

    private FirebaseDatabase database;

    public FirebaseDatabase getDatabase() {
        return database;
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
        FirebaseCrash.report(exception);
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void onError(DatabaseError error) {
        onFirebaseException(error.toException());
    }

    public static void onFirebaseException(DatabaseException exception) {
        onException(exception);
    }

    public static String generateId() {
        return UUID.randomUUID().toString();
    }

}