package entity;

public class Phong {
    private String maPhong;
    private String trangThai;
    private LoaiPhong loaiPhong;
    private String moTa;

    // Constructor mặc định
    public Phong() {
    }
    public Phong(String maPhong) {
    	this.maPhong = maPhong;
    }
    // Constructor đầy đủ tham số
    public Phong(String maPhong, String trangThai, LoaiPhong loaiPhong, String moTa) {
        this.maPhong = maPhong;
        this.trangThai = trangThai;
        this.loaiPhong = loaiPhong;
        this.moTa = moTa;
    }

    // Getter và Setter
    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public LoaiPhong getLoaiPhong() {
        return loaiPhong;
    }

    public void setLoaiPhong(LoaiPhong loaiPhong) {
        this.loaiPhong = loaiPhong;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    // Phương thức hiển thị thông tin Phong
    @Override
    public String toString() {
        return "Phong{" +
                "maPhong='" + maPhong + '\'' +
                ", trangThai='" + trangThai + '\'' +
                ", loaiPhong=" + (loaiPhong != null ? loaiPhong.getTenLoai() : "null") +
                ", moTa='" + moTa + '\'' +
                '}';
    }
}

