package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.DonDatPhong;
import entity.PhieuDichVu;

public class PhieuDichVu_DAO {
    private ArrayList<PhieuDichVu> dsPhieuDichVu;

    public PhieuDichVu_DAO() {
        dsPhieuDichVu = new ArrayList<>();
    }

    // Thêm phiếu dịch vụ
    public boolean themPhieuDichVu(PhieuDichVu pdv) {
        if (dsPhieuDichVu.contains(pdv)) {
            return false; // đã tồn tại
        } else {
        	Connection con = ConnectDB.getConnection();
			PreparedStatement stmt = null;
            int n = 0;
            try {
                stmt = con.prepareStatement("INSERT INTO PhieuDichVu VALUES (?, ?, ?, ?)");
                stmt.setString(1, pdv.getMaPhieuDichVu());
                stmt.setString(2, pdv.getMaDonDatPhong());
                stmt.setTimestamp(3, Timestamp.valueOf(pdv.getNgayLapPhieu()));
                stmt.setString(4, pdv.getTrangThai());

                n = stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return n > 0;
        }
    }

}
