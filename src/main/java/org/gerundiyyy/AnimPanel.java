package org.gerundiyyy;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public class AnimPanel extends JPanel {
    private ShipPainter ship = new ShipPainter();
    private final CannonPainter canon = new CannonPainter();
    private final Vector<BallInstance> balls;
    private ShipMotion shipMotion;
    private final ExplosionPainter exp = new ExplosionPainter();

    public AnimPanel(int startShipX, int startShipY) {
        setPreferredSize(new Dimension(600, 400));
        addComponentListener(new AnimPanelListener(this, startShipX, startShipY));
        balls = new Vector<>();
    }

    public void startShipMotion(int startShipX, int startShipY) {
        shipMotion = new ShipMotion(startShipX, startShipY,
                ThreadLocalRandom.current().nextInt(2, 5),
                ThreadLocalRandom.current().nextInt(0, 3), true,
                ship, getWidth(), getHeight() * 2 / 3);
        new Thread(shipMotion).start();
        Timer timer = new Timer(16, new ShipTimerListener(this));
        timer.start();
    }

    public void startBallMotion(BallInstance ballInst) {
        int startBallX = getWidth() / 2;
        int startBallY = getHeight() * 2 / 3 - 20;

        ballInst.setMotion(new BallMotion(startBallX, startBallY,
                ThreadLocalRandom.current().nextInt(-5, 5),
                ThreadLocalRandom.current().nextInt(-3, 3), true,
                ballInst.getBall(), getWidth(), getHeight() * 2 / 3));
        new Thread(ballInst.getMotion()).start();
        Timer timer = new Timer(16, new BallTimerListener(this, ballInst, 200));
        timer.start();
    }

    public void spawnBall(BallInstance ballInst) {
        balls.add(ballInst);
        startBallMotion(ballInst);
        repaint();
    }

    public void removeBall(BallInstance ballInst) {
        balls.remove(ballInst);
    }

    public void removeShip() {
        // остановите motion если есть
        if (shipMotion != null) {
            shipMotion.setRunning(false); // или другой способ остановки
        }
        ship = null;
        SwingUtilities.invokeLater(this::repaint);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            paintBackgroud(g2);
            paintCanon(g2);
            paintBalls(g2);
            paintShip(g2);
            paintExplosion(g2);
        } finally {
            g2.dispose();
        }
    }

    private void paintBackgroud(Graphics2D g2) {
        int w = getWidth();
        int h = getHeight();
        int twoThirds = getHeight() * 2 / 3;
        Graphics2D graphics = (Graphics2D) g2.create();

        graphics.setColor(new Color(41, 235, 242));
        graphics.fillRect(0, 0, w, twoThirds);

        graphics.setColor(new Color(242, 193, 41));
        graphics.fillRect(0, twoThirds, w, h - twoThirds);
    }

    private void paintBalls(Graphics2D g2) {
        for (BallInstance ballInst : balls) {
            Graphics2D graphics = (Graphics2D) g2.create();
            try {
                if (ballInst.getMotion() != null) {
                    graphics.translate(ballInst.getMotion().getCoordX(),
                            ballInst.getMotion().getCoordY());
                    ballInst.getBall().draw(graphics);
                }
            } finally {
                graphics.dispose();
            }
        }
    }

    private void paintCanon(Graphics2D g2) {
        int startCanonX = getWidth() / 2;
        int startCanonY = getHeight() * 2 / 3 - 20;
        Graphics2D graphics = (Graphics2D) g2.create();
        try {
            if (canon != null) {
                graphics.translate(startCanonX, startCanonY);
                canon.draw(graphics);
            }
        } finally {
            graphics.dispose();
        }
    }

    private void paintShip(Graphics2D g2) {
        Graphics2D graphics = (Graphics2D) g2.create();
        try {
            if (ship != null && shipMotion != null) {
                graphics.translate(shipMotion.getCoordX(), shipMotion.getCoordY());
                ship.draw(graphics);
            }
        } finally {
            graphics.dispose();
        }
    }

    public void paintExplosion(Graphics2D g2) {
        if (exp != null && ship == null) {
            int Xstep, Ystep;
            for (int i = 0; i < 3; i++) {
                Graphics2D graphics = (Graphics2D) g2.create();
                try {
                    Xstep = ThreadLocalRandom.current().nextInt(-30, 30);
                    Ystep = ThreadLocalRandom.current().nextInt(-30, 30);
                    graphics.translate(shipMotion.getCoordX() + Xstep, shipMotion.getCoordY() + Ystep);
                    exp.draw(graphics);
                } finally {
                    graphics.dispose();
                }
            }
        }
    }

    public void setShipMotion(ShipMotion shipMotion) {
        this.shipMotion = shipMotion;
    }

    public ShipMotion getShipMotion() {
        return shipMotion;
    }

    public ShipPainter getShip() {
        return ship;
    }

    public CannonPainter getCanon() {
        return canon;
    }

    public Vector<BallInstance> getBalls() {
        return balls;
    }
}
