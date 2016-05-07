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

    private Items items;
    private Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
        this.items.addListener(new Items.Listener() {
            @Override
            public void onDataChange(String key, Object value) {
                notifyDataSetChanged();
            }
        });
    }

    public RecyclerAdapter(Context context, Items items) {
        setContext(context);
        setItems(items);
    }
}
