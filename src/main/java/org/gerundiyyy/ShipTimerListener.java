package org.gerundiyyy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

public class ShipTimerListener implements ActionListener {
    final private AnimPanel ap;

    ShipTimerListener(AnimPanel ap){
        this.ap = ap;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ap.repaint();
        if(checkCollisions()){
            ap.removeShip();
            ap.repaint();
        }
    }

    private boolean checkCollisions() {
        if (ap.getShipMotion() == null) return false;

        Rectangle2D shipRect = ap.getShipMotion().getRectCollision();

        for (BallEntity ballEntity : ap.getBalls()) {
            BallMotion bm = ballEntity.getBallMotion();
            if (bm == null) continue;

            Rectangle2D ballRect = bm.getRectCollision();
            if (shipRect.intersects(ballRect)) {
                return true;
            }
        }
        return false;
    }
}
