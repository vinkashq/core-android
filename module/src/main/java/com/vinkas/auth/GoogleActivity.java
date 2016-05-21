package com.vinkas.auth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.vinkas.app.R;
import com.vinkas.util.Helper;

public class GoogleActivity extends Activity
        implements GoogleApiClient.OnConnectionFailedListener {

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                setAccount(result.getSignInAccount());
            } else {
                if (result.getStatus().getStatusMessage() != null)
                    Log.e("GoogleSignIn", result.getStatus().getStatusMessage());
                Toast.makeText(this, "Unable to sign in. Please try again.", Toast.LENGTH_LONG).show();
                sendResult(Helper.RESULT_ERROR);
            }
        }
    }

    private GoogleSignInAccount account;

    public GoogleSignInAccount getAccount() {
        return account;
    }

    public void setAccount(GoogleSignInAccount googleSignInAccount) {
        account = googleSignInAccount;
        AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        signInWithCredential(authCredential);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        sendResult(Helper.RESULT_ERROR);
    }

    GoogleApiClient mApiClient;

    protected void signIn() {
        if (signInPrepared == false)
            prepareSignIn();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    boolean signInPrepared = false;

    protected void prepareSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();

        mApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signInPrepared = true;
    }

    int RC_SIGN_IN = 1001;

    private static String TAG = "GoogleAuth";

}