package game.Avatar;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import org.jbox2d.common.Vec2;

import game.Entity.*;
import game.Structure.*;
import game.World.*;

import static java.lang.Math.sqrt;

public class HookCollision implements CollisionListener {
    private Vec2 hookPos;
    private Avatar avatar;
    //private StuckHook stuckhook;

    public HookCollision(){ }

    //in this class,
    // "this" is used to refer to the reporting Body
    // "that is used to refer to the Other Body

    @Override
    public void collide(CollisionEvent e) {
        Hook hk = (Hook)e.getReportingBody();
        avatar = hk.getWorld().getAvatar();
        if (e.getOtherBody() instanceof Case) { }
        else if ((e.getOtherBody() instanceof Wall) || (e.getOtherBody() instanceof Platform) || (e.getOtherBody() instanceof Chest)) {

            hk.setGravityScale(0);
            hk.setLinearVelocity(new Vec2(0, 0));

            hookPos = hk.getPosition();
            //hk.delete();

            // Only useful for angled shots
            float x = hookPos.x - avatar.getPosition().x;
            float y = hookPos.y - avatar.getPosition().y;
            float a = (float) ((20 * x) / (sqrt((x * x) + (y * y))));
            float b = (float) ((20 * y) / (sqrt((x * x) + (y * y))));

            //if(hk.getReact()) { avatar.setLinearVelocity(new Vec2(a, b)); }
            avatar.setGravityScale(0);
            avatar.setLinearVelocity(new Vec2(a, b));
            avatar.setHookSeek();
        }
        else if (e.getOtherBody() instanceof Avatar) {
            if(avatar.getHookTouch()) { avatar.setLinearVelocity(new Vec2(0, 0)); hk.delete(); }
            avatar.setHookTouch();
            avatar.resetHookSeek();
        }
        else { hk.delete(); avatar.resetHookTouch(); }
    }

    public Vec2 getHookPos(){ return hookPos; }
}