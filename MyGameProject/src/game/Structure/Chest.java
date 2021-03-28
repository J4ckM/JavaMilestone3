package game.Structure;

import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;

import game.Avatar.*;

public class Chest extends StaticBody {
    private World w;
    //1 = red, 2 = blue, 3 = green, 4 = yellow
    private int fruit;
    private boolean touched;
    private float x;
    private float y;
    private String s;
    private int counter;
    private int coins;

    private static final Shape chestShape = new PolygonShape(
            -2.19f,0.07f,
            -1.59f,-1.48f,
            1.84f,-1.48f,
            2.18f,0.12f,
            1.74f,1.46f,
            -1.88f,1.34f);
    private static BodyImage image;

    public Chest(World w, float x, float y, int fruit, int coins) {
        super(w, chestShape);
        this.w = w;
        this.x = x;
        this.y = y;
        setPosition(new Vec2(x, y));

        this.coins = coins;
        counter = 0;

        s = "data/chests/chest" + fruit + ".png";
        if(fruit == 0){ image = new BodyImage("data/chests/coinchest.png", 3f); }
        else { image = new BodyImage(s, 3f); }

        addImage(image);

        this.fruit = fruit;
        touched = false;
    }

    //causes fruit to appear above the chest.
    public void fruit(){
        if(touched == false) {
            float fruity = (y + 4);
            if(fruit != 0) {
            /* if (fruit == 1) { FruitRed f = new FruitRed(w); f.setPosition(new Vec2(x, fruity)); }
            else if (fruit == 2) { FruitBlue f = new FruitBlue(w); f.setPosition(new Vec2(x, fruity)); }
            else if (fruit == 3) { FruitGreen f = new FruitGreen(w); f.setPosition(new Vec2(x, fruity)); }
            else { FruitYellow f = new FruitYellow(w); f.setPosition(new Vec2(x, fruity)); } */

                Fruit f = new Fruit(w, fruit);
                f.setFruit(fruit);
                f.setPosition(new Vec2(x, fruity));

                touched = true;

                removeAllImages();
                s = "data/chests/open-chest" + fruit + ".png";
                image = new BodyImage(s, 3f);
                addImage(image);
            }
            else{
                counter++;
                Coin c = new Coin(w, true, x, fruity);
                if(counter >= coins){
                    touched = true;
                    removeAllImages();
                    image = new BodyImage("data/chests/open-coinchest.png", 3f);
                    addImage(image);
                }
            }
        }
    }
}
