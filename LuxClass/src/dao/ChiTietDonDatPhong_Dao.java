package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.ChiTietDonDatPhong;
import entity.DonDatPhong;
import entity.KhachHang;
import entity.Phong;

public class ChiTietDonDatPhong_Dao {
	private ArrayList<ChiTietDonDatPhong> dsctddp;
	private Connection connection;
    public ChiTietDonDatPhong_Dao() {
    	dsctddp = new ArrayList<ChiTietDonDatPhong>();
    	connection = ConnectDB.getConnection();
    }
    
    public int countSoPhongTrong(Timestamp tuNgay, Timestamp denNgay, String loaiPhong) throws SQLException {
        int soLuong = 0;

        // Lấy LocalDate từ Timestamp rồi set giờ
        LocalDateTime tuNgay14h = tuNgay.toLocalDateTime().toLocalDate().atTime(14, 0);
        LocalDateTime denNgay12h = denNgay.toLocalDateTime().toLocalDate().atTime(12, 0);

        Timestamp tuNgayTimestamp = Timestamp.valueOf(tuNgay14h);
        Timestamp denNgayTimestamp = Timestamp.valueOf(denNgay12h);

        String sql = """
            SELECT COUNT(*) AS SoPhongTrong
            FROM Phong
            WHERE loaiPhong = ?
              AND soPhong NOT IN (
                SELECT CT.soPhong
                FROM ChiTietDonDatPhong CT
                JOIN DonDatPhong DDP ON CT.maDonDatPhong = DDP.maDonDatPhong
                WHERE
                    DDP.ngayNhanPhong < ?
                    AND DDP.ngayTraPhong > ?
              )
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, loaiPhong);
            stmt.setTimestamp(2, denNgayTimestamp); // ngày nhận < 12:00 ngày kết thúc
            stmt.setTimestamp(3, tuNgayTimestamp);  // ngày trả > 14:00 ngày bắt đầu

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                soLuong = rs.getInt("SoPhongTrong");
            }
        }

        return soLuong;
    }



    
    public int GetPriceToDay(String tenLoaiPhong) {
        int giaTheoNgay = 0;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT giaTheoNgay FROM LoaiPhong WHERE tenLoai = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setNString(1, tenLoaiPhong);
            rs = stmt.executeQuery();
            if (rs.next()) {
                giaTheoNgay = rs.getInt("giaTheoNgay");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return giaTheoNgay;
    }


    public List<String> layDanhSachPhongTrong(Timestamp tuNgay, Timestamp denNgay, String loaiPhong) throws SQLException {
        List<String> danhSachPhong = new ArrayList<>();

        LocalDateTime tuNgay14h = tuNgay.toLocalDateTime().toLocalDate().atTime(14, 0);
        LocalDateTime denNgay12h = denNgay.toLocalDateTime().toLocalDate().atTime(12, 0);

        Timestamp tuNgayTimestamp = Timestamp.valueOf(tuNgay14h);
        Timestamp denNgayTimestamp = Timestamp.valueOf(denNgay12h);

        System.out.println("Loại phòng: " + loaiPhong);
        System.out.println("Đến ngày 12h: " + denNgayTimestamp);
        System.out.println("Từ ngày 14h: " + tuNgayTimestamp);

        String sql = """
            SELECT soPhong
			FROM Phong
			WHERE loaiPhong = ?
			    AND soPhong NOT IN (
			    SELECT CT.soPhong
			    FROM ChiTietDonDatPhong CT
			    JOIN DonDatPhong DDP ON CT.maDonDatPhong = DDP.maDonDatPhong
			    WHERE
			        DDP.ngayNhanPhong < ?
			        AND DDP.ngayTraPhong > ?
			    )
        """;
        
       
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
   
            stmt.setString(1, loaiPhong);
            stmt.setTimestamp(2, denNgayTimestamp);
            stmt.setTimestamp(3, tuNgayTimestamp);

            try (ResultSet rs = stmt.executeQuery()) {
            	if (!rs.isBeforeFirst()) {
                    System.out.println("Không có phòng trống nào.");
                }

                while (rs.next()) {
                    danhSachPhong.add(rs.getString("soPhong"));
                    System.out.println(danhSachPhong);
                }
            }
        } catch (SQLException e) {
        	System.err.println("Lỗi SQL: " + e.getMessage());
            throw e;  // Re-throw the exception to be handled further up the call stack
        }
        return danhSachPhong;
    }

    
    public int demSoLuongDonTrongNgay() {
        int soLuongDon = 0;
        String sql = "SELECT COUNT(*) AS SoLuongDon FROM DonDatPhong WHERE CONVERT(DATE, ngayNhanPhong) = CONVERT(DATE, GETDATE())";
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();
            if (rs.next()) {
                soLuongDon = rs.getInt("SoLuongDon");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return soLuongDon;
    }

    public KhachHang timKhachHangTheoSDT(String sdt) {
        KhachHang khachHang = null;
        String sql = "SELECT maKH, hoTen FROM KhachHang WHERE sdt = ?";
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, sdt);
            rs = stmt.executeQuery();

            if (rs.next()) {
                String maKH = rs.getString("maKH");
                String hoTen = rs.getNString("hoTen");
                khachHang = new KhachHang(maKH, hoTen);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return khachHang;
    }


}
