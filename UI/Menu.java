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
    //�����Ǵ洢�˵���Ҫ�ĸ���ͼƬ
    BufferedImage[] backgrounds = new BufferedImage[3];
    BufferedImage[] bricksImages = new BufferedImage[5];
    BufferedImage[] bagImages = new BufferedImage[9];
    BufferedImage[] ballTypeImage = new BufferedImage[4];
    String[] bagFunction = new String[9];
    String[] ballType = new String[4];

    public Menu(GameFrame frame) {
        this.frame = frame;
        //��Щ��������������������
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
                //�ж�ѡ����Ѷȣ������������λ��
                if (y >= 77 && y <= 104) {
                    if (x >= 204 && x <= 260)
                        frame.difficulty = difficulty = 1;
                    else if (x >= 302 && x <= 365)
                        frame.difficulty = difficulty = 2;
                    else if (x >= 403 && x <= 460)
                        frame.difficulty = difficulty = 3;
                }
                //�����������λ���ж�ѡ��ĳ���
                if (y >= 178 && y <= 352) {
                    if (x >= 99 && x <= 356)
                        frame.scene = scene = 1;
                    else if (x >= 448 && x <= 706)
                        frame.scene = scene = 2;
                    else if (x >= 799 && x <= 1056)
                        frame.scene = scene = 3;
                }
                //�����������λ���ж���ȷ�������˳�
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
                //����ѶȺͳ�����ѡ�����˲��ҵ��ȷ������رղ˵����棬����Ϸ����
                if (OK && difficulty != 0 && scene != 0) {
                    frame.setVisible(false);
                    frame.remove(Menu.this);
                    GamePanel panel = new GamePanel(frame, difficulty, scene);
                    frame.add(panel);
                    frame.setVisible(true);
                    panel.action();
                }
                //���ѡ���˳������˳�
                if (exit)
                    System.exit(0);

            }

        };
        addMouseListener(adapter);
        addMouseMotionListener(adapter);
    }

    //��Щ������������������
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
        bagFunction[0] = "����ͨ";
        bagFunction[1] = "���С";
        bagFunction[2] = "����";
        bagFunction[3] = "HP-1";
        bagFunction[4] = "������";
        bagFunction[5] = "���ӳ�";
        bagFunction[6] = "��ը";
        bagFunction[7] = "�򴩴�";
        bagFunction[8] = "�����";
    }

    private void fillBallImage() {
        ballTypeImage[0] = ImageManager.getImage("/Image/ball.png");
        ballTypeImage[1] = ImageManager.getImage("/Image/PunchBall.png");
        ballTypeImage[2] = ImageManager.getImage("/Image/BoomBall.png");
        ballTypeImage[3] = ImageManager.getImage("/Image/RocketBall.png");
    }

    private void fillBallType() {
        ballType[0] = "��ͨ��";
        ballType[1] = "������";
        ballType[2] = "��ը��";
        ballType[3] = "�����";
    }

    public void paint(Graphics g) {
        //��Щ������˵��е�����
        g.drawImage(menubackground, 0, 0, 1280, 853, null);
        g.setColor(Color.white);
        g.setFont(new Font("����", Font.BOLD, 30));
        g.drawString("�Ѷȣ�", 100, 100);
        g.drawString("��", 200, 100);
        g.drawString("һ��", 300, 100);
        g.drawString("����", 400, 100);
        if (difficulty != 0) {
            if (difficulty == 1) {
                g.setColor(Color.RED);
                g.drawString("��", 200, 100);
                g.setColor(Color.white);
                g.drawString("һ��", 300, 100);
                g.drawString("����", 400, 100);
            } else if (difficulty == 2) {
                g.setColor(Color.RED);
                g.drawString("һ��", 300, 100);
                g.setColor(Color.white);
                g.drawString("��", 200, 100);
                g.drawString("����", 400, 100);
            } else if (difficulty == 3) {
                g.setColor(Color.RED);
                g.drawString("����", 400, 100);
                g.setColor(Color.white);
                g.drawString("һ��", 300, 100);
                g.drawString("��", 200, 100);
            }
        }

        g.drawString("������", 100, 150);
        for (int i = 0; i < 3; i++)
            g.drawImage(backgrounds[i], 100 + i * 350, 180, 1280 / 5, 853 / 5, null);
        if (scene != 0) {
            if (scene == 1) {
                g.setColor(Color.RED);
                g.drawString("�����Һ", 155, 390);
                g.setColor(Color.white);
                g.drawString("���֮��", 510, 390);
                g.drawString("Ԫ��ɭ��", 865, 390);
            } else if (scene == 2) {
                g.setColor(Color.RED);
                g.drawString("���֮��", 510, 390);
                g.setColor(Color.white);
                g.drawString("�����Һ", 155, 390);
                g.drawString("Ԫ��ɭ��", 865, 390);
            } else if (scene == 3) {
                g.setColor(Color.RED);
                g.drawString("Ԫ��ɭ��", 865, 390);
                g.setColor(Color.white);
                g.drawString("�����Һ", 155, 390);
                g.drawString("���֮��", 510, 390);
            }

        } else {
            g.setColor(Color.white);
            g.drawString("���֮��", 510, 390);
            g.drawString("�����Һ", 155, 390);
            g.drawString("Ԫ��ɭ��", 865, 390);
        }

        g.drawString("����", 100, 435);
        g.drawString("����(Ѫ��)", 100, 480);
        for (int i = 0; i < 5; i++) {
            g.drawImage(bricksImages[i], 260 + i * 100, 450, 40, 40, null);
            g.drawString("x" + (i + 1), 310 + i * 100, 480);
        }
        g.drawString("���߰�", 90, 535);
        for (int i = 0; i < 9; i++) {
            g.drawImage(bagImages[i], 190 + i * 120, 505, 40, 40, null);
            g.setFont(new Font("����", Font.PLAIN, 25));
            g.drawString(bagFunction[i], 230 + i * 120, 535);
        }
        g.setFont(new Font("����", Font.BOLD, 30));
        g.drawString("������", 100, 600);
        for (int i = 0; i < 4; i++) {
            g.drawImage(ballTypeImage[i], 210 + i * 140, 570, 40, 40, null);
            g.drawString(ballType[i], 250 + i * 140, 600);
        }
        if (OK && !exit) {
            g.setColor(Color.RED);
            g.drawString("ȷ��", 440, 670);
            g.setColor(Color.white);
            g.drawString("�˳�", 740, 670);
        } else if (!OK && exit) {
            g.setColor(Color.RED);
            g.drawString("�˳�", 740, 670);
            g.setColor(Color.white);
            g.drawString("ȷ��", 440, 670);
        } else {
            g.setColor(Color.white);
            g.drawString("�˳�", 740, 670);
            g.drawString("ȷ��", 440, 670);
        }
        if (OK) {
            if (difficulty == 0 && scene == 0) {
                g.setColor(Color.RED);
                g.drawString("��ѡ�񳡾����Ѷ�", 520, 730);
            } else if (difficulty == 0 && scene != 0) {
                g.setColor(Color.RED);
                g.drawString("��ѡ���Ѷ�", 525, 730);
            } else if (scene == 0 && difficulty != 0) {
                g.setColor(Color.RED);
                g.drawString("��ѡ�񳡾�", 525, 730);
            }
        }
    }
}