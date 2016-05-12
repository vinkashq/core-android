package io.vinkas;

import android.support.v7.widget.RecyclerView;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.ui.RecyclerAdapter;

/**
 * Created by Vinoth on 10-5-16.
 */
public class ListItem<Parent extends List> extends Item {

    public void move(final Firebase source, final Firebase target, final Listener listener) {
        writeIn(target, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if(firebaseError == null) {
                    removeIn(source, listener);
                } else {
                    listener.onError(firebaseError);
                }
            }
        });
    }

    public void move(Parent parent, String target, Listener listener) {
        move(parent, parent.getParent().child(target), listener);
    }

    private Firebase Firebase;

    @JsonIgnore
    protected Firebase getFirebase() {
        return Firebase;
    }

    @JsonIgnore
    protected void setFirebase(Parent parent) {
        if (getKey() == null) {
            Firebase = parent.push();
            setKey(Firebase.getKey());
        } else {
            Firebase = parent.child(getKey());
        }
    }

    public void writeTo(Parent parent, CreateListener listener) {
        setFirebase(parent);
        super.writeIn(getFirebase(), listener);
    }

    public void writeTo(Parent parent, Firebase.CompletionListener listener) {
        setFirebase(parent);
        super.writeIn(getFirebase(), listener);
    }

    public void removeFrom(Parent parent, RemoveListener listener) {
        setFirebase(parent);
        super.removeIn(getFirebase(), listener);
    }

}