package game;

import city.cs.engine.*;

import javax.swing.JFrame;

import game.Avatar.*;
import game.Misc.*;
import game.World.*;
import org.jbox2d.common.Vec2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * A world with some bodies.
 */
public class Game {

    // /** The World in which the bodies move and interact. */
    //public GameWorld world;

    /**
     * The World in which the bodies move and interact.
     */
    public GameLevel level;

    /** A graphical display of the world (a specialised JPanel). */
    //private UserView view;
    private GameView view;

    private int viewx = 1000;
    private int viewy = 1000;

    private AvatarController controller;
    private JFrame debugView;
    private MouseHandler mh;

    private boolean playing = true;
    private boolean muted = false;

    /**
     * Gets levels passed.
     *
     * @return the levels passed
     */
    public int getLevelsPassed() { return levelspassed; }

    /**
     * Sets levels passed.
     *
     * @param levelspassed the levelspassed
     */
    public void setLevelsPassed(int levelspassed) { this.levelspassed = levelspassed; }

    private int levelspassed = 1;

    //save = which save you decide to choose
    private int save;

    /**
     * Instantiates a new Game.
     *
     * @throws IOException the io exception
     */
    public Game() throws IOException {
        // make the world
        level = new LobbyLevel(this);

        save = 1;
        //startLoadFiles();

        // make a view
        view = new GameView(level, viewx, viewy);
        view.setZoom(20);
        controller = new AvatarController(level.getAvatar(), this);
        view.addKeyListener(controller);

        mh = new MouseHandler(level, view);
        view.addMouseListener(mh);
        view.addMouseListener(new GiveFocus(view));

        if(level instanceof LobbyLevel) { ((LobbyLevel) level).addStepsListen(view); }

        // add the view to a frame (Java top level window)
        final JFrame frame = new JFrame("Basic world");
        frame.add(view);

        ControlPanel buttons = new ControlPanel(this);
        frame.add(buttons.getMainPanel(), BorderLayout.SOUTH);

        // enable the frame to quit the application
        // when the x button is pressed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        // don't let the frame be resized
        frame.setResizable(true);
        // size the frame to fit the world view
        frame.pack();
        // finally, make the frame visible
        frame.setVisible(true);

        // uncomment this to make a debugging view
        //debugView = new DebugViewer(level, viewx, viewy);

        // start our game world simulation!
        level.start();

        //explanation of what's going on
        explain();
    }

    /**
     * Gets the view width.
     *
     * @return view width
     */
    public int getViewX() { return view.getWidth(); }

    /**
     * Gets the view height.
     *
     * @return view height
     */
    public int getViewY() { return view.getHeight(); }

    /**
     * Gets game view object.
     *
     * @return gameview
     */
    public GameView getGameView() { return view; }

    /**
     * Change view zoom between 20 (normal) and 10 (zoomed out).
     *
     * @param z the boolean
     * @param a the AvatarController
     */
    public void changeZoom(boolean z, AvatarController a) {
        if(z) { view.setZoom(10); }
        else { view.setZoom(20); }
        a.setZ();
    }

    /**
     * Mutes the sound, called upon by a button.
     */
    public void mute() {
        if(!muted) { level.musicStop(); muted = true; }
        else { level.musicPlay(); muted = false;}
    }

    /**
     * Pauses the game, called upon by a button.
     */
    public void pause() {
        if(playing) { level.stop(); playing = false; level.musicStop(); }
        else { level.start(); playing = true; level.musicPlay(); }
    }

    private int avcoins;

    /**
     * Stores avatar attributes.
     */
    public void storeAvatar() { avcoins = level.getAvatar().getCoins(); }

    /**
     * Restores avatar attributes.
     */
    public void restoreAvatar() { level.getAvatar().setCoins(avcoins); }

