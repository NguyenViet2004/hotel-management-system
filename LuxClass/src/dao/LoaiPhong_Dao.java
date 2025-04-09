package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.LoaiPhong;

public class LoaiPhong_Dao {
private ArrayList<LoaiPhong> dslp;
    
    public LoaiPhong_Dao() {
        dslp = new ArrayList<LoaiPhong>();
    }

    public ArrayList<LoaiPhong> getAllLoaiPhong() {
        try (Connection con = ConnectDB.getConnection()) {
            String sql = "SELECT * FROM LoaiPhong";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                String maLoaiPhong = rs.getString(1);
                String tenLoai = rs.getString(2);
                int soLuong = rs.getInt(3);
                float dienTich = rs.getFloat(4);
                double giaTheoGio = rs.getDouble(5);
                double giaTheoNgay = rs.getDouble(6);
                double giaTheoDem = rs.getDouble(7);
                double phuThuQuaGio = rs.getDouble(8);

                LoaiPhong lp = new LoaiPhong(maLoaiPhong,tenLoai,soLuong,dienTich,giaTheoGio,giaTheoNgay,giaTheoDem,phuThuQuaGio);
                dslp.add(lp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dslp;
    }
}
