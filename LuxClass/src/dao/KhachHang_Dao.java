package dao;

import connectDB.ConnectDB;
import entity.KhachHang;

import java.sql.*;
import java.util.ArrayList;

public class KhachHang_Dao {
    private ArrayList<KhachHang> dskh;
    
    public KhachHang_Dao() {
        dskh = new ArrayList<KhachHang>();
    }

    public ArrayList<KhachHang> getAllKhachHang() {
        try (Connection con = ConnectDB.getConnection()) {
            String sql = "SELECT * FROM KhachHang";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                String maKH = rs.getString(1);
                String hoTen = rs.getString(2);
                String sdt = rs.getString(3);
                String soCCCD = rs.getString(4);

                KhachHang kh = new KhachHang(maKH, hoTen, sdt, soCCCD);
                dskh.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dskh;
    }

    public boolean them(KhachHang kh) {
        try (Connection con = ConnectDB.getConnection()) {
            String sql = "INSERT INTO KhachHang (maKH, hoTen, sdt, soCCCD) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, kh.getMaKH());
            stmt.setString(2, kh.getHoTen());
            stmt.setString(3, kh.getSdt());
            stmt.setString(4, kh.getSoCCCD());

            int n = stmt.executeUpdate();
            return n > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean sua(KhachHang kh) {
        try (Connection con = ConnectDB.getConnection()) {
            String sql = "UPDATE KhachHang SET hoTen = ?, sdt = ?, soCCCD = ? WHERE maKH = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, kh.getHoTen());
            stmt.setString(2, kh.getSdt());
            stmt.setString(3, kh.getSoCCCD());
            stmt.setString(4, kh.getMaKH());

            int n = stmt.executeUpdate();
            return n > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoa(String maKH) {
        try (Connection con = ConnectDB.getConnection()) {
            String sql = "DELETE FROM KhachHang WHERE maKH = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, maKH);

            int n = stmt.executeUpdate();
            return n > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public KhachHang timTheoMa(String maKH) {
        KhachHang kh = null;
        try (Connection con = ConnectDB.getConnection()) {
            String sql = "SELECT * FROM KhachHang WHERE maKH = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, maKH);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String hoTen = rs.getString(2);
                String sdt = rs.getString(3);
                String soCCCD = rs.getString(4);

                kh = new KhachHang(maKH, hoTen, sdt, soCCCD);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kh;
    }

    public ArrayList<KhachHang> timTheoSdt(String sdt) {
        ArrayList<KhachHang> dskh = new ArrayList<KhachHang>();
        try (Connection con = ConnectDB.getConnection()) {
            String sql = "SELECT * FROM KhachHang WHERE sdt = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, sdt);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String maKH = rs.getString(1);
                String hoTen = rs.getString(2);
                String soCCCD = rs.getString(4);

                KhachHang kh = new KhachHang(maKH, hoTen, sdt, soCCCD);
                dskh.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dskh;
    }
}
