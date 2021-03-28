package game.Structure;

import city.cs.engine.BoxShape;
import city.cs.engine.StaticBody;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

public class DropperInteract extends Platform {
    private World w;
    private float width;
    private float height;
    private float x;
    private float y;
    private Dropper drop;

    public DropperInteract(World w, float width, float height, float x, float y, Dropper drop){
        super(w, width, height, x, y);
        setPosition(new Vec2(x,y));

        this.w = w;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.drop = drop;
    }

    //allows us to get the points we don't cross when we intersect with them
    public float getRightX(){ return x + width; }
    public float getLeftX(){ return x - width; }
    public float getTopY(){ return x + height; }
    public float getBottomY(){ return x - height; }

    public World getWorld(){ return w; }
    public float getX(){ return x; }
    public float getY(){ return y; }
    public float getWidth(){ return width; }
    public float getHeight(){ return height; }
    public Dropper getDropper(){ return drop; }
}
