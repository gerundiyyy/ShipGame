package org.gerundiyyy;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class BeachPainter extends Sprited implements IDrawable {
    private Path2D shape;
    private int width;
    private int height;

    BeachPainter(int width, int height){
        this.width = width;
        this.height = height;
        setImgPath("src/main/resources/sprites/beach.png");
        initImage();
        createShapeOfTheFigure();
    }

    @Override
    public void draw(Graphics2D g2) {
        Rectangle2D spriteRect = new Rectangle2D.Double(0, 0, sprite.getWidth(), sprite.getHeight());
        TexturePaint tp = new TexturePaint(sprite, spriteRect);

        g2.setPaint(tp);
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
