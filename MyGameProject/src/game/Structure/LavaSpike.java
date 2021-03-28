package game.Structure;

import city.cs.engine.PolygonShape;
import city.cs.engine.StaticBody;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

import java.awt.*;

public class LavaSpike extends StaticBody {
    private World w;
    private float width;
    private float height;
    private float x;
    private float y;
    private static Color color;

    public LavaSpike(World w, float x, float y){
        super(w, new PolygonShape(
                2.26f,-1.68f, -1.76f,-1.68f, 0.23f,2.0f));
        setPosition(new Vec2(x,y));
        //setFillColor(Color.orange);
        //setLineColor(Color.orange);

        color = new Color(255,102,25);
        setFillColor(color);
        setLineColor(color);

        this.w = w;
        this.x = x;
        this.y = y;

        LavaCollision lc = new LavaCollision();
        addCollisionListener(lc);
    }

    public static Color getColor(){ return color; }
}
