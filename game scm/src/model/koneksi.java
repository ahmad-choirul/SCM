package model;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.math.BigInteger;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class koneksi {

    Connection con;
    Statement stm;
    java.sql.Connection connection;

    public koneksi() throws SQLException {
        String url = "jdbc:mysql://localhost/scmgame"; //url DB
        String username = "root"; //username DB
        String pass = ""; //password DB, Jika tidak di Password silahkan di Kosongkan.
        //membuat koneksi ke DB
        this.con = (Connection) DriverManager.getConnection(url, username, pass);
        this.stm = (Statement) this.con.createStatement();
    }

    //method untuk ekesekusi query Insert, Update, dan Delete
    public void executeQuery(String query) throws SQLException {
        this.stm.execute(query);
    }

    //method untuk ekesekusi query Select ya
    public ResultSet getResult(String query) throws SQLException {
        ResultSet rs = stm.executeQuery(query);
        return rs;
    }

    public boolean execute(String query) {
        boolean sukseseksekusi;
        try {
            executeQuery(query);
            sukseseksekusi = true;
            System.out.println("query berhasil");
        } catch (SQLException ex) {
            sukseseksekusi = false;
            System.out.println("query salah" + query);
        }

        return sukseseksekusi;
    }

    public String[] getdataid(String query, String data[]) throws SQLException {
        ResultSet rs = getResult(query);
        if (rs.next()) {
            for (int i = 0; i < data.length; i++) {
                data[i] = rs.getString(i + 1);
            }
        }

        return data;
    }

    public String[] getarraykolom(String query, String[] data, String kolom) {
        try {
            ResultSet rs = getResult(query);
            int count = 0;
            while (rs.next()) {
                data[count] = rs.getString(kolom);
                System.out.println("data" + data[count]);
                count++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(koneksi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }

    public DefaultTableModel getDatatotal(String query, String kolom[]) throws SQLException {
        DefaultTableModel table = new DefaultTableModel(null, kolom);
        ResultSet rs = getResult(query);
        while (rs.next()) {
            String row[] = new String[kolom.length];
            for (int i = 0; i < row.length; i++) {
                row[i] = rs.getString(i + 1);
            }
            table.addRow(row);
        }
        return table;
    }

    public String getdataidNoaray(String query) throws SQLException {
        ResultSet rs = getResult(query);
        String data = "";
        if (rs.next()) {
            data = rs.getString(1);
        }
        return data;
    }
}
