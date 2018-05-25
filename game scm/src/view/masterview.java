/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author acer
 */
public class masterview extends javax.swing.JFrame {

    Timer mytimer = new Timer();
    Timer mytimer2 = new Timer();
    boolean stopgame = false;
    int detik = 0;
    boolean loop[] = {false, false, false, false};
    boolean change[] = {false, false, false, false};
    int getdetik[] = {0, 0, 0, 0};
    int setdetikloop[] = {120, 120, 120, 120};
    //getdetik 0= mesinjadi1
    //getdetik 1= mesinjadi2
    //getdetik 2= mesin1
    //getdetik 3= mesin2

    public void setwindows(JFrame a) {
        a.setVisible(true);
        this.dispose();
    }

    public void start() {
//        mainMusik(musikmain);
        //sehari = 5 detik,perawatan = 15 detik
        mytimer.schedule(cek, 1000, 1000);//detik asli
    }

    public void stoptimer(boolean set) {
        stopgame = set;
    }
    TimerTask cek = new TimerTask() {
        @Override
        public void run() {
            if (!stopgame) {
                detik++;
                System.out.println("cek " + detik);
                for (int i = 0; i < 4; i++) {
                    if (loop[i]) {
                        loop[i] = false;
                        getdetik[i] = detik + setdetikloop[i];
                        System.out.println("get detik " + getdetik[0]);
                    }
                    if (getdetik[i] == detik) {
                        System.out.println("gantimesinjadi " + i);
                        //hapus image
                    }
                }
            }
        }
    };

    public void message(String txt) {
        JOptionPane.showMessageDialog(null, txt, "error", JOptionPane.OK_OPTION);
    }

    public void seticongif(String file, JLabel btn) {
        btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/" + file + ".gif")));
    }

    public void seticonpng(String file, JLabel btn) {
        btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/" + file + ".png")));
    }

    public void seticonpng(String file, JButton btn) {
        btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/" + file + ".png")));
    }

    public void setdelayimage(String lokasi, JLabel label, int pil) {
        seticongif(lokasi, label);
        loop[pil] = true;
    }

}
