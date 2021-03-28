package game.Avatar;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

public class Coin extends DynamicBody {

    private static final Shape fruitShape = new CircleShape(0.5f);
    private boolean roll;

    //private static final BodyImage image = new BodyImage("data/fruit/apple-0.png", 2f);

    public Coin(World w, boolean b, float x, float y) {
        super(w,fruitShape);
        BodyImage image;
        image = new BodyImage("data/coin.png", 1f);
        addImage(image);
        roll = b;
        if(!roll) { setGravityScale(0); }
        setPosition(new Vec2(x, y));
    }

}
