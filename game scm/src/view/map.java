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
            setupgradebarang();
            
        } catch (SQLException ex) {
            Logger.getLogger(map.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setpopupvisibelfalse() {
        upgrademesin.setVisible(false);
        popupmesin.setVisible(false);
        popupmesinjadi.setVisible(false);
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
    
    public void tambahproduksi(String idproduksi) {
        if (idproduksi.equalsIgnoreCase("1")) {
            //proses turbo
            modelbarang.tambahproduksi("bjturbo", "100", id);
        }
        if (idproduksi.equalsIgnoreCase("2")) {
            //proses keju
            modelbarang.tambahproduksi("bjserealkeju", "100", id);
        }
        if (idproduksi.equalsIgnoreCase("3")) {
            //proses coklat
            modelbarang.tambahproduksi("bjserealcoklat", "100", id);
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
        if (panelgerak.getX() == 1200) {
            seticonpng("mobil2", labelmobil);
            kanan = false;
        }
        if (panelgerak.getX() < 700) {
            seticonpng("mobil", labelmobil);
            belibarangdarikota();
            setgudangppic();
            setenabelbtnbeli(true);
            belibarang = false;
        }
    }
    
    public void seticonpngloop(int i) {
        System.out.println("seticonpngloop");
        if (i == 0) {
            seticonpng("mesin", labelmesin1);
        }
        if (i == 1) {
            seticonpng("mesin", labelmesin2);
        }
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
        txtbelisereal.setText("0");
        txtbelisusu.setText("0");
        try {
            String data[] = modelbarang.cekbarang(id);
            txtbbjagung.setText(data[5]);
            txtbbgandum.setText(data[6]);
            txtbbsusu.setText(data[7]);
            txtbbgula.setText(data[8]);
            txtbmsereal.setText(data[9]);
            txtbmcoklat.setText(data[10]);
            txtbmplastik.setText(data[11]);
            txtbmkeju.setText(data[12]);
            txtbbjagung1.setText(data[5]);
            txtbbgandum1.setText(data[6]);
            txtbbsusu1.setText(data[7]);
            txtbbgula1.setText(data[8]);
            txtbmsereal1.setText(data[9]);
            txtbmcoklat1.setText(data[10]);
            txtbmplastik1.setText(data[11]);
            txtbmkeju1.setText(data[12]);
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
        popuppiltanaman = new javax.swing.JPanel();
        jagung = new javax.swing.JButton();
        gandum = new javax.swing.JButton();
        btnmap4 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        txtsawah2 = new javax.swing.JButton();
        txtsawah1 = new javax.swing.JButton();
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
        btnbelisereal = new javax.swing.JButton();
        btnbelicoklat = new javax.swing.JButton();
        btnbelikeju = new javax.swing.JButton();
        btnbeliplastik = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txtbeliplastik = new javax.swing.JLabel();
        txtbelijagung = new javax.swing.JLabel();
        txtbeligandum = new javax.swing.JLabel();
        txtbelisusu = new javax.swing.JLabel();
        txtbeligula = new javax.swing.JLabel();
        txtbelisereal = new javax.swing.JLabel();
        txtbelicoklat = new javax.swing.JLabel();
        txtbelikeju = new javax.swing.JLabel();
        jButton27 = new javax.swing.JButton();
        btnmap3 = new javax.swing.JButton();
        panelbahan = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtbbjagung = new javax.swing.JLabel();
        txtbbgandum = new javax.swing.JLabel();
        txtbbsusu = new javax.swing.JLabel();
        txtbbgula = new javax.swing.JLabel();
        txtbmsereal = new javax.swing.JLabel();
        txtbmcoklat = new javax.swing.JLabel();
        txtbmkeju = new javax.swing.JLabel();
        txtbmplastik = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        upgrademobil = new javax.swing.JButton();
        background4 = new javax.swing.JLabel();
        panelpabrik = new javax.swing.JPanel();
        upgrademesin = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        updatemesin1 = new javax.swing.JButton();
        updatemesin2 = new javax.swing.JButton();
        updatemesinjadi1 = new javax.swing.JButton();
        updatemesinjadi2 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton25 = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();
        panelutamapabrik = new diu.swe.habib.JPanelSlider.JPanelSlider();
        panel2 = new javax.swing.JPanel();
        popupmesinjadi = new javax.swing.JPanel();
        tmbproduksicoklat = new javax.swing.JButton();
        tmbproduksiturbo = new javax.swing.JButton();
        tmbproduksikeju = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        tmbmesinjadi2 = new javax.swing.JButton();
        tmbmesinjadi1 = new javax.swing.JButton();
        labelmesinjadi1 = new javax.swing.JLabel();
        labelmesinjadi2 = new javax.swing.JLabel();
        panel1 = new javax.swing.JPanel();
        popupmesin = new javax.swing.JPanel();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        tmbmesin1 = new javax.swing.JButton();
        tmbmesin2 = new javax.swing.JButton();
        labelmesin2 = new javax.swing.JLabel();
        labelmesin1 = new javax.swing.JLabel();
        jButton14 = new javax.swing.JButton();
        btnmap1 = new javax.swing.JButton();
        panelbahan1 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtbbjagung1 = new javax.swing.JLabel();
        txtbbgandum1 = new javax.swing.JLabel();
        txtbbsusu1 = new javax.swing.JLabel();
        txtbbgula1 = new javax.swing.JLabel();
        txtbmsereal1 = new javax.swing.JLabel();
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

        popuppiltanaman.setLayout(new java.awt.GridBagLayout());

        jagung.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/bb_jagung.png"))); // NOI18N
        jagung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jagungActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 37;
        gridBagConstraints.ipady = 87;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(30, 50, 20, 60);
        popuppiltanaman.add(jagung, gridBagConstraints);

        gandum.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/bb_gandum.png"))); // NOI18N
        gandum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gandumActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 37;
        gridBagConstraints.ipady = 87;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(30, 50, 0, 60);
        popuppiltanaman.add(gandum, gridBagConstraints);

        panelsawah.add(popuppiltanaman, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 30, 190, 390));

        btnmap4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/map1.png"))); // NOI18N
        btnmap4.setBorder(null);
        btnmap4.setBorderPainted(false);
        btnmap4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnmap4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmap4ActionPerformed(evt);
            }
        });
        panelsawah.add(btnmap4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1200, 650, 130, 90));

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
        panelsawah.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1160, 450, 170, 160));

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
        panelsawah.add(jButton15, new org.netbeans.lib.awtextra.AbsoluteConstraints(1160, 310, 170, 160));

        txtsawah2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/sawah0.png"))); // NOI18N
        txtsawah2.setBorderPainted(false);
        txtsawah2.setContentAreaFilled(false);
        txtsawah2.setFocusPainted(false);
        txtsawah2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtsawah2ActionPerformed(evt);
            }
        });
        panelsawah.add(txtsawah2, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 310, 360, 370));

        txtsawah1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/sawah0.png"))); // NOI18N
        txtsawah1.setBorderPainted(false);
        txtsawah1.setContentAreaFilled(false);
        txtsawah1.setFocusPainted(false);
        txtsawah1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtsawah1ActionPerformed(evt);
            }
        });
        panelsawah.add(txtsawah1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 310, 360, 370));

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

        btnbelijagung.setText("beli");
        btnbelijagung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbelijagungActionPerformed(evt);
            }
        });
        jPanel1.add(btnbelijagung, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 180, 60, -1));

        btnbeligandum.setText("beli");
        btnbeligandum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbeligandumActionPerformed(evt);
            }
        });
        jPanel1.add(btnbeligandum, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 180, 60, -1));

        btnbelisusu.setText("beli");
        btnbelisusu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbelisusuActionPerformed(evt);
            }
        });
        jPanel1.add(btnbelisusu, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 180, 60, -1));

        btnbeligula.setText("beli");
        btnbeligula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbeligulaActionPerformed(evt);
            }
        });
        jPanel1.add(btnbeligula, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 180, 60, -1));

        btnbelisereal.setText("beli");
        btnbelisereal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbeliserealActionPerformed(evt);
            }
        });
        jPanel1.add(btnbelisereal, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 320, -1, -1));

        btnbelicoklat.setText("beli");
        btnbelicoklat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbelicoklatActionPerformed(evt);
            }
        });
        jPanel1.add(btnbelicoklat, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 320, -1, -1));

        btnbelikeju.setText("beli");
        btnbelikeju.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbelikejuActionPerformed(evt);
            }
        });
        jPanel1.add(btnbelikeju, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 320, -1, -1));

        btnbeliplastik.setText("beli");
        btnbeliplastik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbeliplastikActionPerformed(evt);
            }
        });
        jPanel1.add(btnbeliplastik, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 320, -1, -1));

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/bb_jagung.png"))); // NOI18N
        jPanel1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, 90, 60));

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/bb_gandum.png"))); // NOI18N
        jPanel1.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 80, 90, 60));

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/bm_susu.png"))); // NOI18N
        jPanel1.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 90, 90, 60));

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/bm_gula.png"))); // NOI18N
        jPanel1.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 90, 90, 60));

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/bm_sereal.png"))); // NOI18N
        jPanel1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 230, 90, 60));

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
        jPanel1.add(txtbeliplastik, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 290, 60, 30));

        txtbelijagung.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtbelijagung.setForeground(new java.awt.Color(255, 255, 255));
        txtbelijagung.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtbelijagung.setText("0");
        jPanel1.add(txtbelijagung, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, 60, 30));

        txtbeligandum.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtbeligandum.setForeground(new java.awt.Color(255, 255, 255));
        txtbeligandum.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtbeligandum.setText("0");
        jPanel1.add(txtbeligandum, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 150, 60, 30));

        txtbelisusu.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtbelisusu.setForeground(new java.awt.Color(255, 255, 255));
        txtbelisusu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtbelisusu.setText("0");
        jPanel1.add(txtbelisusu, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 150, 60, 30));

        txtbeligula.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtbeligula.setForeground(new java.awt.Color(255, 255, 255));
        txtbeligula.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtbeligula.setText("0");
        jPanel1.add(txtbeligula, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 150, 60, 30));

        txtbelisereal.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtbelisereal.setForeground(new java.awt.Color(255, 255, 255));
        txtbelisereal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtbelisereal.setText("0");
        jPanel1.add(txtbelisereal, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 290, 60, 30));

        txtbelicoklat.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtbelicoklat.setForeground(new java.awt.Color(255, 255, 255));
        txtbelicoklat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtbelicoklat.setText("0");
        jPanel1.add(txtbelicoklat, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 290, 60, 30));

        txtbelikeju.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtbelikeju.setForeground(new java.awt.Color(255, 255, 255));
        txtbelikeju.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtbelikeju.setText("0");
        jPanel1.add(txtbelikeju, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 290, 60, 30));

        jButton27.setText("pesan");
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton27, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 350, 80, 80));

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

        panelbahan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/bm_plastik.png"))); // NOI18N
        panelbahan.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 10, 90, 60));

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/bm_keju.png"))); // NOI18N
        panelbahan.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 10, 90, 60));

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/bm_coklat.png"))); // NOI18N
        panelbahan.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 10, 90, 60));

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/bm_sereal.png"))); // NOI18N
        panelbahan.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 10, 90, 60));

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

        txtbmsereal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panelbahan.add(txtbmsereal, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 80, 70, 30));

        txtbmcoklat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panelbahan.add(txtbmcoklat, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 80, 70, 30));

        txtbmkeju.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panelbahan.add(txtbmkeju, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 80, 70, 30));

        txtbmplastik.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panelbahan.add(txtbmplastik, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 80, 70, 30));

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

        background4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/market.png"))); // NOI18N
        panelgudangppic.add(background4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1360, 760));

        panelutama.add(panelgudangppic, "card3");

        panelpabrik.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        panelpabrik.add(upgrademesin, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });
        panelpabrik.add(jButton25, new org.netbeans.lib.awtextra.AbsoluteConstraints(1199, 350, 100, 75));

        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton26ActionPerformed(evt);
            }
        });
        panelpabrik.add(jButton26, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 350, 100, 75));

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

        panel2.add(popupmesinjadi, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 20, 300, 400));

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
        panel2.add(tmbmesinjadi2, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 70, 350, 330));

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

        panel1.setOpaque(false);
        panel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        popupmesin.setOpaque(false);
        popupmesin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton10.setText("sereal coklat");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        popupmesin.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 290, 120, 100));

        jButton11.setText("sereal");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        popupmesin.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, 120, 100));

        jButton12.setText("jagung");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        popupmesin.add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 160, 120, 100));

        panel1.add(popupmesin, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 10, 290, -1));

        tmbmesin1.setBorderPainted(false);
        tmbmesin1.setContentAreaFilled(false);
        tmbmesin1.setFocusPainted(false);
        tmbmesin1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tmbmesin1MouseClicked(evt);
            }
        });
        tmbmesin1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tmbmesin1ActionPerformed(evt);
            }
        });
        panel1.add(tmbmesin1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 90, 270, 250));

        tmbmesin2.setBorderPainted(false);
        tmbmesin2.setContentAreaFilled(false);
        tmbmesin2.setFocusPainted(false);
        tmbmesin2.setVerifyInputWhenFocusTarget(false);
        tmbmesin2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tmbmesin2MouseClicked(evt);
            }
        });
        tmbmesin2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tmbmesin2ActionPerformed(evt);
            }
        });
        panel1.add(tmbmesin2, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 100, 260, 250));

        labelmesin2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/mesin.png"))); // NOI18N
        panel1.add(labelmesin2, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 90, 250, 260));

        labelmesin1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/mesin.png"))); // NOI18N
        panel1.add(labelmesin1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, 250, 260));

        panelutamapabrik.add(panel1, "card2");

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

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/bm_sereal.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 0, 0);
        panelbahan1.add(jLabel15, gridBagConstraints);

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

        txtbmsereal1.setForeground(new java.awt.Color(255, 255, 255));
        txtbmsereal1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 70;
        gridBagConstraints.ipady = 30;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 30, 35, 0);
        panelbahan1.add(txtbmsereal1, gridBagConstraints);

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

    private void tmbmesin1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tmbmesin1MouseClicked

    }//GEN-LAST:event_tmbmesin1MouseClicked

    private void tmbmesin2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tmbmesin2MouseClicked

    }//GEN-LAST:event_tmbmesin2MouseClicked

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

    private void tmbmesin1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmbmesin1ActionPerformed
        popupmesin.setVisible(true);
        cekproduksi();
        pillihidmesin = 0;
    }//GEN-LAST:event_tmbmesin1ActionPerformed

    private void tmbmesinjadi2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tmbmesinjadi2MouseClicked

    }//GEN-LAST:event_tmbmesinjadi2MouseClicked

    private void tmbmesinjadi2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmbmesinjadi2ActionPerformed
        popupmesinjadi.setVisible(true);
        pillihidmesin = 3;
    }//GEN-LAST:event_tmbmesinjadi2ActionPerformed

    private void tmbmesin2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmbmesin2ActionPerformed
        popupmesin.setVisible(true);
        pillihidmesin = 1;
    }//GEN-LAST:event_tmbmesin2ActionPerformed

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
        //namakolom,jumlah barang tiap beli,harga sekali beli,id user
