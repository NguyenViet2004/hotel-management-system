package Demo;

import java.time.LocalDate;

import dao.NhanVien_Dao;
import entity.NhanVien;

public class TestThemNhanVien {
    public static void main(String[] args) {
        // Khởi tạo DAO
        NhanVien_Dao nvDao = new NhanVien_Dao();

        // Tạo nhân viên mới
        NhanVien nv = new NhanVien(
                "2025LT001",                       // Mã NV
                "Nguyễn Quốc Việt",            // Họ tên
                LocalDate.of(2004, 9, 27),     // Ngày sinh
                "0333333333",                 // SĐT
                "Gia Lai",         // Địa chỉ
                "123456789012",               // CCCD
                "Quản lý",         // Chức vụ
                "Ca tối"                     // Ca làm việc
        );

        // Gọi hàm thêm
        boolean themThanhCong = nvDao.them(nv);
//        NhanVien tim = nvDao.timTheoMa("NV005");
//        System.out.println(tim);
//        boolean xoa = nvDaso.xoa("NV005");
        // In kết quả
        if (themThanhCong) {
            System.out.println("Thêm nhân viên thành công!");
        } else {
            System.out.println("Thêm nhân viên thất bại hoặc nhân viên đã tồn tại.");
        }
    }
}
