package vinkas.io;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Iterator;

import vinkas.Application;
import vinkas.util.Helper;

/**
 * Created by Vinoth on 3-5-16.
 */
public abstract class Item implements ValueEventListener, Firebase.CompletionListener, Map.Listener {

    protected boolean haveData = false;
    protected boolean synced = false;

    public boolean isSynced() {
        return synced;
    }

    public void setSynced(boolean synced) {
        this.synced = synced;
    }

    @Override
    public void onDataChange(String key, java.lang.Object value) {

    }

    @Override
    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
        if (firebaseError != null)
            Helper.onError(firebaseError);
        else
            setSynced(true);
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
            setSynced(true);
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

    public void onRead(String key, java.lang.Object value) {
        set(key, value);
    }

    protected Map map;

    public void write() {
        getFirebase().setValue(map, this);
    }

    public void write(Item priority) {
        getFirebase().setPriority(priority);
        write();
    }

    public String get(String key) {
        java.lang.Object o = getObject(key);
        if(o == null)
            return null;
        else
            return o.toString();
    }

    public java.lang.Object getObject(String key) {
        return map.get(key);
    }

    public void set(String key, java.lang.Object value) {
        map.put(key, value);
    }

    private Application application;

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Item(Application application, String fullPath) {
        setApplication(application);
        setFirebase(application.getFirebase().child(fullPath));
        setMap();
    }

    public Item(Application application, Firebase firebase) {
        setApplication(application);
        setFirebase(firebase);
        setMap();
    }

    public Item(Application application, DataSnapshot dataSnapshot) {
        setApplication(application);
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