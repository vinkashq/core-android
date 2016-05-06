package vinkas;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import vinkas.io.Database;
import vinkas.library.R;

/**
 * Created by Vinoth on 3-5-16.
 */
public abstract class Application extends android.app.Application {

    private Database database;

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public abstract void setDatabase();

    public boolean isReady() {
        if(getDatabase().isReady())
            return true;
        return false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setDatabase();
    }

    public void setGoogleSignInAccount(GoogleSignInAccount googleSignInAccount) {
        getDatabase().setGoogleSignInAccount(googleSignInAccount);
    }

}
