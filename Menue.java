import sun.security.util.Length;

import java.awt.*;

/**
 * Created by one on 17.10.2015.
 */
public class Menue {

    private int buttonWidth;
    private int buttonHaight;
    private Color color1;
    private String s;
    private int transp;


    public Menue(){
        transp = 0;
        buttonHaight = 60;
        buttonWidth = 200;
        color1 = Color.lightGray;
        s = "Play";
    }

    public void update(){
        if(GamePanel.mouseX > GamePanel.WIDTH/2 - buttonWidth/2 &&
                GamePanel.mouseX < GamePanel.WIDTH/2 + buttonWidth/2 &&
                GamePanel.mouseY > GamePanel.HEIGHT/2 - buttonHaight/2 &&
                GamePanel.mouseY < GamePanel.HEIGHT/2 + buttonHaight/2){
            transp = 80;
            if(GamePanel.leftMouse){
                GamePanel.state = GamePanel.STATES.PLAY;
            }
        } else {
            transp = 0;
        }
    }

    public void draw(Graphics2D g){
        g.setColor(color1);
        g.setStroke(new BasicStroke(3));
        g.drawRect(GamePanel.WIDTH / 2 - buttonWidth / 2, GamePanel.HEIGHT / 2 - buttonHaight / 2, buttonWidth, buttonHaight);
        g.setColor(new Color(255, 255, 255, transp));
        g.fillRect(GamePanel.WIDTH / 2 - buttonWidth / 2, GamePanel.HEIGHT / 2 - buttonHaight / 2, buttonWidth, buttonHaight);
        g.setColor(color1);
        g.setStroke(new BasicStroke(1));
        g.setFont(new Font("Consolas", Font.BOLD, 40));
        long length = (long) g.getFontMetrics().getStringBounds(s, g).getWidth();
        g.drawString(s,GamePanel.WIDTH/2 - length/2, GamePanel.HEIGHT/2 + buttonHaight/4);

    }
}
