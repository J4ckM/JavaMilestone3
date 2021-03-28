package game.Misc;

//A separate class to treat the timer event
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
// Other imports omitted

import city.cs.engine.World;
import game.Avatar.*;
import game.Entity.*;
import game.Structure.*;
import game.World.*;

class TimerHandler implements ActionListener{
    private World w;

    public TimerHandler(World w){
        this.w = w;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Random r = new Random();
        r.nextInt(4);

        //Fruit f = new Fruit(w, r);
        Fruit f = new Fruit(w, 4);
    }
}
