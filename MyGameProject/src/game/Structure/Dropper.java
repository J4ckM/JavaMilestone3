package game.Structure;

import city.cs.engine.World;
import org.jbox2d.common.Vec2;

public class Dropper extends Platform {
    private World world;
    private float width;
    private float height;
    private float x;
    private float y;

    public Dropper(World w, float width, float height, float x, float y){
        super(w, width, height/2, x, y - height/4);
        setPosition(new Vec2(x,y - height/4));

        this.world = w;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;

        DropperInteract dropping = new DropperInteract(w,width, height/2, x, y + height/4, this);
        dropping.setFillColor(PlatformColour.getDarkgreen());

        DropperCollision dropcoll = new DropperCollision();
        dropping.addCollisionListener(dropcoll);
    }

    //allows us to get the points we don't cross when we intersect with them
    public float getRightX(){ return x + (width); }
    public float getLeftX(){ return x - (width); }
    public float getTopY(){ return x + height; }
    public float getBottomY(){ return x - height; }

    public World getWorld(){ return world; }
    public float getX(){ return x; }
    public float getY(){ return y; }
    public float getWidth(){ return width; }
    public float getHeight(){ return height; }
}
