package io.vinkas;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

/**
 * Created by Vinoth on 10-5-16.
 */
public class Item {

    public Item() {

    }

    private String key;
    private Object priority;

    @JsonIgnore
    public String getKey() {
        return key;
    }

    @JsonIgnore
    public void setKey(String key) {
        this.key = key;
    }

    @JsonIgnore
    public Object getPriority() {
        return priority;
    }

    @JsonIgnore
    public void setPriority(Object priority) {
        this.priority = priority;
    }

    public void writeIn(Firebase firebase, final Firebase.CompletionListener listener) {
        firebase.setValue(this, getPriority(), listener);
    }

    public void writeIn(Firebase firebase, final Listener listener) {
        writeIn(firebase, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if(firebaseError == null)
                    listener.onCreate(Item.this);
                else
                    listener.onError(firebaseError);
            }
        });
    }

    public void removeIn(Firebase firebase, Firebase.CompletionListener listener) {
        firebase.child(getKey()).removeValue(listener);
    }

}