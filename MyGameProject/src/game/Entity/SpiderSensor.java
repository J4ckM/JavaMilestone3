package game.Entity;

import game.Avatar.*;
import game.Structure.*;
import game.World.*;
import city.cs.engine.*;

public class SpiderSensor extends Sensor {
    private Spider spider;
    private static final Shape spiderSensorShape = new PolygonShape(
            -1.808f,1.25f, -1.831f,0.217f, 1.62f,0.182f, 1.725f,1.227f);
    private static final BodyImage image = new BodyImage("data/spider.png", 2.5f);

    public SpiderSensor(Spider spider, World world){
        super(spider, spiderSensorShape);
        this.spider = spider;

        SpiderSensorListener spideysenselistener = new SpiderSensorListener(spider, world);
        addSensorListener(spideysenselistener);
    }

    public Spider getSpider() { return spider; }

    public void setSpider(Spider spider) { this.spider = spider; }
}