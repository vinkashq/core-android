package com.vinkas.auth;

/**
 * Created by Vinoth on 19-5-16.
 */
public class AnonymousActivity extends Activity {

    @Override
    public String getProviderId() {
        return "anonymous";
    }

    @Override
    protected void signIn() {
        authFirebase();
    }

    public void authFirebase() {
        mAuth.signInAnonymously().addOnCompleteListener(this, this);
    }
}
