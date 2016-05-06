package vinkas.io;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.lang.*;

/**
 * Created by Vinoth on 3-5-16.
 */
public class Account extends Item {

    private String id;
    private GoogleSignInAccount googleAccount;

    public Account(Database database, AuthData authData) {
        super(database, "accounts/" + authData.getUid());
        setId(authData.getUid());
        read();
    }

    public Account(Database database, AuthData authData, GoogleSignInAccount googleAccount) {
        super(database, "accounts/" + authData.getUid());
        setId(authData.getUid());
        this.googleAccount = googleAccount;
        read();
    }

    @Override
    public void onNonExist() {
        if(googleAccount != null) {
            setName(googleAccount.getDisplayName());
            setEmail(googleAccount.getEmail());
            googleAccount = null;
            setHaveData(true);
        }
        super.onNonExist();
    }

    @Override
    public boolean isValid() {
        return (getName() != null && getEmail() != null);
    }

    @Override
    protected void setFirebase(Firebase firebase) {
        super.setFirebase(firebase);
        firebase.keepSynced(true);
    }

    protected void setEmail(String email) {
        set("Email", email);
    }

    protected void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return get("Name");
    }

    public void setName(String name) {
        set("Name", name);
    }

    public String getEmail() {
        return get("Email");
    }

    public String getId() {
        return id;
    }

}