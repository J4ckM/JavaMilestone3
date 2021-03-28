package game.World;

import city.cs.engine.SoundClip;
import org.jbox2d.common.Vec2;

import game.Avatar.*;
import game.Entity.*;
import game.Misc.*;
import game.Structure.*;
import game.Game;

public class Level3 extends GameLevel{
    private Game game;
    private GameView gameview;
    private Avatar avatar;

    private Bat bat;
    private Bat bat1;
    private Bat bat2;
    private Bat bat3;
    private Bat bat4;
    private Bat bat5;
    private Bat bat6;
    private Bat bat7;
    private Bat bat8;

    private Bat bat11;
    private Bat bat12;
    private Bat bat13;
    private Bat bat14;
    private Bat bat15;
    private Bat bat16;

    private Wolf wolf;
    private Wolf wolf1;

    private Spider spider;
    private Spider spider1;

    private Flag flag;
    private float groundlevel;

    private SoundClip lvl3music;

    private Vec2 spawnpoint;
    private Vec2 halfspawnpoint;

    public Level3(Game game, GameView gameview){
        //base class will create the student, professor
        super(game);
        this.game = game;
        this.gameview = gameview;
        groundlevel = -11.5f;

        music("data/Sound/background.wav");

        avatar = getAvatar(this);
        spawnpoint = new Vec2(0, -10);
        halfspawnpoint = new Vec2(0, -10);
        avatar.setPosition(spawnpoint);

        worldBuild();
        spawn();

        addStepListener(new Tracker(game, gameview,this, avatar));
    }

    public void worldBuild(){

        Platform ground1 = new Platform(this,20, 0.5f, 0, groundlevel);
        PlatformBlue ground4 = new PlatformBlue(this,200, 0.5f, -200, groundlevel);
        ground4.setFillColor(PlatformColour.getLightblue());

        Platform p1 = new Platform(this,10, 0.5f, -40, 0);
        Platform p2 = new Platform(this,10, 0.5f, -100, 0);
        Platform p3 = new Platform(this,10, 0.5f, -160, 0);
        Platform p4 = new Platform(this,10, 0.5f, -220, 0);
        Platform p5 = new Platform(this,10, 0.5f, -280, 0);
        Platform p6 = new Platform(this,10, 0.5f, -340, 0);

        //LavaSpikes, Hookbox and flag
        lavaSpikes(this, 500, 0, -20);
        hookBox(this);
        flag = new Flag(this, -400, groundlevel, true);
    }

    public void spawn(){
        bat = new Bat(this, -5, 20);
        bat1 = new Bat(this, -5, 20); bat1.setPosition(new Vec2(bat1.getPosition().x + 10,bat1.getPosition().y));
        bat2 = new Bat(this, -5, 20); bat2.setPosition(new Vec2(bat2.getPosition().x - 10,bat2.getPosition().y));
        bat3 = new Bat(this, -5, 20); bat3.setPosition(new Vec2(bat1.getPosition().x -40,bat1.getPosition().y));
        bat4 = new Bat(this, -5, 20); bat4.setPosition(new Vec2(bat1.getPosition().x -80,bat1.getPosition().y));
        bat5 = new Bat(this, -5, 20); bat5.setPosition(new Vec2(bat1.getPosition().x -120,bat1.getPosition().y));
        bat6 = new Bat(this, -5, 20); bat6.setPosition(new Vec2(bat1.getPosition().x -160,bat1.getPosition().y));
        bat7 = new Bat(this, -5, 20); bat7.setPosition(new Vec2(bat1.getPosition().x -200,bat1.getPosition().y));
        bat8 = new Bat(this, -5, 20); bat8.setPosition(new Vec2(bat1.getPosition().x -240,bat1.getPosition().y));

        bat11 = new Bat(this, -5, 20); bat11.setPosition(new Vec2(bat1.getPosition().x -280,bat1.getPosition().y));
        bat12 = new Bat(this, -5, 20); bat12.setPosition(new Vec2(bat2.getPosition().x - 300,bat2.getPosition().y));
        bat13 = new Bat(this, -5, 20); bat13.setPosition(new Vec2(bat1.getPosition().x -340,bat1.getPosition().y));
        bat14 = new Bat(this, -5, 20); bat14.setPosition(new Vec2(bat1.getPosition().x -380,bat1.getPosition().y));
        bat15 = new Bat(this, -5, 20); bat15.setPosition(new Vec2(bat1.getPosition().x -400,bat1.getPosition().y));
        bat16 = new Bat(this, -5, 20); bat16.setPosition(new Vec2(bat1.getPosition().x -260,bat1.getPosition().y));

        wolf = new Wolf(this, -280, groundlevel);
        wolf1 = new Wolf(this, -370, groundlevel);

        spider = new Spider(this, -310, groundlevel);
        spider1 = new Spider(this, -360, groundlevel);
    }

    @Override
    public boolean isComplete() {
        //if (getAvatar().getBookCount() == 5)
            return true;
        //else return false;
    }

    public void move(Avatar a) {
        //movement of living objects
        bat.move(a); bat1.move(a); bat2.move(a); bat3.move(a); bat4.move(a); bat5.move(a); bat6.move(a); bat7.move(a); bat8.move(a);
        bat11.move(a); bat12.move(a); bat13.move(a); bat14.move(a); bat15.move(a); bat16.move(a);

        wolf.move(); wolf1.move();
        spider.move(); spider1.move();

        //non-living objects
        if(flag.getFlag()) {
            //if(game.getLevelsPassed() == 3){ game.setLevelsPassed(4); }
            flag.moveCounter();
        }
    }

    public Vec2 getSpawnpoint(){ return spawnpoint; }
    public Vec2 getHalfspawnpoint(){ return halfspawnpoint; }
}
