package com.vinkas.app;

import com.vinkas.util.Helper;

/**
 * Created by Vinoth on 19-5-16.
 */
public class Fragment extends android.support.v4.app.Fragment {

    private Helper helper;
    public Helper getHelper() {
        if(helper == null)
            helper = Helper.getInstance();
        return helper;
    }

}
