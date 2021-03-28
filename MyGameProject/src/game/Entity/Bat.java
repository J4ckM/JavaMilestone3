package game.Entity;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import game.Avatar.*;
import game.Structure.*;
import game.World.*;

/**
 * The type Bat.
 */
public class Bat extends Walker {
    private static final Shape batShape = new PolygonShape(
            -2.465f,0.913f,
            -0.287f,-1.196f,
            2.436f,0.775f,
            1.257f,1.161f,
            -1.109f,1.24f);
    //DynamicBody student = new DynamicBody(world, studentShape);
    //Student student = new DynamicBody(world, studentShape);
    private static final BodyImage image = new BodyImage("data/bat.png", 2.5f);
    private boolean leftwalk = true;
    private boolean upmove = true;
    private boolean striking = false;
    private static int speed = 8;
    private final int FLY = 16;
    private int fly = FLY;

    private final float leftlimit = -400;
    private final float rightlimit = 15;

    //level 3 ground level
    private final float groundlevel = -11.5f;

//timing variables
    /**
     * The Verticallimit.
     */
    float verticallimit;
    /**
     * The Horizontallimit.
     */
    float horizontallimit;

    /**
     * Instantiates a new Bat.
     *
     * @param world the world
     * @param x     the x position
     * @param y     the y position
     */
    public Bat(World world, float x, float y){
        super(world, batShape);
        addImage(image);
        setPosition(new Vec2(8, 10));
        setLinearVelocity(new Vec2(0f, 5f));
        startWalking(2);

        BatCollision batcoll = new BatCollision();
        addCollisionListener(batcoll);

        setGravityScale(-1);
    }

    /* public void move(Avatar a){
        Vec2 bpos = getPosition();
        Vec2 apos = a.getPosition();
        double rdm = Math.random();
        float r = (float)rdm;

        //Flying
        if(bpos.y > apos.y + 10){ upmove = false; }
        if(bpos.y < apos.y - 10){ upmove = true; }
        //moving down
        if(!upmove){
            if(r > 0){ setPosition(new Vec2(bpos.x, bpos.y + r)); }
            else{ setPosition(new Vec2(bpos.x, bpos.y - r)); } }
        //moving up
        if(upmove){
            if(r > 0){ setPosition(new Vec2(bpos.x, bpos.y - r)); }
            else{ setPosition(new Vec2(bpos.x, bpos.y + r)); } }
        //else{ setPosition(new Vec2(bpos.x, bpos.y + r)); }

        //Walking
        if(bpos.x > apos.x + 5) { leftwalk = true; }
        if(bpos.y < apos.y - 5){ leftwalk = false; }
        if(leftwalk) { startWalking(-speed); }
        else{ startWalking(speed); }
    } */

    /**
     * Moves the bat in question. Called consistently by the tracker class
     *
     * @param a the Avatar (required for movement in relation to the player character).
     */
    public void move(Avatar a){
        Vec2 bpos = getPosition();
        Vec2 apos = a.getPosition();

        //Flying
        if(bpos.y > apos.y + 40){ upmove = false; }
        if(bpos.y <= groundlevel){ upmove = true; }
        //moving down
        if (!upmove) { startFlying(-fly); }
        //moving up
        if (upmove) { startFlying(fly); }

        if((bpos.y > apos.y) && (bpos.x < (apos.x + 2)) && (bpos.x > (apos.x - 2))){ striking = true; }
        if((bpos.y > apos.y) && (bpos.x > (apos.x + 5)) && (bpos.x < (apos.x - 5))){ striking = false; }
        if(bpos.y < apos.y){ striking = false; }

        if(striking){
            upmove = false;
            //fly = FLY*2;
            //important, uses the avatar's x, not the bat's x
            //setLinearVelocity(new Vec2(a.getLinearVelocity().x, -fly));
            setLinearVelocity(new Vec2(getLinearVelocity().x, -fly));
        } else{ fly = FLY; }

        if(!striking) {
            //moving down
            if (!upmove) { startFlying(-fly); }
            //moving up
            if (upmove) { startFlying(fly); }
        }

        if(bpos.x > rightlimit){ leftwalk = true; }
        if(bpos.x < leftlimit){ leftwalk = false; }

        //else{ setPosition(new Vec2(bpos.x, bpos.y + r)); }

        //Walking
        //if(bpos.x > apos.x + 40) { setLeftwalk(true); }
        //if(bpos.y < apos.y - 40){ setLeftwalk(false); }
        if(leftwalk) { startWalking(-speed); }
        else{ startWalking(speed); }

        upTimer();
        sideTimer();
    }

    /**
     * Changes striking boolean to false.
     */
    public void strikeFalse(){ striking = false; }

    /**
     * Changes striking upmove to true.
     */
    public void upmoveTrue(){ setUpmove(true); }

    /**
     * Start flying.
     *
     * @param fly the y-Linearvelocity we want
     */
    public void startFlying(int fly){ setLinearVelocity(new Vec2(getLinearVelocity().x, fly)); }

    /**
     * Change horizontal direction.
     */
    public void changeHorizontalDirection(){
        if(leftwalk) { setLeftwalk(false); }
        else{ setLeftwalk(true); }
    }

    /**
     * Change vertical direction.
     * Guarantees the bat will not become stuck to the platform it hits
     *
     * @param p the p
     */
    public void changeVerticalDirection(Platform p){
        float py = p.getPosition().y;
        float by = getPosition().y;
        if(by > py) { setUpmove(true); setLinearVelocity(new Vec2(getLinearVelocity().x, 100)); }
        else{ setUpmove(false); }
    }

    /**
     * Change vertical direction.
     * The simpler method constructor
     */
    public void changeVerticalDirection(){ if(upmove) { upmove = false; } else{ upmove = true; } }

    /**
     * Set upmove.
     *
     * @param b the b
     */
    public void setUpmove(boolean b){ upmove = b; }

    /**
     * Set leftwalk.
     *
     * @param b the b
     */
    public void setLeftwalk(boolean b){ leftwalk = b; }

    /**
     * If bat has gone above or below the required limit (chosen randomly)
     * it will change vertical direction.
     */
    public void upTimer(){
        if(upmove) {
            if (getPosition().y > verticallimit) {
                changeVerticalDirection();
                verticallimit = (int)(randomNo());
            }
        } else {
            if (getPosition().y < -verticallimit) {
                changeVerticalDirection();
                verticallimit = (int)(randomNo());
            }
        }
    }

    /**
     * If bat has gone above or below the required limit (chosen randomly)
     * it will change horizontal direction.
     */
    public void sideTimer(){
        if(!leftwalk) {
            if (getPosition().y > horizontallimit) {
                changeHorizontalDirection();
                horizontallimit = (int)(randomNo());
            }
        } else {
            if (getPosition().y < -horizontallimit) {
                changeHorizontalDirection();
                horizontallimit = (int)(randomNo());
            }
        }
    }

    /**
     * Returns a random float for use as verticallimit or horizontallimit.
     *
     * @return the float
     */
    public float randomNo(){
        double rdm = Math.random();
        float r = (float)rdm;
        //range now between 0 and 30
        r = 30*r;
        //range now between 10 and 40
        r = r + 10;
        return r;
    }

    /**
     * Destroys the bat, not really used yet.
     */
    public void die(){ destroy(); }
}
