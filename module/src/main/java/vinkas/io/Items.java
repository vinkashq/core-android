package vinkas.io;

import java.lang.*;
import java.lang.Object;

/**
 * Created by Vinoth on 7-5-16.
 */
public class Items extends Map {

    @Override
    public List getItem() {
        return (List) super.getItem();
    }

    public Items(List list) {
        super(list);
    }

    @Override
    public ListItem put(String key, Object value) {
        return (ListItem) super.put(key, value);
    }

    @Override
    public ListItem remove(Object key) {
        return (ListItem) super.remove(key);
    }
}