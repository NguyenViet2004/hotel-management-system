package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.LoaiPhong;
import entity.Phong;

public class Phong_Dao {
	private ArrayList<Phong> dsp;
    
    public Phong_Dao() {
        dsp = new ArrayList<Phong>();
    }

    public ArrayList<Phong> getAllPhong() {
        try (Connection con = ConnectDB.getConnection()) {
            String sql = "SELECT * FROM Phong";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                String soPhong = rs.getString(1);
                String trangThai = rs.getString(2);
                LoaiPhong loaiPhong = new LoaiPhong(rs.getString(3));
                String moTa = rs.getString(4);

                Phong p = new Phong(soPhong,trangThai,loaiPhong,moTa);
                dsp.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsp;
    }
    
    public ArrayList<Phong> getDanhSachPhongTheoLoai(String maLoaiPhong) {
    	ArrayList<Phong> danhSachPhong = new ArrayList<>();
        String sql = "SELECT * FROM Phong WHERE loaiPhong = ?";
        
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, maLoaiPhong);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                String soPhong = rs.getString("soPhong");
                String trangThai = rs.getString("trangThai");
                String loaiPhongMa = rs.getString("loaiPhong");
                String moTa = rs.getString("moTa");

                LoaiPhong loaiPhong = new LoaiPhong(loaiPhongMa);
                danhSachPhong.add(new Phong(soPhong, trangThai, loaiPhong, moTa));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachPhong;
    }
    
