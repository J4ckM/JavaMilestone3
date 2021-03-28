package game.Avatar;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import game.World.*;

import static java.lang.Math.sqrt;


/**
 * The type Avatar.
 */
public class Avatar extends Walker {
    private static final Shape avatarShape = new PolygonShape(
    -0.99f,-3.54f, 0.95f,-3.54f, 1.22f,-1.78f, 1.14f,2.96f, -0.01f,3.59f, -1.18f,2.93f, -1.26f,-1.81f);
    private static final BodyImage image = new BodyImage("data/avatar.png", 7.5f);
    private static final BodyImage greenimage = new BodyImage("data/greenavatar.png", 7.5f);
    private static final float X = 0;
    private static final float Y = 20;
    private float x = X;
    private float y = Y;

    //movement
    private static final float WALKING_SPEED = 4 * 2.5f;
    private static float speed;

    /**
     * stores the number of hearts the avatar currently has.
     */
    private int heartnumber;
    /**
     * stores the number of lives the avatar currently has (displayed in the form of heads).
     */
    private int headnumber;

    /**
     * The GameLevel
     */
    private GameLevel gl;
    /**
     * The Hearts object (displays number of hearts)
     */
    private Hearts hearts;
    /**
     * The Hook object
     */
    private Hook hook;
    /**
     * The Heads object (displays number of heads)
     */
    private Heads heads;

    //Alternative to the state method, boolean values
    //blue = agile (speed and jump height increased)
    //green = impervious to damage
    //yellow = can shoot fireballs to the left and right (with buttons Q and E respectively)
    private boolean bluestate;
    private boolean greenstate;
    private boolean yellowstate;
    private int bluecounter;
    private int greencounter;
    private int yellowcounter;
    private boolean sprinting;

    private boolean moving;
    private boolean hooktouch;
    private boolean hookseek;

    private float originalgravity;

    private boolean floortouch;

    private boolean nextlvl = false;

    private static int coins;

    //how many levels passed
    private int atdoor;

    //checkpoints
    private boolean checkpoint;

    /**
     * The Player character.
     *
     * @param gl     the gl
     * @param hearts the hearts
     * @param hook   the hook
     * @param heads  the heads
     */
    public Avatar(GameLevel gl, Hearts hearts, Hook hook, Heads heads){
        super(gl, avatarShape);
        this.gl = gl;
        this.hearts = hearts;
        this.hook = hook;
        this.heads = heads;
        addImage(image);
        originalgravity = getGravityScale();

        setPosition(new Vec2(x, y));
        setLinearVelocity(new Vec2(0f, 5f));

        AvatarCollision eat = new AvatarCollision(this);
        addCollisionListener(eat);

        heartnumber = 5;
        headnumber = 3;
        //state = 0;

        bluestate = false;
        greenstate = false;
        yellowstate = false;
        bluecounter = 0;
        greencounter = 0;
        yellowcounter = 0;
        sprinting = false;

        moving = false;
        hooktouch = false;
        hookseek = false;

        floortouch = false;

        coins = 0;

        //AvatarController acontroller = new AvatarController(level.getAvatar(), this);

        speed = WALKING_SPEED;

        atdoor = 0;

        checkpoint = false;
    }

    /**
     * Tells us which doorway we're standing in currently.
     *
     * @return the int
     */
    public int atDoor() { return atdoor; }

    /**
     * Makes sure the door we think we're standing in is up to date
     *
     * @param atdoor the atdoor
     */
    public void setDoor(int atdoor) { this.atdoor = atdoor; }

    /**
     * Sets whether or not we've passed the checkpoint
     *
     * @param b the b
     */
    public void checkpointSet(boolean b) { checkpoint = b; }

    /* public int getCoins() { return coins; }
    public void setCoins(int coins) { this.coins = coins; }
    public void addCoins() { coins++; System.out.println("Coins: " + coins); }
    public void coinHeart() { coins = coins - 10; } */

    /**
     * Gets coins.
     *
     * @return the coins
     */
    public static int getCoins() { return coins; }

    /**
     * Sets coins.
     *
     * @param coins the coins
     */
    public static void setCoins(int coins) { Avatar.coins = coins; }

    /**
     * Adds 1 coin.
     */
    public static void addCoins() { coins++; System.out.println("Coins: " + coins); }

    /**
     * Takes off 10 coins in exchange for a heart.
     */
    public static void coinHeart() { coins = coins - 10; }

    /**
     * Set level.
     *
     * @param gl the gl
     */
    public void setLevel(GameLevel gl){ this.gl = gl; }

