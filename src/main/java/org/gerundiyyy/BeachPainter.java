package org.gerundiyyy;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

public class BeachPainter implements Painter {
    private Path2D shape;
    private int width;
    private int height;

    BeachPainter(int width, int height){
        this.width = width;
        this.height = height;
        createShapeOfTheFigure();
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(new Color(244, 187, 118));
        g2.fill(shape);
    }

    @Override
    public void createShapeOfTheFigure() {
        shape = new Path2D.Float();

        shape.moveTo(0, 0);
        shape.lineTo(width, 0);
        shape.lineTo(width, height);
        shape.lineTo(0, height);
        shape.lineTo(0, 0);
        shape.closePath();
    }

    @Override
    public Rectangle2D getBounds() {
        return null;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
