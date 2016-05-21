package com.vinkas.firebase;

/**
 * Created by Vinoth on 10-5-16.
 */
public interface Listener<Child extends Item> extends CreateListener<Child>, ReadListener<Child>, RemoveListener<Child> {



}
