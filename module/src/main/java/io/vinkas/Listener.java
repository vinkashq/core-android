package io.vinkas;

import com.firebase.client.FirebaseError;

/**
 * Created by Vinoth on 10-5-16.
 */
public interface Listener<Child extends Item> extends CreateListener<Child>, ReadListener<Child> {



}
