package vinkas.io;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.io.IOException;

import vinkas.util.Helper;

/**
 * Created by Vinoth on 3-5-16.
 */
public class Database implements Firebase.AuthResultHandler {

    private static final String HTTPS = "https://";
    private Account account;
    private String accountId;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    private boolean persistenceEnabled;

    public boolean isPersistenceEnabled() {
        return persistenceEnabled;
    }

    public void setPersistenceEnabled(boolean persistenceEnabled) {
        this.persistenceEnabled = persistenceEnabled;
        Firebase.getDefaultConfig().setPersistenceEnabled(isPersistenceEnabled());
    }

    public Account getAccount() {
        return account;
    }

    protected void setAccount(Account account) {
        this.account = account;
        setAccountId(this.account.getId());
    }

    @Override
    public void onAuthenticated(AuthData authData) {
        if (authData != null) {
            if (getProvider() == null) {
                setAccount(new Account(this, authData));
            } else {
                switch (getProvider()) {
                    case "google":
                        setAccount(new Account(this, authData, getGoogleSignInAccount()));
                        break;
                }
            }
            setAuthenticated(true);
        } else {
            setAuthenticated(false);
        }
    }

    @Override
    public void onAuthenticationError(FirebaseError firebaseError) {
        Helper.onError(firebaseError);
        Log.e("FirebaseError", firebaseError.getMessage() + " " + firebaseError.getDetails());
    }

    private Firebase firebase;
    private String url;
    private String provider;
    private GoogleSignInAccount googleSignInAccount;

    public GoogleSignInAccount getGoogleSignInAccount() {
        return googleSignInAccount;
    }

    public void setGoogleSignInAccount(GoogleSignInAccount googleSignInAccount) {
        this.googleSignInAccount = googleSignInAccount;
        if (!isAuthenticated())
            authWithGoogle();
    }

    private void authWithGoogle() {
        final android.accounts.Account account = new android.accounts.Account(getGoogleSignInAccount().getEmail(), "com.google");
        final String scopes = "oauth2:profile email";
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String token = null;
                try {
                    token = GoogleAuthUtil.getToken(
                            getAndroidContext(),
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
                authWithOAuthToken("google", token);
            }
        };
        task.execute();
    }

    protected String getToken() {
        return token;
    }

    protected void setToken(String token) {
        this.token = token;
    }

    protected String getProvider() {
        return provider;
    }

    protected void setProvider(String provider) {
        this.provider = provider;
    }

    private String token;
    private Context androidContext;
    private boolean authenticated = false;

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public Boolean isReady() {
        if (isAuthenticated() && getAccount().haveData())
            return true;
        return false;
    }

    public void authWithOAuthToken(String provider, String token) {
        onAuthenticated(null);
        setProvider(provider);
        setToken(token);
        getFirebase().authWithOAuthToken(provider, token, this);
    }

    public Boolean isAuthenticated() {
        return authenticated;
    }

    public Context getAndroidContext() {
        return androidContext;
    }

    public void setAndroidContext(Context androidContext) {
        this.androidContext = androidContext;
        Firebase.setAndroidContext(getAndroidContext());
    }

    public Firebase getFirebase() {
        return firebase;
    }

    public void setFirebase(Firebase firebase) {
        this.firebase = firebase;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        setFirebase(new Firebase(getUrl()));
    }

    public Database(Context context, boolean persistenceEnabled, String host) {
        setAndroidContext(context);
        setPersistenceEnabled(persistenceEnabled);
        setUrl(HTTPS + host);
    }

}