/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author acer
 */
public class muser extends koneksi {

    public muser() throws SQLException {
        super();
    }

    public boolean tambahuser(String nama) {
        System.out.println("tambah user");
        String query = "INSERT INTO `user` (`id_user`, `nama`, `score`, `scorepopularitas`, `uang`, `bbjagung`, `bbgandum`, `bbsusu`, `bbgula`, `bmcoklat`, `bmplastik`, `bmkeju`, `bjserealcoklat`, `bjserealkeju`, `bjturbo`) VALUES "
                + "(NULL, '" + nama + "', '0', '100', '1000', '10', '10', '100', '100', '2', '2', '5', '2', '0', '0', '0');";

        System.out.println(query);
        return execute(query);
    }

//    public int[] getpenjualan(String id, int id_barang) {
//        String kolom[] = {"id_user", "nama", "score", "scorepopularitas", "uang", "bbjagung", "bbgandum", "bbsusu", "bbgula", "bmcoklat", "bmplastik", "bmkeju", "bjserealcoklat", "bjserealkeju", "bjturbo"};
//        String query = "SELECT total_jual FROM penjualan WHERE (id_user=" + id + " and id_barang=" + id_barang + ") order by bulan_ke DESC";
//        String[] a = new String[6];
//        try {
//            a = getdataid(query, kolom);
//        } catch (SQLException ex) {
//            Logger.getLogger(muser.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        int[] b = new int[a.length];
//        for (int i = 0; i < a.length; i++) {
//            b[i] = Integer.parseInt(a[i]);
//        }
//        return b;
//    }
    public int[] getpenjualan(String id, int id_barang) {
        String kolom = "total_jual";
        String[] get= new String[6];
        String query = "SELECT total_jual FROM penjualan WHERE (id_user=" + id + " and id_barang=" + id_barang + ") order by bulan_ke DESC limit 6";
        System.out.println(query);
        String[] a = getarraykolom(query,get,kolom);
        int[] b = new int[6];
        for (int i = 0; i < b.length; i++) {
            b[i] = Integer.parseInt(a[i]);
        }
        return b;
    }
    public String[] getuser() {
        String kolom = "nama";
        String[] get= new String[6];
        String query = "SELECT nama FROM user";
        String[] a = getarraykolom(query,get,kolom);
        return a;
    }

    public String[] cekbarang(String id) throws SQLException {
        String kolom[] = {"id_user", "nama", "score", "scorepopularitas", "uang", "bbjagung", "bbgandum", "bbsusu", "bbgula", "bmcoklat", "bmplastik", "bmkeju", "bjserealcoklat", "bjserealkeju", "bjturbo"};
        String query = "select * from user where id_user = " + id;
        return getdataid(query, kolom);

    }

    public String[] getupdatebarang(String id) throws SQLException {
        String kolom[] = {"id_user", "mesin1", "mesin2", "mobil"};
        String query = "SELECT `nama`,`mesin1`,`mesin2`,`mobil` from user WHERE `id_user` =" + id;
        System.out.println("query = " + query);
        return getdataid(query, kolom);

    }

    public boolean updatebarang(String data[]) {
        String query = "UPDATE `barang` SET `nama_barang`='" + data[1] + "',`harga_beli`='" + data[2] + "',`harga_jual`='" + data[3] + "',"
                + "`stok`='" + data[4] + "',`keterangan`='" + data[5] + "' where `kd_barang`='" + data[0] + "' ";
        return execute(query);
    }

    public DefaultTableModel getdata() throws SQLException {
        String kolom[] = {"kd_barang", "nama barang", "harga_beli", "harga_jual", "stok", "keterangan"};
        String query = "SELECT * FROM `barang` ";
        return getDatatotal(query, kolom);
    }

    public String[] getdatawithid(String kodebarang) throws SQLException {
        String kolom[] = {"kd_barang", "nama barang", "harga_jual", "stok", "keterangan"};
        String query = "SELECT * FROM `barang` where kd_barang = '" + kodebarang + "' ";
        return getdataid(query, kolom);
    }

    public DefaultTableModel getdatasearch(String data) throws SQLException {
        String kolom[] = {"kd_barang", "nama barang", "harga_beli", "harga_jual", "stok", "keterangan"};
        String query = "SELECT * FROM `barang` WHERE `kd_barang` LIKE '%" + data + "%' or `nama_barang` LIKE '%" + data + "%' "
                + "or `harga_beli` LIKE '%" + data + "%' or `harga_jual` LIKE '%" + data + "%' or `stok` LIKE '%" + data + "%' or `keterangan` LIKE '%" + data + "%'";
        return getDatatotal(query, kolom);
    }

    public String getiduser(String nama) throws SQLException {

        String query = "SELECT`id_user` from user WHERE nama = '" + nama + "'";
        String id = getdataidNoaray(query);
        return id;
    }

    public int getbulanke(String id1) throws SQLException {
        String query = "SELECT MAX(bulan_ke) from penjualan WHERE `id_user` = '" + id1 + "'";
        String id = getdataidNoaray(query);
        return Integer.parseInt(id);
    }

    public boolean hapusbarang(String data) {
        String query = "delete from barang where kd_barang = " + data;
        return execute(query);
    }

    public DefaultTableModel getdatacaribarangbeli(String data) throws SQLException {
        String kolom[] = {"kd_barang", "nama barang", "harga_jual", "stok", "keterangan"};
        String query = "SELECT kd_barang,nama_barang,harga_jual,stok,keterangan FROM `barang` WHERE `kd_barang` LIKE '%" + data + "%' or `nama_barang` LIKE '%" + data + "%' "
                + "or `harga_jual` LIKE '%" + data + "%' or `stok` LIKE '%" + data + "%' or `keterangan` LIKE '%" + data + "%'";
        return getDatatotal(query, kolom);
    }
}
