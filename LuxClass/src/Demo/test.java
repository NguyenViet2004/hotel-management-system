package Demo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class test {
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String vietString;
        try {
            // Kết nối đến cơ sở dữ liệu
            conn = connectDB.ConnectDB.getConnection();
            stmt = conn.createStatement();
            
            // Truy vấn dữ liệu từ bảng Phong
            String sql = "SELECT * FROM Phong";
            rs = stmt.executeQuery(sql);
            
            // Hiển thị dữ liệu
            System.out.println("Danh sách phòng:");
            System.out.println("-------------------------------------------------");
            while (rs.next()) {
                String soPhong = rs.getString("soPhong");
                String loaiPhong = rs.getString("loaiPhong");
                String trangThai = rs.getString("trangThai");
                String moTa = rs.getString("moTa");

                System.out.printf("Số Phòng: %s | Loại: %-10s | Trạng thái: %s | Mô tả: %s\n",
                                  soPhong, loaiPhong, trangThai, moTa);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
