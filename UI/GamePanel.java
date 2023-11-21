package UI;

import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class GamePanel extends JPanel {
    public boolean win = false;
    int score = 0;
    boolean gameOver = false;
    BufferedImage background;
    Board board;
    Ball ball = new Ball(615, 723, "ball.png");
    List<Brick> bricks = new CopyOnWriteArrayList<Brick>();
    List<Bag> bags = new CopyOnWriteArrayList<Bag>();

    /**
     * �ڹ������е���������������ש������
     * @param difficulty �Ѷ�
     */
    public void fillBricks(int difficulty) {
        int num = 50;
        switch (difficulty) {
            case 1:
                num = 50;
                break;
            case 2:
                num = 100;
                break;
            case 3:
                num = 150;
                break;
        }
        Random random = new Random();
        for (int i = 0; i < num; ) {
            // �������ש���ʱ���������λ��
            Brick brick = new Brick(random.nextInt(25) * 50, (random.nextInt(10) + 1) * 50, random.nextInt(5) + 1);
            if (!bricks.contains(brick)) {
                bricks.add(brick);
                i++;
            }
        }
    }

    public GamePanel(GameFrame frame, int difficulty, int scene) {
        int HP = 1;
        switch (difficulty) {
            case 1:
                HP = 5;
                break;
            case 2:
                HP = 3;
                break;
            case 3:
                HP = 1;
                break;
        }
        board = new Board(0, 763, "board.png", HP);
        fillBricks(difficulty);
        background = ImageManager.getImage("/Image/background" + scene + ".jpg");
        setBackground(Color.BLACK);

        //�������������
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                //�����Ϸû�н�������ô�ͻ�������
                if (!gameOver) {
                    //ͬʱ�ð���������
                    board.moveLeftOrRight(mouseX - board.width / 2);
                    repaint();
                }
            }
        };

        addMouseListener(adapter);
        addMouseMotionListener(adapter);

    }

    public void paint(Graphics g) {
        super.paint(g);
        //1.��������������ש�����߰�
        g.drawImage(background, 0, 0, background.getWidth(), background.getHeight(), null);
        g.drawImage(board.image, board.x, board.y, board.width, board.height, null);
        g.drawImage(ball.image, ball.x, ball.y, ball.width, ball.height, null);
        for (Brick brick : bricks)
            g.drawImage(brick.image, brick.x, brick.y, brick.width, brick.width, null);
        for (Bag bag : bags)
            g.drawImage(bag.image, bag.x, bag.y, bag.width, bag.height, null);
        //������
        g.setColor(Color.white);
        g.setFont(new Font("����", Font.BOLD, 40));
        g.drawString("������" + score, 0, 50);
        //��HP
        BufferedImage HP = ImageManager.getImage("/Image/HP.png");
        for (int i = 1; i <= board.HP; i++) {
            g.drawImage(HP, 1280 - 60 * i, 10, 50, 50, null);
        }
        //������˻���Ӯ�˵Ļ�
        if (gameOver || win) {
            g.setColor(Color.RED);
            g.setFont(new Font("����", Font.BOLD, 50));
            if (gameOver)//����������
                g.drawString("Game Over!", 500, 400);
            else//���Ӯ�����
                g.drawString("��ϲ���ʤ!", 500, 400);
        }
    }

    //����Ǵ���һ���̣߳�����Ϸ����
    public void action() {
        GameRun gameRun = new GameRun(this);
        gameRun.start();
    }
}


//GameRun����Ϸ���е��߳�
class GameRun extends Thread {
    GamePanel panel;

    public GameRun(GamePanel panel) {
        this.panel = panel;
    }

    //ballMove�Ǹı�������꣬�Ӷ������ƶ�
    public void ballMove() {
        panel.ball.moveUp(panel.ball.vy);
        panel.ball.moveRight(panel.ball.vx);
    }

    //ballBound���ж����Ƿ�ײǽ��Ȼ�󷴵�
    public void ballBound() {
        int condition = panel.ball.knockWall(panel.board.x, panel.board.x + panel.board.width);
        if (condition != 0) {
            switch (condition) {
                case 1:
                    panel.ball.vx = -panel.ball.vx;
                    panel.ball.x = 0;
                    break;
                case 2:
                    panel.ball.vx = -panel.ball.vx;
                    panel.ball.x = 1280 - panel.ball.width;
                    break;
                case 3:
                    panel.ball.vy = -panel.ball.vy;
                    panel.ball.y = 0;
                    break;
                case 4:
                    panel.ball.vy = -panel.ball.vy;
                    break;
            }
        }
    }

    //������������ж�С��ײ��ש�飬С��ķ�����ש���ɾ�����Լ����߰��Ĳ���
    public void hitBrickAndBagEnter() {
        Random random = new Random();
        int condition;
        for (Brick brick : panel.bricks) {
            //����ÿ��ש���Ƿ�С���ҵ���condition��¼���
            condition = brick.hitBy(panel.ball);
            if (condition != 0) {
                //condition��Ϊ0��˵�����ҵ���
                panel.score++;
                brick.HP--;
                if (brick.HP == 0) {
                    //���ש�������ֵΪ0�����漴���ɰ�
                    int index = random.nextInt(70) + 1;
                    if (index <= 9) {
                        // ����������index
                        panel.bags.add(Bag.getBag(index, brick.x, brick.y));
                    }
                    panel.bricks.remove(brick);
                } else
                    //���ש������ֵ��Ϊ0�������Ѫ������ͼƬ
                    brick.setImage();
                if (!panel.ball.punch) {
                    //������Ǵ�������ᷴ��
                    switch (condition) {
                        //��������ש����������ߣ������·���ı�
                        case 1:
                            panel.ball.vx = -panel.ball.vx;
                            break;
                        case 2:
                            panel.ball.vy = -panel.ball.vy;
                            break;
                    }
                }
            }
        }
    }

    //������Ƶ��߰����ƶ��Լ��жϴ��Ƿ�Ե���
    public void bagMoveAndEat() {
        BallType ballType;
        for (Bag bag : panel.bags) {
            //�������еİ���ʹ�������ƶ�
            bag.moveDown(10);
            //ÿ���ƶ��ж��Ƿ񱻳Ե�
            if ((ballType = panel.board.eat(bag)) != null) {
                panel.bags.remove(bag);
                //������Ե�����ɾ����
                if (ballType != BallType.Ball) {
                    //����Ball��˵�������������������ı��������
                    panel.ball.improve(ballType);
                }
            }
            if (bag.y >= 1280)
                //�����Խ���ˣ�Ҳɾ��
                panel.bags.remove(bag);
        }
    }

    public void isOver() {
        //ʱ���ж���Ϸ�Ƿ����
        if (panel.board.HP <= 0 || panel.ball.y >= 1280)
            //������Ѫ����0������Խ�磬��ʧ��
            panel.gameOver = true;
        //���ש��Ϊ0����ɹ�
        if (panel.bricks.size() == 0)
            panel.win = true;
    }

    @Override
    public void run() {
        while (true) {
            //���߳��У�ֻҪ��Ϸû�н������ͷ�������
            if (!panel.gameOver && !panel.win) {
                ballMove();
                ballBound();
                hitBrickAndBagEnter();
                bagMoveAndEat();
                isOver();
                try {
                    sleep(40);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                panel.repaint();
            } else
                panel.repaint();
        }
    }
}
