<div align="center">
  <img src="https://raw.githubusercontent.com/soul703/DonationAppBase/main/screenshots/logo.png" alt="DonationApp Logo" width="150"/>
  <h1>💖 DonationApp - Nền tảng Quyên góp Từ thiện</h1>
  <p>
    Một ứng dụng web full-stack được xây dựng bằng Spring Boot và Vanilla JS, giúp tạo và quản lý các chiến dịch quyên góp một cách minh bạch, hiệu quả và dễ dàng.
  </p>
</div>

<!-- Badges -->
<div align="center">
  <a href="https://github.com/soul703/DonationAppBase/actions/workflows/maven.yml">
    <img src="https://github.com/soul703/DonationAppBase/actions/workflows/maven.yml/badge.svg" alt="Build Status">
  </a>
  <img src="https://img.shields.io/badge/Java-17-orange" alt="Java 17">
  <img src="https://img.shields.io/badge/Spring_Boot-3.x-brightgreen" alt="Spring Boot 3.x">
  <img src="https://img.shields.io/badge/Frontend-Vanilla_JS-yellow" alt="Vanilla JS">
  <img src="https://img.shields.io/github/license/soul703/DonationAppBase" alt="License">
  <img src="https://img.shields.io/github/stars/soul703/DonationAppBase?style=social" alt="GitHub stars">
</div>

---

## ✨ Giới thiệu

**DonationApp** là một dự án mã nguồn mở, minh họa cách xây dựng một ứng dụng web full-stack hiện đại từ đầu đến cuối. Dự án tập trung vào việc áp dụng các "best practices" trong cả thiết kế backend và phát triển frontend, bao gồm:
- **Backend:** Kiến trúc 3 tầng rõ ràng (Controller-Service-Repository), thiết kế API RESTful chuẩn, xử lý lỗi tập trung, và tối ưu hóa truy vấn database.
- **Frontend:** Tách biệt logic với HTML/CSS/JS, tương tác API bất đồng bộ, và xây dựng giao diện người dùng trực quan, đáp ứng.

## 🚀 Các Tính Năng Chính

- 💖 **Trang chủ động:** Hiển thị các chiến dịch với thanh tiến độ, cho phép lọc, tìm kiếm và phân trang.
- 📰 **Trang chi tiết chiến dịch:** Cung cấp thông tin đầy đủ, danh sách nhà hảo tâm và form quyên góp trực tiếp.
- ⚙️ **Trang quản trị mạnh mẽ:**
    - Quản lý CRUD (Thêm, Xem, Sửa, Xóa) cho cả **Người dùng (Users)** và **Đợt Quyên góp (Donations)**.
    - Hỗ trợ phân trang và tìm kiếm cho tất cả các bảng dữ liệu.
    - Giao diện modal mượt mà để chỉnh sửa thông tin.
    - Chức năng xem nhanh chi tiết khi click vào từng hàng.
- 💸 **Nghiệp vụ quyên góp hoàn chỉnh:** Xử lý logic cộng dồn số tiền và ghi lại lịch sử quyên góp một cách an toàn qua các giao dịch.

## 📸 Giao diện ứng dụng

<div align="center">
  <p><em>Trang chủ hiển thị các chiến dịch một cách trực quan</em></p>
  <img src="https://raw.githubusercontent.com/soul703/DonationAppBase/main/screenshots/homepage.png" alt="Trang chủ" width="85%">
  <br><br>
  <p><em>Trang quản trị với đầy đủ chức năng CRUD và phân trang</em></p>
  <img src="https://raw.githubusercontent.com/soul703/DonationAppBase/main/screenshots/adminpage.png" alt="Trang quản trị" width="85%">
</div>

## 🛠️ Công nghệ sử dụng

| Phần         | Công nghệ                               | Mục đích                                           |
| :---------- | :-------------------------------------- | :------------------------------------------------- |
| **Backend** | **Java 17 & Spring Boot 3.x**           | Xây dựng API RESTful mạnh mẽ.                       |
|             | **Spring Data JPA & Hibernate**         | Tương tác với CSDL, ORM.                            |
|             | **MySQL**                               | CSDL quan hệ để lưu trữ dữ liệu.                      |
|             | **Maven**                               | Quản lý dependency và build dự án.                 |
| **Frontend**| **HTML5 & CSS3 (Vanilla)**              | Xây dựng cấu trúc và giao diện trang web.             |
|             | **JavaScript (ES6+)**                   | Xử lý logic, tương tác API, thao tác DOM.             |
| **DevOps**  | **GitHub Actions**                      | Tự động build và kiểm tra code (CI).                 |


