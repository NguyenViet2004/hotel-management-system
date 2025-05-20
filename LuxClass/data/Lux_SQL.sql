CREATE DATABASE QuanLyKhachSan;
go
USE QuanLyKhachSan;
go
CREATE TABLE LoaiPhong (
    maLoaiPhong VARCHAR(20) PRIMARY KEY,  -- Mã loại phòng (khóa chính)
    tenLoai NVARCHAR(50) NOT NULL,         -- Dùng NVARCHAR để hỗ trợ tiếng Việt
    soLuong INT NOT NULL,                 -- Số lượng phòng thuộc loại này
    dienTich FLOAT,                        -- Diện tích phòng (m2)
    giaTheoGio DECIMAL(10,2) NOT NULL,    -- Giá thuê theo giờ
    giaTheoNgay DECIMAL(10,2) NOT NULL,   -- Giá thuê theo ngày
    giaTheoDem DECIMAL(10,2) NOT NULL,    -- Giá thuê qua đêm
    phuThuQuaGio DECIMAL(10,2) NOT NULL   -- Phụ thu nếu ở quá giờ
);
go
CREATE TABLE Phong (
    soPhong VARCHAR(20) PRIMARY KEY,         -- Số phòng (mã phòng, khóa chính)
    trangThai NVARCHAR(50) NOT NULL,         -- Trạng thái phòng (VD: Trống, Đã đặt, Đang sử dụng)
    loaiPhong VARCHAR(20) NOT NULL,          -- Mã loại phòng (khóa ngoại từ bảng LoaiPhong)
	moTa NVARCHAR(200),
    CONSTRAINT FK_Phong_LoaiPhong FOREIGN KEY (loaiPhong) REFERENCES LoaiPhong(maLoaiPhong)
);
go
CREATE TABLE NhanVien (
    maNV VARCHAR(20) PRIMARY KEY,         -- Mã nhân viên (khóa chính)
    hoTen NVARCHAR(100) NOT NULL,         -- Họ và tên nhân viên
    ngaySinh DATE NOT NULL,               -- Ngày sinh
    sdt VARCHAR(15) NOT NULL,             -- Số điện thoại
    diaChi NVARCHAR(255) NOT NULL,        -- Địa chỉ nhân viên
    soCCCD VARCHAR(20) UNIQUE NOT NULL,   -- Số CCCD (Căn cước công dân)
    chucVu NVARCHAR(50) NOT NULL,         -- Chức vụ nhân viên
    caLamViec NVARCHAR(50) NOT NULL       -- Ca làm việc
);
go
CREATE TABLE TaiKhoan (
    tenDangNhap VARCHAR(50) PRIMARY KEY,  -- Tên đăng nhập (khóa chính)
    matKhau VARCHAR(255) NOT NULL,        -- Mật khẩu (có thể mã hóa sau)
    trangThai NVARCHAR(50) NOT NULL,      -- Trạng thái tài khoản (VD: Hoạt động, Vô hiệu hóa)
    maNV VARCHAR(20) UNIQUE,              -- Mã nhân viên (khóa ngoại, duy nhất)
    CONSTRAINT FK_TaiKhoan_NhanVien FOREIGN KEY (maNV) REFERENCES NhanVien(maNV)
        ON DELETE SET NULL                 -- Nếu xóa nhân viên, tài khoản vẫn còn nhưng không liên kết
);
go
CREATE TABLE KhachHang (
    maKH VARCHAR(20) PRIMARY KEY,       -- Mã khách hàng (khóa chính)
    hoTen NVARCHAR(100) NOT NULL,       -- Họ và tên khách hàng
    sdt VARCHAR(15) NOT NULL,           -- Số điện thoại
    soCCCD VARCHAR(20) UNIQUE NULL,  -- Số căn cước công dân (duy nhất)
	email NVARCHAR(50)
);
go
CREATE TABLE LoaiDichVu (
    maLoai VARCHAR(20) PRIMARY KEY,  -- Mã loại dịch vụ (khóa chính)
    tenLoai NVARCHAR(100) NOT NULL   -- Tên loại dịch vụ (hỗ trợ tiếng Việt)
);
go
CREATE TABLE DichVu (
    maDV VARCHAR(20) PRIMARY KEY,      -- Mã dịch vụ (khóa chính)
    tenDV NVARCHAR(100) NOT NULL,      -- Tên dịch vụ
    moTa NVARCHAR(255),                -- Mô tả dịch vụ
    giaDV FLOAT CHECK (giaDV >= 0),    -- Giá dịch vụ, không thể âm
    maLoai VARCHAR(20) NOT NULL,       -- Mã loại dịch vụ (khóa ngoại)
    CONSTRAINT FK_DichVu_LoaiDichVu FOREIGN KEY (maLoai) REFERENCES LoaiDichVu(maLoai)
);
go
CREATE TABLE DonDatPhong (
    maDonDatPhong VARCHAR(20) PRIMARY KEY,  -- Mã đơn đặt phòng (khóa chính)
    maKH VARCHAR(20) NOT NULL,              -- Mã khách hàng (khóa ngoại)
	ngayDatPhong DATETIME NOT NULL,         -- Ngày đặt phòng
    ngayNhanPhong DATETIME NOT NULL,        -- Ngày nhận phòng
    ngayTraPhong DATETIME NOT NULL,         -- Ngày trả phòng
    soKhach INT CHECK (soKhach > 0),        -- Số khách (phải lớn hơn 0)
    tienCoc FLOAT CHECK (tienCoc >= 0),     -- Tiền cọc (không thể âm)
	thoiGianCoc DATETIME NOT NULL,          -- Ngày đặt cọc
    maNV VARCHAR(20) NOT NULL,              -- Mã nhân viên xử lý đơn (khóa ngoại)
    loaiDon NVARCHAR(50) NOT NULL,          -- Loại đơn (hỗ trợ tiếng Việt)
	trangThai NVARCHAR(50) NOT NULL,         -- trạng thái đơn đặt phòng
    CONSTRAINT FK_DonDatPhong_KhachHang FOREIGN KEY (maKH) REFERENCES KhachHang(maKH),
    CONSTRAINT FK_DonDatPhong_NhanVien FOREIGN KEY (maNV) REFERENCES NhanVien(maNV)
);
go
CREATE TABLE ChiTietDonDatPhong (
    maDonDatPhong VARCHAR(20) NOT NULL,  -- Mã đơn đặt phòng (khóa ngoại)
    soPhong VARCHAR(20) NOT NULL,        -- Số phòng (khóa ngoại)
    soLuong INT CHECK (soLuong > 0),     -- Số lượng phòng (phải lớn hơn 0)
    PRIMARY KEY (maDonDatPhong, soPhong),  -- Khóa chính kết hợp
    CONSTRAINT FK_ChiTiet_DonDatPhong FOREIGN KEY (maDonDatPhong) REFERENCES DonDatPhong(maDonDatPhong),
    CONSTRAINT FK_ChiTiet_Phong FOREIGN KEY (soPhong) REFERENCES Phong(soPhong)
);
go
CREATE TABLE ChiPhiPhatSinh (
    maChiPhi VARCHAR(50) PRIMARY KEY,
    chiPhiTBHong FLOAT NOT NULL,
    soGioOQuaGio INT ,
    maDonDatPhong VARCHAR(20) NOT NULL,
	moTa NVARCHAR(255) NULL,
    FOREIGN KEY (maDonDatPhong) REFERENCES DonDatPhong(maDonDatPhong)
);
go
CREATE TABLE PhieuDichVu (
    maPhieuDichVu VARCHAR(50) PRIMARY KEY,
    maDonDatPhong VARCHAR(20) NOT NULL,
	ngayLapPhieu DATETIME NOT NULL,
    trangThai NVARCHAR(50) NOT NULL,
    FOREIGN KEY (maDonDatPhong) REFERENCES DonDatPhong(maDonDatPhong)
);
go
CREATE TABLE ChiTietPhieuDichVu (
    maPhieuDichVu VARCHAR(50),
    maDichVu VARCHAR(20),
    soLuong INT NOT NULL CHECK (soLuong > 0),
    PRIMARY KEY (maPhieuDichVu, maDichVu),
    FOREIGN KEY (maPhieuDichVu) REFERENCES PhieuDichVu(maPhieuDichVu),
    FOREIGN KEY (maDichVu) REFERENCES DichVu(maDV)
);
go
INSERT INTO LoaiPhong (maLoaiPhong, tenLoai, soLuong, dienTich, giaTheoGio, giaTheoNgay, giaTheoDem, phuThuQuaGio)
VALUES 
    ('single', N'Single Room', 8, 30, 150000, 800000, 700000, 100000),
    ('twin', N'Twin Room', 7, 50, 200000, 1200000, 1100000, 150000),
    ('double', N'Double Room', 10, 45, 250000, 1000000, 900000, 200000),
    ('triple', N'Triple Room', 6, 50, 300000, 1400000, 1300000, 250000);
