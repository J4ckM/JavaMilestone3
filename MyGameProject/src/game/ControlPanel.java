package game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import game.Game;
import game.Avatar.*;
import game.Entity.*;
import game.Misc.*;
import game.Structure.*;
import game.World.*;

public class ControlPanel {
    private JPanel mainPanel;
    private JButton quitButton;
    private JButton nextLevelButton;
    private JButton muteButton;
    private JButton pausePlayButton;
    private JButton lobbyButton;
    private JButton restartButton;
    private JButton save1Button;
    private JButton save2Button;
    private JButton save3Button;
    private JButton saveButton;
    private JButton clearButton;
    private Game game;

    public ControlPanel(Game game){
        this.game = game;
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { System.exit(0); }
        });
        nextLevelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { game.goToNextLevel(); }
        });
        /* saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { game.goToNextLevel(); }
        }); */
        lobbyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { game.lobby(); }
        });
        pausePlayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { game.pause(); }
        });
        muteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { game.mute(); }
        });
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { game.restart(); }
        });

        save1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    game.save1();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        save2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    game.save2();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        save3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    game.save3();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    game.saveCurrent();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    game.clearFile();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }

    public JPanel getMainPanel(){
        return mainPanel;
    }
}
