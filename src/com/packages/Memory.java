package com.packages;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.*;

/**
 *
 * @author Tiago
 */

public class Memory implements ActionListener {

    JFrame Game;

    JPanel panelGame;
    JButton buttonCard;
    JLabel timerLabel = new JLabel("00:00");

    private int mins = 0;
    private int secs = 0;
    private int tabint = 4;

    private static final List<String> cards;

    static { //Création des cartes
        cards = new ArrayList<>();
        for(int i = 64; i < 96; ++i) {
            cards.add(Character.toString((char) i));
        }
    }

    public Memory(JFrame f) { //Création du JFrame
        f.setEnabled(false);

        Game = new JFrame("Game");
        Game.setVisible(true);
        Game.setSize(tabint*100, tabint*110);
        Game.setLayout(new GridLayout(tabint + 1, 1));
        Game.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        Game.setResizable(false);
        Game.setLocationRelativeTo(null); //Positionnement au milieu

        Game.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                f.setEnabled(true);
                Game.dispose();
            }
        });

        List<String> cardGame = new ArrayList<>(); //Tableau de cartes
        Collections.shuffle(cards); //Mélange des cartes
        for (int i = 0; i < tabint*tabint/2; ++i) {
            cardGame.add(cards.get(i));
            cardGame.add(cards.get(i));
        }
        Collections.shuffle(cardGame);

        int index = 0;
        for(int k = 0; k < tabint; ++k) {

            JPanel panel = new ImgPanel(new ImageIcon("images/wallpaper.jpg").getImage()); ; //Nouveau panel

            for (int i=0; i < tabint; ++i) {
                buttonCard = new JButton("");
                buttonCard.setBackground(Color.WHITE);
                buttonCard.setPreferredSize(new Dimension(65, 65));
                buttonCard.addActionListener(this);
                buttonCard.setActionCommand(cardGame.get(index++));
                panel.add(buttonCard);
            }
            Game.add(panel);
        }
        panelGame = new JPanel();
        panelGame.add(timerLabel);
        Game.add(panelGame);

    }
    private int precedentCard = 0;
    private int nbrclic = 0;
    private int cardopen = 0;
    private boolean timerStarted = false;
    private JButton tmpButton = null;
    private Thread TimerThread = null;

    @Override
    public void actionPerformed(ActionEvent e) {
        new Thread(new Runnable() {

            @Override
            public void run() { //Jeu

                if (!timerStarted) {
                    TimerThread = new Thread(new Time());
                    TimerThread.start();
                    timerStarted = true;
                }

                if(nbrclic >= 2) {
                    return;
                }
                ((JButton) e.getSource()).setEnabled(false);
                ((JButton) e.getSource()).setText(e.getActionCommand());

                if(precedentCard == 0) {
                    tmpButton = (JButton) e.getSource();
                    precedentCard = 1;
                    nbrclic = 1;
                } else if (precedentCard == 1) {
                    nbrclic = 2;
                    if(!tmpButton.getActionCommand().equals(e.getActionCommand())) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                        tmpButton.setText("");
                        ((JButton) e.getSource()).setText("");

                        tmpButton.setEnabled(true);
                        ((JButton) e.getSource()).setEnabled(true);
                    } else if (tmpButton.getActionCommand().equals(e.getActionCommand())){
                        ++cardopen;
                    }
                    nbrclic = 0;
                    precedentCard = 0;
                }

                if(finish()) {
                    TimerThread.interrupt();
                    JOptionPane.showMessageDialog(Game, "Bravo ! Vous avez gagner");
                }
            }
        }).start();

    }

    private class Time implements Runnable { //Calcul du temps

        @Override
        public void run() {
            for(;;) {
                if (Thread.interrupted()) return;
                timerLabel.setText(String.format("%02d", mins) + ":" + String.format("%02d", secs++));

                if (secs % 60 == 0) {
                    ++mins;
                    secs = 0;
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private boolean finish() {
        return cardopen == tabint*tabint/2;
    }
}