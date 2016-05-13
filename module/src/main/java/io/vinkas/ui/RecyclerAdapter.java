package io.vinkas.ui;

import android.support.v7.widget.RecyclerView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.Query;

import io.vinkas.Item;

/**
 * Created by Vinoth on 13-5-16.
 */
public abstract class RecyclerAdapter<T extends Item, VH extends RecyclerView.ViewHolder> extends com.firebase.ui.RecyclerAdapter<T, VH> {

    public RecyclerAdapter(Class<T> modelClass, int modelLayout, Class<VH> viewHolderClass, Firebase ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
    }

    public RecyclerAdapter(Class<T> modelClass, int modelLayout, Class<VH> viewHolderClass, Query ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
    }

    @Override
    protected T parseSnapshot(DataSnapshot snapshot) {
        T item = super.parseSnapshot(snapshot);
        item.setKey(snapshot.getKey());
        item.setPriority(snapshot.getPriority());
        return item;
    }
}
