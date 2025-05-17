package entity_CuaXien;

public class ChiTietApDung {
	  private String maDonDatPhong;
	    private String maKhuyenMai;
	    private Float tongThanhToanSauApDung;
	    private DonDatPhong donDatPhong;
	    private KhuyenMai khuyenMai;
		public String getMaDonDatPhong() {
			return maDonDatPhong;
		}
		public void setMaDonDatPhong(String maDonDatPhong) {
			this.maDonDatPhong = maDonDatPhong;
		}
		public String getMaKhuyenMai() {
			return maKhuyenMai;
		}
		public void setMaKhuyenMai(String maKhuyenMai) {
			this.maKhuyenMai = maKhuyenMai;
		}
		public DonDatPhong getDonDatPhong() {
			return donDatPhong;
		}
		public void setDonDatPhong(DonDatPhong donDatPhong) {
			this.donDatPhong = donDatPhong;
		}
		public KhuyenMai getKhuyenMai() {
			return khuyenMai;
		}
		public void setKhuyenMai(KhuyenMai khuyenMai) {
			this.khuyenMai = khuyenMai;
		}
		
		public Float getTongThanhToanSauApDung() {
			return tongThanhToanSauApDung;
		}
		public void setTongThanhToanSauApDung(Float tongThanhToanSauApDung) {
			this.tongThanhToanSauApDung = tongThanhToanSauApDung;
		}
		public ChiTietApDung(String maDonDatPhong, String maKhuyenMai, Float tongThanhToanSauApDung,
				DonDatPhong donDatPhong, KhuyenMai khuyenMai) {
			super();
			this.maDonDatPhong = maDonDatPhong;
			this.maKhuyenMai = maKhuyenMai;
			this.tongThanhToanSauApDung = tongThanhToanSauApDung;
			this.donDatPhong = donDatPhong;
			this.khuyenMai = khuyenMai;
		}

	    
	    
}
