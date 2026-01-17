package org.gerundiyyy;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public class AnimPanel extends JPanel {
    private GameConfig config;
    private ShipPainter ship = new ShipPainter();
    private final CannonPainter canon = new CannonPainter();
    private final Vector<BallEntity> balls;
    private ShipMotion shipMotion;
    private final ExplosionPainter exp = new ExplosionPainter();
    private SeaPainter seaPainter;
    private BeachPainter beachPainter;

    //Config
    public AnimPanel(GameConfig config) {
        this.config = config;
        addComponentListener(new AnimPanelListener(this,
                config.getShipCoordX(), config.getShipCoordY()));
        balls = new Vector<>();
    }

    //Config
    public void startShipMotion(int startShipX, int startShipY) {
        shipMotion = new ShipMotion(startShipX, startShipY,
                config.getShipSpeedX(), config.getShipSpeedY(),true,
                ship, getWidth(), getHeight() * 2 / 3);
        new Thread(shipMotion).start();
        Timer timer = new Timer(16, new ShipTimerListener(this));
        timer.start();
    }

    //Config
    public void startBallMotion(BallEntity ballEntity) {
        int startBallX = getWidth() / 2;
        int startBallY = getHeight() * 2 / 3 - 20;

        ballEntity.setBallMotion(new BallMotion(startBallX, startBallY,
                ThreadLocalRandom.current().nextInt(-5, 5),
                ThreadLocalRandom.current().nextInt(-3, 3), true,
                ballEntity.getBallPainter(),config.getBallTicksToDelete(), getWidth(), getHeight() * 2 / 3));
        new Thread(ballEntity.getBallMotion()).start();
        Timer timer = new Timer(16, new BallTimerListener(this, ballEntity));
        timer.start();
    }

    public void spawnBall(BallEntity ballInst) {
        balls.add(ballInst);
        startBallMotion(ballInst);
        repaint();
    }

    public void removeBall(BallEntity ballInst) {
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

        if (seaPainter == null) initSea();
        if (beachPainter == null) initBeach();

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
        paintSea(g2);
        paintBeach(g2);
    }

    private void paintSea(Graphics2D g2) {
        Graphics2D graphics = (Graphics2D) g2.create();
        try {
            graphics.translate(0, 0);
            seaPainter.draw(graphics);
        } finally {
            graphics.dispose();
        }
    }

    private void paintBeach(Graphics2D g2) {
        Graphics2D graphics = (Graphics2D) g2.create();
        try {
            graphics.translate(0, getHeight() * 2 / 3);
            beachPainter.draw(graphics);
        } finally {
            graphics.dispose();
        }
    }

    private void paintBalls(Graphics2D g2) {
        for (BallEntity ballballEntity : balls) {
            Graphics2D graphics = (Graphics2D) g2.create();
            try {
                if (ballballEntity.getBallMotion() != null) {
                    graphics.translate(ballballEntity.getBallMotion().getCoordX(),
                            ballballEntity.getBallMotion().getCoordY());
                    ballballEntity.getBallPainter().draw(graphics);
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

    //Config
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

    public void initSea() {
        int w = getWidth();
        int h = getHeight() * 2 / 3;

        seaPainter = new SeaPainter(w, h);
    }

    public void initBeach() {
        int w = getWidth();
        int h = getHeight() / 3;

        beachPainter = new BeachPainter(w, h);
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

    public Vector<BallEntity> getBalls() {
        return balls;
    }

    public void setSeaPainter(SeaPainter seaPainter) {
        this.seaPainter = seaPainter;
    }

    public SeaPainter getSeaPainter() {
        return seaPainter;
    }

    public void setBeachPainter(BeachPainter beachPainter) {
        this.beachPainter = beachPainter;
    }

    public BeachPainter getBeachPainter() {
        return beachPainter;
    }
}
