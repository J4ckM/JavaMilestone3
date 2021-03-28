package game.Avatar;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import game.Entity.*;
import game.Structure.*;
import game.World.*;

import static java.lang.Math.sqrt;

public class Hook extends DynamicBody {
    private static final Shape fireBallShape = new CircleShape(0.25f);
    private static final BodyImage image = new BodyImage("data/hook.png", 0.5f);

    private SolidFixture fx;
    private GameLevel gl;
    private Avatar avatar;

    public Hook(GameLevel gl) {
        super(gl);
        this.gl = gl;
        avatar = gl.getAvatar();

        fx = new SolidFixture(this, fireBallShape);
        addImage(image);
        delete();

        //stuckhook = new StuckHook(gl, getPosition());

        HookCollision hc = new HookCollision();
        addCollisionListener(hc);
    }

    public GameLevel getWorld(){ return gl; }
    public void delete(){
        setPosition(gl.boxCoords());
    }
    public void revive(Vec2 mouse, Vec2 apoint){
        float x = mouse.x - apoint.x;
        float y = mouse.y - apoint.y;
        float a = (float) ((20 * x) / (sqrt((x * x) + (y * y))));
        float b = (float) ((20 * y) / (sqrt((x * x) + (y * y))));

        if (apoint.x > mouse.x) { setPosition(new Vec2(apoint.x - 0.1f, apoint.y)); }
        else { setPosition(new Vec2(apoint.x + 0.1f, apoint.y)); }

        setLinearVelocity(new Vec2(a, b));
    }
}
