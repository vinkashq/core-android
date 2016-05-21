package com.vinkas.firebase;

/**
 * Created by Vinoth on 11-5-16.
 */
public interface RemoveListener<Child extends Item> extends FireListener {
    public void onRemove(Child item);
}
