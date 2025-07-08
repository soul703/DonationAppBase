
<div align="center">
  <img src="https://i.imgur.com/your-logo-image.png" alt="DonationApp Logo" width="150"/>
  <h1>💖 DonationApp - Nền tảng Quyên góp Từ thiện</h1>
  <p>
    Một ứng dụng web Spring Boot & Vanilla JS hoàn chỉnh giúp quản lý và thực hiện các chiến dịch quyên góp từ thiện một cách minh bạch và hiệu quả.
  </p>

  <!-- Badges -->
  <p>
    <img src="https://img.shields.io/badge/Java-17-orange.svg" alt="Java 17">
    <img src="https://img.shields.io/badge/Spring_Boot-3.x-brightgreen.svg" alt="Spring Boot 3.x">
    <img src="https://img.shields.io/badge/JPA_/_Hibernate-blue.svg" alt="JPA/Hibernate">
    <img src="https://img.shields.io/badge/Maven-red.svg" alt="Maven">
    <img src="https://img.shields.io/badge/JavaScript-ES6+-yellow.svg" alt="JavaScript ES6+">
    <img src="https://img.shields.io/badge/License-MIT-blue.svg" alt="License: MIT">
  </p>
</div>

---

## ✨ Giới thiệu

**DonationApp** là một dự án mã nguồn mở được xây dựng để giải quyết bài toán kêu gọi và quản lý các quỹ từ thiện. Với giao diện trực quan và backend mạnh mẽ, dự án cho phép các tổ chức tạo ra các chiến dịch, và các nhà hảo tâm có thể dễ dàng theo dõi, ủng hộ một cách minh bạch.

Dự án này là một ví dụ điển hình về việc xây dựng một ứng dụng web full-stack hiện đại, tuân thủ các "best practices" về thiết kế API RESTful, kiến trúc phần mềm, và trải nghiệm người dùng.

## 🚀 Các Tính Năng Chính

### Dành cho Người dùng & Nhà hảo tâm
- 💖 **Xem danh sách các chiến dịch:** Giao diện trang chủ hiển thị các đợt quyên góp với thanh tiến độ trực quan.
- 🔍 **Tìm kiếm & Lọc:** Dễ dàng tìm kiếm chiến dịch theo tên, mã hoặc lọc theo trạng thái (Đang hoạt động, Sắp diễn ra, Đã kết thúc).
- 📰 **Xem chi tiết chiến dịch:** Cung cấp thông tin đầy đủ, minh bạch về mục tiêu, mô tả và danh sách các nhà hảo tâm đã ủng hộ.
- 💸 **Thực hiện quyên góp:** Giao diện đơn giản để thực hiện quyên góp nhanh chóng.
- 👤 **Quản lý tài khoản:** Xem lịch sử quyên góp và các thông tin thống kê cá nhân.

### Dành cho Quản trị viên
- ⚙️ **Bảng điều khiển tập trung:** Giao diện quản trị riêng biệt để quản lý toàn bộ hệ thống.
- 👥 **Quản lý người dùng:** Thực hiện các thao tác CRUD (Thêm, Xem, Sửa, Xóa), phân trang và tìm kiếm người dùng.
-  fundraisers **Quản lý chiến dịch:** Tạo mới, cập nhật thông tin, thay đổi trạng thái và xóa các đợt quyên góp.
- 📊 **Xem chi tiết:** Click vào từng hàng để xem nhanh thông tin chi tiết của người dùng hoặc chiến dịch.

## 📸 Giao diện ứng dụng

<div align="center">
  <p><strong>Trang chủ hiển thị các chiến dịch</strong></p>
  <img src="https://i.imgur.com/your-homepage-screenshot.png" alt="Trang chủ" width="80%">
  <p><strong>Trang quản trị mạnh mẽ và trực quan</strong></p>
  <img src="https://i.imgur.com/your-adminpage-screenshot.png" alt="Trang quản trị" width="80%">
</div>

## 🛠️ Công nghệ sử dụng

| Phần | Công nghệ | Mục đích |
| :--- | :--- | :--- |
| **Backend** | **Java 17 & Spring Boot 3.x** | Xây dựng API RESTful mạnh mẽ và bảo mật. |
| | **Spring Data JPA & Hibernate** | Tương tác với cơ sở dữ liệu, ánh xạ ORM. |
| | **MySQL** | Cơ sở dữ liệu quan hệ để lưu trữ dữ liệu. |
| | **Maven** | Quản lý dependency và build dự án. |
| | **Lombok** | Giảm thiểu code boilerplate trong các Entity, DTO. |
| **Frontend** | **HTML5 & CSS3** | Xây dựng cấu trúc và giao diện trang web. |
| | **JavaScript (ES6+)** | Xử lý logic, tương tác với API, thao tác DOM. |
| | **Fetch API** | Giao tiếp bất đồng bộ với backend. |

