/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author acer
 */
public class masterview extends javax.swing.JFrame {

    public void setwindows(JFrame a) {
        a.setVisible(true);
        this.dispose();
    }
    public void seticongif(String file, JLabel btn) {
        btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/"+file+".gif")));
    }

    public void seticonpng(String file, JLabel btn) {
        btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/"+file+".png")));
    }
}
