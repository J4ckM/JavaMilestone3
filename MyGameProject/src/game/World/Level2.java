package game.World;

import city.cs.engine.SoundClip;
import org.jbox2d.common.Vec2;

import game.Avatar.*;
import game.Entity.*;
import game.Misc.*;
import game.Structure.*;
import game.Game;

public class Level2 extends GameLevel{
    private Game game;
    private GameView gameview;
    private GameLevel gamelevel;
    private Avatar avatar;
    private Hearts heart;
    private float groundlevel;
    private Flag flag;

    //living bodies
    private Wolf wolf;
    private Wolf wolf1;
    private Wolf wolf2;
    private Wolf wolf3;
    private Wolf wolf4;

    private MouseHandler mh;

    private SoundClip lvl2music;

    private Vec2 spawnpoint;
    private Vec2 halfspawnpoint;

    public Level2(Game game, GameView gameview){
        //base class will create the student, professor
        super(game);
        this.game = game;
        this.gameview = gameview;
        mh = new MouseHandler(this, gameview);
        gameview.addMouseListener(mh);
        gameview.addMouseListener(new GiveFocus(gameview));
        groundlevel = -11.5f;

        music("data/Sound/background 2.wav");

        avatar = getAvatar(this);
        spawnpoint = new Vec2(0, -10);
        halfspawnpoint = new Vec2(0, -10);
        avatar.setPosition(spawnpoint);

        //addStepListener(new Tracker(this,gameview,2, avatar, flag));

        worldBuild();
        spawn();

        addStepListener(new Tracker(game, gameview, this, avatar));
    }

    public void worldBuild(){
        // make the ground
        Platform ground1 = new Platform(this,10, 0.5f, 0, groundlevel);
        //Platform ground2 = new Platform(this,20, 0.5f, 50, groundlevel);
        //Platform ground3 = new Platform(this,20, 0.5f, -50, groundlevel);

        //Platform ground4 = new Platform(this,100, 0.5f, -100, groundlevel);

        Platform ground5 = new Platform(this,10, 0.5f, -30, groundlevel);
        Platform ground6 = new Platform(this,10, 0.5f, -60, groundlevel);
        Platform ground7 = new Platform(this,10, 0.5f, -90, groundlevel);
        Platform ground8 = new Platform(this,10, 0.5f, -120, groundlevel);
        Platform ground9 = new Platform(this,10, 0.5f, -160, groundlevel);
        Platform ground10 = new Platform(this,10, 0.5f, -190, groundlevel);

        Wall w1 = new Wall(this,0.5f, 15, 20, groundlevel + 30);
        Wall w2 = new Wall(this,0.5f, 15, -20, groundlevel + 30);
        Wall w3 = new Wall(this,0.5f, 15, -60, groundlevel + 30);
        Wall w4 = new Wall(this,0.5f, 15, -100, groundlevel + 30);
        Wall w5 = new Wall(this,0.5f, 15, -140, groundlevel + 30);
        Wall w6 = new Wall(this,0.5f, 15, -160, groundlevel + 30);

        //Wall w7 = new Wall(this,0.5f, 2, -120, groundlevel + 2);


        //LavaSpikes, Hookbox and flag
        lavaSpikes(this, 500, 0, -20);
        hookBox(this);
        flag = new Flag(this, -200, groundlevel, true);
    }

    public void spawn(){
        wolf = new Wolf(this, -8, -10);
        wolf1 = new Wolf(this, -50, -10);
        wolf2 = new Wolf(this, -100, -10);
        wolf3 = new Wolf(this, -140, -10);
        wolf4 = new Wolf(this, -180, -10);
    }

    @Override
    public boolean isComplete() {
        //if (getAvatar().getBookCount() == 5)
            return true;
        //else return false;
    }

    public void move() {
        //movement of living objects
        wolf.move();
        wolf1.move();
        wolf2.move();
        wolf3.move();
        wolf4.move();

        //non-living objects
        if(flag.getFlag()) {
            if(game.getLevelsPassed() == 2){ game.setLevelsPassed(3); }
            flag.moveCounter();
        }
    }

    public Vec2 getSpawnpoint(){ return spawnpoint; }
    public Vec2 getHalfspawnpoint(){ return halfspawnpoint; }
}
