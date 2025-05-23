package entity_CuaXien;

import java.time.LocalDateTime;

public class DonDatPhong {

	private String maDonDatPhong;
	private KhachHang khachHang;
	private LocalDateTime ngayDatPhong;
	private LocalDateTime ngayNhanPhong;
	private LocalDateTime ngayTraPhong;
	private int soKhach;
	private double tienCoc;
	private NhanVien nhanVien;
	private String loaiDon;
	private String trangThai;

	public DonDatPhong() {
		// TODO - implement DonDatPhong.DonDatPhong

	}
	
	public DonDatPhong(String maDonDatPhong, KhachHang khachHang, LocalDateTime ngayNhanPhong,
			LocalDateTime ngayTraPhong, int soKhach, double tienCoc, NhanVien nhanVien, String loaiDon,
			String trangThai) {
		super();
		this.maDonDatPhong = maDonDatPhong;
		this.khachHang = khachHang;
		this.ngayNhanPhong = ngayNhanPhong;
		this.ngayTraPhong = ngayTraPhong;
		this.soKhach = soKhach;
		this.tienCoc = tienCoc;
		this.nhanVien = nhanVien;
		this.loaiDon = loaiDon;
		this.trangThai = trangThai;
	}
//	
	public DonDatPhong(String maDonDatPhong, KhachHang khachHang, LocalDateTime ngayDatPhong,
			LocalDateTime ngayNhanPhong, LocalDateTime ngayTraPhong, int soKhach, double tienCoc, NhanVien nhanVien,
			String loaiDon, String trangThai) {
		super();
		this.maDonDatPhong = maDonDatPhong;
		this.khachHang = khachHang;
		this.ngayDatPhong = ngayDatPhong;
		this.ngayNhanPhong = ngayNhanPhong;
		this.ngayTraPhong = ngayTraPhong;
		this.soKhach = soKhach;
		this.tienCoc = tienCoc;
		this.nhanVien = nhanVien;
		this.loaiDon = loaiDon;
		this.trangThai = trangThai;
	}

	public DonDatPhong(String maDonDatPhong) {
		super();
		this.maDonDatPhong = maDonDatPhong;
	}
	
	public String getMaDonDatPhong() {
		return maDonDatPhong;
	}

	public void setMaDonDatPhong(String maDonDatPhong) {
		this.maDonDatPhong = maDonDatPhong;
	}

	public KhachHang getKhachHang() {
		return khachHang;
	}

	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	}

	public LocalDateTime getNgayNhanPhong() {
		return ngayNhanPhong;
	}

	public void setNgayNhanPhong(LocalDateTime ngayNhanPhong) {
		this.ngayNhanPhong = ngayNhanPhong;
	}

	public LocalDateTime getNgayTraPhong() {
		return ngayTraPhong;
	}

	public void setNgayTraPhong(LocalDateTime ngayTraPhong) {
		this.ngayTraPhong = ngayTraPhong;
	}

	public int getSoKhach() {
		return soKhach;
	}

	public void setSoKhach(int soKhach) {
		this.soKhach = soKhach;
	}

	public double getTienCoc() {
		return tienCoc;
	}

	public void setTienCoc(double tienCoc) {
		this.tienCoc = tienCoc;
	}

	public NhanVien getNhanVien() {
		return nhanVien;
	}

	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}

	public String getLoaiDon() {
		return loaiDon;
	}

	public void setLoaiDon(String loaiDon) {
		this.loaiDon = loaiDon;
	}

	public String getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}

	public double tinhTienPhong() {
		// TODO - implement DonDatPhong.tinhTienPhong
		throw new UnsupportedOperationException();
	}

	
	public LocalDateTime getNgayDatPhong() {
		return ngayDatPhong;
	}

	public void setNgayDatPhong(LocalDateTime ngayDatPhong) {
		this.ngayDatPhong = ngayDatPhong;
	}

	/**
	 * 
	 * @param Phong
	 */
	public boolean catNhapTinhTrangPhong(int Phong) {
		// TODO - implement DonDatPhong.catNhapTinhTrangPhong
		throw new UnsupportedOperationException();
	}

	public double tinhTongTien() {
		// TODO - implement DonDatPhong.tinhTongTien
		throw new UnsupportedOperationException();
	}

}