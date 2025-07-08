/**
 * js/app.js
 * Chứa các hằng số và hàm tiện ích được sử dụng trên toàn bộ trang web.
 */

// URL gốc của backend. Thay đổi ở đây nếu cần.
const API_BASE_URL = 'http://localhost:8080/api/v1';

// Hàm định dạng tiền tệ Việt Nam.
const currencyFormatter = new Intl.NumberFormat('vi-VN', {
    style: 'currency',
    currency: 'VND'
});

// Hàm định dạng ngày tháng.
const dateFormatter = new Intl.DateTimeFormat('vi-VN', {
    day: '2-digit',
    month: '2-digit',
    year: 'numeric'
});

function formatCurrencySafe(value) {
    // Chuyển đổi giá trị sang kiểu number
    const numberValue = parseFloat(value);

    // Kiểm tra xem việc chuyển đổi có thành công không (tránh NaN)
    if (isNaN(numberValue)) {
        return currencyFormatter.format(0); // Trả về '0 ₫' nếu giá trị không hợp lệ
    }

    return currencyFormatter.format(numberValue);
}
/**
 * Hiển thị một thông báo nhỏ (toast) ở góc màn hình.
 * @param {string} message - Nội dung thông báo.
 * @param {boolean} isError - Nếu là true, thông báo sẽ có màu đỏ (lỗi).
 */
function showToast(message, isError = false) {
    let container = document.getElementById('toast-container');
    if (!container) {
        container = document.createElement('div');
        container.id = 'toast-container';
        document.body.appendChild(container);
    }

    const toast = document.createElement('div');
    toast.className = `toast ${isError ? 'error' : 'success'}`;
    toast.textContent = message;

    container.appendChild(toast);

    // Tự động xóa thông báo sau 3.5 giây
    setTimeout(() => {
        toast.style.animation = 'slideOut 0.3s forwards';
        toast.addEventListener('animationend', () => toast.remove());
    }, 3500);
}
function checkLoginStatus() {
    const navElement = document.querySelector('.main-header nav');
    if (!navElement) return; // Nếu trang không có thanh nav thì bỏ qua

    const loggedInUserString = sessionStorage.getItem('loggedInUser');

    if (loggedInUserString) {
        // --- TRƯỜNG HỢP: NGƯỜI DÙNG ĐÃ ĐĂNG NHẬP ---
        try {
            const user = JSON.parse(loggedInUserString);
            renderLoggedInNav(navElement, user);
        } catch (e) {
            // Lỗi parse JSON (dữ liệu hỏng), coi như chưa đăng nhập và xóa đi
            sessionStorage.removeItem('loggedInUser');
            renderLoggedOutNav(navElement);
        }
    } else {
        // --- TRƯỜNG HỢP: NGƯỜI DÙNG CHƯA ĐĂNG NHẬP ---
        renderLoggedOutNav(navElement);
    }
}

function renderLoggedInNav(navElement, user) {
    // Kiểm tra xem user có vai trò là ADMIN không
    const isAdmin = user.role && user.role.roleName === 'ROLE_ADMIN';

    // Xây dựng HTML cho thanh điều hướng
    let navHTML = `
        <span>Xin chào, <strong>${user.fullName}</strong>!</span>
    `;

    // *** LOGIC PHÂN QUYỀN NẰM Ở ĐÂY ***
    // Chỉ thêm link "Trang Quản Trị" nếu user là ADMIN
    if (isAdmin) {
        navHTML += `<a href="admin.html">Trang Quản Trị</a>`;
    }

    navHTML += `<a href="#" id="logoutBtn">Đăng xuất</a>`;

    navElement.innerHTML = navHTML;

    // Gắn sự kiện cho nút Đăng xuất
    const logoutBtn = document.getElementById('logoutBtn');
    if (logoutBtn) {
        logoutBtn.addEventListener('click', (e) => {
            e.preventDefault();
            sessionStorage.removeItem('loggedInUser');
            showToast('Đã đăng xuất thành công.');
            setTimeout(() => {
                window.location.href = 'login.html'; // Chuyển về trang đăng nhập
            }, 1500);
        });
    }
}

/**
 * Render thanh điều hướng cho người dùng CHƯA ĐĂNG NHẬP.
 * @param {HTMLElement} navElement - Thẻ <nav> để chèn HTML vào.
 */
function renderLoggedOutNav(navElement) {
    navElement.innerHTML = `
        <a href="register.html">Đăng ký</a>
        <a href="login.html" class="btn btn-primary">Đăng nhập</a>
    `;
}

// Chạy hàm kiểm tra ngay khi DOM được tải xong
document.addEventListener('DOMContentLoaded', checkLoginStatus);