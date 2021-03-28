package game.Avatar;

import city.cs.engine.*;
import game.Game;
import org.jbox2d.common.Vec2;

public class Heads extends StaticBody {

    /*
    Possible change to:
    red = no power
    blue = agile (speed and jump)
    green = invulnerable
    yellow = shoots fireballs
     */

    private static final Shape dotShape = new PolygonShape(0.01f,0.01f, 0.01f,-0.01f, -0.01f,-0.01f );
    private static final Shape headShape = new PolygonShape(
            3.341f,0.5f,
            3.312f,-0.477f,
            -3.511f,-0.477f,
            -3.525f,0.5f);

    private final BodyImage image = new BodyImage("data/head3.png", 3f);
    int x = 0;
    int y = 16;

    private GhostlyFixture gf;

    public Heads(World world){
        super(world, dotShape);
        addImage(image);
        setPosition(new Vec2(x, y));

        gf = new GhostlyFixture(this, headShape);
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
        //float gx = (game.getViewX()/40) - (game.getViewX()/400);
        float gy = (game.getViewY()/40) - 2;
        //float gy = (game.getViewY()/40) - (game.getViewY()/500);
        //setPosition(new Vec2(ax+21f, ay+23f));
        setPosition(new Vec2(ax, ay+gy));
    }
    //a monster of a method
    public void changeImage(Avatar a){
        removeAllImages();
        addImage(new BodyImage("data/head" + String.valueOf(a.getHeadNumber()) + ".png", 3f));
    }
}