## 🏁 Bắt đầu

Làm theo các bước sau để chạy dự án trên máy của bạn.

### Yêu cầu
- JDK 17 hoặc cao hơn
- Maven 3.6+
- MySQL Server 8.0+
- Một IDE cho Java (ví dụ: IntelliJ IDEA, Eclipse)
- Một editor cho Frontend (ví dụ: VS Code) với extension **Live Server**.

### Cài đặt Backend
1.  **Clone repository:**
    ```bash
    git clone https://github.com/your-username/donation-app.git
    cd donation-app
    ```
2.  **Tạo cơ sở dữ liệu:**
    *   Mở MySQL và tạo một database mới, ví dụ: `donation_db`.
    ```sql
    CREATE DATABASE donation_db;
    ```
3.  **Cấu hình kết nối:**
    *   Mở file `src/main/resources/application.properties`.
    *   Cập nhật các thông tin `spring.datasource.url`, `username`, và `password` cho khớp với môi trường MySQL của bạn.
4.  **Build và chạy dự án:**
    *   Sử dụng IDE để chạy lớp `DonationAppApplication.java`.
    *   Hoặc dùng Maven:
    ```bash
    mvn spring-boot:run
    ```
    Backend sẽ khởi động trên `http://localhost:8080`. Lần đầu tiên chạy, dữ liệu mẫu sẽ được tự động chèn vào database.

### Chạy Frontend
1.  **Mở thư mục `frontend`** trong VS Code (hoặc editor bạn chọn).
2.  Cài đặt extension **Live Server** nếu bạn chưa có.
3.  Nhấn chuột phải vào file `index.html` và chọn **"Open with Live Server"**.
4.  Trình duyệt sẽ tự động mở và trang web đã sẵn sàng để sử dụng!

## 🗺️ Cấu trúc API

Dưới đây là một vài endpoint chính của dự án:

| Phương thức | Endpoint | Mô tả |
| :--- | :--- | :--- |
| `GET` | `/api/v1/donations` | Lấy danh sách các đợt quyên góp (hỗ trợ phân trang, lọc, tìm kiếm). |
| `GET` | `/api/v1/donations/{id}` | Lấy chi tiết một đợt quyên góp. |
| `POST`| `/api/v1/donations` | (Admin) Tạo một đợt quyên góp mới. |
| `GET` | `/api/v1/users` | (Admin) Lấy danh sách người dùng (hỗ trợ phân trang, tìm kiếm). |
| `POST`| `/api/v1/contributions`| Người dùng thực hiện một lần quyên góp. |
| `GET` | `/api/v1/donations/{id}/contributors` | Lấy danh sách người đã quyên góp cho một đợt. |

## 🤝 Đóng góp

Mọi đóng góp đều được chào đón! Nếu bạn có ý tưởng để cải thiện dự án, vui lòng fork repository và tạo một Pull Request. Bạn cũng có thể mở một Issue với tag "enhancement".

1.  Fork the Project
2.  Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3.  Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4.  Push to the Branch (`git push origin feature/AmazingFeature`)
5.  Open a Pull Request

## 📜 Giấy phép

Dự án này được cấp phép dưới Giấy phép MIT. Xem file `LICENSE` để biết thêm chi tiết.

## 📧 Liên hệ

[Tên của bạn] - [@YourTwitterHandle] - [your.email@example.com]

Link dự án: [https://github.com/your-username/donation-app](https://github.com/your-username/donation-app)

---

### Hướng Dẫn Tùy Chỉnh

*   **Logo và Ảnh chụp màn hình:** Hãy thay thế các đường dẫn `https://i.imgur.com/...` bằng ảnh thực tế của dự án bạn. Bạn có thể upload ảnh lên Imgur, Postimage hoặc lưu trực tiếp trong repository.
*   **Thông tin cá nhân:** Thay thế `your-username`, `@YourTwitterHandle`, và email bằng thông tin của bạn.
*   **Badges:** Các huy hiệu ở đầu file được tạo từ [Shields.io](https://shields.io/). Bạn có thể tùy chỉnh hoặc thêm các huy hiệu khác để thể hiện công nghệ bạn dùng.
