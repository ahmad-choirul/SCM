/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.SQLException;
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
        String query = "INSERT INTO `user` (`id_user`, `nama`, `score`, `scorepopularitas`, `uang`, `bbjagung`, `bbgandum`, `bbair`, `bmsereal`, `bmcoklat`, `bmplastik`, `bmkeju`, `bjserealcoklat`, `bjserealkeju`, `bjturbo`) VALUES "
                + "(NULL, '"+nama+"', '0', '100', '1000', '10', '10', '100', '2', '2', '5', '2', '0', '0', '0');";
        return execute(query);
    }

    public boolean updatebarang(String data[]) {
        String query = "UPDATE `barang` SET `nama_barang`='" + data[1] + "',`harga_beli`='" + data[2] + "',`harga_jual`='" + data[3] + "',"
                + "`stok`='" + data[4] + "',`keterangan`='" + data[5] + "' where `kd_barang`='" + data[0] + "' ";
        return execute(query);
    }

    public boolean cekbarang(String data) {
        String query = "select * from barang where kd_barang = " + data;
        return execute(query);
    }

    public DefaultTableModel getdata() throws SQLException {
        String kolom[] = {"kd_barang", "nama barang", "harga_beli","harga_jual", "stok", "keterangan"};
        String query = "SELECT * FROM `barang` ";
        return getDatatotal(query, kolom);
    }

    public String[] getdatawithid(String kodebarang) throws SQLException {
        String kolom[] = {"kd_barang", "nama barang", "harga_jual", "stok", "keterangan"};
        String query = "SELECT * FROM `barang` where kd_barang = '" + kodebarang + "' ";
        return getdataid(query, kolom);
    }

    public DefaultTableModel getdatasearch(String data) throws SQLException {
        String kolom[] = {"kd_barang", "nama barang", "harga_beli","harga_jual", "stok", "keterangan"};
        String query = "SELECT * FROM `barang` WHERE `kd_barang` LIKE '%" + data + "%' or `nama_barang` LIKE '%" + data + "%' "
                + "or `harga_beli` LIKE '%" + data + "%' or `harga_jual` LIKE '%" + data + "%' or `stok` LIKE '%" + data + "%' or `keterangan` LIKE '%" + data + "%'";
        return getDatatotal(query, kolom);
    }
    public String getiduser(String nama) throws SQLException {

        String query = "SELECT`id_user` from user WHERE nama = '"+nama+"'";
        String id = getdataidNoaray(query);
        return id;
    }
    public boolean hapusbarang(String data) {
        String query = "delete from barang where kd_barang = " + data;
        return execute(query);
    }
    public DefaultTableModel getdatacaribarangbeli(String data) throws SQLException{
    String kolom[] = {"kd_barang", "nama barang","harga_jual", "stok", "keterangan"};
        String query = "SELECT kd_barang,nama_barang,harga_jual,stok,keterangan FROM `barang` WHERE `kd_barang` LIKE '%" + data + "%' or `nama_barang` LIKE '%" + data + "%' "
                + "or `harga_jual` LIKE '%" + data + "%' or `stok` LIKE '%" + data + "%' or `keterangan` LIKE '%" + data + "%'";
        return getDatatotal(query, kolom);
    }
}
