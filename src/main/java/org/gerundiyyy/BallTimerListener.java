package org.gerundiyyy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BallTimerListener implements ActionListener {
    final private AnimPanel ap;
    private final BallEntity ballEntity;
    private int tickCounter = 0;

    BallTimerListener(AnimPanel ap, BallEntity ballEntity){
        this.ap = ap;
        this.ballEntity = ballEntity;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ap.repaint();
        tickCounter++;
        if(tickCounter == ballEntity.getBallMotion().getTicksToDeleete()) {
            ap.removeBall(ballEntity);
            ap.repaint();
        }
    }
}
