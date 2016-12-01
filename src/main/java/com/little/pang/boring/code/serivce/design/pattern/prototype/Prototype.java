package com.little.pang.boring.code.serivce.design.pattern.prototype;

/**
 * Created by jaky on 11/24/16.
 */
public interface Prototype<T> extends Cloneable {
    T clone();
}
