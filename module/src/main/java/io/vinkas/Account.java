package io.vinkas;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Created by Vinoth on 10-5-16.
 */
public class Account extends ListItem<Accounts> {

    private String name, email, userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Account() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void set(GoogleSignInAccount googleAccount) {
        setName(googleAccount.getDisplayName());
        setEmail(googleAccount.getEmail());
    }

}
