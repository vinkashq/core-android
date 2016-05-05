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
public abstract class Object implements ValueEventListener {

    private String childPath;
    protected boolean haveData = false;

    public boolean haveData() {
        return haveData;
    }

    public void setHaveData(boolean haveData) {
        this.haveData = haveData;
    }

    public String getChildPath() {
        return childPath;
    }

    public void setChildPath(String childPath) {
        this.childPath = childPath;
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
        }
        else
            onNonExist();
    }

    public void onNonExist() {
        if(isValid())
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

    protected HashMap<String, String> map;

    public void write() {
        map = new HashMap<String, String>();
        mapData();
        getFirebase().setValue(map);
    }

    public abstract void mapData();

    public Object(Database database, String childPath) {
        setDatabase(database);
        setChildPath(childPath);
        setFirebase(getDatabase().getFirebase().child(childPath));
    }

    private Database database;
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

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

}
