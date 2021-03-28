package game.Entity;

import game.Avatar.*;
import game.Structure.*;
import game.World.*;
import city.cs.engine.*;

import city.cs.engine.SensorEvent;
import city.cs.engine.SensorListener;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;

import city.cs.engine.SoundClip;

public class SpiderSensorListener implements SensorListener {
    private Spider spider;
    private World world;
    private SoundClip bonk;

    public SpiderSensorListener(Spider spider, World world){ this.spider = spider; this.world = world; }

    @Override
    public void beginContact(SensorEvent e) {
        //Spider spider = (Spider)e.getSensor().getSpider();
        if (e.getContactBody() instanceof Avatar){
            music();
            spider.diecoin();
        }
    }

    @Override
    public void endContact(SensorEvent e) { }

    public void music(){
        //if (world instanceof GameLevel) { ((GameLevel) world).musicStop(); }
        try {
            bonk = new SoundClip("data/Sound/bonk.wav");   // Open an audio input stream
            bonk.setVolume(2);
            bonk.play();  // Set it to continous playback (looping)
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
        //if (world instanceof GameLevel) { ((GameLevel) world).musicPlay(); }
    }
}