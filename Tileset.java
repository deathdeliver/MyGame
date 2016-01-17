/**
 * Created by one on 01.12.2015.
 */
import java.awt.image.*;

public class Tileset {

    private BufferedImage image;
    private boolean blocked;

    public Tileset(BufferedImage image, boolean blocked){

        this.image = image;
        this.blocked = blocked;

    }

    public BufferedImage getImage (){
        return image;
    }

    public boolean isBlocked (){
        return blocked;
    }

}
