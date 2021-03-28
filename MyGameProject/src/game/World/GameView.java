package game.World;

import city.cs.engine.UserView;
import game.Avatar.*;
import game.Entity.*;
import game.Structure.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.image.RenderedImage;

public class GameView extends UserView {
    private Image background;
    private RenderedImage heads;
    private GameLevel gl;
    private int viewx;
    private int viewy;

    public GameView(GameLevel gl, int width, int height) {
        super(gl, width, height);
        background = new ImageIcon("data/blue.png").getImage();
        this.gl = gl;
        viewx = width;
        viewy = height;

        if(gl instanceof Level1){
            Level1 l1 = (Level1) gl;
            l1.returnGameView(this);
        }
    }

    @Override
    protected void paintBackground(Graphics2D g) {
        g.drawImage(background, 0, 0, this);
    }

    @Override
    protected void paintForeground(Graphics2D g) { g.drawString("Coins: " + gl.getAvatar().getCoins(), 10, 20); }

    public int getViewX() { return getWidth(); }
    public int getViewY() { return getHeight(); }
}
