package UI;

public class Ball extends FlyObject {
    boolean punch = false;
    boolean boom = false;
    boolean rocket = false;
    double sizeTime = 1;
    int vx = 10;
    int vy = 10;

    public Ball(int x, int y, String imageName) {
        this.x = x;
        this.y = y;
        this.image = ImageManager.getImage("/Image/" + imageName);
        this.height = 50;
        this.width = 50;
    }

    //重置属性
    public void reset() {
        if (rocket || punch || boom)
            sizeTime = 1;
        rocket = punch = boom = false;
        image = ImageManager.getImage("/Image/ball.png");
        this.height = 50;
        this.width = 50;
        resetSpeed();
    }

    //判断球是否撞到墙或者是板
    public int knockWall(int boardLeft, int boardRight) {
        int result = 0;
        if (x <= 0)
            result = 1;                    //撞到左边 返回1
        else if (x + width >= 1280)
            result = 2;                    //撞到右边 返回2
        else if (y <= 0)
            result = 3;                    //撞到上边 返回3
        else if (x + width >= boardLeft && x <= boardRight && y + height >= 763 && y <= 763 + 30)
            result = 4;                    //撞到下边 返回4
        return result;
    }

    //根据当前速度重置速度
    public void resetSpeed() {
        if (vx == 20)
            vx = 10;
        if (vx == -20)
            vx = -10;
        if (vy == 20)
            vy = 10;
        if (vy == -20)
            vy = -10;
    }

    //火箭球，根据当前速度方向加速
    public void rocket() {
        rocket = true;
        if (vx == 10)
            vx = 20;
        else if (vx == -10)
            vx = -20;
        if (vy == 10)
            vy = 20;
        else if (vy == -10)
            vy = -20;
        image = ImageManager.getImage("/Image/RocketBall.png");
    }

    //炸弹球
    public void boom() {
        boom = true;
        image = ImageManager.getImage("/Image/BoomBall.png");
    }

    //穿刺球
    public void punch() {
        punch = true;
        image = ImageManager.getImage("/Image/PunchBall.png");
    }

    //球变小
    public void smaller() {
        if (sizeTime >= 0.25)
            sizeTime /= 2;
        width *= sizeTime;
        height *= sizeTime;
        image = ImageManager.getImage("/Image/ball.png");
    }

    //球变大
    public void bigger() {
        if (sizeTime <= 4)
            sizeTime *= 2;
        width *= sizeTime;
        height *= sizeTime;
        image = ImageManager.getImage("/Image/ball.png");
    }

    //球升级
    public void improve(BallType ballType) {
        reset();
        switch (ballType) {
            case RocketBall:
                rocket();
                break;
            case BoomBall:
                boom();
                break;
            case PunchBall:
                punch();
                break;
            case BigBall:
                bigger();
                break;
            case SmallBall:
                smaller();
                break;
            case CommonBall:
                reset();
                break;
        }
    }
}
