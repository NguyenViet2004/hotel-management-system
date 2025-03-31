package entity;

public class LoaiPhong {
    private String maLoaiPhong;
    private String tenLoai;
    private int soLuong;
    private float dienTich;
    private double giaTheoGio;
    private double giaTheoNgay;
    private double giaTheoDem;
    private double phuThuQuaGio;

    // Constructor mặc định
    public LoaiPhong() {
    }
    public LoaiPhong(String maLoaiPhong) {
    	this.maLoaiPhong = maLoaiPhong;
    }
    // Constructor đầy đủ tham số
    public LoaiPhong(String maLoaiPhong, String tenLoai, int soLuong, float dienTich,
                     double giaTheoGio, double giaTheoNgay, double giaTheoDem, double phuThuQuaGio) {
        this.maLoaiPhong = maLoaiPhong;
        this.tenLoai = tenLoai;
        this.soLuong = soLuong;
        this.dienTich = dienTich;
        this.giaTheoGio = giaTheoGio;
        this.giaTheoNgay = giaTheoNgay;
        this.giaTheoDem = giaTheoDem;
        this.phuThuQuaGio = phuThuQuaGio;
    }

    // Getter và Setter
    public String getMaLoaiPhong() {
        return maLoaiPhong;
    }

    public void setMaLoaiPhong(String maLoaiPhong) {
        this.maLoaiPhong = maLoaiPhong;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public float getDienTich() {
        return dienTich;
    }

    public void setDienTich(float dienTich) {
        this.dienTich = dienTich;
    }

    public double getGiaTheoGio() {
        return giaTheoGio;
    }

    public void setGiaTheoGio(double giaTheoGio) {
        this.giaTheoGio = giaTheoGio;
    }

    public double getGiaTheoNgay() {
        return giaTheoNgay;
    }

    public void setGiaTheoNgay(double giaTheoNgay) {
        this.giaTheoNgay = giaTheoNgay;
    }

    public double getGiaTheoDem() {
        return giaTheoDem;
    }

    public void setGiaTheoDem(double giaTheoDem) {
        this.giaTheoDem = giaTheoDem;
    }

    public double getPhuThuQuaGio() {
        return phuThuQuaGio;
    }

    public void setPhuThuQuaGio(double phuThuQuaGio) {
        this.phuThuQuaGio = phuThuQuaGio;
    }

    // Phương thức hiển thị thông tin LoaiPhong
    @Override
    public String toString() {
        return "LoaiPhong{" +
                "maLoaiPhong='" + maLoaiPhong + '\'' +
                ", tenLoai='" + tenLoai + '\'' +
                ", soLuong=" + soLuong +
                ", dienTich=" + dienTich +
                ", giaTheoGio=" + giaTheoGio +
                ", giaTheoNgay=" + giaTheoNgay +
                ", giaTheoDem=" + giaTheoDem +
                ", phuThuQuaGio=" + phuThuQuaGio +
                '}';
    }
}

