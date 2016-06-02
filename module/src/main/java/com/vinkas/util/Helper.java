package com.vinkas.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.vinkas.app.Application;
import com.vinkas.app.R;

import java.util.Calendar;
import java.util.UUID;

/**
 * Created by Vinoth on 3-5-16.
 */
public class Helper {

    private boolean configReady = false;
    public Helper(Boolean firebase_persistence_enabled) {
        database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(firebase_persistence_enabled);
        config = FirebaseRemoteConfig.getInstance();
        config.fetch().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                config.activateFetched();
                configReady = true;
            }
        });
    }

    public boolean isConfigReady() {
        return configReady;
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
    private FirebaseRemoteConfig config;

    public FirebaseDatabase getDatabase() {
        return database;
    }

    public FirebaseRemoteConfig getConfig() {
        return config;
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