package game.World;

import city.cs.engine.SoundClip;
import game.Avatar.*;
import game.Entity.*;
import game.*;
import game.Misc.*;
import game.Structure.*;
import org.jbox2d.common.Vec2;

import java.awt.*;

public class LobbyLevel extends GameLevel {

    private Game game;
    private GameView gameview;
    private float groundlevel;
    //private GameView gv;
    private Avatar avatar;
    private Hearts hearts;
    private Heads heads;

    //living beings
    private Spider spider;
    private Spider[] spiders;
    private Bat[] bats;
    private Wolf wolf;
    //private Wolf walf;

    private Slider slider1;

    private MouseHandler mh;

    //private GameLevel gameLevel;

    private Door d1;
    private Door d2;
    private Door d3;

    private boolean music;
    private SoundClip lvl1music;

    private Flag flag;

    private Vec2 spawnpoint;
    private Vec2 halfspawnpoint;

    public LobbyLevel(Game game) {
        //base class will create the student, professor
        super(game);
        //gameLevel = super;
        this.game = game;

        Setup();
    }
    public LobbyLevel(Game game, GameView view) {
        //base class will create the student, professor
        super(game);
        //gameLevel = super;
        this.game = game;
        gameview = view;

        Setup();

        addStepListener(new Tracker(game, view,this, avatar));
    }

    public void Setup(){
        groundlevel = -11.5f;

        music("data/Sound/background.wav");

        avatar = getAvatar(this);
        hearts = avatar.getHearts();
        heads = avatar.getHead();
        spawnpoint = new Vec2(0, -10);
        halfspawnpoint = new Vec2(0, -10);
        avatar.setPosition(spawnpoint);



        worldBuild();
        spawn();
    }

    public void worldBuild(){
        // make the ground
        Platform ground1 = new Platform(this,20, 0.5f, 0, groundlevel);
        Platform ground2 = new Platform(this,20, 0.5f, 50, groundlevel);
        Platform ground3 = new Platform(this,20, 0.5f, -50, groundlevel);

        // make a platform
        PlatformRed platform1 = new PlatformRed(this,4, 0.5f, -9, 5.5f);
        Platform platform2 = new Platform(this,4, 0.5f, 9, 5.5f);
        PlatformYellow platform3 = new PlatformYellow(this,17, 0.5f, -33, 5.5f);
        /* PlatformColour platform3 = new PlatformColour(this,17, 0.5f, -33, 5.5f, 4, false);
        PlatformColour platform4 = new PlatformColour(this,17, 0.5f, -33, 7.5f, 4, true); */

        boolean b = true;
        for(int i = 1; i <= 3; i++){
            /* if(i > Avatar.getLevelsPassed()){ b = false; }
            System.out.println("av levels passed: " + Avatar.getLevelsPassed()); */

            if(i > game.getLevelsPassed()){ b = false; }
            System.out.println("av levels passed: " + game.getLevelsPassed());

            if(i == 1) { d1 = new Door(this, 40, groundlevel, b); System.out.println("boolean 1: " + b); }
            if(i == 2) { d2 = new Door(this, 50, groundlevel, b); System.out.println("boolean 2: " + b); }
            if(i == 3) { d3 = new Door(this, 60, groundlevel, b); System.out.println("boolean 3: " + b); }
        }

        // make Sliders
        //slider1 = new Slider(this,1f, 0.5f, 0, 5.5f, platform1, platform2);

        // make Droppers
        Dropper dropper = new Dropper(this,3, 0.5f, 17, 5.5f);
        //DropperCollision dropcoll = new DropperCollision();
        //dropper.addCollisionListener(dropcoll);

        //Walls
        Wall wall1 = new Wall(this,0.5f, 4, 20, groundlevel);
        wall1.setFillColor(Color.blue);

        //Wall wall2 = new Wall(this,0.5f, 4, -20, -8);
        //wall2.setFillColor(Color.green);

        //living beings

        Chest chest = new Chest(this, 9, 15.5f, 0, 5);

        /* walf = new Wolf(this, -18, -10);
        walf.addCollisionListener(wolfcoll);
         */

        //LavaSpikes, Hookbox and flag
        lavaSpikes(this, 500, 0, -20);
        hookBox(this);
        flag = new Flag(this, -40, groundlevel, false);

        //outlines
        //heart.setAlwaysOutline(true);
        //avatar.setAlwaysOutline(true);
        //spider.setAlwaysOutline(true);
        //bat.setAlwaysOutline(true);
        //wolf.setAlwaysOutline(true);
        //chest.setAlwaysOutline(true);
    }

    private void spawn(){ Spider spider = new Spider(this, 50, groundlevel + 5); }

    public void addStepsListen(GameView gameview){ addStepListener(new Tracker(game, gameview, this, avatar)); }

    @Override
    public boolean isComplete() {
        //if (getAvatar().getBookCount() == 5)
        if(avatar.getNextLVL()) { return true; }
        else return false;
    }

    public float getGroundLevel() { return groundlevel; }
    public Game getGame() { return game; }

    public void move(Vec2 apos){
        //non-living objects
        if((d1.contains(apos)) && (d1.getLock())){ avatar.setDoor(1); }
        else if(d2.contains(apos) && (d2.getLock())){ avatar.setDoor(2);  }
        else if(d3.contains(apos) && (d3.getLock())){ avatar.setDoor(3);  }
        else{ avatar.setDoor(0); }
    }

    //public void musicStop() { lvl1music.close(); }

    public Vec2 getSpawnpoint(){ return spawnpoint; }
    public Vec2 getHalfspawnpoint(){ return halfspawnpoint; }
}
