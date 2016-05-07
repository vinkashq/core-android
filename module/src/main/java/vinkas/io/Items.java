package vinkas.io;

import java.lang.*;
import java.lang.Object;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Vinoth on 7-5-16.
 */
public class Items extends Map {

    @Override
    public List getObject() {
        return (List) super.getObject();
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