go
INSERT INTO Phong (soPhong, trangThai, loaiPhong, moTa) VALUES
	-- Tổ hợp 1: Chỉ Ban công
	('P101', N'Trống', 'single', N'Ban công'),
	('P201', N'Trống', 'single', N'Ban công'),
	('P301', N'Trống', 'double', N'Ban công'),
	('P401', N'Trống', 'triple', N'Ban công'),

	-- Tổ hợp 2: Chỉ View biển
	('P102', N'Trống', 'single', N'View biển'),
	('P202', N'Trống', 'double', N'View biển'),
	('P302', N'Trống', 'triple', N'View biển'),
	('P403', N'Trống', 'double', N'View biển'),

	-- Tổ hợp 3: Chỉ Hút thuốc
	('P103', N'Trống', 'single', N'Phòng hút thuốc'),
	('P203', N'Trống', 'twin', N'Phòng hút thuốc'),
	('P303', N'Trống', 'double', N'Phòng hút thuốc'),
	('P402', N'Trống', 'triple', N'Phòng hút thuốc'),

	-- Tổ hợp 4: Ban công + View biển
	('P104', N'Trống', 'double', N'Ban công, View biển'),
	('P204', N'Trống', 'twin', N'Ban công, View biển'),
	('P304', N'Trống', 'triple', N'Ban công, View biển'),
	('P404', N'Trống', 'double', N'Ban công, View biển'),

	-- Tổ hợp 5: Ban công + Hút thuốc
	('P105', N'Trống', 'double', N'Ban công, Phòng hút thuốc'),
	('P205', N'Trống', 'single', N'Ban công, Phòng hút thuốc'),
	('P305', N'Trống', 'triple', N'Ban công, Phòng hút thuốc'),
	('P405', N'Trống', 'twin', N'Ban công, Phòng hút thuốc'),

	-- Tổ hợp 6: View biển + Hút thuốc
	('P106', N'Trống', 'double', N'View biển, Phòng hút thuốc'),
	('P206', N'Trống', 'twin', N'View biển, Phòng hút thuốc'),
	('P306', N'Trống', 'single', N'View biển, Phòng hút thuốc'),
	('P406', N'Trống', 'double', N'View biển, Phòng hút thuốc'),

	-- Tổ hợp 7: Ban công + View biển + Hút thuốc
	('P501', N'Trống', 'twin', N'Ban công, View biển, Phòng hút thuốc'),
	('P502', N'Trống', 'double', N'Ban công, View biển, Phòng hút thuốc'),
	('P503', N'Trống', 'triple', N'Ban công, View biển, Phòng hút thuốc'),
	('P504', N'Trống', 'twin', N'Ban công, View biển, Phòng hút thuốc'),
	('P601', N'Trống', 'twin', N'Ban công, View biển, Phòng hút thuốc'),
	('P602', N'Trống', 'double', N'Ban công, View biển, Phòng hút thuốc'),
	('P603', N'Trống', 'single', N'Ban công, View biển, Phòng hút thuốc');
