package vinkas.io;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

/**
 * Created by Vinoth on 6-5-16.
 */
public abstract class ListItem extends Object {

    @Override
    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
        super.onComplete(firebaseError, firebase);
        if(firebaseError == null && getList() != null) {
            getList().getItems().put(getKey(), this);
        }
    }

    @Override
    public void onRead(String key, java.lang.Object value) {
        set(key, value);
    }

    public ListItem(DataSnapshot dataSnapshot) {
        super(dataSnapshot);
        setKey(dataSnapshot.getKey());
    }

    public ListItem(List list) {
        super(list.getFirebase().push());
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

    public ListItem(List list, String key) {
        super(list.getFirebase().child(key));
        setKey(key);
        setList(list);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

    }

}
