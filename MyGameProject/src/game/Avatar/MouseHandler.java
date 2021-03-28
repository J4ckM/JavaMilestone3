package game.Avatar;

import org.jbox2d.common.Vec2;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import game.Entity.*;
import game.Structure.*;
import game.World.*;

import static java.lang.Thread.sleep;

public class MouseHandler extends MouseAdapter {

    //private static final float RADIUS = 1f;

    //private static final Shape ballShape = new CircleShape(RADIUS);

    //private static final BodyImage ballImage
            //= new BodyImage("data/books.png", 2*RADIUS);
            //= new BodyImage("data/fruit/apple-0.png", 2*RADIUS);

    //private GameWorld world;
    private GameLevel level;
    private GameView view;
    private Avatar avatar;

    private int counter;
    private Hook hk;

    public MouseHandler(GameLevel l, GameView v) {
        level = l;
        view = v;
        avatar = level.getAvatar();
        counter = 0;
        this.hk = avatar.getHook();
    }

    public void update(GameLevel level){
        this.level = level;
    }

    /**
     * Create a bowling ball at the current mouse position.
     * @param e event object containing the mouse position
     */
    //public void mousePressed(MouseEvent e) {
        //DynamicBody ball = new DynamicBody(view.getWorld(), ballShape);
        //ball.setPosition(view.viewToWorld(e.getPoint()));
        //ball.addImage(ballImage);
    //}
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == 1) {
            Point mousePoint = e.getPoint();
            Vec2 worldPoint = view.viewToWorld(mousePoint);

            /* float ax = avatar.getPosition().x;
            float ay = avatar.getPosition().y;
            float mx = worldPoint.x;
            float my = worldPoint.y;

            // Only useful for angled shots
            float x = mx - ax;
            float y = my - ay;
            float a = (float) ((20 * x) / (sqrt((x * x) + (y * y))));
            float b = (float) ((20 * y) / (sqrt((x * x) + (y * y)))); */

            //hk = new Hook(level);
            //hk.setUpFixture();

            hk.revive(worldPoint, avatar.getPosition());


            //For angled shots
            /* if (ax > mx) {
                hk.setPosition(new Vec2(ax - 0.1f, avatar.getPosition().y));
            } else {
                hk.setPosition(new Vec2(ax + 0.1f, avatar.getPosition().y));
            }

            hk.setLinearVelocity(new Vec2(a, b)); */

        }
        //note: button 3 is the right click, button 2 is mouse wheel
        //if (e.getButton() == 3) { hk.delete(); }
    }

    //we have to implement the other methods to satisfy
    //the interface, but we can leave them empty.
    @Override
    public void mouseClicked(MouseEvent e) {
    }
    @Override
    public void mouseReleased(MouseEvent e) {
    }
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    @Override
    public void mouseExited(MouseEvent e) {
    }
}
