package vinkas.io;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import vinkas.Application;

/**
 * Created by Vinoth on 6-5-16.
 */
public abstract class ListItem extends Item {

    public final static String KEY = "key";

    @Override
    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
        super.onComplete(firebaseError, firebase);
        if (firebaseError == null) {
            getList().getItems().put(getKey(), this);
        }
    }

    public ListItem(Application application, List list, DataSnapshot dataSnapshot) {
        super(application, list.getFirebase().child(dataSnapshot.getKey()));
        setKey(dataSnapshot.getKey());
        setList(list);
        onDataChange(dataSnapshot);
    }

    public ListItem(Application application, List list) {
        super(application, list.getFirebase().push());
        setKey(getFirebase().getKey());
        setList(list);
    }

    private String key;
    private List list;

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ListItem(Application application, List list, String key) {
        super(application, list.getFirebase().child(key));
        setKey(key);
        setList(list);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

    }

}