    /**
     * Get level.
     *
     * @return the game level
     */
    public GameLevel getLevel(){ return gl; }

    /**
     * Set next lvl.
     *
     * @param b the b
     */
    public void setNextLVL(boolean b){ nextlvl = b; }

    /**
     * Get next lvl boolean.
     *
     * @return the boolean
     */
    public boolean getNextLVL(){ return nextlvl; }

    public GameLevel getWorld(){ return gl; }

    /**
     * Get hearts object.
     *
     * @return the hearts
     */
//Heart related methods
    public Hearts getHearts(){ return hearts; }

    /**
     * Get heart number.
     *
     * @return the int
     */
    public int getHeartNumber(){ return heartnumber; }

    /**
     * Add 1 heart.
     */
    public void addHearts(){ if(heartnumber <5){ heartnumber++; } }

    /**
     * Take 1 heart.
     */
    public void takeHearts(){ if(heartnumber >=0){ heartnumber--; } }
    //public AvatarController getController(){ return ; }

    /**
     * Get head object.
     *
     * @return the heads
     */
//Head related methods
    public Heads getHead(){ return heads; }

    /**
     * Get head number.
     *
     * @return the int
     */
    public int getHeadNumber(){ return headnumber; }

    /**
     * Reset head number to 3.
     */
    public void resetHeads(){ headnumber = 3; }

    /**
     * Take 1 head.
     */
    public void takeHead(){ if(headnumber >=0){ headnumber--; } }

    /**
     * Reports whether or not we're moving.
     *
     * @return the move
     */
    public boolean getMove() { return moving; }

    /**
     * Sets moving to true.
     */
    public void yesMove() { moving = true; }

    /**
     * Sets moving to false.
     */
    public void noMove() { moving = false; }

    /**
     * Reports whether we're touching the floor.
     *
     * @return the floor touch
     */
//The following methods allow for you to stop horizontal movement entirely, granted that you're feet are touching a platform
    public boolean getFloorTouch() { return floortouch; }

    /**
     * Sets floor touch.
     *
     * @param b the b
     */
    public void setFloorTouch(boolean b) { floortouch = b; }

    /**
     * Check whether we're staying vertically still or not.
     *
     * @return the boolean
     */
    public boolean checkY() { if(getLinearVelocity().y == 0){ return true; } else { return false; } }

    /**
     * Kills the player in the necessary fashion, whether that be sending him back to the lobby
     * or just having him retry the level.
     */
    public void die() {
        if (headnumber == 1) {
            checkpoint = false;
            headnumber = 3;
            gl.getGame().lobby();
        }
        else {
            headnumber--;

            if (gl instanceof Level1) {
                Level1 l = (Level1) gl;
                if (checkpoint) { setPosition(l.getHalfspawnpoint()); }
                else { setPosition(new Vec2(0, 20)); }
            } else if (gl instanceof Level2) {
                Level2 l = (Level2) gl;
                if (checkpoint) { setPosition(l.getHalfspawnpoint()); }
                else { setPosition(new Vec2(0, 20)); }
            } else if (gl instanceof Level3) {
                Level3 l = (Level3) gl;
                if (checkpoint) { setPosition(l.getHalfspawnpoint()); }
                else { setPosition(new Vec2(0, 20)); }
            } else {
                LobbyLevel l = (LobbyLevel) gl;
                if (checkpoint) { setPosition(l.getHalfspawnpoint()); }
                else { setPosition(new Vec2(0, 20)); }
            }
        }

        setLinearVelocity(new Vec2(0, 0));
        System.out.println("GAME OVER");
        System.out.println("Though there's nothing that happens yet when the game ends");
        heartnumber = 5;
        bluestate = false;
        greenFalse();
        yellowstate = false;
    }

    /**
     * Deals with the avatar moving towards his grappling hook, should the grappling hook have
     * made contact with a wall/platform.
     */
//methods related to the grappling hook
    public void grapple(){
        float x = hook.getPosition().x - getPosition().x;
        float y = hook.getPosition().y - getPosition().y;
        float a = (float) ((20 * x) / (sqrt((x * x) + (y * y))));
        float b = (float) ((20 * y) / (sqrt((x * x) + (y * y))));
        setGravityScale(0);
        setLinearVelocity(new Vec2(a, b));
    }

    /**
     * Gets hook seek.
     *
     * @return the hook seek
     */
    public boolean getHookSeek() { return hookseek; }

    /**
     * Sets hook seek.
     */
    public void setHookSeek() { hookseek = true; }

    /**
     * Reset hook seek.
     */
    public void resetHookSeek() { hookseek = false; }

