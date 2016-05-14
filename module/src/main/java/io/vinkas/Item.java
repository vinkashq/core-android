package io.vinkas;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.vinkas.util.Helper;

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

    public void onCreate() {

    }

    public void onError(FirebaseError firebaseError) {
        Helper.onError(firebaseError);
    }

    public void writeIn(Firebase firebase, final CreateListener listener) {
        writeIn(firebase, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError == null) {
                    onCreate();
                    if (listener != null)
                        listener.onCreate(Item.this);
                } else {
                    onError(firebaseError);
                    if (listener != null)
                        listener.onError(firebaseError);
                }
            }
        });
    }

    public Item parseSnapshot(DataSnapshot snapshot) {
        Item item = snapshot.getValue(this.getClass());
        item.setKey(snapshot.getKey());
        item.setPriority(snapshot.getPriority());
        return item;
    }

    public void removeIn(Firebase firebase, final RemoveListener listener) {
        removeIn(firebase, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError == null) {
                    if (listener != null)
                        listener.onRemove(Item.this);
                } else {
                    onError(firebaseError);
                    if (listener != null)
                        listener.onError(firebaseError);
                }
            }
        });
    }

    public void removeIn(Firebase firebase, Firebase.CompletionListener listener) {
        firebase.removeValue(listener);
    }

}