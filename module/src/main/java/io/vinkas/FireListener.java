package io.vinkas;

import com.google.firebase.database.DatabaseError;

/**
 * Created by Vinoth on 10-5-16.
 */
public interface FireListener {
    public void onError(DatabaseError error);
}
