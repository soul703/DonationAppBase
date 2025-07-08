/**
 * js/admin.js
 * Chứa toàn bộ logic cho trang admin.html:
 * - Quản lý CRUD cho Đợt Quyên Góp (Donations) và Người Dùng (Users).
 * - Hỗ trợ phân trang và tìm kiếm cho cả hai bảng.
 * - Sử dụng modal cho form thêm/sửa và modal để xem chi tiết.
 */
document.addEventListener('DOMContentLoaded', () => {

    // ===================================================================
    //                  I. KHAI BÁO BIẾN VÀ LẤY ELEMENT
    // ===================================================================

    // --- Biến chung và trạng thái phân trang ---
    let currentDonationPage = 0;
    let currentUserPage = 0;
    const PAGE_SIZE = 5; // Hiển thị 5 mục trên mỗi trang theo yêu cầu

    let currentDonationEditingId = null;
    let currentUserEditingId = null;

    // --- Elements cho Donation ---
    const donationsTableBody = document.getElementById('donationsTableBody');
    const donationPagination = document.getElementById('donationPagination');
    const donationModal = document.getElementById('donationModal');
    const donationModalTitle = document.getElementById('modalTitle');
    const donationForm = document.getElementById('donationForm');
    const addDonationBtn = document.getElementById('addDonationBtn');
    const donationCloseButton = donationModal.querySelector('.close-button');

    // --- Elements cho User ---
    const usersTableBody = document.getElementById('usersTableBody');
    const userPagination = document.getElementById('userPagination');
    const userModal = document.getElementById('userModal');
    const userModalTitle = document.getElementById('userModalTitle');
    const userForm = document.getElementById('userForm');
    const addUserBtn = document.getElementById('addUserBtn');
    const userModalCloseBtn = userModal.querySelector('.close-button');
    const roleSelect = document.getElementById('roleId');
    const userEmailSearch = document.getElementById('userEmailSearch');
    const userPhoneSearch = document.getElementById('userPhoneSearch');

    // --- Elements cho Modal Xem Chi Tiết ---
    const viewDetailModal = document.getElementById('viewDetailModal');
    const viewModalTitle = document.getElementById('viewModalTitle');
    const viewModalBody = document.getElementById('viewModalBody');
    const viewModalCloseButton = viewDetailModal.querySelector('.close-button');


    // ===================================================================
    //                  II. LOGIC QUẢN LÝ DONATIONS
    // ===================================================================

    async function loadDonations(page = 0) {
        currentDonationPage = page;
        donationsTableBody.innerHTML = '<tr><td colspan="5" class="loader">Đang tải...</td></tr>';
        donationPagination.innerHTML = '';

        try {
            const url = `${API_BASE_URL}/donations?page=${page}&size=${PAGE_SIZE}&sort=id,desc`;
            const response = await fetch(url);
            const apiResponse = await response.json();
            if (apiResponse.status !== 'SUCCESS') throw new Error(apiResponse.message);

            const pageData = apiResponse.data;
            donationsTableBody.innerHTML = '';

            if (pageData.content && pageData.content.length > 0) {
                pageData.content.forEach(d => {
                    const row = document.createElement('tr');
                    row.className = 'clickable-row';
                    row.dataset.id = d.id;
                    row.dataset.type = 'donation';
                    row.innerHTML = `
                        <td>${d.id}</td>
                        <td>${d.name}</td>
                        <td>${d.code}</td>
                        <td><span class="status ${d.status.toLowerCase()}">${d.status}</span></td>
                        <td class="actions">
                            <button class="btn btn-warning btn-edit-donation" data-id="${d.id}">Sửa</button>
                            <button class="btn btn-danger btn-delete-donation" data-id="${d.id}">Xóa</button>
                        </td>
                    `;
                    donationsTableBody.appendChild(row);
                });
                renderPagination(donationPagination, pageData, loadDonations);
            } else {
                donationsTableBody.innerHTML = '<tr><td colspan="5" style="text-align: center;">Không có dữ liệu.</td></tr>';
            }
        } catch (error) {
            showToast(`Lỗi tải danh sách Donations: ${error.message}`, true);
        }
    }

    function openDonationModalForCreate() {
        currentDonationEditingId = null;
        donationModalTitle.textContent = 'Thêm Đợt Quyên Góp Mới';
        donationForm.reset();
        donationModal.querySelector('#statusGroup').style.display = 'none';
        donationForm.code.disabled = false;
        donationModal.style.display = 'block';
    }

    async function openDonationModalForEdit(id) {
        currentDonationEditingId = id;
        donationModalTitle.textContent = `Chỉnh Sửa Đợt Quyên Góp #${id}`;
        donationForm.reset();

        try {
            const response = await fetch(`${API_BASE_URL}/donations/${id}`);
            const apiResponse = await response.json();
            if (apiResponse.status !== 'SUCCESS') throw new Error(apiResponse.message);

            const d = apiResponse.data;
            donationForm.name.value = d.name;
            donationForm.code.value = d.code;
            donationForm.code.disabled = true;
            donationForm.description.value = d.description || '';
            donationForm.money.value = d.money;
            donationForm.startDate.value = d.startDate;
            donationForm.endDate.value = d.endDate;
            if(donationForm.organizationName) donationForm.organizationName.value = d.organizationName || '';
            if(donationForm.phoneNumber) donationForm.phoneNumber.value = d.phoneNumber || '';

            donationModal.querySelector('#statusGroup').style.display = 'block';
            donationForm.status.value = d.status;

            donationModal.style.display = 'block';
        } catch (error) {
            showToast(`Lỗi tải chi tiết Donation: ${error.message}`, true);
        }
    }

    donationForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const isEditing = currentDonationEditingId !== null;
        const url = isEditing ? `${API_BASE_URL}/donations/${currentDonationEditingId}` : `${API_BASE_URL}/donations`;
        const method = isEditing ? 'PUT' : 'POST';

        const formData = {
            name: donationForm.name.value,
            description: donationForm.description.value,
            money: parseInt(donationForm.money.value),
            startDate: donationForm.startDate.value,
            endDate: donationForm.endDate.value,
            organizationName: donationForm.organizationName ? donationForm.organizationName.value : undefined,
            phoneNumber: donationForm.phoneNumber ? donationForm.phoneNumber.value : undefined
        };

        if (!isEditing) {
            formData.code = donationForm.code.value;
        } else {
            formData.status = donationForm.status.value;
        }

        try {
            const response = await fetch(url, { method, headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(formData) });
            const apiResponse = await response.json();
            if (response.ok && apiResponse.status === 'SUCCESS') {
                showToast(`Đã ${isEditing ? 'cập nhật' : 'tạo mới'} Donation thành công!`);
                donationModal.style.display = 'none';
                loadDonations(isEditing ? currentDonationPage : 0);
            } else {
                throw new Error(apiResponse.errors ? Object.values(apiResponse.errors).join(', ') : apiResponse.message);
            }
        } catch (error) {
            showToast(`Lỗi: ${error.message}`, true);
        }
    });

    async function deleteDonation(id) {
        if (!confirm(`Bạn có chắc muốn xóa đợt quyên góp ID ${id}?`)) return;
        try {
            const response = await fetch(`${API_BASE_URL}/donations/${id}`, { method: 'DELETE' });
            const apiResponse = await response.json();
            if (response.ok && apiResponse.status === 'SUCCESS') {
                showToast('Đã xóa Donation thành công!');
                loadDonations(0);
            } else {
                throw new Error(apiResponse.message);
            }
        } catch (error) {
            showToast(`Lỗi khi xóa Donation: ${error.message}`, true);
        }
    }

    // ===================================================================
    //                  III. LOGIC QUẢN LÝ USERS
    // ===================================================================

    async function loadUsers(page = 0) {
        currentUserPage = page;
        usersTableBody.innerHTML = '<tr><td colspan="9" class="loader">Đang tải...</td></tr>';
        userPagination.innerHTML = '';

        const email = userEmailSearch.value;
        const phone = userPhoneSearch.value;

        try {
            let url = `${API_BASE_URL}/users?page=${page}&size=${PAGE_SIZE}&sort=id,desc`;
            if (email) url += `&email=${encodeURIComponent(email)}`;
            if (phone) url += `&phoneNumber=${encodeURIComponent(phone)}`;

            const response = await fetch(url);
            const apiResponse = await response.json();
            if (apiResponse.status !== 'SUCCESS') throw new Error(apiResponse.message);

            const pageData = apiResponse.data;
            usersTableBody.innerHTML = '';

            if (pageData.content && pageData.content.length > 0) {
                pageData.content.forEach(user => {
                    const row = document.createElement('tr');
                    row.className = 'clickable-row';
                    row.dataset.id = user.id;
                    row.dataset.type = 'user';
                    row.innerHTML = `
                        <td>${user.id}</td>
                        <td>${user.userName}</td>
                        <td>${user.fullName}</td>
                        <td>${user.email}</td>
                        <td>${user.phoneNumber || 'N/A'}</td>
                        <td>${user.address || 'N/A'}</td>
                        <td>${user.role ? user.role.roleName.replace('ROLE_', '') : 'N/A'}</td>
                        <td><span class="status ${user.status.toLowerCase()}">${user.status}</span></td>
                        <td class="actions">
                            <button class="btn btn-warning btn-edit-user" data-id="${user.id}">Sửa</button>
                            <button class="btn btn-danger btn-delete-user" data-id="${user.id}">Xóa</button>
                        </td>
                    `;
                    usersTableBody.appendChild(row);
                });
                renderPagination(userPagination, pageData, loadUsers);
            } else {
                usersTableBody.innerHTML = '<tr><td colspan="9" style="text-align: center;">Không có người dùng nào.</td></tr>';
            }
        } catch (error) {
            showToast(`Lỗi tải danh sách User: ${error.message}`, true);
            usersTableBody.innerHTML = `<tr><td colspan="9" style="text-align: center; color: red;">Lỗi tải dữ liệu.</td></tr>`;
        }
    }

    async function loadRolesForSelect() {
        try {
            const response = await fetch(`${API_BASE_URL}/roles`);
            const apiResponse = await response.json();
            if (apiResponse.status !== 'SUCCESS') throw new Error(apiResponse.message);

            roleSelect.innerHTML = '';
            apiResponse.data.forEach(role => {
                const option = document.createElement('option');
                option.value = role.id;
                option.textContent = role.roleName.replace('ROLE_', '');
                roleSelect.appendChild(option);
            });
        } catch (error) {

            roleSelect.innerHTML = '<option value="1">ADMIN</option><option value="2">USER</option>';
        }
    }

    function openUserModalForCreate() {
        currentUserEditingId = null;
        userModalTitle.textContent = 'Thêm Người Dùng Mới';
        userForm.reset();
        userModal.querySelector('#userStatusGroup').style.display = 'none';
        userModal.querySelector('#passwordGroup').style.display = 'block';
        userForm.password.required = true;
        userForm.userName.disabled = false;
        userModal.style.display = 'block';
    }

    async function openUserModalForEdit(id) {
        currentUserEditingId = id;
        userModalTitle.textContent = `Chỉnh Sửa Người Dùng #${id}`;
        userForm.reset();

        try {
            const response = await fetch(`${API_BASE_URL}/users/${id}`);
            const apiResponse = await response.json();
            if (apiResponse.status !== 'SUCCESS') throw new Error(apiResponse.message);

            const user = apiResponse.data;
            userForm.userName.value = user.userName;
            userForm.userName.disabled = true;
            userForm.fullName.value = user.fullName;
            userForm.email.value = user.email;
            userForm.phoneNumber.value = user.phoneNumber || '';
            userForm.address.value = user.address || '';
            userForm.roleId.value = user.role.id;
            userForm.password.placeholder = "Để trống nếu không muốn đổi";
            userForm.password.required = false;

            userModal.querySelector('#userStatusGroup').style.display = 'block';
            userForm.userStatus.value = user.status;

            userModal.style.display = 'block';
        } catch (error) {
            showToast(`Lỗi tải chi tiết User: ${error.message}`, true);
        }
    }

    userForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const isEditing = currentUserEditingId !== null;
        const url = isEditing ? `${API_BASE_URL}/users/${currentUserEditingId}` : `${API_BASE_URL}/users`;
        const method = isEditing ? 'PUT' : 'POST';

        const password = userForm.password.value;
        let requestBody = {
            fullName: userForm.fullName.value,
            email: userForm.email.value,
            phoneNumber: userForm.phoneNumber.value,
            address: userForm.address.value,
        };

        if (isEditing) {
            requestBody.status = userForm.userStatus.value;
            if (password) requestBody.password = password;
        } else {
            requestBody.userName = userForm.userName.value;
            requestBody.password = password;
            requestBody.roleId = parseInt(userForm.roleId.value);
        }

        try {
            const response = await fetch(url, { method, headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(requestBody) });
            const apiResponse = await response.json();

            if (response.ok && apiResponse.status === 'SUCCESS') {
                showToast(`Đã ${isEditing ? 'cập nhật' : 'tạo mới'} User thành công!`);
                userModal.style.display = 'none';
                loadUsers(isEditing ? currentUserPage : 0);
            } else {
                throw new Error(apiResponse.errors ? Object.values(apiResponse.errors).join(', ') : apiResponse.message);
            }
        } catch (error) {
            showToast(`Lỗi: ${error.message}`, true);
        }
    });

    async function deleteUser(id) {
        if (!confirm(`Bạn có chắc muốn xóa người dùng ID ${id}?`)) return;
        try {
            const response = await fetch(`${API_BASE_URL}/users/${id}`, { method: 'DELETE' });
            const apiResponse = await response.json();
            if (response.ok && apiResponse.status === 'SUCCESS') {
                showToast('Đã xóa User thành công!');
                loadUsers(0);
            } else {
                throw new Error(apiResponse.message);
            }
        } catch (error) {
            showToast(`Lỗi khi xóa User: ${error.message}`, true);
        }
    }


    // ===================================================================
    //                  IV. LOGIC XEM CHI TIẾT (MỚI)
    // ===================================================================

    async function showDonationDetails(id) {
        viewModalTitle.textContent = `Chi tiết Đợt Quyên Góp #${id}`;
        viewModalBody.innerHTML = `<div class="loader">Đang tải...</div>`;
        viewDetailModal.style.display = 'block';

        try {
            const response = await fetch(`${API_BASE_URL}/donations/${id}`);
            const apiResponse = await response.json();
            if (apiResponse.status !== 'SUCCESS') throw new Error(apiResponse.message);
            const d = apiResponse.data;

            viewModalBody.innerHTML = `
                <table class="detail-view-table">
                    <tr><td>ID</td><td>${d.id}</td></tr>
                    <tr><td>Tên chiến dịch</td><td>${d.name}</td></tr>
                    <tr><td>Mã</td><td>${d.code}</td></tr>
                    <tr><td>Trạng thái</td><td><span class="status ${d.status.toLowerCase()}">${d.status}</span></td></tr>
                    <tr><td>Mô tả</td><td>${d.description || 'N/A'}</td></tr>
                    <tr><td>Ngày bắt đầu</td><td>${dateFormatter.format(new Date(d.startDate))}</td></tr>
                    <tr><td>Ngày kết thúc</td><td>${dateFormatter.format(new Date(d.endDate))}</td></tr>
                    <tr><td>Mục tiêu</td><td>${formatCurrencySafe(d.moneyTarget || d.money)}</td></tr>
                    <tr><td>Đã quyên góp</td><td>${formatCurrencySafe(d.amountRaised)}</td></tr>
                    <tr><td>Tổ chức</td><td>${d.organizationName || 'N/A'}</td></tr>
                    <tr><td>SĐT Liên hệ</td><td>${d.phoneNumber || 'N/A'}</td></tr>
                </table>
            `;
        } catch (error) {
            viewModalBody.innerHTML = `<p style="color: red;">Lỗi tải dữ liệu: ${error.message}</p>`;
        }
    }

    async function showUserDetails(id) {
        viewModalTitle.textContent = `Chi tiết Người Dùng #${id}`;
        viewModalBody.innerHTML = `<div class="loader">Đang tải...</div>`;
        viewDetailModal.style.display = 'block';

        try {
            const response = await fetch(`${API_BASE_URL}/users/${id}`);
            const apiResponse = await response.json();
            if (apiResponse.status !== 'SUCCESS') throw new Error(apiResponse.message);
            const u = apiResponse.data;

            viewModalBody.innerHTML = `
                <table class="detail-view-table">
                    <tr><td>ID</td><td>${u.id}</td></tr>
                    <tr><td>Tên đăng nhập</td><td>${u.userName}</td></tr>
                    <tr><td>Họ và Tên</td><td>${u.fullName}</td></tr>
                    <tr><td>Email</td><td>${u.email}</td></tr>
                    <tr><td>Số điện thoại</td><td>${u.phoneNumber || 'N/A'}</td></tr>
                    <tr><td>Địa chỉ</td><td>${u.address || 'N/A'}</td></tr>
                    <tr><td>Vai trò</td><td>${u.role ? u.role.roleName.replace('ROLE_', '') : 'N/A'}</td></tr>
                    <tr><td>Trạng thái</td><td><span class="status ${u.status.toLowerCase()}">${u.status}</span></td></tr>
                    <tr><td>Ngày tạo</td><td>${u.createdAt ? new Date(u.createdAt).toLocaleString('vi-VN') : 'N/A'}</td></tr>
                    <tr><td>Ghi chú</td><td>${u.note || 'N/A'}</td></tr>
                </table>
            `;
        } catch (error) {
            viewModalBody.innerHTML = `<p style="color: red;">Lỗi tải dữ liệu: ${error.message}</p>`;
        }
    }


    // ===================================================================
    //                  V. HÀM CHUNG VÀ EVENT LISTENERS
    // ===================================================================

    function renderPagination(container, pageData, loadFunction) {
        container.innerHTML = '';
        if (pageData.totalPages <= 1) return;

        const prevButton = document.createElement('button');
        prevButton.innerHTML = '« Trước';
        prevButton.disabled = pageData.first;
        prevButton.addEventListener('click', () => loadFunction(pageData.number - 1));
        container.appendChild(prevButton);

        const pageInfo = document.createElement('span');
        pageInfo.className = 'current-page';
        pageInfo.textContent = `Trang ${pageData.number + 1} / ${pageData.totalPages}`;
        container.appendChild(pageInfo);

        const nextButton = document.createElement('button');
        nextButton.innerHTML = 'Sau »';
        nextButton.disabled = pageData.last;
        nextButton.addEventListener('click', () => loadFunction(pageData.number + 1));
        container.appendChild(nextButton);
    }

    addDonationBtn.addEventListener('click', openDonationModalForCreate);
    addUserBtn.addEventListener('click', openUserModalForCreate);

    donationCloseButton.addEventListener('click', () => donationModal.style.display = 'none');
    userModalCloseBtn.addEventListener('click', () => userModal.style.display = 'none');
    viewModalCloseButton.addEventListener('click', () => viewDetailModal.style.display = 'none');

    window.addEventListener('click', (event) => {
        if (event.target == donationModal) donationModal.style.display = 'none';
        if (event.target == userModal) userModal.style.display = 'none';
        if (event.target == viewDetailModal) viewDetailModal.style.display = 'none';
    });

    document.body.addEventListener('click', (e) => {
        const target = e.target;
        const button = target.closest('.btn');
        const row = target.closest('.clickable-row');

        if (button) {
            e.stopPropagation(); // Ngăn sự kiện click nổi bọt lên hàng <tr>
            const id = button.dataset.id;
            if (!id) return;

            if (button.classList.contains('btn-edit-donation')) openDonationModalForEdit(id);
            if (button.classList.contains('btn-delete-donation')) deleteDonation(id);
            if (button.classList.contains('btn-edit-user')) openUserModalForEdit(id);
            if (button.classList.contains('btn-delete-user')) deleteUser(id);

            return;
        }

        if (row) {
            const id = row.dataset.id;
            const type = row.dataset.type;
            if (!id || !type) return;

            if (type === 'donation') showDonationDetails(id);
            if (type === 'user') showUserDetails(id);
        }
    });

    let userSearchTimer;
    function handleUserSearch() {
        clearTimeout(userSearchTimer);
        userSearchTimer = setTimeout(() => loadUsers(0), 500);
    }
    userEmailSearch.addEventListener('input', handleUserSearch);
    userPhoneSearch.addEventListener('input', handleUserSearch);


    // ===================================================================
    //                  V. KHỞI TẠO DỮ LIỆU BAN ĐẦU
    // ===================================================================

    function initializeAdminPage() {
        Promise.all([
            loadDonations(0),
            loadUsers(0),
            loadRolesForSelect()
        ]);
    }

    initializeAdminPage();
});