    /**
     * Restarts the level you're on.
     */
    public void restart(){
        if((level instanceof Level1) || (level instanceof Level2) || (level instanceof Level3)){
            storeAvatar();

            level.musicStop();
            if (level instanceof Level1) { level.stop(); level = new Level1(this, view); }
            else if (level instanceof Level2) { level.stop(); level = new Level2(this, view); }
            else if (level instanceof Level3) { level.stop(); level = new Level3(this, view); }
            view.setWorld(level);
            controller.updateAvatar(level.getAvatar());
            level.start();

            restoreAvatar();
        }
    }

    /**
     * Sends player to the next level.
     */
    public void goToNextLevel(){
        storeAvatar();

        level.musicStop();
        //stop the current level
        if (level instanceof Level1){ level.stop(); level = new Level2(this, view); }
        else if (level instanceof Level2){ level.stop(); level = new Level3(this, view); }
        else if (level instanceof Level3){ level.stop(); level = new LobbyLevel(this, view); }
        /* else if (level instanceof FinalLevel){
            System.out.println("Well done! Game complete.");
            System.exit(0);
        } */

        //debugView = new DebugViewer(level, viewx, viewy);
        //change the view to look into new level
        view.setWorld(level);
        //change the controller to control the student in the new world
        controller.updateAvatar(level.getAvatar());
        //start the simulation in the new level
        level.start();

        restoreAvatar();
    }

    /**
     * Sends player to a specific level.
     *
     * @param levelnumber the levelnumber
     */
    public void goToLevel(int levelnumber){
        storeAvatar();

        level.musicStop();
        level.stop();
        if(levelnumber == 1) { level = new Level1(this, view); }
        if(levelnumber == 2) { level = new Level2(this, view); }
        if(levelnumber == 3) { level = new Level3(this, view); }
        //debugView = new DebugViewer(level, viewx, viewy);

        //change the view to look into new level
        view.setWorld(level);
        //change the controller to control the student in the new world
        controller.updateAvatar(level.getAvatar());

        level.start();

        restoreAvatar();
    }

    /**
     * Sends player to the Lobby.
     */
    public void lobby(){
        storeAvatar();

        level.musicStop();
        level.stop();
        level = new LobbyLevel(this, view);
        //debugView = new DebugViewer(level, viewx, viewy);

        //change the view to look into new level
        view.setWorld(level);
        //change the controller to control the student in the new world
        controller.updateAvatar(level.getAvatar());

        restoreAvatar();

        level.start();

        restoreAvatar();
    }

    /**
     * Explains the game itself in the terminal, using the file "Explanation".
     */
    public void explain() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/game/Misc/Explanation"))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
    Format for files. Only the numbers are in the actual file, but the positions are shown here:
    avatar position x = ?
    avatar position y = ?
    avatar hearts = ?
    avatar lives = ?
    blue state = ?
    green state = ?
    yellow state = ?
    current level = ?
     */

