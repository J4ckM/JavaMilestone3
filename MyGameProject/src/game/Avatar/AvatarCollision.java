package game.Avatar;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;

import game.Entity.*;
import game.Structure.*;
import game.World.*;

import java.awt.*;


public class AvatarCollision implements CollisionListener {

    private Avatar avatar;
    public AvatarCollision(Avatar a){
        this.avatar = a;
    }

    @Override
    public void collide(CollisionEvent e) {
        /* if (e.getOtherBody() instanceof FruitRed) {
            if(avatar.getHeartNumber() < 5) {
                avatar.addHearts();
                e.getOtherBody().destroy();
            }
        } */

        if (e.getOtherBody() instanceof Platform) { avatar.setFloorTouch(true); }
        if ((e.getOtherBody() instanceof Platform) || (e.getOtherBody() instanceof Platform)) {
            if(avatar.getHookSeek()){ avatar.grapple(); }
        }

        if (e.getOtherBody() instanceof Fruit){
            Fruit f = (Fruit)e.getOtherBody();
            if (f.getFruit() == 1) {
                if(avatar.getHeartNumber() < 5) {
                    avatar.addHearts();
                    e.getOtherBody().destroy();
                }
            }
            if (f.getFruit() == 2) { if(!avatar.isBlue()) { avatar.setBlue(true); e.getOtherBody().destroy(); } }
            if (f.getFruit() == 3) { if(!avatar.isGreen()) { avatar.greenTrue(); e.getOtherBody().destroy(); } }
            if (f.getFruit() == 4) { if(!avatar.isYellow()) { avatar.setYellow(true); e.getOtherBody().destroy(); } }
        }

        if (e.getOtherBody() instanceof Coin){
            avatar.addCoins();
            e.getOtherBody().destroy();
            if((avatar.getHeartNumber() < 5) && (avatar.getCoins() >= 10)){ avatar.addHearts(); avatar.coinHeart(); }
        }

        if (e.getOtherBody() instanceof PlatformBlue) { if(!avatar.isBlue()) { avatar.setBlue(true); } }
        if (e.getOtherBody() instanceof PlatformGreen) { if(!avatar.isGreen()) { avatar.greenTrue(); } }
        if (e.getOtherBody() instanceof PlatformYellow) { if(!avatar.isYellow()) { avatar.setYellow(true); } }

        if (e.getOtherBody() instanceof PlatformColour) {
            PlatformColour pc = (PlatformColour)e.getOtherBody();
            if(pc.getColour() == 1) {
                if(pc.getGood()){ avatar.addHearts(); }
                else{ avatar.takeHearts(); }
            }
            if(pc.getColour() == 2) {
                if(pc.getGood()){ avatar.setBlue(true); }
                else{ avatar.setBlue(false); }
            }
            if(pc.getColour() == 3) {
                if(pc.getGood()){ avatar.greenTrue(); }
                else{ avatar.greenFalse(); }
            }
            if(pc.getColour() == 4) {
                if(pc.getGood()){ avatar.setYellow(true); }
                else{ avatar.setYellow(false); }
            }
        }

        if ((e.getOtherBody() instanceof Spider) || (e.getOtherBody() instanceof Wolf) || (e.getOtherBody() instanceof PlatformRed)) {
            if(!avatar.isGreen()) {
                if(avatar.getHeartNumber() > 1) { avatar.takeHearts(); }
                else{ avatar.takeHearts(); avatar.die(); }
            }
        }

        if (e.getOtherBody() instanceof Bat) { if(!avatar.isGreen()) { avatar.die(); } }

        //if (e.getOtherBody() instanceof Flag) { goToNextLevel(); }
        if (e.getOtherBody() instanceof Flag) {
            Flag flag = (Flag)e.getOtherBody();
            flag.touchFlag(avatar);
        }
        if (e.getOtherBody() instanceof Chest) {
            Chest chest = (Chest)e.getOtherBody();
            chest.fruit();
        }


        /* if (e.getOtherBody() instanceof DropperInteract) {
            DropperInteract drop = (DropperInteract)e.getReportingBody();
            Color col = drop.getFillColor();
            DroppingPlatform droppingplat = new DroppingPlatform(drop.getWorld(),drop.getWidth(),
                    drop.getHeight()*2, drop.getX() + drop.getHeight()/2, drop.getY());
            droppingplat.setFillColor(col);
            droppingplat.setLineColor(Color.black);
            drop.getDropper().destroy();
            drop.destroy();
        } */
    }
}