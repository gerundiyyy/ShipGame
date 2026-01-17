package org.gerundiyyy;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public interface IDrawable {
    void draw(Graphics2D g2);
    void createShapeOfTheFigure();
    Rectangle2D getBounds();
}