## 🏁 Bắt đầu

Làm theo các bước sau để chạy dự án trên máy của bạn.

### Yêu cầu
- JDK 17 hoặc cao hơn.
- Maven 3.6+.
- MySQL Server 8.0+.
- Một IDE cho Java (khuyến khích IntelliJ IDEA).
- VS Code với extension **Live Server** cho frontend.

### Cài đặt Backend
1.  **Clone repository:**
    ```bash
    git clone https://github.com/soul703/DonationAppBase.git
    cd DonationAppBase/backend # Đi vào thư mục backend
    ```
2.  **Tạo cơ sở dữ liệu:**
    - Mở MySQL và tạo một database mới:
      ```sql
      CREATE DATABASE donation_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
      ```
3.  **Cấu hình kết nối:**
    - Mở file `src/main/resources/application.properties`.
    - Cập nhật các thông tin `spring.datasource.url`, `username`, và `password`.
4.  **Build và Chạy:**
    - Mở dự án trong IntelliJ, đợi Maven tải các dependencies.
    - Chạy lớp `Assignment01Application.java`.
    - Lần đầu tiên chạy, `ddl-auto` sẽ được đặt là `create`, tự động tạo bảng và chèn dữ liệu mẫu. Sau đó, nó sẽ tự chuyển về `update`.
    - Backend sẽ khởi động trên `http://localhost:8080`.

### Chạy Frontend
1.  **Mở thư mục `frontend`** trong một cửa sổ VS Code mới.
2.  Cài đặt extension **Live Server** nếu bạn chưa có.
3.  Nhấn chuột phải vào file `index.html` và chọn **"Open with Live Server"**.
4.  Trình duyệt sẽ tự động mở trang web tại `http://127.0.0.1:5500`.

## 🗺️ Cấu trúc API

Toàn bộ các endpoint được định nghĩa với tiền tố `/api/v1`. Xem chi tiết trong các lớp Controller:
- [`UserController.java`](https://github.com/soul703/DonationAppBase/blob/main/backend/src/main/java/org/example/assignment01/controller/UserController.java)
- [`DonationController.java`](https://github.com/soul703/DonationAppBase/blob/main/backend/src/main/java/org/example/assignment01/controller/DonationController.java)
- [`UserDonationController.java`](https://github.com/soul703/DonationAppBase/blob/main/backend/src/main/java/org/example/assignment01/controller/UserDonationController.java)


## 🛣️ Lộ trình Phát triển (Roadmap)
Dự án vẫn đang được phát triển với các tính năng dự kiến trong tương lai:

- [ ] **Tích hợp Spring Security & JWT:** Bảo mật API, phân quyền theo vai trò.
- [ ] **Chức năng Đăng nhập/Đăng ký:** Hoàn thiện luồng xác thực người dùng.
- [ ] **Trang cá nhân người dùng:** Cho phép người dùng tự cập nhật thông tin.
- [ ] **Upload hình ảnh:** Cho phép admin upload ảnh đại diện cho các chiến dịch.
- [ ] **Dashboard Thống kê:** Xây dựng các biểu đồ trực quan hóa dữ liệu.

## 🤝 Đóng góp
Mọi đóng góp đều được chào đón! Vui lòng fork repository và tạo một Pull Request.

1.  Fork the Project.
2.  Create your Feature Branch (`git checkout -b feature/NewFeature`).
3.  Commit your Changes (`git commit -m 'Add some NewFeature'`).
4.  Push to the Branch (`git push origin feature/NewFeature`).
5.  Open a Pull Request.

## 📜 Giấy phép
Dự án này được cấp phép dưới Giấy phép MIT - xem file [LICENSE](https://github.com/soul703/DonationAppBase/blob/main/LICENSE) để biết thêm chi tiết.
