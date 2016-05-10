package com.vinkas.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.common.SignInButton;

import com.vinkas.library.R;
import com.vinkas.util.Helper;

public class ConnectActivity extends Activity {

    public final static int REQUEST_CODE_GOOGLE_CONNECT = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);

        SignInButton googleSignIn = (SignInButton) findViewById(R.id.googleSignIn);
        googleSignIn.setSize(SignInButton.SIZE_WIDE);
        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Helper.isNetworkAvailable(getApplicationContext())) {
                    Intent i = new Intent(ConnectActivity.this, GoogleConnectActivity.class);
                    startActivityForResult(i, REQUEST_CODE_GOOGLE_CONNECT);
                } else {
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(ConnectActivity.this);
                    dlgAlert.setMessage("Unable to connect to internet. Please try again later.");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(false);
                    dlgAlert.create().show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GOOGLE_CONNECT)
            sendResult(resultCode);
    }

}
