package com.kvin.toolkit.widget;

import java.util.Collection;

/**
 * Created by Kvin on 2017/2/9.
 */
public class ArrayList<E> extends java.util.ArrayList<E> {


    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c == null) return false;
        return super.addAll(c);
    }
}
