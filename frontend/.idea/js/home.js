/**
 * js/home.js
 * Chứa logic cho trang index.html: tải, hiển thị, lọc, tìm kiếm,
 * và PHÂN TRANG các đợt quyên góp.
 */
document.addEventListener('DOMContentLoaded', () => {
    // Lấy các element cần thiết từ DOM
    const grid = document.getElementById('donationGrid');
    const loader = document.getElementById('loader');
    const searchInput = document.getElementById('searchInput');
    const statusFilter = document.getElementById('statusFilter');
    const paginationContainer = document.getElementById('paginationContainer');

    // Biến để lưu trạng thái hiện tại
    let currentPage = 0;
    const PAGE_SIZE = 5; // Hiển thị 5 thẻ trên mỗi trang (bố cục 3x3 đẹp)

    // Hàm gọi API để lấy danh sách donations (ĐÃ NÂNG CẤP)
    async function fetchDonations(page = 0) {
        currentPage = page; // Cập nhật trang hiện tại
        loader.style.display = 'block';
        grid.innerHTML = '';
        paginationContainer.innerHTML = ''; // Xóa các nút phân trang cũ

        // Lấy các giá trị từ bộ lọc
        const searchTerm = searchInput.value;
        const status = statusFilter.value;

        // Xây dựng URL động với đầy đủ tham số
        let url = `${API_BASE_URL}/donations?page=${page}&size=${PAGE_SIZE}&sort=status,asc&sort=endDate,desc`;
        if (searchTerm) url += `&searchTerm=${encodeURIComponent(searchTerm)}`;
        if (status) url += `&status=${status}`;

        try {
            const response = await fetch(url);
            if (!response.ok) throw new Error(`Lỗi mạng: ${response.statusText}`);

            const apiResponse = await response.json();
            if (apiResponse.status !== 'SUCCESS') throw new Error(apiResponse.message);

            const pageData = apiResponse.data;

            // Nếu có dữ liệu, gọi hàm render. Nếu không, hiển thị thông báo.
            if (pageData && pageData.content.length > 0) {
                renderDonations(pageData.content);
                renderPagination(pageData); // Render các nút phân trang
            } else {
                grid.innerHTML = '<p>Không tìm thấy chiến dịch nào phù hợp.</p>';
            }
        } catch (error) {
            grid.innerHTML = `<p style="color: red;">Không thể tải dữ liệu: ${error.message}</p>`;
        } finally {
            loader.style.display = 'none';
        }
    }

    // Hàm render danh sách donations ra các thẻ card (giữ nguyên)
    function renderDonations(donations) {
        // ... (code hàm renderDonations không thay đổi)
        donations.forEach(donation => {
            const card = document.createElement('a');
            card.className = 'donation-card';
            card.href = `donation.html?id=${donation.id}`;
            card.style.textDecoration = 'none';
            card.style.color = 'inherit';

            const targetMoney = parseFloat(donation.moneyTarget) || 0;
            const raisedAmount = parseFloat(donation.amountRaised) || 0;
            const progress = (targetMoney > 0) ? Math.min((raisedAmount / targetMoney) * 100, 100) : 0;
            const formattedEndDate = dateFormatter.format(new Date(donation.endDate))
            card.innerHTML = ` 
                    <div class="card-image">
                <!-- Đường dẫn đến ảnh chung của bạn -->
                <img src="images/image_donation_1.png" alt="Donation Image">
            </div>
                <div class="donation-card-content">
                    <h3>${donation.name}</h3>
                    <p><span class="status ${String(donation.status).toLowerCase()}">${donation.status}</span></p>
                    <div class="progress-bar">
                        <div class="progress" style="width: ${progress.toFixed(2)}%"></div>
                    </div>
                    <div class="money-info">
                        <span>${formatCurrencySafe(donation.amountRaised)}</span>
                        <span>${progress.toFixed(0)}%</span>
                    </div>
                    <div class="money-info">
                        <span>  Hạn chót: <strong>${formattedEndDate}</strong></span>
                        <span>Mục tiêu: ${formatCurrencySafe(donation.moneyTarget)}</span>
                    </div>
                </div>
            `;
            grid.appendChild(card);
        });
    }

    // --- HÀM MỚI: Render các nút phân trang ---
    function renderPagination(pageData) {
        // Nút Trang Trước
        const prevButton = document.createElement('button');
        prevButton.innerHTML = '« Trước';
        prevButton.disabled = pageData.first;
        prevButton.addEventListener('click', () => fetchDonations(currentPage - 1));
        paginationContainer.appendChild(prevButton);

        // Hiển thị thông tin trang
        const pageInfo = document.createElement('span');
        pageInfo.textContent = `Trang ${pageData.number + 1} / ${pageData.totalPages}`;
        paginationContainer.appendChild(pageInfo);

        // Nút Trang Sau
        const nextButton = document.createElement('button');
        nextButton.innerHTML = 'Sau »';
        nextButton.disabled = pageData.last;
        nextButton.addEventListener('click', () => fetchDonations(currentPage + 1));
        paginationContainer.appendChild(nextButton);
    }

    // Gắn sự kiện để tự động tải lại danh sách khi người dùng thay đổi bộ lọc
    let debounceTimer;
    function handleFilterChange() {
        clearTimeout(debounceTimer);
        // Khi người dùng lọc, luôn quay về trang đầu tiên
        debounceTimer = setTimeout(() => fetchDonations(0), 500);
    }

    searchInput.addEventListener('input', handleFilterChange);
    statusFilter.addEventListener('change', handleFilterChange);

    // Tải dữ liệu cho trang đầu tiên khi trang được mở
    fetchDonations(0);
});