//        modelbarang.belibarang("bbjagung", "1", "10", id);
//        setgudangppic();
        int get = Integer.parseInt(txtbelijagung.getText()) + 1;
        txtbelijagung.setText(get + "");
    }//GEN-LAST:event_btnbelijagungActionPerformed

    private void btnexitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnexitActionPerformed
        setwindows(new mainmenu());
        this.dispose();
    }//GEN-LAST:event_btnexitActionPerformed

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        if (stattampilmesin == 0) {
            stattampilmesin = 1;
            panelutamapabrik.nextPanel(25, panel2, panelutamapabrik.left);
        } else {
            stattampilmesin = 0;
            panelutamapabrik.nextPanel(25, panel1, panelutamapabrik.left);
        }
    }//GEN-LAST:event_jButton25ActionPerformed

    private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton26ActionPerformed
        if (stattampilmesin == 0) {
            stattampilmesin = 1;
            panelutamapabrik.nextPanel(25, panel2, panelutamapabrik.right);
        } else {
            stattampilmesin = 0;
            panelutamapabrik.nextPanel(25, panel1, panelutamapabrik.right);
        }
    }//GEN-LAST:event_jButton26ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        prosesmesin(pillihidmesin);
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        prosesmesin(pillihidmesin);
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        prosesmesin(pillihidmesin);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void tmbproduksiturboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmbproduksiturboActionPerformed
        produkproduksi[pillihidmesin] = "1";
        modelbarang.jualbarang("bbjagung", "90", id);
        modelbarang.jualbarang("bbgula", "25", id);
        modelbarang.jualbarang("bbsusu", "10", id);
        modelbarang.jualbarang("bmplastik", "400", id);
        prosesmesin(pillihidmesin);
    }//GEN-LAST:event_tmbproduksiturboActionPerformed

    private void tmbproduksikejuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmbproduksikejuActionPerformed
        produkproduksi[pillihidmesin] = "2";
        modelbarang.jualbarang("bbjagung", "90", id);
        modelbarang.jualbarang("bbgula", "25", id);
        modelbarang.jualbarang("bbsusu", "10", id);
        modelbarang.jualbarang("bmplastik", "400", id);
        prosesmesin(pillihidmesin);
    }//GEN-LAST:event_tmbproduksikejuActionPerformed

    private void tmbproduksicoklatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmbproduksicoklatActionPerformed
        produkproduksi[pillihidmesin] = "3";
        modelbarang.jualbarang("bbjagung", "90", id);
        modelbarang.jualbarang("bbgula", "25", id);
        modelbarang.jualbarang("bbsusu", "10", id);
        modelbarang.jualbarang("bmplastik", "400", id);
        prosesmesin(pillihidmesin);
    }//GEN-LAST:event_tmbproduksicoklatActionPerformed

    private void btnbeligandumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbeligandumActionPerformed
