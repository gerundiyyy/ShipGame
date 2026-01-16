package org.gerundiyyy;

import javax.swing.*;
import java.awt.*;

public class FrameManager extends JFrame {
    final private AnimPanel animPanel = new AnimPanel(30,30);
    private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    final private int screen_Width = dim.width;
    final private int screen_Height = dim.height;

    FrameManager() {
        setFrameProperties();
        add(animPanel);
        setVisible(true);
    }

    public void setFrameProperties(){
        setTitle("ShipGame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(screen_Width, screen_Height);
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setUndecorated(true);
    }

    public AnimPanel getAnimPanel() {
        return animPanel;
    }

    public Dimension getDim() {
        return dim;
    }

    public void setDim(Dimension dim) {
        this.dim = dim;
    }

    public int getScreen_Height() {
        return screen_Height;
    }

    public int getScreen_Width() {
        return screen_Width;
    }
}
