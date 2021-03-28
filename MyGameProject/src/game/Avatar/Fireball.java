package game.Avatar;

import city.cs.engine.*;

import game.Entity.*;
import game.Structure.*;
import game.World.*;

public class Fireball extends DynamicBody {
    private static final Shape fireBallShape = new CircleShape(0.25f);
    private static final BodyImage image = new BodyImage("data/fireball.png", 0.5f);
    private int bounces;

    public Fireball(World w) {
        super(w);
        SolidFixture fx = new SolidFixture(this, fireBallShape);
        addImage(image);
        fx.setRestitution(0.9f);
        fx.setDensity(0.0001f);
        bounces = 0;

        FireballCollision fbc = new FireballCollision();
        addCollisionListener(fbc);
    }

    public void raiseBounces(){ bounces++; }
    public int getBounces(){ return bounces; }
}
