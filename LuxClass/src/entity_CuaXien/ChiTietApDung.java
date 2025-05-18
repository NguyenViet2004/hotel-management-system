package entity_CuaXien;

public class ChiTietApDung {
	  private String maDonDatPhong;
	    private String maKhuyenMai;
	    private Float tongThanhToanSauApDung;
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
		
		public Float getTongThanhToanSauApDung() {
			return tongThanhToanSauApDung;
		}
		public void setTongThanhToanSauApDung(Float tongThanhToanSauApDung) {
			this.tongThanhToanSauApDung = tongThanhToanSauApDung;
		}
		public ChiTietApDung(String maDonDatPhong, String maKhuyenMai, Float tongThanhToanSauApDung) {
			super();
			this.maDonDatPhong = maDonDatPhong;
			this.maKhuyenMai = maKhuyenMai;
			this.tongThanhToanSauApDung = tongThanhToanSauApDung;
		}

	    
	    
}
