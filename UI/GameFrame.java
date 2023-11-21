package UI;

import javax.swing.*;

public class GameFrame extends JFrame {
    int difficulty;
    int scene;
    public GameFrame(){
        setTitle("´ò×©¿é");
        setSize(1280,853);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String args[]){
        GameFrame frame = new GameFrame();
        Menu menu = new Menu(frame);
        frame.add(menu);
        frame.setVisible(true);
    }
}
