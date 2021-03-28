package game.Misc;

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import game.Avatar.*;
import game.Entity.*;
import game.Game;
import game.Structure.*;
import game.World.*;
import org.jbox2d.common.Vec2;

public class Tracker implements StepListener {
    private Game game;
    private GameView view;
    private Avatar avatar;
    private Hearts hearts;
    private Heads heads;
    private Spider spider;
    private Spider[] spiders;
    private Bat bat;
    private Bat[] bats;
    private Wolf wolf;
    //private Wolf walf;
    private Slider slider;

    private int lvl;
    private GameLevel gl;
    private Level1 l1;
    private Level2 l2;
    private Level3 l3;
    private Flag flag;

    /*
    public Tracker(Game game, GameView view, int lvl, Avatar avatar, Hearts hearts, Spider spider, Bat bat, Wolf wolf) {
        this.game = game;
        this.view = view;
        this.lvl = lvl;
        this.avatar = avatar;
        this.hearts = hearts;
        this.spider = spider;
        this.bat = bat;
        this.wolf = wolf;
    }
     */
    //constructor for GameLevel
    public Tracker(Game game, GameView view, GameLevel gl, Avatar avatar) {
        this.game = game;
        this.view = view;
        this.gl = gl;
        this.avatar = avatar;
        this.hearts = avatar.getHearts();
        this.heads = avatar.getHead();
    }

    public void preStep(StepEvent e) {}
    public void postStep(StepEvent e) {
        view.setCentre(new Vec2(avatar.getPosition()));
        avatar.tracker();
        hearts.tracker(avatar, avatar.getWorld().getGame());
        heads.tracker(avatar, avatar.getWorld().getGame());
        if (gl instanceof Level1){ Level1 l = (Level1) gl; l.move(); }
        if (gl instanceof Level2){ Level2 l = (Level2) gl; l.move(); }
        if (gl instanceof Level3){ Level3 l = (Level3) gl;l.move(avatar); }
        if (gl instanceof LobbyLevel){ LobbyLevel l = (LobbyLevel) gl; l.move(avatar.getPosition()); }
    }
}