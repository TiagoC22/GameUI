package com.packages;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Tiago
 */

public class ImgPanel extends JPanel {

    private Image img;

    public ImgPanel(String img) {
        this(new ImageIcon(img).getImage());
    }

    public ImgPanel(Image img) {
        this.img = img;
    }

    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, this);
    }

}