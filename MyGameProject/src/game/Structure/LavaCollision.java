package game.Structure;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;

import game.Avatar.*;

public class LavaCollision implements CollisionListener {

    public LavaCollision(){  }

    //in this class,
    // "this" is used to refer to the reporting Body
    // "that is used to refer to the Other Body

    @Override
    public void collide(CollisionEvent e) {
        //if (!((e.getOtherBody() instanceof Avatar) || (e.getOtherBody() instanceof Hearts) || (e.getOtherBody() instanceof Hook))){
        if (!((e.getOtherBody() instanceof Avatar) || (e.getOtherBody() instanceof Hook))){
            e.getOtherBody().destroy();
        }
        if (e.getOtherBody() instanceof Avatar) { Avatar avatar = (Avatar)e.getOtherBody(); avatar.die(); }
        if (e.getOtherBody() instanceof Hook) { Hook hk = (Hook)e.getOtherBody(); hk.delete(); }
    }
}