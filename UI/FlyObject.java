package UI;

import java.awt.image.BufferedImage;

public abstract class FlyObject {
    BufferedImage image;
    int x;
    int y;
    int width;
    int height;

    public void moveToMouse(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moveUp(int x) {
        this.y -= x;
    }

    public void moveDown(int x) {
        this.y += x;
    }

    public void moveLeft(int x) {
        this.x -= x;
    }

    public void moveRight(int x) {
        this.x += x;
    }
}
