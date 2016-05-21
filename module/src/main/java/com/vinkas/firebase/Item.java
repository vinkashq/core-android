package com.vinkas.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.vinkas.util.Helper;

/**
 * Created by Vinoth on 10-5-16.
 */
@IgnoreExtraProperties
public class Item {

    static Helper helper;
    static public Helper getHelper() {
        if(helper == null)
            helper = Helper.getInstance();
        return helper;
    }

    private DatabaseReference reference;

    public DatabaseReference getReference() {
        return reference;
    }

    public void setReference(DatabaseReference reference) {
        this.reference = reference;
    }

    public Item() {

    }

    private String key;
    private Object priority;

    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }

    @Exclude
    public Object getPriority() {
        return priority;
    }

    @Exclude
    public void setPriority(Object priority) {
        this.priority = priority;
    }

    public void writeIn(final DatabaseReference.CompletionListener listener) {
        getReference().setValue(this, getPriority(), listener);
    }

    public void onCreate() {

    }

    public void onError(DatabaseError error) {
        Helper.onError(error);
    }

    public void writeIn(final CreateListener listener) {
        writeIn(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    onCreate();
                    if (listener != null)
                        listener.onCreate(Item.this);
                } else {
                    onError(databaseError);
                    if (listener != null)
                        listener.onError(databaseError);
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

    public void removeIn(final RemoveListener listener) {
        removeIn(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    if (listener != null)
                        listener.onRemove(Item.this);
                } else {
                    onError(databaseError);
                    if (listener != null)
                        listener.onError(databaseError);
                }
            }
        });
    }

    public void removeIn(DatabaseReference.CompletionListener listener) {
        getReference().removeValue(listener);
    }

}