package com.kvin.toolkit.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Kvin on 2017/2/13.
 */
public class CollectionUtils {

    /**
     * sort a collection
     */
    public static ArrayList<String> sort(Set<String> set) {
        ArrayList<String> output = new ArrayList<>();
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            output.add(iterator.next());
        }

        return output;
    }
}