    /**
     * Gets hook object.
     *
     * @return the hook
     */
    public Hook getHook() { return hook; }

    /**
     * Gets hook touch.
     *
     * @return the hook touch
     */
    public boolean getHookTouch() { return hooktouch; }

    /**
     * Swaps the hooktouch boolean.
     */
    public void setHookTouch() { hooktouch = !hooktouch; }

    /**
     * Reset hook touch to false.
     */
    public void resetHookTouch() { hooktouch = false; }

    /**
     * Gets original gravity.
     *
     * @return the original gravity
     */
    public float getOriginalGravity() { return originalgravity; }

    /**
     * Tells us if bluestate is true.
     *
     * @return the boolean
     */
//methods related to powers
    public boolean isBlue(){ return bluestate; }

    /**
     * Tells us if greenstate is true.
     *
     * @return the boolean
     */
    public boolean isGreen(){ return greenstate; }

    /**
     * Tells us if yellowstate is true.
     *
     * @return the boolean
     */
    public boolean isYellow(){ return yellowstate; }

    /**
     * Set bluestate.
     *
     * @param x the x
     */
    public void setBlue(boolean x){ bluestate = x; }

    /**
     * Sets Green to true.
     */
    public void greenTrue(){ removeAllImages(); addImage(greenimage); greenstate = true; }

    /**
     * Sets Green to false.
     */
    public void greenFalse(){ greenstate = false; removeAllImages(); addImage(image); }

    /**
     * Set yellowstate.
     *
     * @param x the x
     */
    public void setYellow(boolean x){ yellowstate = x; }

    /**
     * Get the avatar's current state.
     * The state is the specific combination of his 3 colour states
     *
     * @return the int
     */
    public int getState(){
        if(!isBlue() && !isGreen() && !isYellow()){ return 0; }
        if(isBlue() && !isGreen() && !isYellow()){ return 1; }
        if(!isBlue() && isGreen() && !isYellow()){ return 2; }
        if(!isBlue() && !isGreen() && isYellow()){ return 3; }
        if(isBlue() && isGreen() && !isYellow()){ return 5; }
        if(isBlue() && !isGreen() && isYellow()){ return 6; }
        if(!isBlue() && isGreen() && isYellow()){ return 7; }
        else { return 8; }
    }

    /**
     * Tells us if the avatar has a combo, i.e. if he has more than one colour state.
     *
     * @return the boolean
     */
    public boolean getCombo(){
        if(!isBlue() && !isGreen() && !isYellow()){ return false; }
        else if(isBlue() && !isGreen() && !isYellow()){ return false; }
        else if(!isBlue() && isGreen() && !isYellow()){ return false; }
        else if(!isBlue() && !isGreen() && isYellow()){ return false; }
        else { return true; }
    }

    /**
     * Counts how long the avatar should remain in each state for, after becoming such
     * (in this case 29 seconds).
     */
    public void stateCounter(){
        //note, 500 gives us roughly 9.7 seconds. Do calculations from there.
        int max = 1500; //since it's 1500, you've been given roughly 29 seconds (approximate to 30)
        if(bluestate){ bluecounter++; if(bluecounter > max) { bluestate = false; bluecounter = 0; speedReset(); } }
        if(greenstate){ greencounter++; if(greencounter > max) { greenFalse(); greencounter = 0; } }
        if(yellowstate){ yellowcounter++; if(yellowcounter > max) { yellowstate = false; yellowcounter = 0; } }
    }

    /**
     * Calls the methods we wish to be called frequently.
     * It's called by the Tracker class.
     */
    public void tracker(){
        stateCounter();
        setFloorTouch(checkY());
    }

    /**
     * Walk left.
     */
    public void walkLeft(){ startWalking(-speed); }

    /**
     * Walk right.
     */
    public void walkRight(){ startWalking(speed); }

    /**
     * increases walking speed and changes sprinting boolean to true.
     */
    public void sprintSpeed(){ speed = WALKING_SPEED * 4; sprinting = true; }

    /**
     * Changes speed back to normal and sets sprinting boolean to false.
     */
    public void speedReset(){ speed = WALKING_SPEED; sprinting = false; }

    /**
     * Returns whether or not the avatar is sprinting.
     *
     * @return the boolean
     */
    public boolean isSprinting() { return sprinting; }

    /**
     * Sets heartnumber.
     *
     * @param heartnumber the heartnumber
     */
    public void setHeartnumber(int heartnumber) { this.heartnumber = heartnumber; }

    /**
     * Sets headnumber.
     *
     * @param headnumber the headnumber
     */
    public void setHeadnumber(int headnumber) { this.headnumber = headnumber; }
}
