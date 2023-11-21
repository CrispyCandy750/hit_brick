package UI;

import java.util.Objects;

/**
 * 砖块类
 */
public class Brick extends FlyObject {
    // 砖块的HP
    int HP;

    public Brick(int x, int y, int index) {
        this.HP = index;
        this.x = x;
        this.y = y;
        image = ImageManager.getImage("/Image/brick" + index + ".png");
        this.height = 50;
        this.width = 50;
    }

    @Override
    //equals�ж�ש���λ���Ƿ�һ��
    public boolean equals(Object o) {
        Brick brick = (Brick) o;
        return (brick.x == this.x) && (brick.y == this.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    //�ж����ש���Ƿ����ҵ�
    public int hitBy(Ball ball) {
        boolean isBoomBall = ball.boom;
        int scope = isBoomBall ? 4 : 1;
        boolean hit = false;
        boolean boom = false;
        //�����ը����
        if (isBoomBall && x <= ball.x + ball.width * scope && x >= ball.x - width * scope && y <= ball.y + ball.height * scope && y >= ball.y - height * scope)
            boom = true;
            //���û��ը�����ж��Ƿ񱻻�����
        else if (x <= ball.x + ball.width && x >= ball.x - width && y <= ball.y + ball.height && y >= ball.y - height)
            hit = true;
        //������򵹣�����1��2����ʱ����ᷴ��
        if (hit) {
            if (x <= ball.x + ball.width && x + width >= ball.x)
                return 1;
            else if (y <= ball.y + ball.height && y + height >= ball.y)
                return 2;
        }//���û������������ը���ˣ�����3
        else if (boom)
            return 3;
        //�����û�б�������Ҳû�б�ը�����ͷ���0
        return 0;
    }

    public void setImage() {
        image = ImageManager.getImage("/Image/brick" + HP + ".png");
    }
}
