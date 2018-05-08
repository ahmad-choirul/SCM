package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Gerak extends JPanel {

    private Image gambarpanel;//variabel image untuk menampung gambar dan di load ke jpanel
    private Image gambarpanel2;//variabel image untuk menampung gambar dan di load ke jpanel
    int x = 0;//koordinat x gambar1
    boolean kanan = true;

    public Gerak() throws InterruptedException {
        System.out.println("masuk ke gerak");
        gambarpanel = new ImageIcon(getClass().getResource("car.png")).getImage();
        gambarpanel2 = new ImageIcon(getClass().getResource("car2.png")).getImage();
        setFocusable(true);
        while (true) {
            this.move();
            this.repaint();
            Thread.sleep(5);
        }
    }

    public void move() {
        if (kanan == true) {
            System.out.println("ke kanan");
            x++;
        }
        if (kanan == false) {
            System.out.println("ke kiri");
            x--;
        }
        if (x == 0) {
            kanan = true;
        }
        if (x == 600) {
            kanan = false;
        }
        System.out.println("x = " + x);
    }

    public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        move();
        if (kanan) {
            g2d.drawImage(gambarpanel, x, 0, 80, 35, null);

        } else {
            g2d.drawImage(gambarpanel2, x, 0, 80, 35, null);
        }
        g2d.dispose();
//        g2d.fillRect(x, y, 60, 10);
    }

}
