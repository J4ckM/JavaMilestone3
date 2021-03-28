package game.Avatar;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import game.Entity.*;
import game.Structure.*;
import game.World.*;

import game.Game;

public class Hearts extends StaticBody {

    /*
    Possible change to:
    red = no power
    blue = agile (speed and jump)
    green = invulnerable
    yellow = shoots fireballs
     */

    private static final Shape dotShape = new PolygonShape(0.01f,0.01f, 0.01f,-0.01f, -0.01f,-0.01f );
    private static final Shape heartShape = new PolygonShape(
            3.341f,0.5f,
            3.312f,-0.477f,
            -3.511f,-0.477f,
            -3.525f,0.5f);

    private final BodyImage image = new BodyImage("data/hearts/5hearts-0.png", 1f);
    int x = 29;
    int y = 25;

    private GhostlyFixture gf;

    public Hearts(World world){
        super(world, dotShape);
        addImage(image);
        setPosition(new Vec2(x, y));

        gf = new GhostlyFixture(this, heartShape);
        //setAlwaysOutline(true);
    }

    public void tracker(Avatar a, Game g){
        setPos(a, g);
        changeImage(a);
    }

    public void setPos(Avatar a, Game game){
        Vec2 apos = a.getPosition();
        float ax = apos.x;
        float ay = apos.y;

        //float gx = (game.getViewX()/40) - 6;
        float gx = (game.getViewX()/40) - 4;

        //float gx = (game.getViewX()/40) - (game.getViewX()/400);

        //float gy = (game.getViewY()/40) - 1;
        float gy = (game.getViewY()/40) - 1;

        //float gy = (game.getViewY()/40) - (game.getViewY()/500);
        //setPosition(new Vec2(ax+21f, ay+23f));
        setPosition(new Vec2(ax+gx, ay+gy));
    }
    //a monster of a method
    public void changeImage(Avatar a){
        removeAllImages();

        String heartint = String.valueOf(a.getHeartNumber());
        String stateint = String.valueOf(a.getState());
        String s;
        if(!a.getCombo()){ s = "data/hearts/" + String.valueOf(a.getHeartNumber()) + "hearts-" + String.valueOf(a.getState()) + ".png"; }
        else{ s = "data/hearts/1" + heartint + "hearts-" + stateint + ".png"; }

        BodyImage image = new BodyImage(s, 1f);
        addImage(image);
    }
}
