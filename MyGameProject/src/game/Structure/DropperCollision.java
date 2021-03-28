package game.Structure;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;

import game.Avatar.Avatar;

import java.awt.*;

public class DropperCollision implements CollisionListener {

    public DropperCollision(){ }

    @Override
    public void collide(CollisionEvent e) {
        //Dropper drop = (Dropper)e.getReportingBody();
        DropperInteract drop = (DropperInteract)e.getReportingBody();
        if (e.getOtherBody() instanceof Avatar) {
            Color col = drop.getFillColor();
            DroppingPlatform droppingplat = new DroppingPlatform(drop.getWorld(),drop.getWidth(),
                    drop.getHeight()*2, drop.getX() + drop.getHeight()/2, drop.getY());
            droppingplat.setFillColor(col);
            droppingplat.setLineColor(Color.black);
            drop.getDropper().destroy();
            drop.destroy();
        }
    }
}