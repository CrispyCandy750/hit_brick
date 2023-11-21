package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Menu extends JPanel {
    GameFrame frame;
    int difficulty = 0;
    int scene = 0;
    boolean exit;
    boolean OK;
    BufferedImage menubackground = ImageManager.getImage("/Image/menubackground.jpg");
    //以下是存储菜单需要的各种图片
    BufferedImage[] backgrounds = new BufferedImage[3];
    BufferedImage[] bricksImages = new BufferedImage[5];
    BufferedImage[] bagImages = new BufferedImage[9];
    BufferedImage[] ballTypeImage = new BufferedImage[4];
    String[] bagFunction = new String[9];
    String[] ballType = new String[4];

    public Menu(GameFrame frame) {
        this.frame = frame;
        //这些方法都是用来填充数组的
        fillBackgrounds();
        fillBagImage();
        fillBallImage();
        fillBrickImage();
        fillBagFunction();
        fillBallType();
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                //判定选择的难度，根据鼠标点击的位置
                if (y >= 77 && y <= 104) {
                    if (x >= 204 && x <= 260)
                        frame.difficulty = difficulty = 1;
                    else if (x >= 302 && x <= 365)
                        frame.difficulty = difficulty = 2;
                    else if (x >= 403 && x <= 460)
                        frame.difficulty = difficulty = 3;
                }
                //根据鼠标点击的位置判定选择的场景
                if (y >= 178 && y <= 352) {
                    if (x >= 99 && x <= 356)
                        frame.scene = scene = 1;
                    else if (x >= 448 && x <= 706)
                        frame.scene = scene = 2;
                    else if (x >= 799 && x <= 1056)
                        frame.scene = scene = 3;
                }
                //根据鼠标点击的位置判定是确定还是退出
                if (y >= 640 && y <= 678) {
                    if (x >= 440 && x <= 500) {
                        OK = true;
                        exit = false;
                    } else if (x >= 735 && x <= 803) {
                        OK = false;
                        exit = true;
                    }
                }
                repaint();
                //如果难度和场景都选择完了并且点击确定，则关闭菜单界面，打开游戏界面
                if (OK && difficulty != 0 && scene != 0) {
                    frame.setVisible(false);
                    frame.remove(Menu.this);
                    GamePanel panel = new GamePanel(frame, difficulty, scene);
                    frame.add(panel);
                    frame.setVisible(true);
                    panel.action();
                }
                //如果选择退出，则退出
                if (exit)
                    System.exit(0);

            }

        };
        addMouseListener(adapter);
        addMouseMotionListener(adapter);
    }

    //这些方法都是来填充数组的
    private void fillBackgrounds() {
        for (int i = 1; i <= 3; i++) {
            backgrounds[i - 1] = ImageManager.getImage("/Image/background" + i + ".jpg");
        }
    }

    private void fillBrickImage() {
        for (int i = 0; i < 5; i++)
            bricksImages[i] = ImageManager.getImage("/Image/brick" + (i + 1) + ".png");
    }

    private void fillBagImage() {
        bagImages[0] = ImageManager.getImage("/Image/ball.png");
        bagImages[1] = ImageManager.getImage("/Image/SmallBallBag.png");
        bagImages[2] = ImageManager.getImage("/Image/BigBallBag.png");
        bagImages[3] = ImageManager.getImage("/Image/DieBag.png");
        bagImages[4] = ImageManager.getImage("/Image/ShortenBag.png");
        bagImages[5] = ImageManager.getImage("/Image/LengthenBag.png");
        bagImages[6] = ImageManager.getImage("/Image/BoomBag.png");
        bagImages[7] = ImageManager.getImage("/Image/PunchBag.png");
        bagImages[8] = ImageManager.getImage("/Image/RocketBag.png");
    }

    private void fillBagFunction() {
        bagFunction[0] = "球普通";
        bagFunction[1] = "球变小";
        bagFunction[2] = "球变大";
        bagFunction[3] = "HP-1";
        bagFunction[4] = "板缩短";
        bagFunction[5] = "板延长";
        bagFunction[6] = "球爆炸";
        bagFunction[7] = "球穿刺";
        bagFunction[8] = "球加速";
    }

    private void fillBallImage() {
        ballTypeImage[0] = ImageManager.getImage("/Image/ball.png");
        ballTypeImage[1] = ImageManager.getImage("/Image/PunchBall.png");
        ballTypeImage[2] = ImageManager.getImage("/Image/BoomBall.png");
        ballTypeImage[3] = ImageManager.getImage("/Image/RocketBall.png");
    }

    private void fillBallType() {
        ballType[0] = "普通球";
        ballType[1] = "穿刺球";
        ballType[2] = "爆炸球";
        ballType[3] = "火箭球";
    }

    public void paint(Graphics g) {
        //这些是输出菜单中的内容
        g.drawImage(menubackground, 0, 0, 1280, 853, null);
        g.setColor(Color.white);
        g.setFont(new Font("楷体", Font.BOLD, 30));
        g.drawString("难度：", 100, 100);
        g.drawString("简单", 200, 100);
        g.drawString("一般", 300, 100);
        g.drawString("困难", 400, 100);
        if (difficulty != 0) {
            if (difficulty == 1) {
                g.setColor(Color.RED);
                g.drawString("简单", 200, 100);
                g.setColor(Color.white);
                g.drawString("一般", 300, 100);
                g.drawString("困难", 400, 100);
            } else if (difficulty == 2) {
                g.setColor(Color.RED);
                g.drawString("一般", 300, 100);
                g.setColor(Color.white);
                g.drawString("简单", 200, 100);
                g.drawString("困难", 400, 100);
            } else if (difficulty == 3) {
                g.setColor(Color.RED);
                g.drawString("困难", 400, 100);
                g.setColor(Color.white);
                g.drawString("一般", 300, 100);
                g.drawString("简单", 200, 100);
            }
        }

        g.drawString("场景：", 100, 150);
        for (int i = 0; i < 3; i++)
            g.drawImage(backgrounds[i], 100 + i * 350, 180, 1280 / 5, 853 / 5, null);
        if (scene != 0) {
            if (scene == 1) {
                g.setColor(Color.RED);
                g.drawString("想你的液", 155, 390);
                g.setColor(Color.white);
                g.drawString("洪荒之地", 510, 390);
                g.drawString("元气森林", 865, 390);
            } else if (scene == 2) {
                g.setColor(Color.RED);
                g.drawString("洪荒之地", 510, 390);
                g.setColor(Color.white);
                g.drawString("想你的液", 155, 390);
                g.drawString("元气森林", 865, 390);
            } else if (scene == 3) {
                g.setColor(Color.RED);
                g.drawString("元气森林", 865, 390);
                g.setColor(Color.white);
                g.drawString("想你的液", 155, 390);
                g.drawString("洪荒之地", 510, 390);
            }

        } else {
            g.setColor(Color.white);
            g.drawString("洪荒之地", 510, 390);
            g.drawString("想你的液", 155, 390);
            g.drawString("元气森林", 865, 390);
        }

        g.drawString("规则", 100, 435);
        g.drawString("敌人(血量)", 100, 480);
        for (int i = 0; i < 5; i++) {
            g.drawImage(bricksImages[i], 260 + i * 100, 450, 40, 40, null);
            g.drawString("x" + (i + 1), 310 + i * 100, 480);
        }
        g.drawString("道具包", 90, 535);
        for (int i = 0; i < 9; i++) {
            g.drawImage(bagImages[i], 190 + i * 120, 505, 40, 40, null);
            g.setFont(new Font("楷体", Font.PLAIN, 25));
            g.drawString(bagFunction[i], 230 + i * 120, 535);
        }
        g.setFont(new Font("楷体", Font.BOLD, 30));
        g.drawString("球类型", 100, 600);
        for (int i = 0; i < 4; i++) {
            g.drawImage(ballTypeImage[i], 210 + i * 140, 570, 40, 40, null);
            g.drawString(ballType[i], 250 + i * 140, 600);
        }
        if (OK && !exit) {
            g.setColor(Color.RED);
            g.drawString("确定", 440, 670);
            g.setColor(Color.white);
            g.drawString("退出", 740, 670);
        } else if (!OK && exit) {
            g.setColor(Color.RED);
            g.drawString("退出", 740, 670);
            g.setColor(Color.white);
            g.drawString("确定", 440, 670);
        } else {
            g.setColor(Color.white);
            g.drawString("退出", 740, 670);
            g.drawString("确定", 440, 670);
        }
        if (OK) {
            if (difficulty == 0 && scene == 0) {
                g.setColor(Color.RED);
                g.drawString("请选择场景和难度", 520, 730);
            } else if (difficulty == 0 && scene != 0) {
                g.setColor(Color.RED);
                g.drawString("请选择难度", 525, 730);
            } else if (scene == 0 && difficulty != 0) {
                g.setColor(Color.RED);
                g.drawString("请选择场景", 525, 730);
            }
        }
    }
}