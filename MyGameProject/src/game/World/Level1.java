package game.World;

import city.cs.engine.SoundClip;
import org.jbox2d.common.Vec2;

import game.Avatar.*;
import game.Entity.*;
import game.Misc.*;
import game.Structure.*;
import game.Game;

public class Level1 extends GameLevel {

    private Game game;
    private GameView gv;
    private float groundlevel;
    private Flag smallflag;
    private Flag flag;
    private Avatar avatar;
    private Hearts hearts;

    //living beings
    private Spider spider;
    private Spider spider1;
    private Spider spider2;
    private Spider spider3;
    private Spider spider4;
    private Spider spider5;
    private Spider spider6;
    private Spider spider7;

    private Slider slider1;

    private MouseHandler mh;

    //private GameLevel gameLevel;

    private boolean music;
    private SoundClip lvl1music;

    private Vec2 halfspawnpoint;
    private Vec2 spawnpoint;

    //spider level
    public Level1(Game game, GameView gameview) {
        //base class will create the student, professor
        super(game);
        //gameLevel = super;
        this.game = game;
        gv = gameview;
        groundlevel = -11.5f;

        music("data/Sound/background.wav");

        avatar = getAvatar(this);
        hearts = avatar.getHearts();
        spawnpoint = new Vec2(0, -10);
        avatar.setPosition(spawnpoint);

        //level.addStepListener(new Tracker(this, view, level.getAvatar(), level.getHeart(), level.getSpider(), level.getBat(), level.getWolf()));

        //addStepListener(new Tracker(game, this,1, avatar, spiders, bats, wolf, slider1, flag));
        //addStepListener(new Tracker(game, this,1, avatar, flag));

        worldBuild();
        spawn();

        addStepListener(new Tracker(game, gameview,this, avatar));
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

        // make Sliders
        slider1 = new Slider(this,1f, 0.5f, 0, 5.5f, true, platform1, platform2);
        //slider1 = new Slider(this,1f, 0.5f, 0, 5.5f, 10, -5f, false);

        // make Droppers
        Dropper dropper = new Dropper(this,3, 0.5f, 17, 5.5f);

        //Walls
        Wall wall1 = new Wall(this,0.5f, 2, 20, groundlevel + 2);
        //wall1.setFillColor(Color.blue);
        //Wall wall2 = new Wall(this,0.5f, 4, -20, -8);
        Wall wall2 = new Wall(this,0.5f, 2, -20, groundlevel + 2);
        //wall2.setFillColor(Color.green);

        Dropper dropper1 = new Dropper(this,2, 0.5f, -25, groundlevel);
        dropper1.setFillColor(PlatformColour.getDarkgreen());

        Wall wall3 = new Wall(this,0.5f, 2, -30, groundlevel + 2);

        Platform ground4 = new Platform(this,30, 0.5f, -100, groundlevel);
        Platform ground5 = new Platform(this,30, 0.5f, -150, groundlevel);
        Platform ground6 = new Platform(this,30, 0.5f, -200, groundlevel);

        Wall wall4 = new Wall(this,0.5f, 2, -75, groundlevel + 2);
        Chest chest1 = new Chest(this, -75, groundlevel + 15, 1, 0);

        Wall wall5 = new Wall(this,0.5f, 2, -120, groundlevel + 2);
        Chest chest2 = new Chest(this, -120, groundlevel + 15, 4, 0);

        Wall wall6 = new Wall(this,0.5f, 2, -160, groundlevel + 2);
        Chest chest3 = new Chest(this, -160, groundlevel + 15, 3, 0);

        Chest chest4 = new Chest(this, -180, groundlevel + 15, 2, 0);

        Chest chest = new Chest(this, 9, 15.5f, 0, 5);

        smallflag = new Flag(this, -100, groundlevel, false);
        halfspawnpoint = new Vec2(smallflag.getPosition().x, smallflag.getPosition().y + 2);

        //LavaSpikes, Hookbox and flag
        lavaSpikes(this, 500, 0, -20);
        hookBox(this);
        flag = new Flag(this, -200, groundlevel, true);
    }

    public void spawn(){
        spider = new Spider(this, 8, -10);
        spider1 = new Spider(this, -35, -10);
        spider2 = new Spider(this, -50, -10);
        spider3 = new Spider(this, -100, -10);
        spider4 = new Spider(this, -150, -10);

        spider5 = new Spider(this, -75, -10);
        spider6 = new Spider(this, -125, -10);
        spider7 = new Spider(this, -175, -10);

        //outlines
        //heart.setAlwaysOutline(true);
        //avatar.setAlwaysOutline(true);
        //spider.setAlwaysOutline(true);
        //bat.setAlwaysOutline(true);
        //wolf.setAlwaysOutline(true);
        //chest.setAlwaysOutline(true);
        //flag.setAlwaysOutline(true);
    }

    @Override
    public boolean isComplete() {
        //if (getAvatar().getBookCount() == 5)
        if(avatar.getNextLVL()) { return true; }
        else return false;
    }

    public float getGroundLevel() { return groundlevel; }
    public Game getGame() { return game; }

    public void move() {
        //movement of living objects
        spider.move();
        spider1.move();
        spider2.move();
        spider3.move();
        spider4.move();
        spider5.move();
        spider6.move();
        spider7.move();

        //non-living objects
        slider1.tracker();
        if(flag.getFlag()) {
            if (game.getLevelsPassed() == 1) { game.setLevelsPassed(2); }
            flag.moveCounter();
        }
        if(smallflag.getFlag()){
            flag.red();
            avatar.checkpointSet(true);
        }
    }

    //public Vec2 smallFlagPos(){ return smallflag.getPosition(); }
    public Vec2 getSpawnpoint(){ return spawnpoint; }
    public Vec2 getHalfspawnpoint(){ return halfspawnpoint; }

    public Flag getSmallflag(){ return smallflag; }

    public void returnGameView(GameView gv) {
        this.gv = gv;
    }

    /* public void Tracker(){
        slider1.tracker();
        spider.move();
        wolf.move();
        bat.move(avatar);
    } */

    //public void musicStop() { lvl1music.close(); }
}
