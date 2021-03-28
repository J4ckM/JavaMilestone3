package game.Structure;

import city.cs.engine.World;
import org.jbox2d.common.Vec2;

import java.awt.*;

public class PlatformColour extends Platform {
    private World w;
    private float width;
    private float height;
    private float x;
    private float y;
    private int color;
    private boolean good;
    private static Color lightblue = new Color(54,154,255);
    private static Color darkgreen = new Color(21,128,0);
    private Color lightyellow = new Color(255,255,128);
    private Color darkyellow = new Color(230,191,0);

    public PlatformColour(World w, float width, float height, float x, float y, int color, boolean good){
        //super(w, new BoxShape(width, height));
        super(w, width, height, x, y);
        setPosition(new Vec2(x,y));

        this.color = color;
        this.good = good;
        color();
    }

    public void color(){
        if(color == 1){
            if(good){ setFillColor(Color.pink); }
            else{ setFillColor(Color.red); }
        }
        if(color == 2){
            if(good){ setFillColor(lightblue); }
            else{ setFillColor(Color.blue); }
        }
        if(color == 3){
            if(good){ setFillColor(Color.green); }
            else{ setFillColor(darkgreen); }
        }
        if(color == 4){
            if(good){ setFillColor(lightyellow); }
            else{ setFillColor(darkyellow); }
        }
    }

    public int getColour(){ return color; }
    public boolean getGood(){ return good; }

    public static Color getDarkgreen() { return darkgreen; }
    public static Color getLightblue() { return lightblue; }
}
