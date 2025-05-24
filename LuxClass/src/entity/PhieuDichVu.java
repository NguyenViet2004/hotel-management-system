package entity;

import java.time.LocalDateTime;

public class PhieuDichVu {
	// TODO Auto-generated constructor stub
	private String maPhieuDichVu;
	private String maDonDatPhong;
	private LocalDateTime ngayLapPhieu;
	private String trangThai;
	public PhieuDichVu() {
		super();
	}
	public PhieuDichVu(String maPhieuDichVu, String maDonDatPhong, LocalDateTime ngayLapPhieu, String trangThai) {
		super();
		this.maPhieuDichVu = maPhieuDichVu;
		this.maDonDatPhong = maDonDatPhong;
		this.ngayLapPhieu = ngayLapPhieu;
		this.trangThai = trangThai;
	}
	public PhieuDichVu(String maPhieuDichVu) {
		super();
		this.maPhieuDichVu = maPhieuDichVu;
	}
	public String getMaPhieuDichVu() {
		return maPhieuDichVu;
	}
	public void setMaPhieuDichVu(String maPhieuDichVu) {
		this.maPhieuDichVu = maPhieuDichVu;
	}
	public String getMaDonDatPhong() {
		return maDonDatPhong;
	}
	public void setMaDonDatPhong(String maDonDatPhong) {
		this.maDonDatPhong = maDonDatPhong;
	}
	public LocalDateTime getNgayLapPhieu() {
		return ngayLapPhieu;
	}
	public void setNgayLapPhieu(LocalDateTime ngayLapPhieu) {
		this.ngayLapPhieu = ngayLapPhieu;
	}
	public String getTrangThai() {
		return trangThai;
	}
	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}
	@Override
	public String toString() {
		return "PhieuDichVu [maPhieuDichVu=" + maPhieuDichVu + ", maDonDatPhong=" + maDonDatPhong + ", ngayLapPhieu="
				+ ngayLapPhieu + ", trangThai=" + trangThai + "]";
	}
	
}
