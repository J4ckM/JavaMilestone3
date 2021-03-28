package game.Avatar;

import city.cs.engine.*;

import org.jbox2d.common.Vec2;

import game.Entity.*;
import game.Structure.*;
import game.World.*;

public class Fruit extends DynamicBody {

    private static final Shape fruitShape = new CircleShape(1);
    private int fruit;

    //private static final BodyImage image = new BodyImage("data/fruit/apple-0.png", 2f);

    public Fruit(World w, int fruit) {
        super(w,fruitShape);
        this.fruit = fruit;
        BodyImage image = new BodyImage("data/fruit/apple-" + fruit + ".png", 2f);
        addImage(image);

    }

    public int getFruit(){ return fruit; }
    public void setFruit(int i){ fruit = i; }
}
