import javax.swing.*;

/**
 * Created by one on 25.09.2015.
 */
public class Main {
    public static void main (String[] args){

        GamePanel panel = new GamePanel();

        JFrame startFrame = new JFrame("Arkanoid+");

        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );

        startFrame.setContentPane(panel);

        startFrame.pack();

        startFrame.setLocationRelativeTo(null);

        startFrame.setVisible(true);

        panel.start();
//        panel.run();
//        panel.GameDraw();

    }
}
