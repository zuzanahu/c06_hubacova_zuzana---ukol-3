package rasterize;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Optional;

public class Raster {

    private final BufferedImage img;

    public Raster(int width, int height) {
        this.img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    public int getWidth() {
        return img.getWidth();
    }

    public int getHeight() {
        return img.getHeight();
    }

    public Optional<Integer> getColor(int c, int r) {
        if (c < img.getWidth() && r < img.getHeight() && c >= 0 && r >= 0) {
            return Optional.of(img.getRGB(c, r));
        }
        return Optional.empty();
    }

    public boolean setColor(int c, int r,int color ) {
        if (c < img.getWidth() && r < img.getHeight() && c >= 0 && r >= 0) {
            img.setRGB(c, r, color);
            return true;
        }
        return false;
    }

    public void clear(int backgroundColor) {
        Graphics gr = img.getGraphics();
        try {
            gr.setColor(new Color(backgroundColor));
            gr.fillRect(0, 0, img.getWidth(), img.getHeight());
        } finally {
            gr.dispose();
        }
    }


    public void present(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }
}