package entity;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Map;

import dao.ChiTietDonDatPhong_Dao;
import dao.LoaiPhong_Dao;
import dao.Phong_Dao;

public class DonDatPhong {

	private String maDonDatPhong;
	private KhachHang khachHang;
	private LocalDateTime ngayNhanPhong;
	private LocalDateTime ngayTraPhong;
	private int soKhach;
	private double tienCoc;
	private NhanVien nhanVien;
	private String loaiDon;
	private String trangThai;
	private ArrayList<ChiTietDonDatPhong> chiTietPhong;
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

	public ArrayList<ChiTietDonDatPhong> getChiTietPhong() {
	    return chiTietPhong;
	}

	public void setChiTietPhong(ArrayList<ChiTietDonDatPhong> chiTietPhong) {
	    this.chiTietPhong = chiTietPhong;
	}

	public double tinhTienPhong() {
	    ChiTietDonDatPhong_Dao chiTietDonDatPhong_Dao = new ChiTietDonDatPhong_Dao();
	    chiTietPhong = chiTietDonDatPhong_Dao.getChiTietDonDatPhongTheoMaDon(maDonDatPhong);

	    Phong_Dao phong_Dao = new Phong_Dao();
	    LoaiPhong_Dao loaiPhongDao = new LoaiPhong_Dao();

	    double tongTien = 0;
	    
	    // Chuyển LocalDateTime thành Instant
	    ZoneId zone = ZoneId.systemDefault(); // hoặc chọn zone cụ thể nếu cần
	    Instant start = ngayNhanPhong.atZone(zone).toInstant();
	    Instant end = ngayTraPhong.atZone(zone).toInstant();
	    
	    // Tính số giờ và số ngày (hoặc số đêm nếu cần)
	    long soGio = Duration.between(start, end).toHours();  // Tổng số giờ
	    long soNgay = Duration.between(start, end).toDays();  // Tổng số ngày
	    long soDem = soNgay;  // Theo logic tính số đêm như số ngày

	    // Nếu bạn cần làm tròn số ngày/đêm, tính lại như sau:
	    if (Duration.between(start, end).toHours() > 12) {
	        soNgay++;
	    }

	    // Vòng lặp xử lý các chi tiết phòng
	    for (ChiTietDonDatPhong ct : chiTietPhong) {
	        Phong phong = ct.getPhong();
	        phong = phong_Dao.getPhongTheoMa(phong.getSoPhong());  // Lấy thông tin phòng
	        LoaiPhong loaiPhong = loaiPhongDao.getLoaiPhongTheoMa(phong.getLoaiPhong().getMaLoaiPhong());  // Lấy loại phòng

	        // Tính tiền tùy theo loại đơn
	        switch (loaiDon) {
	            case "Theo giờ":
	                tongTien += loaiPhong.getGiaTheoGio() * soGio;
	                break;
	            case "Theo ngày":
	                tongTien += loaiPhong.getGiaTheoNgay() * soNgay;
	                break;
	            case "Theo đêm":
	                tongTien += loaiPhong.getGiaTheoDem() * soDem;
	                break;
	            default:
	                break;
	        }
	    }
	    return tongTien;
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