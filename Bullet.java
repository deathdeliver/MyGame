import javafx.print.PageLayout;

import java.awt.*;

/**
 * Created by one on 03.10.2015.
 */
public class Bullet {
    private double x;
    private double y;
    private double dx;
    private double dy;
    private double distx;
    private double disty;
    private double dist;
    private int r;
    private Color color;
    private double speed;

    public Bullet(final double x, final double y, final int tx, final int ty ) {

        this.r = 2;
        this.speed = 12;
        distx = tx - x;
        disty = ty - y;
        dist = Math.sqrt(distx * distx + disty * disty);
        dx = (distx/dist * speed)+(Math.random());
        dy = (disty/dist * speed)-(Math.random());
        this.x = x + (dx);
        this.y = y + (dy);

        this.color = Color.yellow;
    }

    public double getX(){return x;}
    public double getY(){return y;}
    public int getR(){return r;}

    public void update() {
        y += dy;
        x += dx;
    }

    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fillOval((int) (x-GamePanel.player.getX()+5+GamePanel.WIDTH/2), (int) (y-GamePanel.player.getY()+5+GamePanel.HEIGHT/2), r*2, r*2 );
    }

    public boolean remove(){
        if (GamePanel.map.blocked((int)((x+dx)/GamePanel.map.SIZE),(int)((y+dy)/GamePanel.map.SIZE)))return true;
        return false;
    }
}