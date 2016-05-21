package com.vinkas.app;

import com.vinkas.util.Helper;

/**
 * Created by Vinoth on 19-5-16.
 */
public class Fragment<H extends Helper> extends android.support.v4.app.Fragment {

    private H helper;
    public H getHelper() {
        if(helper == null)
            helper = (H) H.getInstance();
        return helper;
    }

}