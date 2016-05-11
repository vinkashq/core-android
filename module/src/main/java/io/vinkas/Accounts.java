package io.vinkas;

import android.os.AsyncTask;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.client.core.Path;
import com.firebase.client.core.Repo;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.vinkas.util.Helper;

import java.io.IOException;

/**
 * Created by Vinoth on 10-5-16.
 */
public class Accounts extends List<Account> implements Firebase.AuthResultHandler, Listener<Account> {

    private Account account;

    public Account getAccount() {
        return account;
    }

    private void setAccount(Account account) {
        this.account = account;
        Helper.setUserId(account.getUserId());
    }

    @Override
    public void onCreate(Account item) {
        setAccount(item);
    }

    @Override
    public void onError(FirebaseError error) {
        Helper.onError(error);
    }

    @Override
    public void onRead(Account item) {
        setAccount(item);
    }

    @Override
    public void onRemove(Account item) {

    }

    @Override
    public void onAuthenticated(AuthData authData) {
        final String key = authData.getUid();
        final Firebase f = child(key);
        f.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Account account;
                if (dataSnapshot.exists()) {
                    account = dataSnapshot.getValue(Account.class);
                    account.setKey(dataSnapshot.getKey());
                    account.setPriority(dataSnapshot.getPriority());
                    onRead(account);
                } else if (getGoogleAccount() != null) {
                    account = new Account();
                    account.setKey(key);
                    account.set(getGoogleAccount());
                    account.setUserId(push().getKey());
                    account.writeTo(Accounts.this, Accounts.this);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                onError(firebaseError);
            }
        });
    }

    @Override
    public void onAuthenticationError(FirebaseError firebaseError) {
        Helper.onError(firebaseError);
    }

    public Accounts(Repo repo, Path path) {
        super(repo, path);
    }

    public static String getUrl() {
        return Helper.getRootUrl("accounts");
    }

    private GoogleSignInAccount googleAccount;

    public GoogleSignInAccount getGoogleAccount() {
        return googleAccount;
    }

    public void setGoogleAccount(GoogleSignInAccount googleAccount) {
        this.googleAccount = googleAccount;
        authWithGoogle();
    }

    public void authWithGoogle() {
        final android.accounts.Account account = new android.accounts.Account(getGoogleAccount().getEmail(), "com.google");
        final String scopes = "oauth2:profile email";
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String token = null;
                try {
                    token = GoogleAuthUtil.getToken(
                            Helper.getApplication(),
                            account,
                            scopes);
                } catch (IOException e) {
                    Helper.onException(e);
                } catch (GoogleAuthException e) {
                    Helper.onException(e);
                }
                return token;
            }

            @Override
            protected void onPostExecute(String token) {
                getRoot().authWithOAuthToken("google", token, Accounts.this);
            }
        };
        task.execute();
    }

    public Accounts() {
        super(getUrl());
    }
}
