package game.Structure;

import city.cs.engine.Shape;
import city.cs.engine.*;
import game.World.GameLevel;
import game.World.Level1;
import game.World.Level2;
import game.World.Level3;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;

public class Door extends StaticBody {
    private int flymove;
    private boolean flagtouched;
    private boolean flagpost;
    private World w;
    private int lvl;
    private GhostlyFixture gf;

    //to see if it's a checkpoint or a flag that brings you to a new level
    private boolean large;

    private SoundClip victory;

    //private static final Shape stumpShape = new PolygonShape(-1.64f,-2.93f, -2.08f,-3.43f, 2.06f,-3.46f, 1.65f,-2.93f);
    //private static final Shape doorshape = new PolygonShape(-1.65f,-2.93f, -1.64f,3.49f, 1.64f,3.49f, 1.64f,-2.93f);

    private static final Shape stumpShape = new PolygonShape(-1.89f,-3.36f, -2.38f,-3.95f, 2.35f,-3.94f, 1.89f,-3.36f);
    private static final Shape doorshape = new PolygonShape(-1.89f,-3.34f, 1.87f,-3.34f, 1.87f,3.98f, -1.9f,4.0f);

    // door = -1.65f,-2.93f, -1.64f,3.49f, 1.64f,3.49f, 1.64f,-2.93f

    // original stump = -3.097f,0.5f, -3.855f,-0.475f, 3.855f,-0.475f, 3.097f,0.5f
    // stump = -1.64f,-2.93f, -2.08f,-3.43f, 2.06f,-3.46f, 1.65f,-2.93f

    private static final BodyImage door = new BodyImage("data/door.png", 8f);
    private static final BodyImage lockeddoor = new BodyImage("data/doorlock.png", 8f);
    private static final BodyImage opendoor = new BodyImage("data/opendoor.png", 8f);

    private boolean unlocked;

    public Door(World w, float x, float groundlevel, boolean unlocked) {
        super(w, stumpShape);
        this.unlocked = unlocked;
        // x is obvious, for the rest, it sets the centre at groundlevel + 1/2 height of image + 1/2 ground height
            //  (minus a tiny bit so it acts as a ramp and you don't bump into it)
        setPosition(new Vec2(x, groundlevel + 4f + 0.45f));

        setAlwaysOutline(true);
        gf = new GhostlyFixture(this, doorshape);

        if(unlocked) { addImage(door); }
        else{ addImage(lockeddoor); }
    }

    public void open(){
        removeAllImages();
        addImage(opendoor);
    }

    public boolean getLock(){ return unlocked; }

    public static void enterLevel(int level){ }
}
