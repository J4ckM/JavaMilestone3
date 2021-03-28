package game.Avatar;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;

import game.Entity.*;
import game.Structure.*;
import game.World.*;

public class FireballCollision implements CollisionListener {

    public FireballCollision(){  }

    //in this class,
    // "this" is used to refer to the reporting Body
    // "that is used to refer to the Other Body

    @Override
    public void collide(CollisionEvent e) {
        Fireball fb = (Fireball)e.getReportingBody();
        /*
        if ((e.getOtherBody() instanceof Wolf) || (e.getOtherBody() instanceof Spider) || (e.getOtherBody() instanceof Bat)){
            fb.destroy();
            e.getOtherBody().destroy();
        }
        else { fb.raiseBounces(); }
        if (fb.getBounces() > 5) { fb.destroy(); }
         */
        if (e.getOtherBody() instanceof Platform) { fb.raiseBounces(); }
        else if (e.getOtherBody() instanceof Avatar) { }
        else {
            fb.destroy();
            if ((e.getOtherBody() instanceof Wolf) || (e.getOtherBody() instanceof Bat)){
                e.getOtherBody().destroy();
            }
            if (e.getOtherBody() instanceof Spider) { Spider s = (Spider)e.getOtherBody(); s.diecoin(); }
        }
        if (fb.getBounces() > 5) { fb.destroy(); }
    }
}