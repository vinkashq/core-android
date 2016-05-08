package vinkas.io;

import java.lang.*;
import java.util.ArrayList;

/**
 * Created by Vinoth on 7-5-16.
 */
public class Map extends java.util.HashMap<String, java.lang.Object> {

    private Item item;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Map(Item item) {
        setItem(item);
    }

    private ArrayList<Listener> listeners = new ArrayList<Listener>();

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void onDataChange(String key, java.lang.Object value) {
        getItem().onDataChange(key, value);
        for (int i = 0; i < listeners.size(); i++) {
            listeners.get(i).onDataChange(key, value);
        }
    }

    @Override
    public void clear() {
        super.clear();
        onDataChange(null, null);
    }

    @Override
    public java.lang.Object put(String key, java.lang.Object value) {
        java.lang.Object o = super.put(key, value);
        onDataChange(key, o);
        return o;
    }

    @Override
    public java.lang.Object remove(java.lang.Object key) {
        java.lang.Object o = super.remove(key);
        onDataChange(key.toString(), o);
        return o;
    }

    public interface Listener {
        public void onDataChange(String key, java.lang.Object value);
    }

}