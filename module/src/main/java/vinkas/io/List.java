package vinkas.io;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vinoth on 6-5-16.
 */
public abstract class List extends Object implements DatabaseHaver, ChildEventListener {

    @Override
    public void onDataChange(String key, java.lang.Object value) {

    }

    public abstract ListItem getItem(DataSnapshot dataSnapshot);

    public Items getItems() {
        return (Items) getMap();
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        getItems().put(dataSnapshot.getKey(), getItem(dataSnapshot));
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        getItems().put(dataSnapshot.getKey(), getItem(dataSnapshot));
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        getItems().remove(dataSnapshot.getKey());
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        getItems().put(dataSnapshot.getKey(), getItem(dataSnapshot));
    }

    @Override
    public void read() {
        getFirebase().addChildEventListener(this);
    }

    private Database database;

    public List(Database database, String childPath) {
        super(database.getFirebase().child(childPath));
        setDatabase(database);
    }

    @Override
    protected void setMap() {
        map = new Items(this);
    }

    @Override
    public Database getDatabase() {
        return database;
    }

    @Override
    public void setDatabase(Database database) {
        this.database = database;
    }



}
