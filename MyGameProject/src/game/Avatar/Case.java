package game.Avatar;

import city.cs.engine.BoxShape;
import city.cs.engine.StaticBody;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

import game.Entity.*;
import game.Structure.*;
import game.World.*;

public class Case extends StaticBody {
    private World w;
    private float width;
    private float height;
    private float x;
    private float y;

    public Case(World w, float width, float height, float x, float y){
        super(w, new BoxShape(width, height));
        setPosition(new Vec2(x,y));

        this.w = w;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    //allows us to get the points we don't cross when we intersect with them
    public float getRightX(){ return x + width; }
    public float getLeftX(){ return x - width; }
    public float getTopY(){ return x + height; }
    public float getBottomY(){ return x - height; }
}
