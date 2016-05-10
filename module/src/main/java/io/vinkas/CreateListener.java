package io.vinkas;

/**
 * Created by Vinoth on 10-5-16.
 */
public interface CreateListener<Child extends Item> extends FireListener {
    public void onCreate(Child item);
}