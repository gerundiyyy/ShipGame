package org.gerundiyyy;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Sprited {
    protected BufferedImage sprite = null;
    protected String imgPath = "";

    void initImage(){
        try {
            sprite = ImageIO.read(new File(imgPath));
        } catch (IOException e) {
            System.err.println("Problem is: " + e); // если проблема с файлом картинки
        }
    };

    public BufferedImage getSprite() {
        return sprite;
    }

    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
