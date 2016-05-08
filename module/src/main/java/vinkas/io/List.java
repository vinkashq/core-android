package vinkas.io;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;

import vinkas.Application;

/**
 * Created by Vinoth on 6-5-16.
 */
public abstract class List extends Item implements ChildEventListener {

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

    public List(Application app, String childPath) {
        super(app, app.getFirebase().child(childPath));
    }

    @Override
    protected void setMap() {
        map = new Items(this);
    }

}
