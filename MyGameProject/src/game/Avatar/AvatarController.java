package game.Avatar;

import org.jbox2d.common.Vec2;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import game.Entity.*;
import game.Structure.*;
import game.World.*;
import game.Game;

public class AvatarController implements KeyListener {

    private static final float WALKING_SPEED = 4 * 2;
    private static final float JUMP_HEIGHT = 10;

    private boolean z = true;

    //for when I know how to use a timer, and can time how long a button is held down for. The longer you move in a direction, the faster you go
    //public int speedmodifierleft = 1;
    //public int speedmodifierright = 1;

    private Avatar avatar;
    private Game game;
    private GameLevel gl;

    public AvatarController(Avatar a, Game g){
        avatar = a;
        game = g;
        gl = avatar.getLevel();
    }

    public void updateAvatar(Avatar a) { avatar = a; }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //avatar.yesMove();
        avatar.setGravityScale(avatar.getOriginalGravity());
        avatar.getHook().delete();
        avatar.resetHookTouch();
        avatar.resetHookSeek();
        int code = e.getKeyCode();
        // other key commands omitted

        if (code == KeyEvent.VK_SHIFT) { if (avatar.isBlue()) { avatar.sprintSpeed(); } }

        if (code == KeyEvent.VK_W) {
            if(avatar.atDoor() != 0) {
                // door.open()
                // make it wait 2 seconds
                System.out.println("avatar is at door: " + avatar.atDoor());
                game.goToLevel(avatar.atDoor());
            }
        }

        if (code == KeyEvent.VK_A) {
            //if (avatar.isBlue()) { avatar.startWalking(-WALKING_SPEED * 4); } else { avatar.startWalking(-WALKING_SPEED); }
            avatar.walkLeft();
            //StuckHookCollision.delete();
        } else if (code == KeyEvent.VK_D){
            //if (avatar.isBlue()) { avatar.startWalking(WALKING_SPEED * 4); } else { avatar.startWalking(WALKING_SPEED); }
            avatar.walkRight();
        } else if (code == KeyEvent.VK_S) {
            Vec2 currentvelocity = avatar.getLinearVelocity();
            currentvelocity.y = -JUMP_HEIGHT;
            if(avatar.isGreen()){ currentvelocity.y = -(JUMP_HEIGHT*2); }
            if((avatar.isBlue()) && (!avatar.isSprinting())){ currentvelocity.x = (currentvelocity.x)/2; }
            if(avatar.getFloorTouch()){ currentvelocity.x = 0; }
            float greeny = -(JUMP_HEIGHT*2);
            float bluex;
            if((avatar.isBlue()) && (!avatar.isSprinting())){ avatar.setLinearVelocity(new Vec2(currentvelocity.x, -(JUMP_HEIGHT*8))); }
            else if(avatar.isGreen()){ avatar.setLinearVelocity(new Vec2(currentvelocity.x, greeny)); }
            else { avatar.setLinearVelocity(new Vec2(currentvelocity.x, -JUMP_HEIGHT)); }
            //if(currentvelocity.y > 0) { avatar.setLinearVelocity(new Vec2(currentvelocity.x, -currentvelocity.y)); }

            //avatar.setLinearVelocity(new Vec2(currentvelocity.x, currentvelocity.y));
        }
        if (code == KeyEvent.VK_SPACE) {
            if(avatar.isBlue()){ avatar.jump(JUMP_HEIGHT*2); }
            else { avatar.jump(JUMP_HEIGHT); }
            avatar.setFloorTouch(false);
        }
        if(avatar.isYellow()) {
            if (code == KeyEvent.VK_Q) {
                Fireball fb = new Fireball(avatar.getLevel());
                fb.setPosition(new Vec2(avatar.getPosition().x - 0.1f, avatar.getPosition().y));
                fb.setLinearVelocity(new Vec2(-15, 0));
            }
            if (code == KeyEvent.VK_E) {
                Fireball fb = new Fireball(avatar.getLevel());
                fb.setPosition(new Vec2(avatar.getPosition().x + 0.1f, avatar.getPosition().y));
                fb.setLinearVelocity(new Vec2(15, 0));
            }
        }

        if (code == KeyEvent.VK_M) { if(gl.getMute()){ gl.soundUp(); gl.setMute(); } else{ gl.mute(); gl.setMute(); } }

        if (code == KeyEvent.VK_7) { avatar.setBlue(true); }
        if (code == KeyEvent.VK_8) { avatar.greenTrue(); }
        if (code == KeyEvent.VK_9) { avatar.setYellow(true); }
        if (code == KeyEvent.VK_0) { avatar.setBlue(false); avatar.greenFalse(); avatar.setYellow(false); }

        if (code == KeyEvent.VK_X) { if(avatar.getHeartNumber() == 1){avatar.die();} else{avatar.takeHearts();} }
        if (code == KeyEvent.VK_C) { if(avatar.getHeartNumber() < 5){avatar.addHearts();} }

        if (code == KeyEvent.VK_Z) { game.changeZoom(z, this); }
        if (code == KeyEvent.VK_P) { avatar.setPosition(new Vec2(1000,1000)); }
        if (code == KeyEvent.VK_O) { game.goToNextLevel(); } //avatar.setNextLVL(true); }
    }

    public void setZ() { z = !z; }

    // State 0 is powerless, red hearts
    // state 1 is power 1, blue, faster
    // state 2 is power 2, green, jump higher
    // state 3 is power 3, yellow, invulnerability
    // state 4 = dead
    // state 5 = states 1&2
    // state 6 = states 1&3
    // state 7 = states 2&3
    // state 8 = states 1&2&3

    @Override
    public void keyReleased(KeyEvent e) {
        //avatar.noMove();
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_SHIFT) { avatar.speedReset(); }
        if (code == KeyEvent.VK_A) { avatar.stopWalking(); }
        else if (code == KeyEvent.VK_D) { avatar.stopWalking(); }
    }
}