go
INSERT INTO NhanVien (maNV, hoTen, ngaySinh, sdt, diaChi, soCCCD, chucVu, caLamViec)
VALUES 
('2025LT001', N'Nguyễn Thị Xuyến', '1990-01-01', '0981234567', N'Hà Nội', '123456789012', N'Lễ tân', N'Sáng'),
('2025KT002', N'Phạm Văn Yến', '1988-05-12', '0972345678', N'Đà Nẵng', '123456789013', N'Kế toán', N'Chiều');
go
INSERT INTO TaiKhoan (tenDangNhap, matKhau, trangThai, maNV)
VALUES ('nv001', '123456', N'Hoạt động', '2025LT001');
go

USE QuanLyKhachSan;
go
CREATE TABLE KhuyenMai (
    maKhuyenMai VARCHAR(20) PRIMARY KEY,  
    tenKhuyenMai NVARCHAR(50) NOT NULL,         
    loaiKhuyenMai NVARCHAR(50) NOT NULL,                
    giaTriKhuyenMai FLOAT,                        
    ngayBatDau DATETIME NOT NULL,    
    ngayKetThuc DATETIME NOT NULL,   
    dieuKienApDung FLOAT NOT NULL,
	trangThai NVARCHAR(20) NOT NULL
);
USE QuanLyKhachSan;
go
CREATE TABLE ChiTietApDung (
    maDonDatPhong VARCHAR(20) NOT NULL,   -- Mã đơn đặt phòng (khóa ngoại)
    maKhuyenMai VARCHAR(20) NOT NULL,     -- Mã khuyến mãi (khóa ngoại)
    tongThanhToanSauApDung Float NOT NULL,
    PRIMARY KEY (maDonDatPhong, maKhuyenMai),  -- Khóa chính kết hợp
    CONSTRAINT FK_ChiTietApDung_DonDatPhong FOREIGN KEY (maDonDatPhong) REFERENCES DonDatPhong(maDonDatPhong),
    CONSTRAINT FK_ChiTietApDung_KhuyenMai FOREIGN KEY (maKhuyenMai) REFERENCES KhuyenMai(maKhuyenMai)
);
GO


