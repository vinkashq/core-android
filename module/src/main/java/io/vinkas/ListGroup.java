package io.vinkas;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.core.Path;
import com.firebase.client.core.Repo;

/**
 * Created by Vinoth on 10-5-16.
 */
public class ListGroup<Parent extends List, Child extends ListItem> extends Firebase {

    String url;

    public ListGroup(Repo repo, Path path) {
        super(repo, path);
    }

    public ListGroup(String url) {
        super(url);
        this.url = url;
    }

    public Parent getItem(String key) {
        return (Parent) new List<Child>(url + "/" + key);
    }

    public void move(final String from, final String to, final Child listItem, final CompletionListener listener) {
        Parent target = getItem(to);
        target.add(listItem, new CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if(firebaseError == null) {
                    Parent source = getItem(from);
                    source.remove(listItem, listener);
                } else {
                    listener.onComplete(firebaseError, firebase);
                }
            }
        });
    }

    public void add(Child listItem) {

    }

}
