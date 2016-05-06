package vinkas.io;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.firebase.client.DataSnapshot;

/**
 * Created by Vinoth on 6-5-16.
 */
public abstract class ListItem extends Object {

    @Override
    public void onRead(String key, java.lang.Object value) {
        set(key, value);
    }

    public ListItem(DataSnapshot dataSnapshot) {
        super(dataSnapshot);
    }

    public ListItem(List list) {
        super(list.getFirebase().push());
    }

    public ListItem(List list, String childPath) {
        super(list.getFirebase().child(childPath));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

    }

}
