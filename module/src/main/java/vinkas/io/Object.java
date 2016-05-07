package vinkas.io;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;

import vinkas.util.Helper;

/**
 * Created by Vinoth on 3-5-16.
 */
public abstract class Object implements ValueEventListener, Firebase.CompletionListener, Map.Listener {

    protected boolean haveData = false;

    @Override
    public void onDataChange(String key, java.lang.Object value) {

    }

    @Override
    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
        if (firebaseError != null)
            Helper.onError(firebaseError);
    }

    public boolean haveData() {
        return haveData;
    }

    public void setHaveData(boolean haveData) {
        this.haveData = haveData;
    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {
        Helper.onError(firebaseError);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        if (dataSnapshot.exists()) {
            onRead(dataSnapshot);
            setHaveData(true);
        } else
            onNonExist();
    }

    public void onNonExist() {
        if (isValid())
            write();
    }

    public abstract boolean isValid();

    public void onRead(DataSnapshot dataSnapshot) {
        Iterator<DataSnapshot> it = dataSnapshot.getChildren().iterator();
        while (it.hasNext()) {
            DataSnapshot data = it.next();
            onRead(data.getKey(), data.getValue());
        }
    }

    public abstract void onRead(String key, java.lang.Object value);

    protected Map map;

    public void write() {
        getFirebase().setValue(map, this);
    }

    public void write(Object priority) {
        getFirebase().setPriority(priority);
        write();
    }

    public String get(String key) {
        return map.get(key).toString();
    }

    public void set(String key, java.lang.Object value) {
        map.put(key, value);
    }

    public Object(Firebase firebase) {
        setFirebase(firebase);
        setMap();
    }

    public Object(DataSnapshot dataSnapshot) {
        setFirebase(dataSnapshot.getRef());
        setMap();
        onDataChange(dataSnapshot);
    }

    protected Map getMap() {
        return map;
    }

    protected void setMap() {
        map = new Map(this);
    }

    protected Firebase firebase;

    public Firebase getFirebase() {
        return firebase;
    }

    protected void setFirebase(Firebase firebase) {
        this.firebase = firebase;
    }

    public void read() {
        getFirebase().addListenerForSingleValueEvent(this);
    }

}