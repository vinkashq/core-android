package com.vinkas.app;

import com.vinkas.util.Helper;

/**
 * Created by Vinoth on 3-5-16.
 */
public abstract class Application extends android.app.Application {

    public Boolean isReady() {
        return getHelper().isReady();
    }

    private Helper helper;

    public Helper getHelper() {
        return helper;
    }

    protected void setHelper(Helper value) {
        helper = value;
    }

    public abstract void setHelper();

    @Override
    public void onCreate() {
        super.onCreate();
        setHelper();
    }

}
