
package com.segvek.analitic.model;

import java.awt.Point;

public class GraphicElement {
    private Point position;

    public GraphicElement() {
        position = new Point((int)(Math.random()*500), (int)(Math.random()*500));
    }

    public GraphicElement(Point position) {
        this.position = position;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
    
}
