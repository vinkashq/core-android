package com.vinkas.auth;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.vinkas.util.Helper;
import com.vinkas.app.R;

/**
 * Created by Vinoth on 19-5-16.
 */
public abstract class Activity extends com.vinkas.app.Activity implements OnCompleteListener<AuthResult>, FirebaseAuth.AuthStateListener {

    public abstract String getProviderId();

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
            if (user.getProviderId().equals(getProviderId())) {
                getHelper().setUser(user);
                sendResult(Helper.RESULT_OK);
            }
        } else {
            Log.d(TAG, "onAuthStateChanged:signed_out");
        }
    }

    protected void signInWithCredential(AuthCredential authCredential) {
        FirebaseUser user = getHelper().getUser();
        if (user == null) {
            mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, this);
        } else {
            user.linkWithCredential(authCredential).addOnCompleteListener(this, this);
        }
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
        if (!task.isSuccessful()) {
            Toast.makeText(this, "Unable to sign in. Please try again.", Toast.LENGTH_LONG).show();
            if (task.getException() != null) {
                Log.e(TAG, task.getException().getMessage());
                Helper.onException(task.getException());
            }
            sendResult(Helper.RESULT_ERROR);
        }
    }

    protected FirebaseAuth mAuth;
    private static String TAG = "Auth Activity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar);
        mAuth = FirebaseAuth.getInstance();
        if (savedInstanceState == null)
            signIn();
    }

    protected abstract void signIn();

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(this);
    }
}
