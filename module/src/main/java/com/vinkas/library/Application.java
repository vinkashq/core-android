package com.vinkas.library;

import com.firebase.client.Firebase;
import com.vinkas.util.Helper;


import io.vinkas.Accounts;

/**
 * Created by Vinoth on 3-5-16.
 */
public abstract class Application extends android.app.Application {

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private Firebase firebase;

    public Firebase getFirebase() {
        if (firebase == null)
            firebase = getAccounts().getRoot();
        return firebase;
    }

    private Accounts accounts;

    public Accounts getAccounts() {
        return accounts;
    }

    public void setAccounts(Accounts accounts) {
        this.accounts = accounts;
    }

    public Boolean isReady() {
        return getUserId() != null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Helper.setApplication(this);
        setFirebase();
    }

    public void setFirebase() {
        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(getResources().getBoolean(R.bool.firebase_persistence_enabled));
        setAccounts(new Accounts());
    }

}
