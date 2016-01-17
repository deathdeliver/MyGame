import java.awt.*;

/**
 * Created by one on 15.10.2015.
 */
public class Wave {

    private int waveNumber;

    private long waveTimer;
    private long waveDelay;
    private long waveTimerDiff;
    private int waveMulti;
    private String waveText;


    public Wave(){
        waveNumber = 1;
        waveTimer = 0;
        waveDelay = 5000;
        waveTimerDiff = 0;
        waveMulti = 5;
        waveText = "Х В И Л Я" ;
    }

    public void createEnemies(){
        int enemieCount = waveNumber * waveMulti;
        if(waveNumber < 10){
            while (enemieCount > 0){
                int type = 1;
                int rank = 1;
                GamePanel.enemies.add(new Enemy(type, rank));
                enemieCount -= type * rank;
            }
            waveNumber++;

        }

    }

    public void update(){
        if(GamePanel.enemies.size() == 0 && waveTimer == 0){
            waveTimer = System.nanoTime();

        }
        if(waveTimer > 0){
            waveTimerDiff += (System.nanoTime() - waveTimer)/1000000;
            waveTimer = System.nanoTime();
        }
        if(waveTimerDiff > waveDelay){
            createEnemies();
            waveTimer = 0;
            waveTimerDiff = 0;
        }


    }

    public boolean showWave(){
        if(waveTimer != 0){
            return true;
        } else return false;
    }

    public void draw(Graphics2D g){
        double alpha = waveTimerDiff/(waveDelay / 180);
        alpha = 255 * Math.sin(Math.toRadians(alpha));
        if(alpha < 0){alpha = 0;}
        if(alpha > 255){alpha = 255;}
        g.setColor(new Color(255, 255, 255, (int) alpha));
        g.setFont(new Font("Consolas", Font.PLAIN, 20));
        g.drawString(waveText+"   "+waveNumber, 20, 20);

    }
}
