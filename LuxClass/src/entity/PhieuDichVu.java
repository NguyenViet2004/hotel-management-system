package entity;

public class PhieuDichVu {

	private String maPhieuDichVu;
	private DonDatPhong donDatPhong;

	public PhieuDichVu() {
		// TODO - implement PhieuDichVu.PhieuDichVu
		throw new UnsupportedOperationException();
	}

	public double tinhTienDichVu() {
		// TODO - implement PhieuDichVu.tinhTienDichVu
		throw new UnsupportedOperationException();
	}

	public PhieuDichVu(String maPhieuDichVu, DonDatPhong donDatPhong) {
		super();
		this.maPhieuDichVu = maPhieuDichVu;
		this.donDatPhong = donDatPhong;
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

	public DonDatPhong getDonDatPhong() {
		return donDatPhong;
	}

	public void setDonDatPhong(DonDatPhong donDatPhong) {
		this.donDatPhong = donDatPhong;
	}
	
}