//namakolom,jumlah barang tiap beli,harga sekali beli,id user
//        
//        setgudangppic();
        int get = Integer.parseInt(txtbeligandum.getText()) + 1;
        txtbeligandum.setText(get + "");
    }//GEN-LAST:event_btnbeligandumActionPerformed

    private void btnbelisusuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbelisusuActionPerformed
//namakolom,jumlah barang tiap beli,harga sekali beli,id user
//        
//        setgudangppic();
        int get = Integer.parseInt(txtbelisusu.getText()) + 1;
        txtbelisusu.setText(get + "");
    }//GEN-LAST:event_btnbelisusuActionPerformed

    private void btnbeligulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbeligulaActionPerformed
//namakolom,jumlah barang tiap beli,harga sekali beli,id user
//        
//        setgudangppic();
        int get = Integer.parseInt(txtbeligula.getText()) + 1;
        txtbeligula.setText(get + "");
    }//GEN-LAST:event_btnbeligulaActionPerformed

    private void btnbeliserealActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbeliserealActionPerformed
//namakolom,jumlah barang tiap beli,harga sekali beli,id user
//        
//        setgudangppic();
        int get = Integer.parseInt(txtbelisereal.getText()) + 1;
        txtbelisereal.setText(get + "");
    }//GEN-LAST:event_btnbeliserealActionPerformed

    private void btnbelicoklatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbelicoklatActionPerformed
