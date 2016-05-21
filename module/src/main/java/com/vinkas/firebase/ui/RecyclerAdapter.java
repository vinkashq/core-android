package com.vinkas.firebase.ui;

import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import com.vinkas.firebase.Item;

/**
 * Created by Vinoth on 13-5-16.
 */
public abstract class RecyclerAdapter<T extends Item, VH extends RecyclerView.ViewHolder> extends com.google.firebase.ui.RecyclerAdapter<T, VH> {

    public RecyclerAdapter(Class<T> modelClass, int modelLayout, Class<VH> viewHolderClass, DatabaseReference ref) {
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
