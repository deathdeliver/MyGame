/**
 * Created by one on 27.09.2015.
 */
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import javax.imageio.ImageIO;

public class Player {
    private double x;
    private double y;
    private int r;
    int health;
    private static int fireRate;
    private long fireTime;
    private double speedx;
    private double speedy;
    private double accel;
    private double maxSpeed;
    private Color color1;
    private Color color2;
    public static boolean up;
    public static boolean down;
    public static boolean left;
    public static boolean right;
    public static boolean isFiring;
    public Animation animation;
    private BufferedImage [] moveSprites;

    public Player(){

        fireRate = 90000000;
        fireTime = 0;
        x = GamePanel.WIDTH/2;
        y = GamePanel.HEIGHT/2;
        r = 16;
        health = 5;
        speedx = 0;
        speedy = 0;
        accel = 0.5;
        maxSpeed = 3;
        color1 = Color.red;
        up = false;
        down = false;
        left = false;
        right = false;
        isFiring = false;
        animation = new Animation();

        try {

            moveSprites = new BufferedImage[6];
            BufferedImage image = ImageIO.read(new File("E:\\codding\\MyGame1\\src\\walkSprites.gif"));
            for(int i = 0; i < moveSprites.length; i++){
                moveSprites[i] = image.getSubimage(
                        i * r,
                        0,
                        r,
                        r
                );
            }
            System.out.println(image.getWidth());


        } catch (Exception e){e.printStackTrace();}


    }

    public double getX() {
        return x+(r/2);
    }

    public double getY() {
        return y+(r/2);
    }

    public double getR() {
        return r;
    }

    public void hit(){
        health--;
    }

    public void update(){


        if(down && speedy<maxSpeed){
            speedy += accel;
        }else if(!down && speedy > 0){
            speedy -=accel;
        };

        if(right && speedx<maxSpeed){
            speedx += accel;
        }else if(!right && speedx > 0){
            speedx -=accel;
        };

        if(up && speedy>-maxSpeed){
            speedy -= accel;
        }else if(!up && speedy < 0){
            speedy +=accel;
        };

        if(left && speedx>-maxSpeed){
            speedx -= accel;
        }else if(!left && speedx < 0){
            speedx +=accel;
        }


        if(validLocation(x + speedx, y + speedy)) {
            y += speedy;
            GamePanel.mouseY = (int)(GamePanel.mouseY + speedy);
            x += speedx;
            GamePanel.mouseX = (int)(GamePanel.mouseX + speedx);
//            System.out.println('-' + x +-+ y +"______"+ GamePanel.mouseX +-+ GamePanel.mouseY);
        }
        else if((y + speedy) >= 0 || (y + speedy) <= GamePanel.WIDTH || (x + speedx) >= 0 || (x + speedx) <= GamePanel.HEIGHT){
            speedy = 0;
            speedx = 0;
        }

        if(fireTime<System.nanoTime()) {
            if (isFiring) {
                GamePanel.bullets.add(new Bullet(x, y, GamePanel.mouseX, GamePanel.mouseY));
                fireTime = System.nanoTime() + fireRate;
            }
        }

        animation.setFrames(moveSprites);
        animation.setDelay(100);
        animation.update();

    }

    public void draw(Graphics2D g){

//
//        g.setColor(color1);
//        g.fillOval((GamePanel.WIDTH/2), GamePanel.HEIGHT/2, r, r);
//        g.setStroke(new BasicStroke(3));
//        g.setColor(color1.darker());
//        g.drawOval((GamePanel.WIDTH/2), GamePanel.HEIGHT/2,r, r);
//        g.setStroke(new BasicStroke(1));

        g.drawImage(animation.getImage(),(GamePanel.WIDTH/2), GamePanel.HEIGHT/2, null);


//        g.setColor(color1);
//        g.fillOval((int) (x), (int) (y), r, r);
//        g.setStroke(new BasicStroke(3));
//        g.setColor(color1.darker());
//        g.drawOval((int) (x), (int) (y),r, r);
//        g.setStroke(new BasicStroke(1));
    }

    public boolean validLocation (final double x, final double y){

        if(GamePanel.map.blocked((int)(x/Map.SIZE), (int)(y/Map.SIZE))){
            return false;
        }   else
        if(GamePanel.map.blocked((int)(x/Map.SIZE), (int)((y+r)/Map.SIZE))){
            return false;
        }   else
        if(GamePanel.map.blocked((int)((x+r)/Map.SIZE), (int)(y/Map.SIZE))){
            return false;
        }   else
        if(GamePanel.map.blocked((int)((x+r)/Map.SIZE), (int)((y+r)/Map.SIZE))){
            return false;
        }   else return true;

    }
}

