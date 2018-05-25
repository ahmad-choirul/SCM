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
public class mbarang extends koneksi {

    public mbarang() throws SQLException {
        super();
    }

    public boolean belibarang(String barang, String jumlah, String uang, String id) {
        String query2 = "UPDATE `user` SET `" + barang + "` = " + barang + "+'" + jumlah + "' WHERE `user`.`id_user` = " + id + ";";
        String query = "UPDATE `user` SET `uang` = uang-'" + uang + "' WHERE `user`.`id_user` = " + id + " ";
        if (execute(query)) {
            return execute(query2);
        } else {
            return false;
        }
    }

    public String[] cekbarang(String id) throws SQLException {
        String kolom[] = {"id_user", "nama", "score", "scorepopularitas", "uang", "bbjagung", "bbgandum", "bbsusu", "bbgula","bmcoklat", "bmplastik", "bmkeju", "bjserealcoklat", "bjserealkeju", "bjturbo"};
        String query = "select * from user where id_user = " + id;
        return getdataid(query, kolom);
    }
    public String[] cekbibit(String id) throws SQLException {
        String kolom[] = {"bbjagung", "bbgandum"};
        String query = "select bbjagung,bbgandum from user where id_user = " + id;
        return getdataid(query, kolom);
    }

    public boolean jualbarang(String barang, String jumlah, String id) {
        String query2 = "UPDATE `user` SET `" + barang + "` = " + barang + "-'" + jumlah + "' WHERE `user`.`id_user` = " + id + ";";
        return execute(query2);
    }

    public boolean tambahproduksi(String barang, String jumlah, String id) {
        String query2 = "UPDATE `user` SET `" + barang + "` = " + barang + "+'" + jumlah + "' WHERE `user`.`id_user` = " + id + ";";
        return execute(query2);
    }

    public String[] getdatawithid(String kodebarang) throws SQLException {
        String kolom[] = {"kd_barang", "nama barang", "harga_jual", "stok", "keterangan"};
        String query = "SELECT * FROM `barang` where kd_barang = '" + kodebarang + "' ";
        return getdataid(query, kolom);
    }

    public String getiduser(String nama) throws SQLException {

        String query = "SELECT`id_user` from user WHERE nama = '" + nama + "'";
        String id = getdataidNoaray(query);
        return id;
    }

    public boolean hapusbarang(String data) {
        String query = "delete from barang where kd_barang = " + data;
        return execute(query);
    }

}
