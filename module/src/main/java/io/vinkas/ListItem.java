package io.vinkas;

import com.google.firebase.database.DatabaseReference;

/**
 * Created by Vinoth on 10-5-16.
 */
public class ListItem<Parent extends List> extends Item {

    /*public void move(final DatabaseReference source, final DatabaseReference target, final Listener listener) {
        writeIn(target, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(databaseError == null) {
                    removeIn(source, listener);
                } else {
                    listener.onError(databaseError);
                }
            }
        });
    }*/

    /*public void move(Parent parent, String target, Listener listener) {
        move(parent.getReference(), parent.getReference().getParent().child(target), listener);
    }*/

    protected void setReference(Parent parent) {
        DatabaseReference pr = parent.getReference();
        if (getKey() == null) {
            setReference(pr.push());
            setKey(getReference().getKey());
        } else {
            setReference(pr.child(getKey()));
        }
    }

    public void writeTo(Parent parent, CreateListener listener) {
        setReference(parent);
        super.writeIn(listener);
    }

    public void writeTo(Parent parent, DatabaseReference.CompletionListener listener) {
        setReference(parent);
        super.writeIn(listener);
    }

    public void removeFrom(Parent parent, RemoveListener listener) {
        setReference(parent);
        super.removeIn(listener);
    }

}