    public Phong getPhongTheoMa(String soPhong) {
        Phong phong = null;
        Connection conn = ConnectDB.getConnection(); 
        String sql = "SELECT * FROM Phong WHERE soPhong = ?"; 
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, soPhong); 
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) { // Nếu có kết quả
                String trangThai = rs.getString("trangThai");
                String loaiPhongMa = rs.getString("loaiPhong"); 
                String moTa = rs.getString("moTa");
                

                LoaiPhong loaiPhong = new LoaiPhong(loaiPhongMa);
                
                // Tạo đối tượng Phong từ kết quả truy vấn
                phong = new Phong(soPhong, trangThai, loaiPhong, moTa);
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        
        return phong; // Trả về null nếu không tìm thấy phòng
    }
    
	public ArrayList<Phong> getPhongTheoMaDonDatPhong(String maDonDatPhong) {
		ArrayList<Phong> dsPhong = new ArrayList<>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			con = ConnectDB.getConnection();
			String sql = "SELECT p.soPhong, p.trangThai, lp.*, p.moTa " + "FROM Phong p "
					+ "JOIN ChiTietDonDatPhong ctd ON p.soPhong = ctd.soPhong "
					+ "JOIN LoaiPhong lp ON p.loaiPhong = lp.maLoaiPhong "
					+ "WHERE ctd.maDonDatPhong = ? AND p.trangThai = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, maDonDatPhong);
			stmt.setString(2, "Đang ở");
			rs = stmt.executeQuery();

			while (rs.next()) {
				// Dữ liệu phòng
				String soPhong = rs.getString("soPhong");
				String trangThai = rs.getString("trangThai");
				String mota = rs.getString("moTa");

				// Dữ liệu loại phòng đầy đủ
				String maLoai = rs.getString("maLoaiPhong");
				String tenLoai = rs.getString("tenLoai");
				int soLuong = rs.getInt("soLuong");
				float dienTich = rs.getFloat("dienTich");
				double giaGio = rs.getDouble("giaTheoGio");
				double giaNgay = rs.getDouble("giaTheoNgay");
				double giaDem = rs.getDouble("giaTheoDem");
				double phuThu = rs.getDouble("phuThuQuaGio");

				// Tạo LoaiPhong
				LoaiPhong lp = new LoaiPhong(maLoai, tenLoai, soLuong, dienTich, giaGio, giaNgay, giaDem, phuThu);

				// Tạo Phong
				Phong p = new Phong(soPhong, trangThai, lp, mota);
				dsPhong.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace(); // hoặc log lỗi
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
			}
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}

		return dsPhong;
	}

	public ArrayList<Phong> getPhongTheoMaDonDatPhong1(String maDonDatPhong) {
		ArrayList<Phong> dsPhong = new ArrayList<>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			con = ConnectDB.getConnection();
			String sql = "SELECT p.soPhong, p.trangThai, lp.*, p.moTa " + "FROM Phong p "
					+ "JOIN ChiTietDonDatPhong ctd ON p.soPhong = ctd.soPhong "
					+ "JOIN LoaiPhong lp ON p.loaiPhong = lp.maLoaiPhong " + "WHERE ctd.maDonDatPhong = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, maDonDatPhong);
			rs = stmt.executeQuery();

			while (rs.next()) {
				// Dữ liệu phòng
				String soPhong = rs.getString("soPhong");
				String trangThai = rs.getString("trangThai");
				String mota = rs.getString("moTa");

				// Dữ liệu loại phòng đầy đủ
				String maLoai = rs.getString("maLoaiPhong");
				String tenLoai = rs.getString("tenLoai");
				int soLuong = rs.getInt("soLuong");
				float dienTich = rs.getFloat("dienTich");
				double giaGio = rs.getDouble("giaTheoGio");
				double giaNgay = rs.getDouble("giaTheoNgay");
				double giaDem = rs.getDouble("giaTheoDem");
				double phuThu = rs.getDouble("phuThuQuaGio");

				// Tạo LoaiPhong
				LoaiPhong lp = new LoaiPhong(maLoai, tenLoai, soLuong, dienTich, giaGio, giaNgay, giaDem, phuThu);

				// Tạo Phong
				Phong p = new Phong(soPhong, trangThai, lp, mota);
				dsPhong.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace(); // hoặc log lỗi
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
			}
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}

		return dsPhong;
	}

	public Phong getPhongBySoPhong(String soPhong) {
		Phong phong = null;
		String sql = "SELECT p.soPhong, p.trangThai, p.moTa, lp.maLoaiPhong, lp.tenLoaiPhong, lp.donGia "
				+ "FROM Phong p JOIN LoaiPhong lp ON p.loaiPhong = lp.maLoaiPhong " + "WHERE p.soPhong = ?";

		try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, soPhong);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				String trangThai = rs.getString("trangThai");
				String mota = rs.getString("moTa");

				// Dữ liệu loại phòng đầy đủ
				String maLoai = rs.getString("maLoaiPhong");
				String tenLoai = rs.getString("tenLoai");
				int soLuong = rs.getInt("soLuong");
				float dienTich = rs.getFloat("dienTich");
				double giaGio = rs.getDouble("giaTheoGio");
				double giaNgay = rs.getDouble("giaTheoNgay");
				double giaDem = rs.getDouble("giaTheoDem");
				double phuThu = rs.getDouble("phuThuQuaGio");

				// Tạo LoaiPhong
				LoaiPhong lp = new LoaiPhong(maLoai, tenLoai, soLuong, dienTich, giaGio, giaNgay, giaDem, phuThu);

				// Tạo Phong
				Phong p = new Phong(soPhong, trangThai, lp, mota);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return phong;
	}

	public ArrayList<Phong> getPhongChuaThanhToan() {
		ArrayList<Phong> danhSach = new ArrayList<>();
		String sql = "SELECT p.soPhong, p.trangThai, lp.*, p.moTa " + "FROM Phong p "
				+ "JOIN LoaiPhong lp ON p.loaiPhong = lp.maLoaiPhong "
				+ "JOIN ChiTietDonDatPhong ctd ON p.soPhong = ctd.soPhong "
				+ "JOIN DonDatPhong ddp ON ctd.maDonDatPhong = ddp.maDonDatPhong "
				+ "WHERE ddp.trangThai = ? AND p.trangThai = ?"; // "Chưa thanh toán"

		try (Connection conn = ConnectDB.getConnection(); // Thay ConnectDB bằng class kết nối DB của bạn
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, "Nhận phòng");
			stmt.setString(2, "Đang ở");
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					// Dữ liệu phòng
					String soPhong = rs.getString("soPhong");
					String trangThai = rs.getString("trangThai");
					String mota = rs.getString("moTa");

					// Dữ liệu loại phòng đầy đủ
					String maLoai = rs.getString("maLoaiPhong");
					String tenLoai = rs.getString("tenLoai");
					int soLuong = rs.getInt("soLuong");
					float dienTich = rs.getFloat("dienTich");
					double giaGio = rs.getDouble("giaTheoGio");
					double giaNgay = rs.getDouble("giaTheoNgay");
					double giaDem = rs.getDouble("giaTheoDem");
					double phuThu = rs.getDouble("phuThuQuaGio");

					// Tạo LoaiPhong
					LoaiPhong lp = new LoaiPhong(maLoai, tenLoai, soLuong, dienTich, giaGio, giaNgay, giaDem, phuThu);

					// Tạo Phong
					Phong p = new Phong(soPhong, trangThai, lp, mota);
					danhSach.add(p);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return danhSach;
	}

	public boolean setTrangThaiPhong(String soPhong, String trangThai) {
		String sql = "UPDATE Phong SET trangThai = ? WHERE soPhong = ?";

		try (Connection conn = ConnectDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, trangThai);
			stmt.setString(2, soPhong);

			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0; // true nếu có ít nhất 1 dòng bị ảnh hưởng
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public ArrayList<String> getPhongTrongTheoLoaiVaThoiGian(String tenLoai, LocalDateTime thoiDiem) {
	    ArrayList<String> ds = new ArrayList<>();
	    String sql = """
	        SELECT p.soPhong
	        FROM Phong p
	        JOIN LoaiPhong lp ON p.loaiPhong = lp.maLoaiPhong
	        WHERE lp.tenLoai = ?
	          AND p.soPhong NOT IN (
	              SELECT soPhong
	              FROM ChiTietSuDungPhong
	              WHERE ? BETWEEN ngayBatDau AND ngayKetThuc
	          )
	    """;

	    try (Connection con = ConnectDB.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setString(1, tenLoai);
	        ps.setTimestamp(2, Timestamp.valueOf(thoiDiem));

	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            ds.add(rs.getString("soPhong"));
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return ds;
	}


	public String getTenLoaiTheoSoPhong(String soPhong) {
		String sql = "SELECT lp.tenLoai FROM Phong p JOIN LoaiPhong lp ON p.loaiPhong = lp.maLoaiPhong WHERE p.soPhong = ?";
		try (Connection con = ConnectDB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, soPhong);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("tenLoai");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
