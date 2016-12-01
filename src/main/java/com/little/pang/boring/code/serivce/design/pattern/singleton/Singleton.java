package com.little.pang.boring.code.serivce.design.pattern.singleton;


import javax.xml.ws.Service;

/**
 * Created by jaky on 11/21/16.
 */
public class Singleton {

    private Singleton(){};

    private static volatile Service service;

    public static Service getInstance() {

        if (null == service) {
            synchronized (Singleton.class) {
                if (null == service) {
//                    service = new Service();
                }
            }
        }

        return service;
    }

}
