package org.gerundiyyy;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

public class CannonPainter implements Painter {
    private Path2D shape;

    CannonPainter() {
        createShapeOfTheFigure();
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(new Color(226, 71, 117));
        g2.fill(shape); // контур
        g2.setColor(Color.BLACK);
        g2.draw(shape);
    }

    @Override
    public void createShapeOfTheFigure() {
        shape = new Path2D.Float();
        // корпус
        shape.moveTo(-20, 0);
        shape.lineTo(20, 0);
        shape.lineTo(20, 100);
        shape.lineTo(-20, 100);
        shape.lineTo(-20, 0);
        shape.closePath();
    }

    @Override
    public Rectangle2D getBounds() {
        return shape.getBounds2D();
    }
}
