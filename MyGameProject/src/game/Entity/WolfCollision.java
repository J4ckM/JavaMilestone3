package game.Entity;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;

import game.Avatar.*;
import game.Structure.*;
import game.World.*;

public class WolfCollision implements CollisionListener {

    public WolfCollision(){  }

    //in this class,
    // "this" is used to refer to the reporting Body
    // "that is used to refer to the Other Body

    @Override
    public void collide(CollisionEvent e) {
        Wolf wolf = (Wolf)e.getReportingBody();
        if (e.getOtherBody() instanceof Wall){ wolf.changeDirection(); }

        if (e.getOtherBody() instanceof Spider){
            Spider spider = (Spider)e.getOtherBody();
            float thatx = spider.getPosition().x;
            boolean thatdirection = spider.getDirection();
            changeDirection(wolf, thatx, thatdirection);
        }
        if (e.getOtherBody() instanceof Wolf){
            Wolf thatwolf = (Wolf)e.getOtherBody();
            float thatx = thatwolf.getPosition().x;
            boolean thatdirection = thatwolf.getDirection();
            changeDirection(wolf, thatx, thatdirection);
        }
    }

    public void changeDirection(Wolf wolf, float thatx, boolean thatdirection){
        //this goes left, that goes right
        if(wolf.getDirection() && !thatdirection && (wolf.getPosition().x > thatx)) { wolf.changeDirection(); }
        //this goes right, that goes left
        if(!wolf.getDirection() && thatdirection && (wolf.getPosition().x < thatx)) { wolf.changeDirection(); }
        //both left
        if(wolf.getDirection() && thatdirection && (wolf.getPosition().x > thatx)) { wolf.changeDirection(); }
        //both right
        if(!wolf.getDirection() && !thatdirection && (wolf.getPosition().x < thatx)) { wolf.changeDirection(); }
    }
}