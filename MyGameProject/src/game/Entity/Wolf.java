package game.Entity;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import game.Avatar.*;
import game.Structure.*;
import game.World.*;

public class Wolf extends Walker {
    private static final Shape leftwolfShape = new PolygonShape(
            -5.61f,1.55f,
            -3.25f,-3.65f,
            2.44f,-3.63f,
            5.56f,-2.21f,
            4.19f,0.5f,
            1.93f,1.78f,
            -4.04f,3.75f,
            -4.71f,3.55f);
    private static final Shape rightwolfShape = new PolygonShape(
            3.99f,3.75f,
            -3.92f,0.86f,
            -5.61f,-2.19f,
            -1.88f,-3.69f,
            2.65f,-3.72f,
            5.58f,1.49f,
            4.71f,3.5f);
    private static final BodyImage leftimage = new BodyImage("data/LeftWolf.png", 7.5f);
    private static final BodyImage rightimage = new BodyImage("data/RightWolf.png", 7.5f);
    private boolean leftwalk;
    private static int speed = 4;
    private SolidFixture fx;

    public Wolf(World world, float x, float y){
        //super(world, wolfShape);
        super(world);
        leftwalk = true;
        //SolidFixture fx = new SolidFixture(this, wolfShape);
        fx = new SolidFixture(this, leftwolfShape);
        addImage(leftimage);
        setPosition(new Vec2(x, y));
        setLinearVelocity(new Vec2(0f, 5f));

        WolfCollision wolfcoll = new WolfCollision();
        addCollisionListener(wolfcoll);
    }

    public void move(){
        if(leftwalk) { startWalking(-speed); }
        else{ startWalking(speed); }
    }

    public void changeDirection(){
        if(leftwalk) {
            leftwalk = false;
            removeAllImages();
            Vec2 vc = getPosition();
            setPosition(new Vec2(vc.x + 0.1f, vc.y));
            fx.destroy();
            fx = new SolidFixture(this, rightwolfShape);
            addImage(rightimage);
        } else {
            leftwalk = true;
            removeAllImages();
            fx.destroy();
            fx = new SolidFixture(this, leftwolfShape);
            addImage(leftimage);
        }
    }

    public boolean getDirection(){ return leftwalk; }

    public void die(){ destroy(); }
}