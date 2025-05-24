package dao_CuaXien;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import connectDB_CuaXien.ConnectDB;
import entity_CuaXien.DonDatPhong;
import entity_CuaXien.KhachHang;
import entity_CuaXien.NhanVien;

public class DonDatPhong_DAO {
	ArrayList<DonDatPhong> danhSach;

	public DonDatPhong_DAO() {
		// TODO Auto-generated constructor stub
		danhSach = new ArrayList<>();
	}

	public ArrayList<DonDatPhong> getDonDatPhongDaThanhToan() {
		ArrayList<DonDatPhong> danhSach = new ArrayList<>();

		String sql = "SELECT ddp.*, kh.maKH, kh.hoTen AS tenKH, kh.sdt AS sdtKH, kh.soCCCD, kh.email, "
				+ "nv.maNV, nv.hoTen AS tenNV, nv.ngaySinh, nv.sdt AS sdtNV, nv.diaChi, "
				+ "nv.soCCCD AS cccdNV, nv.chucVu, nv.caLamViec " + "FROM DonDatPhong ddp "
				+ "JOIN KhachHang kh ON ddp.maKH = kh.maKH " + "JOIN NhanVien nv ON ddp.maNV = nv.maNV "
				+ "WHERE ddp.trangThai = N'Đã thanh toán'";

		try (Connection conn = ConnectDB.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				KhachHang kh = new KhachHang(rs.getString("maKH"), rs.getString("tenKH"), rs.getString("sdtKH"),
						rs.getString("soCCCD"), rs.getString("email"));

				NhanVien nv = new NhanVien(rs.getString("maNV"), rs.getString("tenNV"),
						rs.getDate("ngaySinh").toLocalDate(), rs.getString("sdtNV"), rs.getString("diaChi"),
						rs.getString("cccdNV"), rs.getString("chucVu"), rs.getString("caLamViec"));

				DonDatPhong ddp = new DonDatPhong(rs.getString("maDonDatPhong"), kh,
						rs.getTimestamp("ngayDatPhong").toLocalDateTime(),
						rs.getTimestamp("ngayNhanPhong").toLocalDateTime(),
						rs.getTimestamp("ngayTraPhong").toLocalDateTime(), rs.getInt("soKhach"),
						rs.getDouble("tienCoc"), nv, rs.getString("loaiDon"), rs.getString("trangThai"));

				danhSach.add(ddp);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return danhSach;
	}

	public ArrayList<DonDatPhong> getDonDatPhongDaThanhToanTheoTenVaSĐT(String ten, String sdt) {
		ArrayList<DonDatPhong> danhSach = new ArrayList<>();

		String sql = "SELECT ddp.*, kh.maKH, kh.hoTen AS tenKH, kh.sdt AS sdtKH, kh.soCCCD, kh.email, "
				+ "nv.maNV, nv.hoTen AS tenNV, nv.ngaySinh, nv.sdt AS sdtNV, nv.diaChi, "
				+ "nv.soCCCD AS cccdNV, nv.chucVu, nv.caLamViec " + "FROM DonDatPhong ddp "
				+ "JOIN KhachHang kh ON ddp.maKH = kh.maKH " + "JOIN NhanVien nv ON ddp.maNV = nv.maNV "
				+ "WHERE ddp.trangThai = N'Đã thanh toán' " + "AND (kh.hoTen LIKE ? OR kh.sdt LIKE ?)";
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%" + ten + "%");
			stmt.setString(2, "%" + sdt + "%");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				KhachHang kh = new KhachHang(rs.getString("maKH"), rs.getString("tenKH"), rs.getString("sdtKH"),
						rs.getString("soCCCD"), rs.getString("email"));

				NhanVien nv = new NhanVien(rs.getString("maNV"), rs.getString("tenNV"),
						rs.getDate("ngaySinh").toLocalDate(), rs.getString("sdtNV"), rs.getString("diaChi"),
						rs.getString("cccdNV"), rs.getString("chucVu"), rs.getString("caLamViec"));

				DonDatPhong ddp = new DonDatPhong(rs.getString("maDonDatPhong"), kh,
						rs.getTimestamp("ngayDatPhong").toLocalDateTime(),
						rs.getTimestamp("ngayNhanPhong").toLocalDateTime(),
						rs.getTimestamp("ngayTraPhong").toLocalDateTime(), rs.getInt("soKhach"),
						rs.getDouble("tienCoc"), nv, rs.getString("loaiDon"), rs.getString("trangThai"));

				danhSach.add(ddp);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return danhSach;
	}

	public ArrayList<DonDatPhong> getDonDatPhongTheoMaPhong(String soPhong) {
		String sql = "SELECT ddp.*, kh.maKH, kh.hoTen AS tenKH, kh.sdt AS sdtKH, kh.soCCCD, "
				+ "nv.maNV, nv.hoTen AS tenNV, nv.ngaySinh, nv.sdt AS sdtNV, nv.diaChi, "
				+ "nv.soCCCD AS cccdNV, nv.chucVu, nv.caLamViec " + "FROM DonDatPhong ddp "
				+ "JOIN ChiTietDonDatPhong ct ON ddp.maDonDatPhong = ct.maDonDatPhong "
				+ "JOIN KhachHang kh ON ddp.maKH = kh.maKH " + "JOIN NhanVien nv ON ddp.maNV = nv.maNV "
				+ "WHERE ct.soPhong = ? AND ddp.trangThai = N'Chưa thanh toán'";

		Connection conn = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, soPhong);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				KhachHang kh = new KhachHang(rs.getString("maKH"), rs.getString("tenKH"), rs.getString("sdtKH"),
						rs.getString("soCCCD"), rs.getString("email"));
				NhanVien nv = new NhanVien(rs.getString("maNV"), rs.getString("tenNV"),
						rs.getDate("ngaySinh").toLocalDate(), rs.getString("sdtNV"), rs.getString("diaChi"),
						rs.getString("cccdNV"), rs.getString("chucVu"), rs.getString("caLamViec"));
				DonDatPhong ddp = new DonDatPhong(rs.getString("maDonDatPhong"), kh,
						rs.getTimestamp("ngayNhanPhong").toLocalDateTime(),
						rs.getTimestamp("ngayTraPhong").toLocalDateTime(), rs.getInt("soKhach"),
						rs.getDouble("tienCoc"), nv, rs.getString("loaiDon"), rs.getString("trangThai"));
				danhSach.add(ddp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return danhSach;
	}

	public DonDatPhong getDonDatPhongTheoMaP(String soPhong) {
		String sql = "SELECT ddp.*, kh.maKH, kh.hoTen AS tenKH, kh.sdt AS sdtKH, kh.soCCCD, kh.email, "
				+ "nv.maNV, nv.hoTen AS tenNV, nv.ngaySinh, nv.sdt AS sdtNV, nv.diaChi, "
				+ "nv.soCCCD AS cccdNV, nv.chucVu, nv.caLamViec " + "FROM DonDatPhong ddp "
				+ "JOIN ChiTietDonDatPhong ct ON ddp.maDonDatPhong = ct.maDonDatPhong "
				+ "JOIN KhachHang kh ON ddp.maKH = kh.maKH " + "JOIN NhanVien nv ON ddp.maNV = nv.maNV "
				+ "WHERE ct.soPhong = ? AND ddp.trangThai = N'Chưa thanh toán'";

		try (Connection conn = ConnectDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, soPhong);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				KhachHang kh = new KhachHang(rs.getString("maKH"), rs.getString("tenKH"), rs.getString("sdtKH"),
						rs.getString("soCCCD"), rs.getString("email"));

				NhanVien nv = new NhanVien(rs.getString("maNV"), rs.getString("tenNV"),
						rs.getDate("ngaySinh").toLocalDate(), rs.getString("sdtNV"), rs.getString("diaChi"),
						rs.getString("cccdNV"), rs.getString("chucVu"), rs.getString("caLamViec"));

				return new DonDatPhong(rs.getString("maDonDatPhong"), kh,
						rs.getTimestamp("ngayNhanPhong").toLocalDateTime(),
						rs.getTimestamp("ngayTraPhong").toLocalDateTime(), rs.getInt("soKhach"),
						rs.getDouble("tienCoc"), nv, rs.getString("loaiDon"), rs.getString("trangThai"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null; // Nếu không tìm thấy
	}

	public ArrayList<DonDatPhong> getDonDatPhongTheoTenVaSDT(String tenKH, String sdt) {
		String sql = "SELECT ddp.*, kh.maKH, kh.hoTen AS tenKH, kh.sdt AS sdtKH, kh.soCCCD, kh.email,"
				+ "nv.maNV, nv.hoTen AS tenNV, nv.ngaySinh, nv.sdt AS sdtNV, nv.diaChi, "
				+ "nv.soCCCD AS cccdNV, nv.chucVu, nv.caLamViec " + "FROM DonDatPhong ddp "
				+ "JOIN KhachHang kh ON ddp.maKH = kh.maKH " + "JOIN NhanVien nv ON ddp.maNV = nv.maNV "
				+ "WHERE (kh.hoTen LIKE ? OR kh.sdt = ?) AND ddp.trangThai = N'Chưa thanh toán'";

		try {
			Connection connection = ConnectDB.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, "%" + tenKH + "%");
			stmt.setString(2, sdt);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					KhachHang kh = new KhachHang(rs.getString("maKH"), rs.getString("tenKH"), rs.getString("sdtKH"),
							rs.getString("soCCCD"), rs.getString("email"));
					NhanVien nv = new NhanVien(rs.getString("maNV"), rs.getString("tenNV"),
							rs.getDate("ngaySinh").toLocalDate(), rs.getString("sdtNV"), rs.getString("diaChi"),
							rs.getString("cccdNV"), rs.getString("chucVu"), rs.getString("caLamViec"));
					DonDatPhong ddp = new DonDatPhong(rs.getString("maDonDatPhong"), kh,
							rs.getTimestamp("ngayNhanPhong").toLocalDateTime(),
							rs.getTimestamp("ngayTraPhong").toLocalDateTime(), rs.getInt("soKhach"),
							rs.getDouble("tienCoc"), nv, rs.getString("loaiDon"), rs.getString("trangThai"));
					danhSach.add(ddp);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return danhSach;
	}

	public DonDatPhong getDonDatPhongTheoMa(String maDon) {
		String sql = "SELECT ddp.*, kh.maKH, kh.hoTen AS tenKH, kh.sdt AS sdtKH, kh.soCCCD, kh.email, "
				+ "nv.maNV, nv.hoTen AS tenNV, nv.ngaySinh, nv.sdt AS sdtNV, nv.diaChi, "
				+ "nv.soCCCD AS cccdNV, nv.chucVu, nv.caLamViec " + "FROM DonDatPhong ddp "
				+ "JOIN KhachHang kh ON ddp.maKH = kh.maKH " + "JOIN NhanVien nv ON ddp.maNV = nv.maNV "
				+ "WHERE ddp.maDonDatPhong = ?";

		try {
			Connection connection = ConnectDB.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, maDon);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					KhachHang kh = new KhachHang(rs.getString("maKH"), rs.getString("tenKH"), rs.getString("sdtKH"),
							rs.getString("soCCCD"), rs.getString("email"));
					NhanVien nv = new NhanVien(rs.getString("maNV"), rs.getString("tenNV"),
							rs.getDate("ngaySinh").toLocalDate(), rs.getString("sdtNV"), rs.getString("diaChi"),
							rs.getString("cccdNV"), rs.getString("chucVu"), rs.getString("caLamViec"));
					return new DonDatPhong(rs.getString("maDonDatPhong"), kh,
							rs.getTimestamp("ngayDatPhong").toLocalDateTime(),
							rs.getTimestamp("ngayNhanPhong").toLocalDateTime(),
							rs.getTimestamp("ngayTraPhong").toLocalDateTime(), rs.getInt("soKhach"),
							rs.getDouble("tienCoc"), nv, rs.getString("loaiDon"), rs.getString("trangThai"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean setTrangThaiDonDatPhong(String maDonDatPhong, String trangThai) {
		String sql = "UPDATE DonDatPhong SET trangThai = ? WHERE maDonDatPhong = ?";

		try (Connection conn = ConnectDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, trangThai);
			stmt.setString(2, maDonDatPhong);

			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean setTienCocVeKhong(String maDonDatPhong) {
		String sql = "UPDATE DonDatPhong SET tienCoc = 0 WHERE maDonDatPhong = ?";

		try (Connection conn = ConnectDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, maDonDatPhong);

			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean coTheCapNhatTrangThai(String maDonDatPhong) {
		String sql = """
				    SELECT COUNT(*) FROM ChiTietDonDatPhong ctd
				    JOIN Phong p ON ctd.soPhong = p.soPhong
				    WHERE ctd.maDonDatPhong = ? AND p.trangThai <> N'Trống'
				""";

		try (Connection con = ConnectDB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, maDonDatPhong);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int count = rs.getInt(1);
				return count == 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

}
