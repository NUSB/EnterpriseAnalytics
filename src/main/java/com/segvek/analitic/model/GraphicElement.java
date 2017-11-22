
package com.segvek.analitic.model;

import java.awt.Dimension;

public class GraphicElement {
    private Dimension position;

    public GraphicElement() {
        position = new Dimension((int)(Math.random()*500), (int)(Math.random()*500));
    }

    public GraphicElement(Dimension position) {
        this.position = position;
    }

    public Dimension getPosition() {
        return position;
    }

    public void setPosition(Dimension position) {
        this.position = position;
    }
    
}
