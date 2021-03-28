package game.Structure;

import city.cs.engine.*;
import city.cs.engine.Shape;
import game.Avatar.Avatar;
import org.jbox2d.common.Vec2;

import game.World.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;

import city.cs.engine.SoundClip;

public class Flag extends StaticBody {
    private int flymove;
    private boolean flagtouched;
    private boolean flagpost;
    private int lvl;

    //to see if it's a checkpoint or a flag that brings you to a new level
    private boolean large;
    private World w;
    private float x;
    private float y;

    private SoundClip victory;

    private static final Shape flagShape = new PolygonShape(
            -2.8f,1.25f,
            2.8f,0,
            -2.8f,-1.25f);

    public Flag(World w, float x, float y, boolean large) {
        super(w, flagShape);
        this.w = w; this.x = x; this.y = y; this.large = large;

        if(large){ largeFlag(); }
        else{ smallFlag(); }
    }

    public void largeFlag(){
        setPosition(new Vec2(x, y + 8));

        Wall w2 = new Wall(w, 3, 2, x - 2.8f, y + 1.5f);
        //w2.setFillColor(Color.yellow);
        Wall w1 = new Wall(w, 0.25f, 13, x - 2.8f, y + 13);
        //w1.setFillColor(Color.yellow);

        flymove = 0;
        //setPosition(new Vec2(x, y + 8));
        setFillColor(Color.red);
    }

    public void smallFlag(){
        setPosition(new Vec2(x, y + 4));

        //w2.setFillColor(Color.yellow);
        Wall w1 = new Wall(w, 0.25f, 2, x - 2.8f, y + 2);
        //w1.setFillColor(Color.yellow);

        flymove = 0;
        setFillColor(Color.blue);
    }

    public void touchFlag(Avatar a) {
        if(!flagtouched) {
            if(large) {
                if (w instanceof GameLevel) {
                    ((GameLevel) w).musicStop();
                    GameLevel world = (GameLevel)w;
                    world.getGame();
                }

                try {
                    victory = new SoundClip("data/Sound/victory.wav");   // Open an audio input stream
                    victory.setVolume(0.5);
                    victory.play();  // Set it to continous playback (looping)
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                    System.out.println(e);
                }
            } else {
                a.checkpointSet(true);
                setFillColor(Color.red);
            }
        }
        flagtouched = true;
    }

    public boolean getFlag() { return flagtouched; }

    public boolean getSize() { return large; }
    public void red() { setFillColor(Color.red); }
    public void blue() { setFillColor(Color.blue); }

    public void flyTheFlag() {
        Vec2 v = getPosition();
        setPosition(new Vec2(v.x, v.y+0.5f));
    }

    public void moveCounter() {
        int height = 34;
        int incrementer = 200;
        if (flymove < height) { flyTheFlag(); flymove++; }
        if ((flymove >= height) && (flymove < height + incrementer)) { flymove++; }
        if (flymove >= height + incrementer) { Success(); }
    }

    public void Success(){
        flagtouched = false;
        if(w instanceof Level1){ Level1 l1 = (Level1) w; l1.getGame().goToNextLevel(); }
        if(w instanceof Level2){ Level2 l2 = (Level2) w; l2.getGame().goToNextLevel(); }
        if(w instanceof Level3){ Level3 l3 = (Level3) w; l3.getGame().goToNextLevel(); }
    }
}
