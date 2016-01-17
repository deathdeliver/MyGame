import java.awt.*;

public class GameBack {

    public Color color;

    public GameBack(){
        color = new Color(170, 150, 150);
    }

    public void update(){

    }

    public void draw(Graphics2D g){
        g.setColor(color);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

    }

}
