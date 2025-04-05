package dao;

import connectDB.ConnectDB;
import entity.LoaiDichVu;

import java.sql.*;
import java.util.ArrayList;

public class LoaiDichVu_Dao {

    public ArrayList<LoaiDichVu> getAllLoaiDichVu() {
        ArrayList<LoaiDichVu> listLoaiDichVu = new ArrayList<LoaiDichVu>();
        try (Connection con = ConnectDB.getConnection()) {
            String sql = "SELECT * FROM LoaiDichVu";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                String maLoai = rs.getString(1);
                String tenLoai = rs.getString(2);
                LoaiDichVu loaiDichVu = new LoaiDichVu(maLoai, tenLoai);
                listLoaiDichVu.add(loaiDichVu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listLoaiDichVu;
    }

    public boolean them(LoaiDichVu loaiDichVu) {
        try (Connection con = ConnectDB.getConnection()) {
            String sql = "INSERT INTO LoaiDichVu (maLoai, tenLoai) VALUES (?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, loaiDichVu.getMaLoai());
            stmt.setString(2, loaiDichVu.getTenLoai());

            int n = stmt.executeUpdate();
            return n > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean sua(LoaiDichVu loaiDichVu) {
        try (Connection con = ConnectDB.getConnection()) {
            String sql = "UPDATE LoaiDichVu SET tenLoai = ? WHERE maLoai = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, loaiDichVu.getTenLoai());
            stmt.setString(2, loaiDichVu.getMaLoai());

            int n = stmt.executeUpdate();
            return n > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoa(String maLoai) {
        try (Connection con = ConnectDB.getConnection()) {
            String sql = "DELETE FROM LoaiDichVu WHERE maLoai = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, maLoai);

            int n = stmt.executeUpdate();
            return n > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public LoaiDichVu timTheoMaLoai(String maLoai) {
        LoaiDichVu loaiDichVu = null;
        try (Connection con = ConnectDB.getConnection()) {
            String sql = "SELECT * FROM LoaiDichVu WHERE maLoai = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, maLoai);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String tenLoai = rs.getString(2);
                loaiDichVu = new LoaiDichVu(maLoai, tenLoai);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loaiDichVu;
    }
}
