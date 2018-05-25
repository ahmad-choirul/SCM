/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.sql.SQLException;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import model.mbarang;
import model.muser;

/**
 *
 * @author acer
 */
public class map extends masterview {

    /**
     * Creates new form play
     */
    String id;
    mbarang modelbarang;
    muser modeluser;
    byte stattampilmesin = 1;
    byte pillihidmesin;
    boolean gerakmobil = false;
    boolean kanan = true;
    String produkproduksi[] = {null, null, null, null};
    int locx = 700;
    boolean cekjalanawal = false;
    boolean belibarang = false;
    byte pilsawah = 0;
    byte kecepatan = 1;
    int sawah1 = 0;
    int sawah2 = 0;
    boolean waktusawah1 = false;
    boolean waktusawah2 = false;
    String tanam = "";
    String[] tanamsawah = {"", ""};
    double hargabeli = 0;

    public map(String id) {
        initComponents();
        panelgerak.setLocation(locx, 640);
        this.setVisible(true);
        start();
        System.out.println("id = " + id);
        setpopupvisibelfalse();
        this.id = id;
        try {
            modeluser = new muser();
            modelbarang = new mbarang();
//            setupgradebarang();

        } catch (SQLException ex) {
            Logger.getLogger(map.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setpopupvisibelfalse() {
        upgrademesin.setVisible(false);
        popupmesinjadi.setVisible(false);
        popuppanentanaman.setVisible(false);
    }

    public void setupgradebarang() throws SQLException {
        String data[] = modeluser.getupdatebarang(id);
        for (int i = 0; i < 4; i++) {
            setdetikloop[i] = Integer.parseInt(data[i + 1]);
        }
    }

    public void start() {
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
                if (waktusawah1) {
                    if (tanam.equalsIgnoreCase("jagung")) {
                        System.out.println("tanam jagung sawah 1");
                        sawah1 = detik + 100;
                        seticonpng("jagung1", txtsawah1);
                    }
                    if (tanam.equalsIgnoreCase("gandum")) {
                        System.out.println("tanam gandum sawah 1");
                        sawah1 = detik + 10;
                        seticonpng("gandum1", txtsawah1);
                    }
                    tanam = "";
                    waktusawah1 = false;
                }
                if (waktusawah2) {
                    if (tanam.equalsIgnoreCase("jagung")) {
                        System.out.println("tanam jagung sawah 2");
                        sawah2 = detik + 100;
                        seticonpng("jagung1", txtsawah2);
                    }
                    if (tanam.equalsIgnoreCase("gandum")) {
                        System.out.println("tanam gandum sawah 2");
                        sawah2 = detik + 10;
                        seticonpng("gandum1", txtsawah2);
                    }
                    tanam = "";
                    waktusawah2 = false;
                }
                setstatsawah();
                if (sawah1 == detik) {
                    sawah1 = -1;
                }
                if (sawah2 == detik) {
                    sawah2 = -1;
                }
                if (sawah1 > detik) {
                    txtwaktusawah1.setText(sawah1 - detik + "");
                }
                if (sawah2 > detik) {
                    txtwaktusawah2.setText(sawah2 - detik + "");
                }
                for (int i = 0; i < 4; i++) {
                    if (loop[i]) {
                        System.out.println("ada yang true");
                        loop[i] = false;
                        getdetik[i] = detik + setdetikloop[i];
                        System.out.println("get detik " + getdetik[0]);
                    }
                    if (getdetik[i] == detik) {
                        seticonpngloop(i);
                        tambahproduksi(produkproduksi[i]);
                        produkproduksi[i] = "0";
                        System.out.println("ganti png");
                    }
                    if (gerakmobil) {
                        if (!cekjalanawal) {
                            mytimer2.schedule(movecar, 0, 100);//movecar
                            cekjalanawal = true;
                        }
                        belibarang = true;
                        gerakmobil = false;
                        kanan = true;
                    }
                }
            }
        }
    };
    TimerTask movecar = new TimerTask() {
        @Override
        public void run() {
            if (belibarang) {
                setgerakpanel();
            }
        }
    };

    public void setstatsawah() {
        if (tanamsawah[0].equalsIgnoreCase("jagung")) {
            if (sawah1 - 50 == detik) {
                seticonpng("jagung2", txtsawah1);
            }
            if (sawah1 - 5 == detik) {
                seticonpng("jagung3", txtsawah1);
            }
        }
        if (tanamsawah[0].equalsIgnoreCase("gandum")) {
            if (sawah1 - 30 == detik) {
                seticonpng("gandum2", txtsawah1);
            }
            if (sawah1 - 5 == detik) {
                seticonpng("gandum3", txtsawah1);
            }
        }
        if (tanamsawah[1].equalsIgnoreCase("jagung")) {
            if (sawah2 - 50 == detik) {
                seticonpng("jagung2", txtsawah2);
            }
            if (sawah2 - 5 == detik) {
                seticonpng("jagung3", txtsawah2);
            }
        }
        if (tanamsawah[0].equalsIgnoreCase("gandum")) {
            if (sawah2 - 30 == detik) {
                seticonpng("gandum2", txtsawah2);
            }
            if (sawah2 - 5 == detik) {
                seticonpng("gandum3", txtsawah2);
            }
        }
    }

    public void tambahproduksi(String idproduksi) {
        if (idproduksi.equalsIgnoreCase("1")) {
            //proses turbo
            modelbarang.tambahproduksi("bjturbo", "400", id);
        }
        if (idproduksi.equalsIgnoreCase("2")) {
            //proses keju
            modelbarang.tambahproduksi("bjserealkeju", "400", id);
        }
        if (idproduksi.equalsIgnoreCase("3")) {
            //proses coklat
            modelbarang.tambahproduksi("bjserealcoklat", "400", id);
        }
    }

    public void setgerakpanel() {

        if (kanan) {
            locx = locx + kecepatan;
            panelgerak.setLocation(locx, 670);
        }
        if (!kanan) {
            locx = locx - kecepatan;
            panelgerak.setLocation(locx, 670);
        }
        if (panelgerak.getX() > 1200) {
            seticonpng("mobil2", labelmobil);
            kanan = false;
        }
        if (panelgerak.getX() < 700) {
            message("Pesanan berhasil");
            seticonpng("mobil", labelmobil);
            belibarangdarikota();
            setgudangppic();
            setenabelbtnbeli(true);
            belibarang = false;
        }
    }

    public void seticonpngloop(int i) {
        System.out.println("seticonpngloop");
        if (i == 2) {
            seticonpng("mesinjadi", labelmesinjadi1);
        }
        if (i == 3) {
            seticonpng("mesinjadi", labelmesinjadi2);
        }
    }

    public void setgudangfinish() {
        try {
            String data[] = modelbarang.cekbarang(id);
            txtserealcoklat.setText(data[13]);
            txtserealkeju.setText(data[14]);
            txtturbo.setText(data[15]);
        } catch (SQLException ex) {
            Logger.getLogger(map.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setgudangppic() {
        txtbelicoklat.setText("0");
        txtbeligandum.setText("0");
        txtbeligula.setText("0");
        txtbelijagung.setText("0");
        txtbelikeju.setText("0");
        txtbeliplastik.setText("0");
        txtbelisusu.setText("0");
        try {
            String data[] = modelbarang.cekbarang(id);
            txtbbjagung.setText(data[5]);
            txtbbgandum.setText(data[6]);
            txtbbsusu.setText(data[7]);
            txtbbgula.setText(data[8]);
            txtbmcoklat.setText(data[9]);
            txtbmplastik.setText(data[10]);
            txtbmkeju.setText(data[11]);
            txtbbjagung1.setText(data[5]);
            txtbbgandum1.setText(data[6]);
            txtbbsusu1.setText(data[7]);
            txtbbgula1.setText(data[8]);
            txtbmcoklat1.setText(data[9]);
            txtbmplastik1.setText(data[10]);
            txtbmkeju1.setText(data[11]);
            txtuang.setText(data[4]);
        } catch (SQLException ex) {
            Logger.getLogger(map.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        upgrademesin = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        updatemesin1 = new javax.swing.JButton();
        updatemesin2 = new javax.swing.JButton();
        updatemesinjadi1 = new javax.swing.JButton();
        updatemesinjadi2 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        panelutama = new diu.swe.habib.JPanelSlider.JPanelSlider();
        panelmap = new javax.swing.JPanel();
        btnparik = new javax.swing.JButton();
        btnsawah = new javax.swing.JButton();
        btnmarketing = new javax.swing.JButton();
        btngudangppic = new javax.swing.JButton();
        btngudangfinsih = new javax.swing.JButton();
        btnexit = new javax.swing.JButton();
        background = new javax.swing.JLabel();
        panelmarketing = new javax.swing.JPanel();
        btnmap2 = new javax.swing.JButton();
        panelantri = new javax.swing.JPanel();
        antri1 = new diu.swe.habib.JPanelSlider.JPanelSlider();
        antri2 = new diu.swe.habib.JPanelSlider.JPanelSlider();
        antri3 = new diu.swe.habib.JPanelSlider.JPanelSlider();
        background3 = new javax.swing.JLabel();
        panelsawah = new javax.swing.JPanel();
        txtwaktusawah2 = new javax.swing.JLabel();
        txtwaktusawah1 = new javax.swing.JLabel();
        popuppanentanaman = new javax.swing.JPanel();
        btnsawah2 = new javax.swing.JButton();
        btnsawah1 = new javax.swing.JButton();
        popuppiltanaman = new javax.swing.JPanel();
        jagung = new javax.swing.JButton();
        gandum = new javax.swing.JButton();
        btnmap4 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        txtsawah2 = new javax.swing.JButton();
        txtsawah1 = new javax.swing.JButton();
        txtbibitjagung = new javax.swing.JLabel();
        txtbibitgandum = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        background5 = new javax.swing.JLabel();
        panelgudangppic = new javax.swing.JPanel();
        txtuang = new javax.swing.JLabel();
        panelgerak = new javax.swing.JPanel();
        labelmobil = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnbelijagung = new javax.swing.JButton();
        btnbeligandum = new javax.swing.JButton();
        btnbelisusu = new javax.swing.JButton();
        btnbeligula = new javax.swing.JButton();
        btnbelicoklat = new javax.swing.JButton();
        btnbelikeju = new javax.swing.JButton();
        btnbeliplastik = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txtbeliplastik = new javax.swing.JLabel();
        txtbelijagung = new javax.swing.JLabel();
        txtbeligandum = new javax.swing.JLabel();
        txtbelisusu = new javax.swing.JLabel();
        txtbeligula = new javax.swing.JLabel();
        txtbelicoklat = new javax.swing.JLabel();
        txtbelikeju = new javax.swing.JLabel();
        jButton27 = new javax.swing.JButton();
        btnbelijagung1 = new javax.swing.JButton();
        btnbeligandum1 = new javax.swing.JButton();
        btnbelisusu1 = new javax.swing.JButton();
        btnbeligula1 = new javax.swing.JButton();
        btnbelicoklat1 = new javax.swing.JButton();
        btnbelikeju1 = new javax.swing.JButton();
        btnbeliplastik1 = new javax.swing.JButton();
        txthargatotal = new javax.swing.JLabel();
        btnmap3 = new javax.swing.JButton();
        panelbahan = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtbbjagung = new javax.swing.JLabel();
        txtbbgandum = new javax.swing.JLabel();
        txtbbsusu = new javax.swing.JLabel();
        txtbbgula = new javax.swing.JLabel();
        txtbmcoklat = new javax.swing.JLabel();
        txtbmkeju = new javax.swing.JLabel();
        txtbmplastik = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        upgrademobil = new javax.swing.JButton();
        txtlevelmobil = new javax.swing.JLabel();
        background4 = new javax.swing.JLabel();
        panelpabrik = new javax.swing.JPanel();
        panelutamapabrik = new diu.swe.habib.JPanelSlider.JPanelSlider();
        panel2 = new javax.swing.JPanel();
        popupmesinjadi = new javax.swing.JPanel();
        tmbproduksicoklat = new javax.swing.JButton();
        tmbproduksiturbo = new javax.swing.JButton();
        tmbproduksikeju = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();
        txtlevelmesin1 = new javax.swing.JLabel();
        txtlevelmesin2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        tmbmesinjadi2 = new javax.swing.JButton();
        tmbmesinjadi1 = new javax.swing.JButton();
        labelmesinjadi1 = new javax.swing.JLabel();
        labelmesinjadi2 = new javax.swing.JLabel();
        jButton14 = new javax.swing.JButton();
        btnmap1 = new javax.swing.JButton();
        panelbahan1 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtbbjagung1 = new javax.swing.JLabel();
        txtbbgandum1 = new javax.swing.JLabel();
        txtbbsusu1 = new javax.swing.JLabel();
        txtbbgula1 = new javax.swing.JLabel();
        txtbmcoklat1 = new javax.swing.JLabel();
        txtbmkeju1 = new javax.swing.JLabel();
        txtbmplastik1 = new javax.swing.JLabel();
        background2 = new javax.swing.JLabel();
        panelgudangfinish = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnmap = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        txtturbo = new javax.swing.JLabel();
        txtserealcoklat = new javax.swing.JLabel();
        txtserealkeju = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        background1 = new javax.swing.JLabel();

        upgrademesin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setText("update");
        upgrademesin.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(478, 5, -1, -1));

        updatemesin1.setText("mesin1");
        updatemesin1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updatemesin1ActionPerformed(evt);
            }
        });
        upgrademesin.add(updatemesin1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 120, -1, -1));

        updatemesin2.setText("mesin2");
        updatemesin2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updatemesin2ActionPerformed(evt);
            }
        });
        upgrademesin.add(updatemesin2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 120, -1, -1));

        updatemesinjadi1.setText("mesinjadi1");
        updatemesinjadi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updatemesinjadi1ActionPerformed(evt);
            }
        });
        upgrademesin.add(updatemesinjadi1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 120, -1, -1));

        updatemesinjadi2.setText("mesinjadi2");
        updatemesinjadi2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updatemesinjadi2ActionPerformed(evt);
            }
        });
        upgrademesin.add(updatemesinjadi2, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 130, -1, -1));

        jButton16.setText("ok");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        upgrademesin.add(jButton16, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 350, -1, -1));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new java.awt.CardLayout());

        panelmap.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnparik.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/pabrik1.png"))); // NOI18N
        btnparik.setBorderPainted(false);
        btnparik.setContentAreaFilled(false);
        btnparik.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnparik.setFocusPainted(false);
        btnparik.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/pabrik2.png"))); // NOI18N
        btnparik.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnparikMouseClicked(evt);
            }
        });
        btnparik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnparikActionPerformed(evt);
            }
        });
        panelmap.add(btnparik, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 320, 270, 240));

        btnsawah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/sawah1.png"))); // NOI18N
        btnsawah.setBorderPainted(false);
        btnsawah.setContentAreaFilled(false);
        btnsawah.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnsawah.setFocusPainted(false);
        btnsawah.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/sawah2.png"))); // NOI18N
        btnsawah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnsawahMouseClicked(evt);
            }
        });
        btnsawah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsawahActionPerformed(evt);
            }
        });
        panelmap.add(btnsawah, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 300, 290));

        btnmarketing.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/seller1.png"))); // NOI18N
        btnmarketing.setBorderPainted(false);
        btnmarketing.setContentAreaFilled(false);
        btnmarketing.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnmarketing.setFocusPainted(false);
        btnmarketing.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/seller2.png"))); // NOI18N
        btnmarketing.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnmarketingMouseClicked(evt);
            }
        });
        panelmap.add(btnmarketing, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 480, 300, 270));

        btngudangppic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/ppic1.png"))); // NOI18N
        btngudangppic.setBorderPainted(false);
        btngudangppic.setContentAreaFilled(false);
        btngudangppic.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btngudangppic.setFocusPainted(false);
        btngudangppic.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/ppic2.png"))); // NOI18N
        btngudangppic.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btngudangppicMouseClicked(evt);
            }
        });
        btngudangppic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btngudangppicActionPerformed(evt);
            }
        });
        panelmap.add(btngudangppic, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 90, 320, 290));

        btngudangfinsih.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/FG1.png"))); // NOI18N
        btngudangfinsih.setBorderPainted(false);
        btngudangfinsih.setContentAreaFilled(false);
        btngudangfinsih.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btngudangfinsih.setFocusPainted(false);
        btngudangfinsih.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/FG2.png"))); // NOI18N
        btngudangfinsih.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btngudangfinsihMouseClicked(evt);
            }
        });
        panelmap.add(btngudangfinsih, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 150, 290, 260));

        btnexit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/b_back.png"))); // NOI18N
        btnexit.setBorderPainted(false);
        btnexit.setContentAreaFilled(false);
        btnexit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnexit.setFocusPainted(false);
        btnexit.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/b_back2.png"))); // NOI18N
        btnexit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnexitMouseClicked(evt);
            }
        });
        btnexit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnexitActionPerformed(evt);
            }
        });
        panelmap.add(btnexit, new org.netbeans.lib.awtextra.AbsoluteConstraints(1230, 0, 140, 130));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/bgmap.png"))); // NOI18N
        panelmap.add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 0, 1360, 770));

        panelutama.add(panelmap, "card2");

        panelmarketing.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnmap2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/map1.png"))); // NOI18N
        btnmap2.setBorderPainted(false);
        btnmap2.setContentAreaFilled(false);
        btnmap2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnmap2.setFocusPainted(false);
        btnmap2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmap2ActionPerformed(evt);
            }
        });
        panelmarketing.add(btnmap2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 0, 130, 110));

        panelantri.setLayout(new java.awt.GridLayout(1, 0));

        antri1.setBorder(null);
        panelantri.add(antri1);

        antri2.setBorder(null);
        panelantri.add(antri2);

        antri3.setBorder(null);
        panelantri.add(antri3);

        panelmarketing.add(panelantri, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 530, 550, 180));

        background3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/bgmarket.png"))); // NOI18N
        panelmarketing.add(background3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1370, 770));

        panelutama.add(panelmarketing, "card3");

        panelsawah.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtwaktusawah2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtwaktusawah2.setForeground(new java.awt.Color(255, 255, 255));
        panelsawah.add(txtwaktusawah2, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 290, 80, 30));

        txtwaktusawah1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtwaktusawah1.setForeground(new java.awt.Color(255, 255, 255));
        panelsawah.add(txtwaktusawah1, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 290, 70, 30));

        popuppanentanaman.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                popuppanentanamanMouseClicked(evt);
            }
        });
        popuppanentanaman.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnsawah2.setText("sawah 2");
        btnsawah2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsawah2ActionPerformed(evt);
            }
        });
        popuppanentanaman.add(btnsawah2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 40, 110, 60));

        btnsawah1.setText("sawah 1");
        btnsawah1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsawah1ActionPerformed(evt);
            }
        });
        popuppanentanaman.add(btnsawah1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 108, 60));

        panelsawah.add(popuppanentanaman, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 290, 270, 130));

        popuppiltanaman.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jagung.setText("jagung");
        jagung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jagungActionPerformed(evt);
            }
        });
        popuppiltanaman.add(jagung, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 40, 110, 60));

        gandum.setText("gandum");
        gandum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gandumActionPerformed(evt);
            }
        });
        popuppiltanaman.add(gandum, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 108, 60));

        panelsawah.add(popuppiltanaman, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 480, 270, 130));

        btnmap4.setText("map");
        btnmap4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnmap4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmap4ActionPerformed(evt);
            }
        });
        panelsawah.add(btnmap4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1180, 660, 130, 90));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/tpanen1.png"))); // NOI18N
        jButton3.setBorderPainted(false);
        jButton3.setContentAreaFilled(false);
        jButton3.setFocusPainted(false);
        jButton3.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/tpanen2.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        panelsawah.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1200, 280, 150, 150));

        jButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/ttanam1.png"))); // NOI18N
        jButton15.setBorderPainted(false);
        jButton15.setContentAreaFilled(false);
        jButton15.setFocusPainted(false);
        jButton15.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/ttanam2.png"))); // NOI18N
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        panelsawah.add(jButton15, new org.netbeans.lib.awtextra.AbsoluteConstraints(1200, 470, 140, 140));

        txtsawah2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/sawah0.png"))); // NOI18N
        txtsawah2.setBorderPainted(false);
        txtsawah2.setContentAreaFilled(false);
        txtsawah2.setFocusPainted(false);
        txtsawah2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtsawah2ActionPerformed(evt);
            }
        });
        panelsawah.add(txtsawah2, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 270, 360, 370));

        txtsawah1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/sawah0.png"))); // NOI18N
        txtsawah1.setBorderPainted(false);
        txtsawah1.setContentAreaFilled(false);
        txtsawah1.setFocusPainted(false);
        txtsawah1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtsawah1ActionPerformed(evt);
            }
        });
        panelsawah.add(txtsawah1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 270, 360, 370));

        txtbibitjagung.setText("jLabel33");
        panelsawah.add(txtbibitjagung, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 50, 70, 30));

        txtbibitgandum.setText("jLabel34");
        panelsawah.add(txtbibitgandum, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 50, 60, 30));

        jLabel35.setText("gandum");
        panelsawah.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 10, 60, 30));

        jLabel36.setText("jagung");
        panelsawah.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 10, 70, 30));

        background5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/bgsawah.jpg"))); // NOI18N
        panelsawah.add(background5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1370, 770));

        panelutama.add(panelsawah, "card3");

        panelgudangppic.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtuang.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtuang.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtuang.setText("uang");
        panelgudangppic.add(txtuang, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 20, 170, 40));

        panelgerak.setOpaque(false);
        panelgerak.setLayout(new java.awt.CardLayout());

        labelmobil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/mobil.png"))); // NOI18N
        panelgerak.add(labelmobil, "card2");

        panelgudangppic.add(panelgerak, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 670, 120, 70));

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnbelijagung.setText("+");
        btnbelijagung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbelijagungActionPerformed(evt);
            }
        });
        jPanel1.add(btnbelijagung, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 170, 60, -1));

        btnbeligandum.setText("+");
        btnbeligandum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbeligandumActionPerformed(evt);
            }
        });
        jPanel1.add(btnbeligandum, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 310, 60, -1));

        btnbelisusu.setText("+");
        btnbelisusu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbelisusuActionPerformed(evt);
            }
        });
        jPanel1.add(btnbelisusu, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 180, 60, -1));

        btnbeligula.setText("+");
        btnbeligula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbeligulaActionPerformed(evt);
            }
        });
        jPanel1.add(btnbeligula, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 180, 60, -1));

        btnbelicoklat.setText("+");
        btnbelicoklat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbelicoklatActionPerformed(evt);
            }
        });
        jPanel1.add(btnbelicoklat, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 320, -1, -1));

        btnbelikeju.setText("+");
        btnbelikeju.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbelikejuActionPerformed(evt);
            }
        });
        jPanel1.add(btnbelikeju, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 320, -1, -1));

        btnbeliplastik.setText("+");
        btnbeliplastik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbeliplastikActionPerformed(evt);
            }
        });
        jPanel1.add(btnbeliplastik, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 320, -1, -1));

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/bb_jagung.png"))); // NOI18N
        jPanel1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, 90, 60));

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/bb_gandum.png"))); // NOI18N
        jPanel1.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 240, 90, 60));

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/bm_susu.png"))); // NOI18N
        jPanel1.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 90, 90, 60));

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/bm_gula.png"))); // NOI18N
        jPanel1.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 90, 90, 60));

        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/bm_coklat.png"))); // NOI18N
        jPanel1.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 230, 90, 60));

        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/bm_keju.png"))); // NOI18N
        jPanel1.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 230, 90, 60));

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/bm_plastik.png"))); // NOI18N
        jPanel1.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 230, 90, 60));

        txtbeliplastik.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtbeliplastik.setForeground(new java.awt.Color(255, 255, 255));
        txtbeliplastik.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtbeliplastik.setText("0");
        jPanel1.add(txtbeliplastik, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 310, 60, 30));

        txtbelijagung.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtbelijagung.setForeground(new java.awt.Color(255, 255, 255));
        txtbelijagung.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtbelijagung.setText("0");
        jPanel1.add(txtbelijagung, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 170, 60, 30));

        txtbeligandum.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtbeligandum.setForeground(new java.awt.Color(255, 255, 255));
        txtbeligandum.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtbeligandum.setText("0");
        jPanel1.add(txtbeligandum, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 300, 60, 30));

        txtbelisusu.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtbelisusu.setForeground(new java.awt.Color(255, 255, 255));
        txtbelisusu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtbelisusu.setText("0");
        jPanel1.add(txtbelisusu, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 170, 60, 30));

        txtbeligula.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtbeligula.setForeground(new java.awt.Color(255, 255, 255));
        txtbeligula.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtbeligula.setText("0");
        jPanel1.add(txtbeligula, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 170, 60, 30));

        txtbelicoklat.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtbelicoklat.setForeground(new java.awt.Color(255, 255, 255));
        txtbelicoklat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtbelicoklat.setText("0");
        jPanel1.add(txtbelicoklat, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 320, 60, 30));

        txtbelikeju.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtbelikeju.setForeground(new java.awt.Color(255, 255, 255));
        txtbelikeju.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtbelikeju.setText("0");
        jPanel1.add(txtbelikeju, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 310, 60, 30));

        jButton27.setText("pesan");
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton27, new org.netbeans.lib.awtextra.AbsoluteConstraints(1160, 350, 80, 80));

        btnbelijagung1.setText("-");
        btnbelijagung1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbelijagung1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnbelijagung1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 170, 60, -1));

        btnbeligandum1.setText("-");
        btnbeligandum1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbeligandum1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnbeligandum1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 310, 60, -1));

        btnbelisusu1.setText("-");
        btnbelisusu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbelisusu1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnbelisusu1, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 180, 60, -1));

        btnbeligula1.setText("-");
        btnbeligula1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbeligula1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnbeligula1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 180, 60, -1));

        btnbelicoklat1.setText("-");
        btnbelicoklat1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbelicoklat1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnbelicoklat1, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 320, -1, -1));

        btnbelikeju1.setText("-");
        btnbelikeju1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbelikeju1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnbelikeju1, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 320, -1, -1));

        btnbeliplastik1.setText("-");
        btnbeliplastik1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbeliplastik1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnbeliplastik1, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 320, -1, -1));

        txthargatotal.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txthargatotal.setForeground(new java.awt.Color(255, 255, 255));
        txthargatotal.setText("harga");
        jPanel1.add(txthargatotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 370, 180, 50));

        panelgudangppic.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 170, 1260, 440));

        btnmap3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/map1.png"))); // NOI18N
        btnmap3.setBorderPainted(false);
        btnmap3.setContentAreaFilled(false);
        btnmap3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnmap3.setFocusPainted(false);
        btnmap3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmap3ActionPerformed(evt);
            }
        });
        panelgudangppic.add(btnmap3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1230, 0, 130, 110));

        panelbahan.setOpaque(false);
        panelbahan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/bm_plastik.png"))); // NOI18N
        panelbahan.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 10, 90, 60));

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/bm_keju.png"))); // NOI18N
        panelbahan.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 10, 90, 60));

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/bm_coklat.png"))); // NOI18N
        panelbahan.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 10, 90, 60));

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/bm_gula.png"))); // NOI18N
        panelbahan.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, 90, 60));

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/bm_susu.png"))); // NOI18N
        panelbahan.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 90, 60));

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/bb_gandum.png"))); // NOI18N
        panelbahan.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 90, 60));

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/bb_jagung.png"))); // NOI18N
        panelbahan.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 90, 60));

        txtbbjagung.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panelbahan.add(txtbbjagung, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 70, 30));

        txtbbgandum.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panelbahan.add(txtbbgandum, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 80, 70, 30));

        txtbbsusu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panelbahan.add(txtbbsusu, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, 70, 30));

        txtbbgula.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panelbahan.add(txtbbgula, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 80, 70, 30));

        txtbmcoklat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panelbahan.add(txtbmcoklat, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 80, 70, 30));

        txtbmkeju.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panelbahan.add(txtbmkeju, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 90, 70, 30));

        txtbmplastik.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panelbahan.add(txtbmplastik, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 90, 70, 30));

        panelgudangppic.add(panelbahan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 140));

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/bgmapmini.png"))); // NOI18N
        panelgudangppic.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 670, 660, 80));

        upgrademobil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/upgrade.png"))); // NOI18N
        upgrademobil.setBorderPainted(false);
        upgrademobil.setContentAreaFilled(false);
        upgrademobil.setFocusPainted(false);
        upgrademobil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                upgrademobilActionPerformed(evt);
            }
        });
        panelgudangppic.add(upgrademobil, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 680, 90, 60));

        txtlevelmobil.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtlevelmobil.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtlevelmobil.setText("1");
        panelgudangppic.add(txtlevelmobil, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 700, 30, 20));

        background4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/market.png"))); // NOI18N
        panelgudangppic.add(background4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1360, 760));

        panelutama.add(panelgudangppic, "card3");

        panelpabrik.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelutamapabrik.setBorder(null);
        panelutamapabrik.setOpaque(false);

        panel2.setOpaque(false);
        panel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        popupmesinjadi.setOpaque(false);
        popupmesinjadi.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tmbproduksicoklat.setBorderPainted(false);
        tmbproduksicoklat.setContentAreaFilled(false);
        tmbproduksicoklat.setFocusPainted(false);
        tmbproduksicoklat.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/btn_serco.png"))); // NOI18N
        tmbproduksicoklat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tmbproduksicoklatActionPerformed(evt);
            }
        });
        popupmesinjadi.add(tmbproduksicoklat, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, 230, 100));

        tmbproduksiturbo.setBorderPainted(false);
        tmbproduksiturbo.setContentAreaFilled(false);
        tmbproduksiturbo.setFocusPainted(false);
        tmbproduksiturbo.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/btn_turbo.png"))); // NOI18N
        tmbproduksiturbo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tmbproduksiturboActionPerformed(evt);
            }
        });
        popupmesinjadi.add(tmbproduksiturbo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 240, 90));

        tmbproduksikeju.setBorderPainted(false);
        tmbproduksikeju.setContentAreaFilled(false);
        tmbproduksikeju.setFocusPainted(false);
        tmbproduksikeju.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/btn_serkeju.png"))); // NOI18N
        tmbproduksikeju.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tmbproduksikejuActionPerformed(evt);
            }
        });
        popupmesinjadi.add(tmbproduksikeju, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 230, 90));

        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/bgpopup.png"))); // NOI18N
        popupmesinjadi.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 300, 400));

        panel2.add(popupmesinjadi, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 20, 300, 400));

        txtlevelmesin1.setText("1");
        panel2.add(txtlevelmesin1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 390, -1, -1));

        txtlevelmesin2.setText("1");
        panel2.add(txtlevelmesin2, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 390, -1, -1));

        jButton1.setText("upgrade");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        panel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 390, -1, -1));

        jButton6.setText("upgrade");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        panel2.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 390, -1, -1));

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("mesin 2");
        panel2.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 30, 270, 30));

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("mesin 1");
        panel2.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 30, 270, 30));

        tmbmesinjadi2.setBorderPainted(false);
        tmbmesinjadi2.setContentAreaFilled(false);
        tmbmesinjadi2.setFocusPainted(false);
        tmbmesinjadi2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tmbmesinjadi2MouseClicked(evt);
            }
        });
        tmbmesinjadi2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tmbmesinjadi2ActionPerformed(evt);
            }
        });
        panel2.add(tmbmesinjadi2, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 100, 350, 330));

        tmbmesinjadi1.setBorderPainted(false);
        tmbmesinjadi1.setContentAreaFilled(false);
        tmbmesinjadi1.setFocusPainted(false);
        tmbmesinjadi1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tmbmesinjadi1MouseClicked(evt);
            }
        });
        tmbmesinjadi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tmbmesinjadi1ActionPerformed(evt);
            }
        });
        panel2.add(tmbmesinjadi1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 70, 350, 340));

        labelmesinjadi1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/mesinjadi.png"))); // NOI18N
        labelmesinjadi1.setToolTipText("");
        panel2.add(labelmesinjadi1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 70, 350, 340));

        labelmesinjadi2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/mesinjadi.png"))); // NOI18N
        labelmesinjadi2.setToolTipText("");
        panel2.add(labelmesinjadi2, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 70, 350, 340));

        panelutamapabrik.add(panel2, "card2");

        panelpabrik.add(panelutamapabrik, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 180, 980, 470));

        jButton14.setText("upgrade");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        panelpabrik.add(jButton14, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 30, 130, 60));

        btnmap1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/map1.png"))); // NOI18N
        btnmap1.setBorderPainted(false);
        btnmap1.setContentAreaFilled(false);
        btnmap1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnmap1.setFocusPainted(false);
        btnmap1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/map2.png"))); // NOI18N
        btnmap1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmap1ActionPerformed(evt);
            }
        });
        panelpabrik.add(btnmap1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1223, 10, 130, 110));

        panelbahan1.setOpaque(false);
        panelbahan1.setLayout(new java.awt.GridBagLayout());

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/bm_plastik.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 14;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 34;
        gridBagConstraints.ipady = -1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 40, 0, 0);
        panelbahan1.add(jLabel18, gridBagConstraints);

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/bm_keju.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = -1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 40, 0, 0);
        panelbahan1.add(jLabel19, gridBagConstraints);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/bm_coklat.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = -2;
        gridBagConstraints.ipady = -2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 30, 0, 0);
        panelbahan1.add(jLabel14, gridBagConstraints);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/bm_gula.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 46;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        panelbahan1.add(jLabel20, gridBagConstraints);

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/bm_susu.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 43;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 30, 0, 0);
        panelbahan1.add(jLabel10, gridBagConstraints);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/bb_gandum.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 23;
        gridBagConstraints.ipady = -1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 0, 0);
        panelbahan1.add(jLabel8, gridBagConstraints);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/bb_jagung.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 31;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 0, 0);
        panelbahan1.add(jLabel5, gridBagConstraints);

        txtbbjagung1.setForeground(new java.awt.Color(255, 255, 255));
        txtbbjagung1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 70;
        gridBagConstraints.ipady = 30;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 30, 35, 0);
        panelbahan1.add(txtbbjagung1, gridBagConstraints);

        txtbbgandum1.setForeground(new java.awt.Color(255, 255, 255));
        txtbbgandum1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 70;
        gridBagConstraints.ipady = 30;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 30, 35, 0);
        panelbahan1.add(txtbbgandum1, gridBagConstraints);

        txtbbsusu1.setForeground(new java.awt.Color(255, 255, 255));
        txtbbsusu1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 70;
        gridBagConstraints.ipady = 30;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 40, 35, 0);
        panelbahan1.add(txtbbsusu1, gridBagConstraints);

        txtbbgula1.setForeground(new java.awt.Color(255, 255, 255));
        txtbbgula1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 70;
        gridBagConstraints.ipady = 30;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 20, 35, 0);
        panelbahan1.add(txtbbgula1, gridBagConstraints);

        txtbmcoklat1.setForeground(new java.awt.Color(255, 255, 255));
        txtbmcoklat1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 70;
        gridBagConstraints.ipady = 30;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 40, 35, 0);
        panelbahan1.add(txtbmcoklat1, gridBagConstraints);

        txtbmkeju1.setForeground(new java.awt.Color(255, 255, 255));
        txtbmkeju1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 70;
        gridBagConstraints.ipady = 30;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 50, 35, 0);
        panelbahan1.add(txtbmkeju1, gridBagConstraints);

        txtbmplastik1.setForeground(new java.awt.Color(255, 255, 255));
        txtbmplastik1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 14;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 70;
        gridBagConstraints.ipady = 30;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 50, 35, 0);
        panelbahan1.add(txtbmplastik1, gridBagConstraints);

        panelpabrik.add(panelbahan1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 970, 140));

        background2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/bgpabrik.jpg"))); // NOI18N
        panelpabrik.add(background2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1370, 770));

        panelutama.add(panelpabrik, "card3");

        panelgudangfinish.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/bjserealkeju.png"))); // NOI18N
        panelgudangfinish.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 310, 160, 150));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/bjturbo.png"))); // NOI18N
        panelgudangfinish.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 310, 140, 150));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/bjserealcoklat.png"))); // NOI18N
        panelgudangfinish.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 310, 110, 150));

        btnmap.setText("map");
        btnmap.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnmap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmapActionPerformed(evt);
            }
        });
        panelgudangfinish.add(btnmap, new org.netbeans.lib.awtextra.AbsoluteConstraints(1223, 10, 130, 90));
        panelgudangfinish.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 520, 150, 70));
        panelgudangfinish.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 520, 150, 70));
        panelgudangfinish.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 520, 140, 70));

        txtturbo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtturbo.setText("jLabel4");
        panelgudangfinish.add(txtturbo, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 480, 80, 30));

        txtserealcoklat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtserealcoklat.setText("jLabel4");
        panelgudangfinish.add(txtserealcoklat, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 480, 80, 30));

        txtserealkeju.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtserealkeju.setText("jLabel4");
        panelgudangfinish.add(txtserealkeju, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 480, 80, 30));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        panelgudangfinish.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, 180, 300));

        background1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/bggudang.jpg"))); // NOI18N
        panelgudangfinish.add(background1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1370, 770));

        panelutama.add(panelgudangfinish, "card3");

        getContentPane().add(panelutama, "card8");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnsawahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnsawahMouseClicked
