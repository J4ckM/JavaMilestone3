package game.Structure;

import city.cs.engine.PolygonShape;
import city.cs.engine.StaticBody;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

import java.awt.*;

public class Slope extends StaticBody {
    private World w;
    private float width;
    private float height;
    private float x;
    private float y;

    public Slope(World w, float x, float y){
        super(w, new PolygonShape(
                2.26f,-1.68f, -1.76f,-1.68f, 0.23f,2.0f));
        setPosition(new Vec2(x,y));
        setFillColor(Color.orange);

        this.w = w;
        this.x = x;
        this.y = y;
    }
}
