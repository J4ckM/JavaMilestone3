package game.Entity;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;
import city.cs.engine.Fixture;

import game.Avatar.*;
import game.Structure.*;
import game.World.*;

import org.jbox2d.common.Vec2;

public class Spider extends Walker {
    private static final Shape spiderShape = new PolygonShape(
            -2.887f,0.64f,
            -2.746f,-1.18f,
            2.77f,-1.18f,
            2.84f,0.593f,
            1.808f,1.238f,
            -1.749f,1.25f);
    private static final BodyImage image = new BodyImage("data/spider.png", 2.5f);
    private boolean leftwalk = true;
    private static int speed = 2;
    private World world;

    public Spider(World world, float x, float y){
        super(world, spiderShape);
        addImage(image);
        setPosition(new Vec2(x, y));
        setLinearVelocity(new Vec2(0f, 5f));

        SpiderCollision spidcoll = new SpiderCollision();
        addCollisionListener(spidcoll);

        SpiderSensor spideysense = new SpiderSensor(this, world);

        this.world = world;
    }

    public void move(){ if(leftwalk) { startWalking(-speed); } else{ startWalking(speed); } }

    public void changeDirection(){ if(leftwalk) { leftwalk = false; } else{ leftwalk = true; } }

    public boolean getDirection(){
        return leftwalk;
    }

    public void die(){ destroy(); }
    public void diecoin(){
        Vec2 v = getPosition();
        destroy();
        Coin c = new Coin(world, true, v.x, v.y);
    }
}
