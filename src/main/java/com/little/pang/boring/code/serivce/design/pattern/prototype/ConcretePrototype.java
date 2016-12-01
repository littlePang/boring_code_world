package com.little.pang.boring.code.serivce.design.pattern.prototype;

/**
 * Created by jaky on 11/24/16.
 */
public class ConcretePrototype implements Prototype<ConcretePrototype> {

    public ConcretePrototype clone() {
        try {
            return (ConcretePrototype) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
