package game.Structure;

import city.cs.engine.BoxShape;
import city.cs.engine.StaticBody;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

public class Slider extends Platform {
    private boolean leftmove;
    private boolean upmove;
    private float x;
    private float y;
    private float width;
    private float height;
    //Platform and coord 1 equates to left if platform moves left/right, and equates to up otherwise
    Platform plat1; Platform plat2;
    float coord1; float coord2;
    boolean leftnotup;
    boolean betweenplatforms;

    public Slider(World w, float width, float height, float x, float y, boolean left, Platform plat1, Platform plat2){
        super(w, width, height, x, y);
        genericSetup(width, height, x, y, left);
        this.plat1 = plat1;
        this.plat2 = plat2;
        betweenplatforms = true;
        if(left) { coord1 = plat1.getRightX(); coord2 = plat2.getLeftX(); }
        else { coord1 = plat1.getBottomY(); coord2 = plat2.getTopY(); }
    }

    public Slider(World w, float width, float height, float x, float y, float coord1, float coord2, boolean left){
        super(w, width, height, x, y);
        genericSetup(width, height, x, y, left);
        this.coord1 = coord1;
        this.coord2 = coord2;
        betweenplatforms = false;
    }

    public void genericSetup(float width, float height, float x, float y, boolean b){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.leftnotup = b;
        if(b) { leftmove = true; } else { upmove = true; }
    }

    public void tracker(){
        changeDirection();
        if(leftnotup) { leftMove(); } else { upMove(); }
    }

    public void leftMove(){
        if(leftmove){ x = x - 0.1f; }
        else{ x = x + 0.1f; }
        setPosition(new Vec2(x,y));
    }
    public void upMove(){
        if(upmove){ y = y + 0.1f; }
        else{ y = y - 0.1f; }
        setPosition(new Vec2(x,y));
    }

    public float getRightBound(){ return x + width; }
    public float getLeftBound(){ return x - width; }
    public float getTopBound(){ return y + height; }
    public float getBottomBound(){ return y - height; }

    public void changeDirection(){
        if(leftnotup) { if ((coord1 > getLeftBound()) || (coord2 < getRightBound())) { leftmove = !leftmove; } }
        //else { if ((coord1 < getTopBound()) || (coord2 > getBottomBound())) { upmove = !upmove; }
        else { if ((coord1 < getTopBound()) || (coord2 > getBottomBound())) { upmove = !upmove; }
        }
    }
}
