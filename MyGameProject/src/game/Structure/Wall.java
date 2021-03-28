package game.Structure;

import city.cs.engine.BoxShape;
import city.cs.engine.StaticBody;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

public class Wall extends StaticBody {

    public Wall(World w, float width, float height, float x, float y){
        super(w, new BoxShape(width, height));
        setPosition(new Vec2(x,y));
    }
}