//namakolom,jumlah barang tiap beli,harga sekali beli,id user
//        
//        setgudangppic();
        int get = Integer.parseInt(txtbelicoklat.getText()) + 1;
        txtbelicoklat.setText(get + "");
    }//GEN-LAST:event_btnbelicoklatActionPerformed

    private void btnbelikejuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbelikejuActionPerformed
//namakolom,jumlah barang tiap beli,harga sekali beli,id user
//        
//        setgudangppic();
        int get = Integer.parseInt(txtbelikeju.getText()) + 1;
        txtbelikeju.setText(get + "");
    }//GEN-LAST:event_btnbelikejuActionPerformed

    private void btnbeliplastikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbeliplastikActionPerformed

//        
//        
        int get = Integer.parseInt(txtbeliplastik.getText()) + 1;
        txtbeliplastik.setText(get + "");
    }//GEN-LAST:event_btnbeliplastikActionPerformed

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
//namakolom,jumlah barang tiap beli,harga sekali beli,id user
        setenabelbtnbeli(false);
        gerakmobil = true;
    }//GEN-LAST:event_jButton27ActionPerformed

    private void btnsawahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsawahActionPerformed
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

    private void gandumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gandumActionPerformed
        if (pilsawah == 0) {
            seticonpng("gandum1", txtsawah1);
        }
        if (pilsawah == 1) {
            seticonpng("gandum1", txtsawah2);
        }
        popuppiltanaman.setVisible(false);
    }//GEN-LAST:event_gandumActionPerformed

    private void upgrademobilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_upgrademobilActionPerformed
        kecepatan++;
    }//GEN-LAST:event_upgrademobilActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jagungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jagungActionPerformed
             if (pilsawah == 0) {
            seticonpng("gandum1", txtsawah1);
        }
        if (pilsawah == 1) {
            seticonpng("gandum1", txtsawah2);
        }
        popuppiltanaman.setVisible(false);
    }//GEN-LAST:event_jagungActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        popuppiltanaman.setVisible(true);
    }//GEN-LAST:event_jButton15ActionPerformed
    public void setenabelbtnbeli(boolean bool) {
        btnbelicoklat.setEnabled(bool);
        btnbeligandum.setEnabled(bool);
        btnbeligula.setEnabled(bool);
        btnbelijagung.setEnabled(bool);
        btnbelikeju.setEnabled(bool);
        btnbeliplastik.setEnabled(bool);
        btnbelisereal.setEnabled(bool);
        btnbelisusu.setEnabled(bool);
        upgrademobil.setEnabled(bool);
    }
    
    public void belibarangdarikota() {
        System.out.println("beli barang");
        modelbarang.belibarang("bbjagung", txtbelijagung.getText(), "10", id);
        modelbarang.belibarang("bbgandum", txtbeligandum.getText(), "10", id);
        modelbarang.belibarang("bbsusu", txtbelisusu.getText(), "10", id);
        modelbarang.belibarang("bbgula", txtbeligula.getText(), "10", id);
        modelbarang.belibarang("bmsereal", txtbelisereal.getText(), "10", id);
        modelbarang.belibarang("bmcoklat", txtbelicoklat.getText(), "10", id);
        modelbarang.belibarang("bmkeju", txtbelikeju.getText(), "10", id);
        modelbarang.belibarang("bmplastik", txtbeliplastik.getText(), "10", id);
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
            case 0:
                seticongif("mesin", labelmesin1);
                break;
            case 1:
                seticongif("mesin", labelmesin2);
                break;
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
    private javax.swing.JButton btnbeligandum;
    private javax.swing.JButton btnbeligula;
    private javax.swing.JButton btnbelijagung;
    private javax.swing.JButton btnbelikeju;
    private javax.swing.JButton btnbeliplastik;
    private javax.swing.JButton btnbelisereal;
    private javax.swing.JButton btnbelisusu;
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
    private javax.swing.JButton gandum;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
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
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
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
    private javax.swing.JLabel labelmesin1;
    private javax.swing.JLabel labelmesin2;
    private javax.swing.JLabel labelmesinjadi1;
    private javax.swing.JLabel labelmesinjadi2;
    private javax.swing.JLabel labelmobil;
    private javax.swing.JPanel panel1;
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
    private javax.swing.JPanel popupmesin;
    private javax.swing.JPanel popupmesinjadi;
    private javax.swing.JPanel popuppiltanaman;
    private javax.swing.JButton tmbmesin1;
    private javax.swing.JButton tmbmesin2;
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
    private javax.swing.JLabel txtbelisereal;
    private javax.swing.JLabel txtbelisusu;
    private javax.swing.JLabel txtbmcoklat;
    private javax.swing.JLabel txtbmcoklat1;
    private javax.swing.JLabel txtbmkeju;
    private javax.swing.JLabel txtbmkeju1;
    private javax.swing.JLabel txtbmplastik;
    private javax.swing.JLabel txtbmplastik1;
    private javax.swing.JLabel txtbmsereal;
    private javax.swing.JLabel txtbmsereal1;
    private javax.swing.JButton txtsawah1;
    private javax.swing.JButton txtsawah2;
    private javax.swing.JLabel txtserealcoklat;
    private javax.swing.JLabel txtserealkeju;
    private javax.swing.JLabel txtturbo;
    private javax.swing.JLabel txtuang;
    private javax.swing.JButton updatemesin1;
    private javax.swing.JButton updatemesin2;
    private javax.swing.JButton updatemesinjadi1;
    private javax.swing.JButton updatemesinjadi2;
    private javax.swing.JPanel upgrademesin;
    private javax.swing.JButton upgrademobil;
    // End of variables declaration//GEN-END:variables
}
