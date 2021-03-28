package game.Structure;

import city.cs.engine.BoxShape;
import city.cs.engine.StaticBody;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

import java.awt.*;

public class PlatformYellow extends Platform {

    public PlatformYellow(World w, float width, float height, float x, float y){
        //super(w, new BoxShape(width, height));
        super(w, width, height, x, y);
        setPosition(new Vec2(x,y));
        setFillColor(Color.yellow);
    }
}
