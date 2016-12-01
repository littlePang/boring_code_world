package com.little.pang.boring.code.serivce.design.pattern.prototype;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaky on 11/28/16.
 */
public class Example implements Cloneable {

    private ArrayList<String> list;

    @Override
    @SuppressWarnings("unchecked")
    public Example clone() throws CloneNotSupportedException {
            Example cloneObj = (Example)super.clone();
            cloneObj.list = (ArrayList<String>) list.clone();
            return cloneObj;
    }

}
