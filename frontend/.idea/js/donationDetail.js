/**
 * js/donationDetail.js
 * Chứa logic cho trang donation.html: tải chi tiết, hiển thị người ủng hộ (có phân trang), và xử lý form quyên góp.
 */
document.addEventListener('DOMContentLoaded', () => {
    // --- Lấy các element cần thiết từ DOM ---
    const loader = document.getElementById('loader');
    const detailContainer = document.getElementById('donationDetail');
    const contributionForm = document.getElementById('contributionForm');

    // Elements cho danh sách người quyên góp
    const contributorsList = document.getElementById('contributors');
    const contributorsLoader = document.getElementById('contributorsLoader');
    const contributorsPagination = document.getElementById('contributorsPagination');
    const totalContributorsSpan = document.getElementById('totalContributors');

    // --- Lấy ID từ URL và kiểm tra ---
    const urlParams = new URLSearchParams(window.location.search);
    const donationId = urlParams.get('id');

    // --- Biến trạng thái phân trang ---
    let currentContributorsPage = 0;
    const CONTRIBUTORS_PAGE_SIZE = 5; // Hiển thị 5 người trên mỗi trang

    if (!donationId) {
        detailContainer.style.display = 'block';
        detailContainer.innerHTML = '<p style="color: red;">Thiếu ID của đợt quyên góp trên URL.</p>';
        loader.style.display = 'none';
        return;
    }

    // --- Hàm chính để tải tất cả dữ liệu cho trang ---
    async function loadPageData() {
        loader.style.display = 'block';
        detailContainer.style.display = 'none';
        try {
            // Tải song song cả hai API để tăng tốc độ
            await Promise.all([
                fetchDonationDetails(),
                fetchContributors(0) // Tải trang đầu tiên của danh sách
            ]);
        } catch (error) {
            detailContainer.innerHTML = `<p style="color: red;">Lỗi tải trang: ${error.message}</p>`;
        } finally {
            loader.style.display = 'none';
            detailContainer.style.display = 'block';
        }
    }

    // --- Hàm tải và hiển thị chi tiết donation ---
    async function fetchDonationDetails() {
        try {
            const response = await fetch(`${API_BASE_URL}/donations/${donationId}`);
            if (!response.ok) throw new Error('Không thể tải chi tiết chiến dịch.');

            const apiResponse = await response.json();
            if (apiResponse.status !== 'SUCCESS') throw new Error(apiResponse.message);

            const donation = apiResponse.data;
            document.title = donation.name; // Cập nhật tiêu đề trang
            document.getElementById('donationName').textContent = donation.name;

            const statusEl = document.getElementById('donationStatus');
            statusEl.textContent = donation.status;
            statusEl.className = `status ${donation.status.toLowerCase()}`;

            document.getElementById('organizationName').textContent = donation.organizationName || 'Chưa có';
            document.getElementById('donationDate').textContent = `${dateFormatter.format(new Date(donation.startDate))} - ${dateFormatter.format(new Date(donation.endDate))}`;
            document.getElementById('donationDescription').innerHTML = (donation.description || 'Chưa có mô tả.').replace(/\n/g, '<br>');

            const targetMoney = parseFloat(donation.moneyTarget || donation.money) || 0;
            const raisedAmount = parseFloat(donation.amountRaised) || 0;
            const progress = (targetMoney > 0) ? (raisedAmount / targetMoney) * 100 : 0;

            document.getElementById('detailProgress').style.width = `${progress.toFixed(2)}%`;
            document.getElementById('amountRaised').textContent = formatCurrencySafe(donation.amountRaised);
            document.getElementById('targetMoney').textContent = formatCurrencySafe(donation.moneyTarget || donation.money);

            if (donation.status !== 'ACTIVE') {
                contributionForm.innerHTML = `<p style="text-align: center; font-weight: bold; color: #e76f51;">Chiến dịch này hiện không nhận quyên góp.</p>`;
            }
        } catch (error) {
            // Hiển thị lỗi ở phần chính của trang nếu không tải được chi tiết
            const mainContent = document.querySelector('.detail-main-content');
            if(mainContent) mainContent.innerHTML = `<p style="color: red;">${error.message}</p>`;
        }
    }

    // --- Hàm tải và hiển thị danh sách người đã quyên góp ---
    async function fetchContributors(page = 0) {
        currentContributorsPage = page;
        contributorsLoader.style.display = 'block';
        contributorsList.innerHTML = '';
        contributorsPagination.innerHTML = '';

        try {
            const url = `${API_BASE_URL}/donations/${donationId}/contributors?page=${page}&size=${CONTRIBUTORS_PAGE_SIZE}&sort=createdAt,desc`;
            const response = await fetch(url);
            if (!response.ok) throw new Error("Không thể tải danh sách người ủng hộ.");

            const apiResponse = await response.json();
            if (apiResponse.status !== 'SUCCESS') throw new Error(apiResponse.message);

            const pageData = apiResponse.data;
            totalContributorsSpan.textContent = pageData.totalElements;

            if (pageData.content && pageData.content.length > 0) {
                pageData.content.forEach(c => {
                    const li = document.createElement('li');
                    li.innerHTML = `
                        <strong>${c.user.fullName}</strong> đã quyên góp <strong>${formatCurrencySafe(c.amount)}</strong>
                        <div style="font-size: 0.8em; color: #6c757d;">${new Date(c.contributionDate).toLocaleString('vi-VN')}</div>
                        ${c.message ? `<div class="message">"${c.message}"</div>` : ''}
                    `;
                    contributorsList.appendChild(li);
                });
                renderContributorsPagination(pageData);
            } else {
                contributorsList.innerHTML = '<li>Chưa có nhà hảo tâm nào. Hãy là người đầu tiên!</li>';
            }
        } catch (error) {
            contributorsList.innerHTML = `<li><p style="color: red;">${error.message}</p></li>`;
        } finally {
            contributorsLoader.style.display = 'none';
        }
    }

    // --- Hàm render các nút phân trang cho danh sách contributors ---
    function renderContributorsPagination(pageData) {
        contributorsPagination.innerHTML = ''; // Xóa nút cũ
        if (pageData.totalPages <= 1) return;

        const prevButton = document.createElement('button');
        prevButton.innerHTML = '« Trước';
        prevButton.disabled = pageData.first;
        prevButton.addEventListener('click', () => fetchContributors(currentContributorsPage - 1));
        contributorsPagination.appendChild(prevButton);

        const pageInfo = document.createElement('span');
        pageInfo.textContent = `Trang ${pageData.number + 1} / ${pageData.totalPages}`;
        contributorsPagination.appendChild(pageInfo);

        const nextButton = document.createElement('button');
        nextButton.innerHTML = 'Sau »';
        nextButton.disabled = pageData.last;
        nextButton.addEventListener('click', () => fetchContributors(currentContributorsPage + 1));
        contributorsPagination.appendChild(nextButton);
    }

    // --- Xử lý sự kiện submit form quyên góp ---
    contributionForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const form = e.target;
        const submitButton = form.querySelector('button[type="submit"]');
        submitButton.disabled = true;
        submitButton.textContent = 'Đang xử lý...';

        const requestBody = {
            userId: parseInt(form.userId.value),
            donationId: parseInt(donationId),
            amount: parseInt(form.amount.value),
            message: form.message.value
        };

        try {
            const response = await fetch(`${API_BASE_URL}/contributions`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(requestBody)
            });
            const apiResponse = await response.json();

            if (response.ok && apiResponse.status === 'SUCCESS') {
                showToast('Quyên góp thành công! Cảm ơn tấm lòng của bạn.');
                form.reset();
                // Tải lại cả chi tiết (để cập nhật amountRaised) và trang đầu của danh sách người ủng hộ
                await fetchDonationDetails();
                await fetchContributors(0);
            } else {
                const errorMsg = apiResponse.errors ? Object.values(apiResponse.errors).join(', ') : apiResponse.message;
                throw new Error(errorMsg);
            }
        } catch (error) {
            showToast(`Quyên góp thất bại: ${error.message}`, true);
        } finally {
            submitButton.disabled = false;
            submitButton.textContent = 'Quyên góp ngay';
        }
    });

    // --- Tải dữ liệu lần đầu khi trang được mở ---
    loadPageData();
});