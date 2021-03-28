package game.World;

import city.cs.engine.SoundClip;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

import java.awt.*;

import game.Avatar.*;
import game.Entity.*;
import game.Structure.*;
import game.Game;

import city.cs.engine.SoundClip;

import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.jbox2d.common.Vec2;

public abstract class GameLevel extends World {
    private Avatar avatar;
    private Hearts heart;
    private Hook hk;
    private Heads heads;
    private Game game;

    private SoundClip gameMusic;
    private SoundClip gameSound;
    private double originalvolume = 0.5;
    private boolean muted;

    public GameLevel(Game game){
        this.game = game;
        heart = new Hearts(this);
        hk = new Hook(this);
        heads = new Heads(this);
        avatar = new Avatar(this, heart, hk, heads);

        muted = false;
    }

    public Avatar getAvatar(GameLevel gl){ avatar.setLevel(gl); return avatar; }
    public Avatar getAvatar(){ return avatar; }

    public Hearts getHeart(){ return heart; }
    public Game getGame(){ return game; }

    public abstract boolean isComplete();

    public void lavaSpikes(World w, int spikes, float x, float y){
        int mid;
        int spikewidth = 4;
        if(spikes % 2 == 0) { mid = spikes/2; }
        else { mid = spikes/2 + 1; }
        for (int i = 0; i < mid; i++) {
            new LavaSpike(this, x + i*spikewidth, y + 1.5f);
            new LavaSpike(this, x - i*spikewidth, y + 1.5f);
        }
        PlatformRed lavabase = new PlatformRed(this, spikes * spikewidth, 100, x, y - 100);
        lavabase.setFillColor(LavaSpike.getColor());
        lavabase.setLineColor(LavaSpike.getColor());
    }

    public void hookBox(GameLevel w){
        float x = boxCoords().x;
        float y = boxCoords().y;
        Case w1 = new Case(w, 1, 3, x - 1.5f, y);
        Case p = new Case(w, 3, 0.5f, x, y - 1);
        Case w2 = new Case(w, 1, 3, x + 1.5f, y);

    }

    public Vec2 boxCoords(){
        float x = 1000;
        float y = 1000;
        Vec2 v = new Vec2(x, y);
        return v;
    }

    //music related
    public void music(String s){
        try {
            gameMusic = new SoundClip(s);   // Open an audio input stream
            gameMusic.setVolume(0.2);
            gameMusic.loop();  // Set it to continous playback (looping)
        } catch (UnsupportedAudioFileException|IOException|LineUnavailableException e) { System.out.println(e); }
    }

    public void sound(String s){
        try {
            gameSound = new SoundClip(s);   // Open an audio input stream
            gameSound.setVolume(0.4);
            gameSound.loop();  // Set it to continous playback (looping)
        } catch (UnsupportedAudioFileException|IOException|LineUnavailableException e) { System.out.println(e); }
    }

    public void soundUp() { gameMusic.setVolume(originalvolume); }
    public void mute() { double O = 0.001; gameMusic.setVolume(O); }
    public boolean getMute() { return muted; }
    public void setMute() { muted = !muted; }
    public void musicStop() { gameMusic.pause(); }
    public void musicPlay() { gameMusic.play(); }
}


