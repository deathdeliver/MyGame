import com.sun.org.apache.xml.internal.security.keys.storage.StorageResolver;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Array;


/**
 * Created by one on 21.10.2015.
 */
public class Map {

    private static final int CLEAR = 0;
    private static final int BLOCKED = 1;
    private static int WIDTH;
    private static int HEIGHT;
    protected static int  SIZE;
    private static int[][] data;
    private BufferedImage tileset;
    private Tileset [][] tiles;

    public Map() {

        WIDTH = 60;
        HEIGHT = 60;
        SIZE = 20;
        data = new int[WIDTH][HEIGHT];

//        for(int i = 9; i < WIDTH-10; i++){
//            for(int j = 9; j < HEIGHT-10; j++){
//                data[i][j] =BLOCKED;
//            }
//        }

        for(int i = 1; i < WIDTH-1; i++){
            for(int j = 3; j < HEIGHT-1; j++){
                data[i][j] =(int) Math.round(Math.random()-0.4);
            }
        }

        for(int i = 0; i < WIDTH; i++){
            data[i][HEIGHT-1] =BLOCKED;
            data[i][0] =BLOCKED;
        }
        for(int j = 0; j < HEIGHT; j++){
            data[0][j] = BLOCKED;
            data[WIDTH-1][j] = BLOCKED;
        }
        for(int i = 1; i < WIDTH-1; i++){
            data[i][1] = CLEAR;
        }
        for(int i = 1; i < WIDTH-1; i++){
            data[i][2] = CLEAR;
        }
        data[15][15] = CLEAR;
//        data[14][1] = BLOCKED;
//        data[15][1] = BLOCKED;
//        data[14][2] = BLOCKED;
//        data[15][2] = BLOCKED;

    }
    public Map(String s, int tileSize) throws RuntimeException  {

        SIZE = tileSize;
        try {

            BufferedReader br = new BufferedReader(new FileReader(s));
            String line;
            try{
                HEIGHT = Integer.parseInt(br.readLine());
                WIDTH = Integer.parseInt(br.readLine());
                data = new int[HEIGHT][WIDTH];
            String de = "\\s+";
            for(int row = 0; row < HEIGHT; row++){
                line = br.readLine();
                String [] tokens = line.split(de);

                for(int col = 0; col < WIDTH; col++){
                    data[col][row] = Integer.parseInt(tokens[col]);
                }

            }


            } catch (Exception e){}
            br.close();

        }catch (IOException e){
            throw new RuntimeException(e);
        }

        for(int i = 1; i < WIDTH-1; i++){
            for(int j = 1; j < HEIGHT-1; j++){


                    if(data[i][j] == BLOCKED){
                    combine(i,j,2,0,0,0,0);
                    combine(i,j,3,1,0,0,0);
                    combine(i,j,4,0,1,0,0);
                    combine(i,j,5,1,1,0,0);
                    combine(i,j,6,0,0,1,0);
                    combine(i,j,7,1,0,1,0);
                    combine(i,j,8,0,1,1,0);
                    combine(i,j,9,1,1,1,0);
                    combine(i,j,10,0,0,0,1);
                    combine(i,j,11,1,0,0,1);
                    combine(i,j,12,0,1,0,1);
                    combine(i,j,13,1,1,0,1);
                    combine(i,j,14,0,0,1,1);
                    combine(i,j,15,1,0,1,1);
                    combine(i,j,16,0,1,1,1);
                    combine(i,j,17,1,1,1,1);

                 };
            }
        };

//        COMAND LINE OUT

//        for(int i = 1; i < WIDTH-1; i++){
//            for(int j = 1; j < HEIGHT-1; j++) {
//                System.out.print(data[i][j]+" ");
//
//            }
//            System.out.println(" ");
//        }



    }

    private void combine(int i, int j, int tile,int b1, int b2, int b3, int b4){
        boolean ifTrue = true;
        if(ifTrue && b1 == 1){
            if(blocked(i,j-1)){
            }
            else ifTrue = false;

        }
        else if(ifTrue && b1 == 0){
            if(!blocked(i, j-1)){
            }
            else ifTrue = false;
        }

        if(ifTrue && b2 == 1){
            if(blocked(i-1,j)){
            }
            else ifTrue = false;

        }
        else if(ifTrue && b2 == 0){
            if(!blocked(i-1, j)){
            }
            else ifTrue = false;
        }

        if(ifTrue && b3 == 1){
            if(blocked(i,j+1)){
            }
            else ifTrue = false;

        }
        else if(ifTrue && b3 == 0){
            if(!blocked(i, j+1)){
            }
            else ifTrue = false;
        }

        if(ifTrue && b4 == 1){
            if(blocked(i+1,j)){
            }
            else ifTrue = false;

        }
        else if(ifTrue && b4 == 0){
            if(!blocked(i+1, j)){
            }
            else ifTrue = false;
        }

        if(ifTrue)data[i][j]=tile;
    }

    public boolean blocked(final int i, final int j){
        return(data[i][j] != CLEAR);

    }

    public Boolean isBlocked(int row, int col){
        int rc = data[row][col];
        int r = rc / tiles[0].length;
        int c = rc % tiles[0].length;
        return tiles[r][c].isBlocked();
    }

    public void loadTiles(String s){

        try{
            tileset = ImageIO.read(new File(s));
            int numTilesAcross = (tileset.getWidth()+1)/(SIZE +1);
            tiles = new Tileset[2][numTilesAcross];
            BufferedImage subimage;
            for(int col = 0; col < numTilesAcross; col++){
                subimage = tileset.getSubimage(
                        col * SIZE + col+1,
                        0+1,
                        SIZE,
                        SIZE
                );
                tiles[0][col] = new Tileset(subimage, false);
                subimage = tileset.getSubimage(
                        col * SIZE + col+1,
                        SIZE + 1,
                        SIZE,
                        SIZE
                );
                tiles[1][col] = new Tileset(subimage, true);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public void drawRect(Graphics2D g){

        for(int i = 0; i < WIDTH; i++){
            for(int j = 0; j < HEIGHT; j++){

                g.setColor(Color.BLACK);
                if(data[i][j] == BLOCKED){
                    g.setColor(new Color(170,150,150));
                }
                g.fillRect((int)((i*SIZE)-GamePanel.player.getX()+5+GamePanel.WIDTH/2),
                        (int)((j*SIZE)-GamePanel.player.getY()+5+GamePanel.HEIGHT/2),
                        SIZE,
                        SIZE);
            }
        }

    }

    public void draw(Graphics2D g){

        for(int i = 1; i < WIDTH-1; i++){
            for(int j = 1; j < HEIGHT-1; j++){

                int rc = data[i][j];
                if(rc > 0){
                    int r = rc / tiles[0].length;
                    int c = rc % tiles[0].length;

                    g.drawImage(
                            tiles[r][c].getImage(),
                            (int)((i*SIZE)-GamePanel.player.getX()+5+GamePanel.WIDTH/2),
                            (int)((j*SIZE)-GamePanel.player.getY()+5+GamePanel.HEIGHT/2),
                            null
                    );
                }

            }
        }

    }
}
