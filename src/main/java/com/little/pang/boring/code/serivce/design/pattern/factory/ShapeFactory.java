package com.little.pang.boring.code.serivce.design.pattern.factory;

/**
 * Created by jaky on 11/21/16.
 */
public class ShapeFactory {

    public static Shape createShape(String shapeType) {

        if ("rectangle".equalsIgnoreCase(shapeType)) {
            return new Rectangle();

        } else if ("square".equalsIgnoreCase(shapeType)) {
            return new Square();
        }

        return null;
    }

    public static void main(String[] args) {
        ShapeFactory.createShape("square").draw();
    }

}
