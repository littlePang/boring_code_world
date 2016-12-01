package com.little.pang.boring.code.serivce.design.pattern.prototype;

/**
 * Created by jaky on 11/24/16.
 */
public class Client {

    public static void main(String[] args) {
        Prototype<ConcretePrototype> concretePrototype = new ConcretePrototype();
        Prototype<ConcretePrototype> cloneObj = concretePrototype.clone();

        System.out.println(concretePrototype == cloneObj);
        System.out.println(concretePrototype.getClass().equals(cloneObj.getClass()));
    }

}
