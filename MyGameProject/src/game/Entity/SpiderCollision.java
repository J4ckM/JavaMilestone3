package game.Entity;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;

import game.Avatar.*;
import game.Structure.*;
import game.World.*;

import org.jbox2d.common.Vec2;

public class SpiderCollision implements CollisionListener {

    public SpiderCollision(){ }

    @Override
    public void collide(CollisionEvent e) {
        Spider spider = (Spider)e.getReportingBody();
        if (e.getOtherBody() instanceof Wall){ spider.changeDirection(); }

        if (e.getOtherBody() instanceof Wolf){
            Wolf wolf = (Wolf)e.getOtherBody();
            float thatx = wolf.getPosition().x;
            boolean thatdirection = wolf.getDirection();
            changeDirection(spider, thatx, thatdirection);
        }
        if (e.getOtherBody() instanceof Spider){
            Spider thatspider = (Spider)e.getOtherBody();
            float thatx = thatspider.getPosition().x;
            boolean thatdirection = thatspider.getDirection();
            changeDirection(spider, thatx, thatdirection);
        }
    }

    public void changeDirection(Spider spider, float thatx, boolean thatdirection){
        //this goes left, that goes right
        if(spider.getDirection() && !thatdirection && (spider.getPosition().x > thatx)) { spider.changeDirection(); }
        //this goes right, that goes left
        if(!spider.getDirection() && thatdirection && (spider.getPosition().x < thatx)) { spider.changeDirection(); }
        //both left
        if(spider.getDirection() && thatdirection && (spider.getPosition().x > thatx)) { spider.changeDirection(); }
        //both right
        if(!spider.getDirection() && !thatdirection && (spider.getPosition().x < thatx)) { spider.changeDirection(); }
    }
}