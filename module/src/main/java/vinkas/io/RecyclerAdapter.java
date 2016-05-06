package vinkas.io;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.lang.*;
import java.lang.Object;
import java.util.*;
import java.util.List;

/**
 * Created by Vinoth on 6-5-16.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<ListItem.ViewHolder> {
    @Override
    public int getItemCount() {
        return (items != null ? items.size() : 0);
    }

    @Override
    public ListItem.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ListItem.ViewHolder holder, int position) {

    }

    private java.util.List<ListItem> items;
    private Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<ListItem> getItems() {
        return items;
    }

    public void setItems(List<ListItem> items) {
        this.items = items;
    }

    public RecyclerAdapter(Context context, Map<String, ListItem> items) {
        setContext(context);
        getItems().addAll(items.values());
    }
}
