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
     * 在构造器中调用这个方法来填充砖块数组
     * @param difficulty 难度
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
            // 这里绘制砖块的时候随机赋予位置
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

        //创建鼠标配适器
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                //如果游戏没有结束，那么就会监听鼠标
                if (!gameOver) {
                    //同时让板跟着鼠标走
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
        //1.画背景、船、球、砖、道具包
        g.drawImage(background, 0, 0, background.getWidth(), background.getHeight(), null);
        g.drawImage(board.image, board.x, board.y, board.width, board.height, null);
        g.drawImage(ball.image, ball.x, ball.y, ball.width, ball.height, null);
        for (Brick brick : bricks)
            g.drawImage(brick.image, brick.x, brick.y, brick.width, brick.width, null);
        for (Bag bag : bags)
            g.drawImage(bag.image, bag.x, bag.y, bag.width, bag.height, null);
        //画分数
        g.setColor(Color.white);
        g.setFont(new Font("楷体", Font.BOLD, 40));
        g.drawString("分数：" + score, 0, 50);
        //画HP
        BufferedImage HP = ImageManager.getImage("/Image/HP.png");
        for (int i = 1; i <= board.HP; i++) {
            g.drawImage(HP, 1280 - 60 * i, 10, 50, 50, null);
        }
        //如果输了或者赢了的话
        if (gameOver || win) {
            g.setColor(Color.RED);
            g.setFont(new Font("楷体", Font.BOLD, 50));
            if (gameOver)//如果输了输出
                g.drawString("Game Over!", 500, 400);
            else//如果赢了输出
                g.drawString("恭喜你获胜!", 500, 400);
        }
    }

    //这个是创建一个线程，让游戏运行
    public void action() {
        GameRun gameRun = new GameRun(this);
        gameRun.start();
    }
}


//GameRun是游戏运行的线程
class GameRun extends Thread {
    GamePanel panel;

    public GameRun(GamePanel panel) {
        this.panel = panel;
    }

    //ballMove是改变球的坐标，从而让球移动
    public void ballMove() {
        panel.ball.moveUp(panel.ball.vy);
        panel.ball.moveRight(panel.ball.vx);
    }

    //ballBound先判断球是否撞墙，然后反弹
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

    //这个函数用来判断小球撞到砖块，小球的反弹，砖块的删除，以及道具包的产生
    public void hitBrickAndBagEnter() {
        Random random = new Random();
        int condition;
        for (Brick brick : panel.bricks) {
            //遍历每块砖，是否被小球砸到，condition记录情况
            condition = brick.hitBy(panel.ball);
            if (condition != 0) {
                //condition不为0，说明被砸到了
                panel.score++;
                brick.HP--;
                if (brick.HP == 0) {
                    //如果砖块的生命值为0，则随即生成包
                    int index = random.nextInt(70) + 1;
                    if (index <= 9) {
                        // 这里生成了index
                        panel.bags.add(Bag.getBag(index, brick.x, brick.y));
                    }
                    panel.bricks.remove(brick);
                } else
                    //如果砖块生命值不为0，则根据血量更换图片
                    brick.setImage();
                if (!panel.ball.punch) {
                    //如果球不是穿刺球，则会反弹
                    switch (condition) {
                        //球碰到了砖块的左右两边，则上下方向改变
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

    //这个控制道具包的移动以及判断船是否吃到包
    public void bagMoveAndEat() {
        BallType ballType;
        for (Bag bag : panel.bags) {
            //遍历所有的包，使其向下移动
            bag.moveDown(10);
            //每次移动判断是否被吃到
            if ((ballType = panel.board.eat(bag)) != null) {
                panel.bags.remove(bag);
                //如果被吃到，则删除包
                if (ballType != BallType.Ball) {
                    //不是Ball，说明这个包会对球操作，则改变球的类型
                    panel.ball.improve(ballType);
                }
            }
            if (bag.y >= 1280)
                //如果包越界了，也删除
                panel.bags.remove(bag);
        }
    }

    public void isOver() {
        //时刻判断游戏是否结束
        if (panel.board.HP <= 0 || panel.ball.y >= 1280)
            //如果板的血量≤0或者球越界，则失败
            panel.gameOver = true;
        //如果砖块为0，则成功
        if (panel.bricks.size() == 0)
            panel.win = true;
    }

    @Override
    public void run() {
        while (true) {
            //在线程中，只要游戏没有结束，就反复运行
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
