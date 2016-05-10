package io.vinkas;

/**
 * Created by Vinoth on 10-5-16.
 */
public interface ReadListener<Child extends Item> extends FireListener {
    public void onRead(Child item);
}