//        setwindows(new sawah());
        panelutama.nextPanel(25, panelsawah, panelutama.right);
    }//GEN-LAST:event_btnsawahMouseClicked

    private void btngudangppicMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btngudangppicMouseClicked

    }//GEN-LAST:event_btngudangppicMouseClicked

    private void btnmarketingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnmarketingMouseClicked
//        setwindows(new marketing());
        panelutama.nextPanel(25, panelmarketing, panelutama.right);
    }//GEN-LAST:event_btnmarketingMouseClicked

    private void btnparikMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnparikMouseClicked

    }//GEN-LAST:event_btnparikMouseClicked

    private void btngudangfinsihMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btngudangfinsihMouseClicked
//        setwindows(new gudangfinish());
        panelutama.nextPanel(25, panelgudangfinish, panelutama.right);
        setgudangfinish();
    }//GEN-LAST:event_btngudangfinsihMouseClicked

    private void btnexitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnexitMouseClicked
        setwindows(new mainmenu());
    }//GEN-LAST:event_btnexitMouseClicked

    private void btnparikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnparikActionPerformed
//        setwindows(new pabrik());
        setgudangppic();
        panelutama.nextPanel(25, panelpabrik, panelutama.right);
    }//GEN-LAST:event_btnparikActionPerformed

    private void btnmapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmapActionPerformed
