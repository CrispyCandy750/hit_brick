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

    //��������
    public void reset() {
        if (rocket || punch || boom)
            sizeTime = 1;
        rocket = punch = boom = false;
        image = ImageManager.getImage("/Image/ball.png");
        this.height = 50;
        this.width = 50;
        resetSpeed();
    }

    //�ж����Ƿ�ײ��ǽ�����ǰ�
    public int knockWall(int boardLeft, int boardRight) {
        int result = 0;
        if (x <= 0)
            result = 1;                    //ײ����� ����1
        else if (x + width >= 1280)
            result = 2;                    //ײ���ұ� ����2
        else if (y <= 0)
            result = 3;                    //ײ���ϱ� ����3
        else if (x + width >= boardLeft && x <= boardRight && y + height >= 763 && y <= 763 + 30)
            result = 4;                    //ײ���±� ����4
        return result;
    }

    //���ݵ�ǰ�ٶ������ٶ�
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

    //����򣬸��ݵ�ǰ�ٶȷ������
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

    //ը����
    public void boom() {
        boom = true;
        image = ImageManager.getImage("/Image/BoomBall.png");
    }

    //������
    public void punch() {
        punch = true;
        image = ImageManager.getImage("/Image/PunchBall.png");
    }

    //���С
    public void smaller() {
        if (sizeTime >= 0.25)
            sizeTime /= 2;
        width *= sizeTime;
        height *= sizeTime;
        image = ImageManager.getImage("/Image/ball.png");
    }

    //����
    public void bigger() {
        if (sizeTime <= 4)
            sizeTime *= 2;
        width *= sizeTime;
        height *= sizeTime;
        image = ImageManager.getImage("/Image/ball.png");
    }

    //������
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