    /**
     * Writes current situation to the current saveFile.
     *
     * @throws IOException the io exception
     */
    public void saveCurrent() throws IOException {
        Avatar a = level.getAvatar();
        int cl = 0;
        if(level instanceof LobbyLevel){ cl = 0; }
        if(level instanceof Level1){ cl = 1; }
        if(level instanceof Level2){ cl = 2; }
        if(level instanceof Level3){ cl = 3; }

        boolean append = true;
        FileWriter writer = null;
        try {
            writer = new FileWriter("src/game/Misc/Save" + save);
            writer.write(a.getPosition().x + "\n");
            writer.write(a.getPosition().y + "\n");

            writer.write(a.getHeartNumber() + "\n");
            writer.write(a.getHeadNumber() + "\n");

            writer.write(a.isBlue() + "\n");
            writer.write(a.isGreen() + "\n");
            writer.write(a.isYellow() + "\n");

            writer.write(cl + "\n");
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * If not already, switches to save file 1, called upon by button.
     *
     * @throws IOException the io exception
     */
    public void save1() throws IOException { if(save != 1){ save = 1; load(1); } }

    /**
     * If not already, switches to save file 2, called upon by button.
     *
     * @throws IOException the io exception
     */
    public void save2() throws IOException { if(save != 2){ save = 2; load(2); } }

    /**
     * If not already, switches to save file 3, called upon by button.
     *
     * @throws IOException the io exception
     */
    public void save3() throws IOException { if(save != 3){ save = 3; load(3); } }

    /**
     * Loads the saveFile wanted.
     *
     * @param savetype the number of the save file in question
     * @throws IOException the io exception
     */
    public void load(int savetype) throws IOException {
        float ax = 0; float ay = 0;
        int h = 0; int lives = 0;
        boolean bs = false; boolean gs = false; boolean ys = false;
        int cl = 0;

        //reading text using BufferedReader
        /* FileReader fr = null;
        BufferedReader reader = null;
        try {
            fr = new FileReader("src/game/Misc/save1");
            reader = new BufferedReader(fr);
            String line = reader.readLine();
            for(int i = 0; i < 8; i++){
                line = reader.readLine();
                if(i == 0){ ax = Float.parseFloat(line); }
                if(i == 1){ ay = Float.parseFloat(line); }

                //if(i == 0){ ax = (float) Integer.parseInt(line); }
                //if(i == 1){ ay = (float) Integer.parseInt(line); }

                if(i == 2){ h = Integer.parseInt(line); }
                if(i == 3){ lives = Integer.parseInt(line); }

                if(i == 4){ bs = Boolean.parseBoolean(line); }
                if(i == 5){ gs = Boolean.parseBoolean(line); }
                if(i == 6){ ys = Boolean.parseBoolean(line); }

                if(i == 7){ cl = Integer.parseInt(line); }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (fr != null) {
                fr.close();
            }
        } */

        try (BufferedReader br = new BufferedReader(new FileReader("src/game/Misc/Save" + savetype))) {
            String line;
            for(int i = 0; i < 8; i++){
                line = br.readLine();
                if(i == 0){ ax = Float.parseFloat(line); }
                if(i == 1){ ay = Float.parseFloat(line); }

                //if(i == 0){ ax = (float) Integer.parseInt(line); }
                //if(i == 1){ ay = (float) Integer.parseInt(line); }

                if(i == 2){ h = Integer.parseInt(line); }
                if(i == 3){ lives = Integer.parseInt(line); }

                if(i == 4){ bs = Boolean.parseBoolean(line); }
                if(i == 5){ gs = Boolean.parseBoolean(line); }
                if(i == 6){ ys = Boolean.parseBoolean(line); }

                if(i == 7){ cl = Integer.parseInt(line); }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        level.musicStop();
        level.stop();
        if(cl == 0) { level = new LobbyLevel(this, view); }
        if(cl == 1) { level = new Level1(this, view); }
        if(cl == 2) { level = new Level2(this, view); }
        if(cl == 3) { level = new Level3(this, view); }

        Avatar a = level.getAvatar();
        //change the view to look into new level
        view.setWorld(level);
        //change the controller to control the student in the new world
        controller.updateAvatar(a);
        a.setPosition(new Vec2(ax,ay));
        a.setHeartnumber(h); a.setHeadnumber(lives);
        a.setBlue(bs); a.setYellow(ys);
        if(gs){ a.greenTrue(); } else { a.greenFalse(); }

        level.start();
    }

    /**
     * Clears the current save file, i.e. makes that file a completely new game, the start position.
     * It also resets the game for the player.
     *
     * @throws IOException the io exception
     */
    public void clearFile() throws IOException {
        FileWriter writer = null;
        try {
            writer = new FileWriter("src/game/Misc/Save" + save);
            writer.write(0 + "\n");
            writer.write(20 + "\n");

            writer.write(5 + "\n");
            writer.write(3 + "\n");

            writer.write(false + "\n");
            writer.write(false + "\n");
            writer.write(false + "\n");

            writer.write(0 + "\n");
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
        load(save);
    }

    /**
     * Run the game.  @param args the input arguments
     *
     * @throws IOException the io exception
     */
    public static void main(String[] args) throws IOException {

        new Game();
    }
}
