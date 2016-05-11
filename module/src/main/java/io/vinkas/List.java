package io.vinkas;

import com.firebase.client.Firebase;
import com.firebase.client.core.Path;
import com.firebase.client.core.Repo;

/**
 * Created by Vinoth on 10-5-16.
 */
public class List<Child extends ListItem> extends Firebase {

    public List(String url) {
        super(url);
    }

    public List(Repo repo, Path path) {
        super(repo, path);
    }

    public void add(Child item, CreateListener listener) {
        item.writeTo(this, listener);
    }

    public void remove(Child item, RemoveListener listener) {
        item.removeFrom(this, listener);
    }

    public void move(String target, Child item, Listener listener) {
        item.move(this, getParent().child(target), listener);
    }

}
