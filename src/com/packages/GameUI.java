package com.packages;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author Tiago
 */

public class GameUI implements ActionListener {

	JFrame frame;
	JButton buttonPlay;
	JButton buttonExit;


	public GameUI() {
		frame = new JFrame("Memory");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setSize(300, 80);

		JPanel contentPanel = new ImgPanel(new ImageIcon("images/interface.png").getImage()); ; //Nouveau panel

		buttonPlay = new JButton("Jouer");
		buttonPlay.setActionCommand("Jouer");
		buttonPlay.addActionListener(this);
		contentPanel.add(buttonPlay, BorderLayout.WEST);

		buttonExit = new JButton("Quitter");
		buttonExit.setActionCommand("Quitter");
		buttonExit.addActionListener(this);
		contentPanel.add(buttonExit, BorderLayout.EAST);

		frame.setContentPane(contentPanel);
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object  source = e.getSource();
			if  (source == buttonExit) {
				frame.dispose();

			} else if (source == buttonPlay) {

			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					new Memory(frame);
					frame.setVisible(false);
				}
			});
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new GameUI();
			}
		});
	}
}
