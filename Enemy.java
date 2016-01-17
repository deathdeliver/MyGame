import java.awt.*;

/**
 * Created by one on 04.10.2015.
 */
public class Enemy {
    private double x;
    private double y;
    private double speed;
    private double dx;
    private double dy;
    private double health;
    private int r;
    private int type;
    private int rank;
    private Color color;
    private static int fireRate;
    private long fireTime;

    public Enemy(int type,int rank) {
        this.type = type;
        this.rank = rank;

        switch (type){
            case (1): color = new Color(22, 59,100);
                switch (rank){
                case (1):
                    x = Math.abs((Math.random() * (GamePanel.WIDTH-44))+22);
                    y = 40;
                    fireRate = 190000000;
                    fireTime = 0;
                    speed = 4;
                    health = 1;
                    r = 5;
                    double angle = Math.toRadians(Math.random() * 360);
                    dx = Math.sin(angle) * speed;
                    dy = Math.cos(angle) * speed;
            }
        }
    }

    public double getX(){return x;}
    public double getY(){return y;}
    public int getR(){return r;}

    public boolean remove(){

        return(health <= 0);

    }

    public void hit(){
        health --;
    }

    public void update(){

        if(!validLocation(x+dx, y+dy) && validLocation(x-dx, y+dy)) {
            dx = -dx;
        }
        if(!validLocation(x+dx, y+dy) && validLocation(x+dx, y-dy)) {
            dy = -dy;
        }
        if(!validLocation(x+dx, y+dy) && validLocation(x-dx, y-dy)) {
            dy = -dy;
            dx = -dx;
        }

        if(validLocation(x+dx, y+dy)){
            x += dx;
            y += dy;
        }

//        if(fireTime<System.nanoTime()) {
//
//            GamePanel.bullets.add(new Bullet(x, y, (int)GamePanel.player.getX(), (int)GamePanel.player.getY()));
//            fireTime = System.nanoTime() + fireRate;
//
//        }




    }

    public void draw(Graphics2D g){

        g.setColor(color);
        g.fillOval((int)(x-GamePanel.player.getX()+5+GamePanel.WIDTH/2), (int)(y-GamePanel.player.getY()+5+GamePanel.HEIGHT/2), 2*r, 2*r);
        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(2));
        g.drawOval((int)(x-GamePanel.player.getX()+5+GamePanel.WIDTH/2), (int)(y-GamePanel.player.getY()+5+GamePanel.HEIGHT/2), 2*r, 2*r);
        g.setStroke(new BasicStroke(1));
//        g.setColor(color);
//        g.fillOval((int)(x), (int)(y), 2*r, 2*r);
//        g.setStroke(new BasicStroke(3));
//        g.setColor(color.darker());
//        g.drawOval((int)(x), (int)(y), 2*r, 2*r);
//        g.setStroke(new BasicStroke(1));

    }

    public boolean validLocation (final double x, final double y){

        if(GamePanel.map.blocked((int)(x/Map.SIZE), (int)(y/Map.SIZE))){
            return false;
        }
        if(GamePanel.map.blocked((int)(x/Map.SIZE), (int)((y+2*r)/Map.SIZE))){
            return false;
        }
        if(GamePanel.map.blocked((int)((x+2*r)/Map.SIZE), (int)(y/Map.SIZE))){
            return false;
        }
        if(GamePanel.map.blocked((int)((x+2*r)/Map.SIZE), (int)((y+2*r)/Map.SIZE))){
            return false;
        }

        return true;

    }


}
