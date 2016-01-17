import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.Book;
import java.util.ArrayList;

/**
 * Created by one on 25.09.2015.
 */
public class GamePanel extends JPanel implements Runnable {

    static public int WIDTH = 600;
    static public int HEIGHT = 600;

    private int FPS;
    private long timerFPS;
    private double millisFPS;
    private int sleepTime;

    private Thread thread;
    private BufferedImage image;
    private Graphics2D g;
    private Graphics2D g2;

    public static enum STATES{
        MENUE,
        PLAY
    }
    public static STATES state = STATES.MENUE;

    public static int mouseX;
    public static int mouseY;
    public static boolean leftMouse;
    public static GameBack background;
    public static Player player;
    public static ArrayList<Bullet> bullets;
    public static ArrayList<Enemy> enemies;
    public static Wave wave;
    public static Menue menue;
    public static Map map;




    public GamePanel(){
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
        addKeyListener(new Listeners());
        addMouseListener(new Listeners());
        addMouseMotionListener(new Listeners());
    }

    public void start () {
        thread = new Thread(this);
        thread.start();
    }

    public void run(){
        FPS = 30;
        millisFPS = 1000/FPS;
        sleepTime = 0;
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB );
        g = (Graphics2D) image.getGraphics();
        g2 = (Graphics2D) image.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        background = new GameBack();
        player = new Player();
        bullets = new ArrayList<Bullet>();
        enemies = new ArrayList<Enemy>();
        wave = new Wave();
        menue = new Menue();
        map = new Map("E:\\codding\\MyGame1\\src\\map1.txt", 16);
        map.loadTiles("E:\\codding\\MyGame1\\src\\mytiles.gif");
//        map = new Map();
        leftMouse = false;
//
        Toolkit kit =  Toolkit.getDefaultToolkit();
        BufferedImage buffered = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g3 = (Graphics2D) buffered.getGraphics();
        g3.setColor(new Color(150, 3, 0));
        g3.drawLine(2, 0, 2, 4);
        g3.drawLine(0, 2, 4, 2);
        Cursor myCursor = kit.createCustomCursor(buffered, new Point(3,3), "myCursor");
        g3.dispose();


        while (true) {/*TODO STATES*/

            this.setCursor(myCursor);
            timerFPS = System.nanoTime();
            timerFPS = (System.nanoTime() - timerFPS)/1000000;
            if(millisFPS > timerFPS){
                sleepTime = (int) (millisFPS - timerFPS);
            } else sleepTime = 1;

            if(state.equals(STATES.MENUE)){
                background.update();
                background.draw(g);
                menue.update();
                menue.draw(g);
                gameDraw();

            }

            if(state.equals(STATES.PLAY)){
                gameUpdate();
                gameRender();
                gameDraw();
            }





             try    {

                thread.sleep(sleepTime);
             } catch (InterruptedException e) {e.printStackTrace();
                }
            timerFPS = 0;
            sleepTime = 1;
        }
    }

    public void gameUpdate (){
        background.update();
        player.update();

        for(int i=0; i < bullets.size(); i++){
            bullets.get(i).update();
            if(bullets.get(i).remove()){
                bullets.remove(i);
                i--;
            };
        }

        for(int i=0; i < enemies.size(); i++){
            enemies.get(i).update();
        }

        // Bullet Collide
        for(int i=0;i < enemies.size(); i++){
            Enemy e = enemies.get(i);
            double ex = e.getX();
            double ey = e.getY();
            for(int j=0; j < bullets.size(); j++){
                Bullet b = bullets.get(j);
                double bx = b.getX();
                double by = b.getY();
                double dist = Math.sqrt(((ex-bx)*(ex-bx))+((ey-by)*(ey-by)));
                if((int)dist < (e.getR()+b.getR())){
                    e.hit();
                    bullets.remove(j);
                    break;
                }

            }

            if(e.remove()){
                enemies.remove(i);
                i--;
            }
        }

        //Player collide

        for(int i=0; i<enemies.size(); i++){
            double ex = enemies.get(i).getX();
            double ey = enemies.get(i).getY();
            double px = player.getX();
            double py = player.getY();
            double dist = Math.sqrt(((ex-px)*(ex-px))+((ey-py)*(ey-py)));
            if((int)dist < (player.getR()+enemies.get(i).getR())){
                enemies.get(i).hit();
                player.hit();
            }
            if(enemies.get(i).remove()){
                enemies.remove(i);
                i--;
            }
        }

        wave.update();

    }


    public void gameRender (){

//                LIGHT MAP
//
//        g2.fillRect(0,0,WIDTH,HEIGHT);
//        g2.setComposite(AlphaComposite.DstOut);
//        g2.fillOval(WIDTH/2-14, HEIGHT/2-14, 45, 45);
//        g2.dispose();


        background.draw(g);
        map.draw(g);
        player.draw(g);

        for(int i=0; i < bullets.size(); i++){
            bullets.get(i).draw(g);
        }

        for(int i=0; i < enemies.size(); i++){
            enemies.get(i).draw(g);
        }

        if(wave.showWave()){
            wave.draw(g);
        }
        g.setColor(new Color(130, 4, 6));
        g.fillOval((int) (mouseX - GamePanel.player.getX() + 5 + GamePanel.WIDTH / 2), (int) (mouseY - GamePanel.player.getY() + 5 + GamePanel.HEIGHT / 2), 3, 3);
        g.setColor(Color.BLACK);
//
//        g2.setColor(new Color(0, 0, 0, 230));
//        g2.fillRect(0, 0, WIDTH, HEIGHT);
//        g2.setComposite(AlphaComposite.DstOut);
//        g2.setColor(new Color(255, 255, 255, 100));
//        g2.fillOval(HEIGHT/2 - 30, WIDTH/2 - 30, 60, 60 );





    }

    public void gameDraw () {
        Graphics g2 = this.getGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();

    }

}