//        setwindows(new map());
        panelutama.nextPanel(25, panelmap, panelutama.left);
    }//GEN-LAST:event_btnmapActionPerformed

    private void btnmap1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmap1ActionPerformed
//        setwindows(new map());
        panelutama.nextPanel(25, panelmap, panelutama.left);
    }//GEN-LAST:event_btnmap1ActionPerformed

    private void tmbmesinjadi1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tmbmesinjadi1MouseClicked
    }//GEN-LAST:event_tmbmesinjadi1MouseClicked

    private void btnmap2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmap2ActionPerformed
//        setwindows(new map());
        panelutama.nextPanel(25, panelmap, panelutama.left);
    }//GEN-LAST:event_btnmap2ActionPerformed

    private void btnmap3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmap3ActionPerformed
//        setwindows(new map());
        panelutama.nextPanel(25, panelmap, panelutama.left);
    }//GEN-LAST:event_btnmap3ActionPerformed

    private void btnmap4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmap4ActionPerformed
        //        setwindows(new map());
        panelutama.nextPanel(25, panelmap, panelutama.left);
    }//GEN-LAST:event_btnmap4ActionPerformed

    private void btngudangppicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btngudangppicActionPerformed
//        setwindows(new gudangppic());
        setgudangppic();
        panelutama.nextPanel(25, panelgudangppic, panelutama.right);
    }//GEN-LAST:event_btngudangppicActionPerformed

    private void tmbmesinjadi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmbmesinjadi1ActionPerformed
        popupmesinjadi.setVisible(true);
        cekproduksi();
        pillihidmesin = 2;
    }//GEN-LAST:event_tmbmesinjadi1ActionPerformed

    private void tmbmesinjadi2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tmbmesinjadi2MouseClicked

    }//GEN-LAST:event_tmbmesinjadi2MouseClicked

    private void tmbmesinjadi2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmbmesinjadi2ActionPerformed
        popupmesinjadi.setVisible(true);
        pillihidmesin = 3;
    }//GEN-LAST:event_tmbmesinjadi2ActionPerformed

    private void updatemesin1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updatemesin1ActionPerformed
        setdetikloop[0] -= 1;
    }//GEN-LAST:event_updatemesin1ActionPerformed

    private void updatemesin2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updatemesin2ActionPerformed
        setdetikloop[1] -= 1;
    }//GEN-LAST:event_updatemesin2ActionPerformed

    private void updatemesinjadi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updatemesinjadi1ActionPerformed
        setdetikloop[2] -= 1;
    }//GEN-LAST:event_updatemesinjadi1ActionPerformed

    private void updatemesinjadi2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updatemesinjadi2ActionPerformed
        setdetikloop[0] -= 1;
    }//GEN-LAST:event_updatemesinjadi2ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        upgrademesin.setVisible(true);
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        upgrademesin.setVisible(false);
    }//GEN-LAST:event_jButton16ActionPerformed

    private void btnbelijagungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbelijagungActionPerformed
        int get = Integer.parseInt(txtbelijagung.getText()) + 10;
        txtbelijagung.setText(get + "");
        hargabeli += 100;
        setbeli();
    }//GEN-LAST:event_btnbelijagungActionPerformed

    private void btnexitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnexitActionPerformed
        setwindows(new mainmenu());
        this.dispose();
    }//GEN-LAST:event_btnexitActionPerformed

    private void tmbproduksiturboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmbproduksiturboActionPerformed
        produkproduksi[pillihidmesin] = "1";
        modelbarang.jualbarang("bbjagung", "90", id);
        modelbarang.jualbarang("bbgula", "15", id);
        modelbarang.jualbarang("bbsusu", "10", id);
        modelbarang.jualbarang("bmplastik", "400", id);
        prosesmesin(pillihidmesin);
    }//GEN-LAST:event_tmbproduksiturboActionPerformed

    private void tmbproduksikejuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmbproduksikejuActionPerformed
        produkproduksi[pillihidmesin] = "2";
        modelbarang.jualbarang("bbgandum", "60", id);
        modelbarang.jualbarang("bbgula", "15", id);
        modelbarang.jualbarang("bbsusu", "25", id);
        modelbarang.jualbarang("bmkeju", "20", id);
        modelbarang.jualbarang("bbjagung", "10", id);
        modelbarang.jualbarang("bmplastik", "400", id);
        prosesmesin(pillihidmesin);
    }//GEN-LAST:event_tmbproduksikejuActionPerformed

    private void tmbproduksicoklatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmbproduksicoklatActionPerformed
        produkproduksi[pillihidmesin] = "3";
        modelbarang.jualbarang("bbgandum", "60", id);
        modelbarang.jualbarang("bbgula", "10", id);
        modelbarang.jualbarang("bbsusu", "20", id);
        modelbarang.jualbarang("bmcoklat", "30", id);
        modelbarang.jualbarang("bmplastik", "400", id);
        prosesmesin(pillihidmesin);
    }//GEN-LAST:event_tmbproduksicoklatActionPerformed

    private void btnbeligandumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbeligandumActionPerformed
        int get = Integer.parseInt(txtbeligandum.getText()) + 10;
        txtbeligandum.setText(get + "");
        hargabeli += 125;
        setbeli();
    }//GEN-LAST:event_btnbeligandumActionPerformed

    private void btnbelisusuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbelisusuActionPerformed
        int get = Integer.parseInt(txtbelisusu.getText()) + 5;
        txtbelisusu.setText(get + "");
        hargabeli += 75;
        setbeli();
    }//GEN-LAST:event_btnbelisusuActionPerformed

    private void btnbeligulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbeligulaActionPerformed
        int get = Integer.parseInt(txtbeligula.getText()) + 5;
        txtbeligula.setText(get + "");
        hargabeli += 50;
        setbeli();
    }//GEN-LAST:event_btnbeligulaActionPerformed

    private void btnbelicoklatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbelicoklatActionPerformed
        int get = Integer.parseInt(txtbelicoklat.getText()) + 5;
        txtbelicoklat.setText(get + "");
        hargabeli += 60;
        setbeli();
    }//GEN-LAST:event_btnbelicoklatActionPerformed

    private void btnbelikejuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbelikejuActionPerformed
        int get = Integer.parseInt(txtbelikeju.getText()) + 5;
        txtbelikeju.setText(get + "");
        hargabeli += 85;
        setbeli();
    }//GEN-LAST:event_btnbelikejuActionPerformed

    private void btnbeliplastikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbeliplastikActionPerformed
        int get = Integer.parseInt(txtbeliplastik.getText()) + 500;
        txtbeliplastik.setText(get + "");
        hargabeli += 90;
        setbeli();
    }//GEN-LAST:event_btnbeliplastikActionPerformed

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
//namakolom,jumlah barang tiap beli,harga sekali beli,id user
        setenabelbtnbeli(false);
        gerakmobil = true;
    }//GEN-LAST:event_jButton27ActionPerformed

    private void btnsawahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsawahActionPerformed
        setsawah();
        popuppiltanaman.setVisible(false);

    }//GEN-LAST:event_btnsawahActionPerformed

    private void txtsawah1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtsawah1ActionPerformed
        popuppiltanaman.setVisible(true);
        pilsawah = 0;
    }//GEN-LAST:event_txtsawah1ActionPerformed

    private void txtsawah2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtsawah2ActionPerformed
        popuppiltanaman.setVisible(true);
        pilsawah = 1;
    }//GEN-LAST:event_txtsawah2ActionPerformed

    private void upgrademobilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_upgrademobilActionPerformed
        int level = Integer.parseInt(txtlevelmobil.getText());
        if (level <= 3) {
            if (level == 1) {
                if (Integer.parseInt(txtuang.getText()) > 12000) {
                    int uang = Integer.parseInt(txtuang.getText()) - 12000;
                    txtuang.setText(uang + "");
                    modelbarang.belibarang("bmplastik", "0", uang + "", id);
                    level++;
                    kecepatan += 2;
                    txtlevelmobil.setText(level + "");
                    message("upgrade mobil berhasil");
                } else {
                    message("uang tidak mencukupi \n membutuhkan 12000 koin");
                }
            } else if (level == 2) {
                if (Integer.parseInt(txtuang.getText()) > 20000) {
                    level++;
                    kecepatan += 2;
                    int uang = Integer.parseInt(txtuang.getText()) - 20000;
                    txtuang.setText(uang + "");
                    modelbarang.belibarang("bmplastik", "0", uang + "", id);
                    txtlevelmobil.setText(level + "");
                    message("upgrade mobil berhasil");
                } else {
                    message("uang tidak mencukupi \n membutuhkan 20000 koin");
                }
            }
        } else {
            message("upgrade mobil sudah maksimal");
        }
    }//GEN-LAST:event_upgrademobilActionPerformed

    private void gandumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gandumActionPerformed
        tanam = "gandum";
        if (sawah1 == 0 && tanamsawah[0].equalsIgnoreCase("")) {
            if (modelbarang.jualbarang("bbgandum", "30", id)) {
                System.out.println("berhasil tanam gandum dan kurang");
                tanamsawah[0] = tanam;
            }
            waktusawah1 = true;
        } else if (sawah2 == 0 && tanamsawah[1].equalsIgnoreCase("")) {
            if (modelbarang.jualbarang("bbgandum", "30", id)) {
                System.out.println("berhasil tanam gandum dan kurang");
                tanamsawah[1] = tanam;
            }
            waktusawah2 = true;
        } else {
            message("sawah penuh");
        }
        setsawah();
        popuppiltanaman.setVisible(false);
    }//GEN-LAST:event_gandumActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        popuppiltanaman.setVisible(true);
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jagungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jagungActionPerformed
        tanam = "jagung";
        if (sawah1 == 0 && tanamsawah[0].equalsIgnoreCase("")) {
            if (modelbarang.jualbarang("bbjagung", "45", id)) {
                System.out.println("berhasil tanam jagung dan kurang");
                tanamsawah[0] = tanam;

            }
            waktusawah1 = true;
        } else if (sawah2 == 0 && tanamsawah[1].equalsIgnoreCase("")) {
            if (modelbarang.jualbarang("bbjagung", "45", id)) {
                System.out.println("berhasil tanam jagung dan kurang");
                tanamsawah[1] = tanam;
            }
            waktusawah2 = true;
        } else {
            message("sawah penuh");
        }
        setsawah();
        popuppiltanaman.setVisible(false);
    }//GEN-LAST:event_jagungActionPerformed

    private void btnsawah2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsawah2ActionPerformed

        if (sawah2 == -1) {
            sawah2 = 0;
            if (tanamsawah[1].equalsIgnoreCase("jagung")) {

                modelbarang.tambahproduksi("bbjagung", "90", id);
            } else if (tanamsawah[1].equalsIgnoreCase("gandum")) {
                modelbarang.tambahproduksi("bbgandum", "60", id);

            }
            tanamsawah[1] = "";
            seticonpng("sawah0", txtsawah2);
            setsawah();
        } else {
            message("sawah belum siap panen");
        }
        popuppanentanaman.setVisible(false);
    }//GEN-LAST:event_btnsawah2ActionPerformed

    private void btnsawah1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsawah1ActionPerformed
        if (sawah1 == -1) {
            sawah1 = 0;
            if (tanamsawah[0].equalsIgnoreCase("jagung")) {
                modelbarang.tambahproduksi("bbjagung", "90", id);
                message("");
            } else if (tanamsawah[0].equalsIgnoreCase("gandum")) {
                modelbarang.tambahproduksi("bbgandum", "60", id);
            }
            tanamsawah[0] = "";
            seticonpng("sawah0", txtsawah1);
            setsawah();
        } else {
            message("sawah belum siap panen");
        }
        popuppanentanaman.setVisible(false);
    }//GEN-LAST:event_btnsawah1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        popuppanentanaman.setVisible(true);
        if (sawah1 == 0) {
            btnsawah1.setEnabled(false);
        } else {
            btnsawah1.setEnabled(true);
        }
        if (sawah2 == 0) {
            btnsawah2.setEnabled(false);
        } else {
            btnsawah2.setEnabled(true);
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void popuppanentanamanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_popuppanentanamanMouseClicked
        popuppanentanaman.setVisible(false);
    }//GEN-LAST:event_popuppanentanamanMouseClicked

    private void btnbelijagung1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbelijagung1ActionPerformed
        int get = Integer.parseInt(txtbelijagung.getText()) - 10;

        if (get >= 0) {
            txtbelijagung.setText(get + "");
            hargabeli -= 100;
        }
        setbeli();
    }//GEN-LAST:event_btnbelijagung1ActionPerformed

    private void btnbeligandum1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbeligandum1ActionPerformed
        int get = Integer.parseInt(txtbeligandum.getText()) - 10;

        if (get >= 0) {
            txtbeligandum.setText(get + "");
            hargabeli -= 125;
        }
        setbeli();
    }//GEN-LAST:event_btnbeligandum1ActionPerformed

    private void btnbelisusu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbelisusu1ActionPerformed
        int get = Integer.parseInt(txtbelisusu.getText()) - 5;
        if (get >= 0) {
            txtbelisusu.setText(get + "");
            hargabeli -= 75;
        }
        setbeli();
    }//GEN-LAST:event_btnbelisusu1ActionPerformed

    private void btnbeligula1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbeligula1ActionPerformed
        int get = Integer.parseInt(txtbeligula.getText()) - 5;
        if (get >= 0) {
            txtbeligula.setText(get + "");
            hargabeli -= 50;
        }
        setbeli();
    }//GEN-LAST:event_btnbeligula1ActionPerformed

    private void btnbelicoklat1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbelicoklat1ActionPerformed
        int get = Integer.parseInt(txtbelicoklat.getText()) - 5;
        if (get >= 0) {
            txtbelicoklat.setText(get + "");
            hargabeli -= 60;
        }
        setbeli();
    }//GEN-LAST:event_btnbelicoklat1ActionPerformed

    private void btnbelikeju1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbelikeju1ActionPerformed
        int get = Integer.parseInt(txtbelikeju.getText()) - 5;
        if (get >= 0) {
            txtbelikeju.setText(get + "");
            hargabeli -= 85;
        }
        setbeli();
    }//GEN-LAST:event_btnbelikeju1ActionPerformed

    private void btnbeliplastik1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbeliplastik1ActionPerformed
        int get = Integer.parseInt(txtbeliplastik.getText()) - 500;

        if (get >= 0) {
            txtbeliplastik.setText(get + "");
            hargabeli -= 90;
        }
        setbeli();
    }//GEN-LAST:event_btnbeliplastik1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int level = Integer.parseInt(txtlevelmesin1.getText());
        if (level == 1) {
            level++;
            txtlevelmesin1.setText(level + "");
            setdetikloop[2] -= 25;
            message("mesin 1 berhasil di upgrade");
        } else if (level == 2) {
            level++;
            txtlevelmesin1.setText(level + "");
            setdetikloop[2] -= 25;
            message("mesin 1 berhasil di upgrade");
        } else {
            message("upgrade sudah maksimal");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        int level = Integer.parseInt(txtlevelmesin2.getText());
        if (level == 1) {
            level++;
            txtlevelmesin2.setText(level + "");
            setdetikloop[3] -= 25;
            message("mesin 2 berhasil di upgrade");
        } else if (level == 2) {
            level++;
            txtlevelmesin2.setText(level + "");
            setdetikloop[3] -= 25;
            message("mesin 2 berhasil di upgrade");
        } else {
            message("upgrade sudah maksimal");
        }
    }//GEN-LAST:event_jButton6ActionPerformed
    public void setbeli() {
        if (hargabeli > Double.parseDouble(txtuang.getText())) {
            message("Uang tidak mencukupi");
        } else {
            txthargatotal.setText(hargabeli + "");
        }
    }

    public void setenabelbtnbeli(boolean bool) {
        btnbelicoklat.setEnabled(bool);
        btnbeligandum.setEnabled(bool);
        btnbeligula.setEnabled(bool);
        btnbelijagung.setEnabled(bool);
        btnbelikeju.setEnabled(bool);
        btnbeliplastik.setEnabled(bool);
        btnbelisusu.setEnabled(bool);
        upgrademobil.setEnabled(bool);
        btnbelicoklat1.setEnabled(bool);
        btnbeligandum1.setEnabled(bool);
        btnbeligula1.setEnabled(bool);
        btnbelijagung1.setEnabled(bool);
        btnbelikeju1.setEnabled(bool);
        btnbeliplastik1.setEnabled(bool);
        btnbelisusu1.setEnabled(bool);
        upgrademobil.setEnabled(bool);
    }

    public void belibarangdarikota() {
        System.out.println("beli barang");
        modelbarang.belibarang("bbjagung", txtbelijagung.getText(), "0", id);
        modelbarang.belibarang("bbgandum", txtbeligandum.getText(), "0", id);
        modelbarang.belibarang("bbsusu", txtbelisusu.getText(), "0", id);
        modelbarang.belibarang("bbgula", txtbeligula.getText(), "0", id);
        modelbarang.belibarang("bmcoklat", txtbelicoklat.getText(), "0", id);
        modelbarang.belibarang("bmkeju", txtbelikeju.getText(), "0", id);
        modelbarang.belibarang("bmplastik", txtbeliplastik.getText(), "0", id);
        modelbarang.belibarang("bmplastik", "0", hargabeli + "", id);
    }

    public void cekproduksi() {
        if (Integer.parseInt(txtbbjagung1.getText()) >= 90 && Integer.parseInt(txtbbgula1.getText()) >= 25 && Integer.parseInt(txtbbsusu1.getText()) >= 10 && Integer.parseInt(txtbmplastik1.getText()) >= 400) {
            tmbproduksiturbo.setEnabled(true);
        } else {
            tmbproduksiturbo.setEnabled(false);
        }
        if (Integer.parseInt(txtbbgandum1.getText()) >= 60 && Integer.parseInt(txtbbgula1.getText()) >= 15 && Integer.parseInt(txtbbsusu1.getText()) >= 25 && Integer.parseInt(txtbmkeju1.getText()) >= 25 && Integer.parseInt(txtbbjagung1.getText()) >= 10 && Integer.parseInt(txtbmplastik1.getText()) >= 400) {
            tmbproduksikeju.setEnabled(true);
        } else {
            tmbproduksikeju.setEnabled(false);
        }
        if (Integer.parseInt(txtbbgandum1.getText()) >= 60 && Integer.parseInt(txtbbgula1.getText()) >= 10 && Integer.parseInt(txtbbsusu1.getText()) >= 20 && Integer.parseInt(txtbmcoklat1.getText()) >= 30 && Integer.parseInt(txtbmplastik1.getText()) >= 400) {
            tmbproduksicoklat.setEnabled(true);
        } else {
            tmbproduksicoklat.setEnabled(false);
        }
    }

    public void prosesmesin(int idmesin) {
        setpopupvisibelfalse();
        switch (idmesin) {
            case 2:
                seticongif("mesinjadi", labelmesinjadi1);
                break;
            case 3:
                seticongif("mesinjadi", labelmesinjadi2);
                break;
            default:
                break;
        }
        loop[idmesin] = true;
        setgudangppic();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(map.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(map.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(map.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(map.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private diu.swe.habib.JPanelSlider.JPanelSlider antri1;
    private diu.swe.habib.JPanelSlider.JPanelSlider antri2;
    private diu.swe.habib.JPanelSlider.JPanelSlider antri3;
    private javax.swing.JLabel background;
    private javax.swing.JLabel background1;
    private javax.swing.JLabel background2;
    private javax.swing.JLabel background3;
    private javax.swing.JLabel background4;
    private javax.swing.JLabel background5;
    private javax.swing.JButton btnbelicoklat;
    private javax.swing.JButton btnbelicoklat1;
    private javax.swing.JButton btnbeligandum;
    private javax.swing.JButton btnbeligandum1;
    private javax.swing.JButton btnbeligula;
    private javax.swing.JButton btnbeligula1;
    private javax.swing.JButton btnbelijagung;
    private javax.swing.JButton btnbelijagung1;
    private javax.swing.JButton btnbelikeju;
    private javax.swing.JButton btnbelikeju1;
    private javax.swing.JButton btnbeliplastik;
    private javax.swing.JButton btnbeliplastik1;
    private javax.swing.JButton btnbelisusu;
    private javax.swing.JButton btnbelisusu1;
    private javax.swing.JButton btnexit;
    private javax.swing.JButton btngudangfinsih;
    private javax.swing.JButton btngudangppic;
    private javax.swing.JButton btnmap;
    private javax.swing.JButton btnmap1;
    private javax.swing.JButton btnmap2;
    private javax.swing.JButton btnmap3;
    private javax.swing.JButton btnmap4;
    private javax.swing.JButton btnmarketing;
    private javax.swing.JButton btnparik;
    private javax.swing.JButton btnsawah;
    private javax.swing.JButton btnsawah1;
    private javax.swing.JButton btnsawah2;
    private javax.swing.JButton gandum;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JButton jagung;
    private javax.swing.JLabel labelmesinjadi1;
    private javax.swing.JLabel labelmesinjadi2;
    private javax.swing.JLabel labelmobil;
    private javax.swing.JPanel panel2;
    private javax.swing.JPanel panelantri;
    private javax.swing.JPanel panelbahan;
    private javax.swing.JPanel panelbahan1;
    private javax.swing.JPanel panelgerak;
    private javax.swing.JPanel panelgudangfinish;
    private javax.swing.JPanel panelgudangppic;
    private javax.swing.JPanel panelmap;
    private javax.swing.JPanel panelmarketing;
    private javax.swing.JPanel panelpabrik;
    private javax.swing.JPanel panelsawah;
    private diu.swe.habib.JPanelSlider.JPanelSlider panelutama;
    private diu.swe.habib.JPanelSlider.JPanelSlider panelutamapabrik;
    private javax.swing.JPanel popupmesinjadi;
    private javax.swing.JPanel popuppanentanaman;
    private javax.swing.JPanel popuppiltanaman;
    private javax.swing.JButton tmbmesinjadi1;
    private javax.swing.JButton tmbmesinjadi2;
    private javax.swing.JButton tmbproduksicoklat;
    private javax.swing.JButton tmbproduksikeju;
    private javax.swing.JButton tmbproduksiturbo;
    private javax.swing.JLabel txtbbgandum;
    private javax.swing.JLabel txtbbgandum1;
    private javax.swing.JLabel txtbbgula;
    private javax.swing.JLabel txtbbgula1;
    private javax.swing.JLabel txtbbjagung;
    private javax.swing.JLabel txtbbjagung1;
    private javax.swing.JLabel txtbbsusu;
    private javax.swing.JLabel txtbbsusu1;
    private javax.swing.JLabel txtbelicoklat;
    private javax.swing.JLabel txtbeligandum;
    private javax.swing.JLabel txtbeligula;
    private javax.swing.JLabel txtbelijagung;
    private javax.swing.JLabel txtbelikeju;
    private javax.swing.JLabel txtbeliplastik;
    private javax.swing.JLabel txtbelisusu;
    private javax.swing.JLabel txtbibitgandum;
    private javax.swing.JLabel txtbibitjagung;
    private javax.swing.JLabel txtbmcoklat;
    private javax.swing.JLabel txtbmcoklat1;
    private javax.swing.JLabel txtbmkeju;
    private javax.swing.JLabel txtbmkeju1;
    private javax.swing.JLabel txtbmplastik;
    private javax.swing.JLabel txtbmplastik1;
    private javax.swing.JLabel txthargatotal;
    private javax.swing.JLabel txtlevelmesin1;
    private javax.swing.JLabel txtlevelmesin2;
    private javax.swing.JLabel txtlevelmobil;
    private javax.swing.JButton txtsawah1;
    private javax.swing.JButton txtsawah2;
    private javax.swing.JLabel txtserealcoklat;
    private javax.swing.JLabel txtserealkeju;
    private javax.swing.JLabel txtturbo;
    private javax.swing.JLabel txtuang;
    private javax.swing.JLabel txtwaktusawah1;
    private javax.swing.JLabel txtwaktusawah2;
    private javax.swing.JButton updatemesin1;
    private javax.swing.JButton updatemesin2;
    private javax.swing.JButton updatemesinjadi1;
    private javax.swing.JButton updatemesinjadi2;
    private javax.swing.JPanel upgrademesin;
    private javax.swing.JButton upgrademobil;
    // End of variables declaration//GEN-END:variables

    private void setsawah() {
        try {
            String[] a = new String[2];
            a = modelbarang.cekbibit(id);
            txtbibitgandum.setText(a[1]);
            txtbibitjagung.setText(a[0]);
        } catch (SQLException ex) {
            Logger.getLogger(map.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
