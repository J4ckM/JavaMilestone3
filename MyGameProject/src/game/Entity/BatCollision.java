package game.Entity;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;

import city.cs.engine.DynamicBody;
import game.Avatar.*;
import game.Structure.*;
import game.World.*;

public class BatCollision implements CollisionListener {

    public BatCollision(){ }

    @Override
    public void collide(CollisionEvent e) {
        Bat bat = (Bat)e.getReportingBody();
        if (e.getOtherBody() instanceof Platform){ Platform p = (Platform) e.getOtherBody();
            bat.changeVerticalDirection(p); }
        if (e.getOtherBody() instanceof Wall){
            bat.changeHorizontalDirection();
        }
        //if ((e.getOtherBody() instanceof Wolf) || (e.getOtherBody() instanceof Bat) || (e.getOtherBody() instanceof Avatar)){
        if (e.getOtherBody() instanceof DynamicBody){
            bat.changeHorizontalDirection();
            bat.changeVerticalDirection();
            bat.upmoveTrue();
        }
        bat.strikeFalse();
    }
}