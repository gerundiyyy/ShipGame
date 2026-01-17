package org.gerundiyyy;

public class BallEntity {
    private BallPainter ballPainter;
    private BallMotion ballMotion;

    public BallPainter getBallPainter() {
        return ballPainter;
    }

    public void setBallPainter(BallPainter ballPainter) {
        this.ballPainter = ballPainter;
    }

    public BallMotion getBallMotion() {
        return ballMotion;
    }

    public void setBallMotion(BallMotion ballMotion) {
        this.ballMotion = ballMotion;
    }

    public BallEntity(BallPainter ballPainter) {
        this.ballPainter = ballPainter;
    }
}
