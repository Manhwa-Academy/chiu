```md
# 🛍️ Ứng dụng Quản lý Bán mô hình

Ứng dụng **Quản lý bán mô hình** được xây dựng bằng **Java Swing + Maven**, giúp quản lý nghiệp vụ bán hàng mô hình gồm:
- Quản lý sản phẩm
- Quản lý kho
- Quản lý nhân viên & tài khoản
- Phân quyền người dùng
- Phiếu nhập / xuất
- Đăng nhập bằng tài khoản & Google
- Ảnh đại diện người dùng

---

## 🚀 Tính năng chính

### 🔑 Đăng nhập
- Đăng nhập bằng tài khoản hệ thống
- Đăng nhập bằng Google OAuth

### 📦 Quản lý
- Sản phẩm
- Kho hàng
- Khách hàng
- Nhà cung cấp
- Nhân viên
- Tài khoản & phân quyền
- Thống kê báo cáo

### 👤 Cá nhân
- Thay đổi thông tin cá nhân
- Đổi mật khẩu
- Cập nhật ảnh đại diện

---

## 📁 Cấu trúc thư mục

src/
├── main/
│   ├── java/                 # Code nguồn
│   │   ├── BUS/
│   │   ├── DAO/
│   │   ├── DTO/
│   │   ├── GUI/
│   │   └── helper/
│   └── resources/            # Tài nguyên
│       ├── img/
│       ├── icon/
│       └── database/
avatar/                        # Lưu ảnh đại diện người dùng lúc chạy

````

---

## 🧠 Công nghệ sử dụng

- Java SE 21
- Swing UI
- **Maven** (with Maven Wrapper)
- MySQL
- BCrypt password hashing
- FlatLaf UI theme
- Google OAuth 2.0 login

---

## 🛠️ Yêu cầu hệ thống

- Java JDK 21 hoặc mới hơn
- MySQL
- Không yêu cầu cài Apache Maven (dùng Maven Wrapper)

---

## 📌 Cách cài đặt & chạy

### 1. Clone source
```bash
git clone https://github.com/Manhwa-Academy/chiu.git
cd pt-ungdung-didong
````

---

### 2. Cấu hình MySQL

* Import dữ liệu từ file SQL trong:

```
src/main/resources/database
```

* Chỉnh sửa cấu hình kết nối trong:

```
src/main/java/config/JDBCUtil.java
```

---

### 3. Build & Run (không cần Maven cài sẵn)

#### Windows

```bash
mvnw.cmd clean compile
mvnw.cmd exec:java
```

#### Linux/macOS

```bash
./mvnw clean compile
./mvnw exec:java
```

Hoặc chạy trực tiếp class:

```
GUI.Log_In
```

---

## 🧩 Thông tin thêm

### 🔐 Bảo mật

* Mật khẩu được mã hóa bằng BCrypt
* Không lưu mật khẩu dạng plain text

### 🖼 Ảnh đại diện

* Ảnh sẽ lưu tại:

```
avatar/<manv>.png
```

---

## 📦 Triển khai

Bạn có thể đóng gói project thành JAR bằng:

```bash
mvnw clean package
```

---

## 📣 Góp ý & phát triển

Nếu bạn có ý tưởng cải tiến, giao diện mới, hoặc muốn tham gia phát triển thêm module:

* Tạo issue
* Gửi pull request

---

## ❤️ Tác giả

Manhwa-Academy

---


> Đây là project học tập do nhóm 4 thực hiện.
