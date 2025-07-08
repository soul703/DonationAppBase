package org.example.assignment01.runner;
import org.example.assignment01.entity.Donation;
import org.example.assignment01.entity.Role;
import org.example.assignment01.entity.User;
import org.example.assignment01.entity.UserDonation;
import org.example.assignment01.repository.DonationRepository;
import org.example.assignment01.repository.RoleRepository;
import org.example.assignment01.repository.UserDonationRepository;
import org.example.assignment01.repository.UserRepository;
import org.example.assignment01.useenum.DonationStatus;
import org.example.assignment01.useenum.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal; // <-- IMPORT QUAN TRỌNG
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired private RoleRepository roleRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private DonationRepository donationRepository;
    @Autowired private UserDonationRepository userDonationRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (roleRepository.count() > 0 || userRepository.count() > 0) {
            System.out.println("--- [DataInitializer] Data already exists. Skipping initialization. ---");
            return;
        }

        System.out.println("--- [DataInitializer] Starting data initialization... ---");

        Role adminRole = createRole("ROLE_ADMIN");
        Role userRole = createRole("ROLE_USER");
        List<User> sampleUsers = createSampleUsers(adminRole, userRole);
        List<Donation> sampleDonations = createSampleDonations();
        createSampleContributions(sampleUsers, sampleDonations);

        System.out.println("--- [DataInitializer] Data initialization finished successfully. ---");
    }

    private Role createRole(String roleName) {
        Role role = new Role();
        role.setRoleName(roleName);
        return roleRepository.save(role);
    }

    private List<User> createSampleUsers(Role adminRole, Role userRole) {
        // ... (phần này không thay đổi vì không có trường tiền tệ)
        System.out.println("Creating sample users...");
        List<User> users = new ArrayList<>();
        String defaultPassword = "password123";
        users.add(createUser("admin", defaultPassword, "Administrator", "admin@donationapp.com", "0987654321", "1 Ho Chi Minh", adminRole, UserStatus.ACTIVE));
        users.add(createUser("nguyenvana", defaultPassword, "Nguyễn Văn An", "nguyenvana@example.com", "0912345678", "2 Hanoi", userRole, UserStatus.ACTIVE));
        users.add(createUser("tranthib", defaultPassword, "Trần Thị Bình", "tranthib@example.com", "0923456789", "3 Da Nang", userRole, UserStatus.ACTIVE));
        users.add(createUser("lethic", defaultPassword, "Lê Thị Cúc", "lethic@example.com", "0934567890", "4 Can Tho", userRole, UserStatus.ACTIVE));
        users.add(createUser("phamvand", defaultPassword, "Phạm Văn Dũng", "phamvand@example.com", "0945678901", "5 Hai Phong", userRole, UserStatus.ACTIVE));
        users.add(createUser("banneduser", defaultPassword, "Tài Khoản Bị Khóa", "banned@example.com", "0956789012", "6 Nowhere", userRole, UserStatus.BANNED));
        for (int i = 1; i <= 14; i++) {
            String username = "user" + i;
            users.add(createUser(username, defaultPassword, "Người Dùng Mẫu " + i, username + "@example.com", "09" + (10000000 + i), (i+6) + " Some Street", userRole, UserStatus.ACTIVE));
        }
        return userRepository.saveAll(users);
    }

    private User createUser(String userName, String password, String fullName, String email, String phone, String address, Role role, UserStatus status) {
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhoneNumber(phone);
        user.setAddress(address);
        user.setRole(role);
        user.setStatus(status);
        return user;
    }

    private List<Donation> createSampleDonations() {
        System.out.println("Creating sample donations...");
        List<Donation> donations = new ArrayList<>();
        Random random = new Random();
        LocalDate today = LocalDate.now();

        donations.add(createDonation("Hỗ trợ trẻ em vùng cao đến trường 2024", "TREEM_VUNGCAO_2024", "Gây quỹ mua sách vở...", today.minusDays(20), today.plusDays(40), BigDecimal.valueOf(150_000_000L), "Quỹ Hy Vọng", DonationStatus.ACTIVE));
        donations.add(createDonation("Xây cầu dân sinh \"Nối Yêu Thương\"", "XAYCAU_HAUGIANG_2024", "Xây dựng cây cầu bê tông...", today.plusDays(10), today.plusDays(100), BigDecimal.valueOf(300_000_000L), "Nhóm Thiện Nguyện Xanh", DonationStatus.UPCOMING));
        donations.add(createDonation("Bữa cơm có thịt cho em", "BUACOM_SAPA_2023", "Cải thiện bữa ăn dinh dưỡng...", today.minusDays(150), today.minusDays(90), BigDecimal.valueOf(80_000_000L), "Quỹ Tấm Lòng Vàng", DonationStatus.ENDED));
        donations.add(createDonation("Nước sạch cho bản làng", "NUOCSACH_TAYBAC_2024", "Xây dựng hệ thống lọc nước...", today.minusDays(10), today.plusDays(80), BigDecimal.valueOf(250_000_000L), "Hội Chữ Thập Đỏ", DonationStatus.ACTIVE));
        donations.add(createDonation("Áo ấm mùa đông 2023", "AOAM_MUADONG_2023", "Trao tặng hơn 1000 chiếc áo ấm...", today.minusDays(200), today.minusDays(150), BigDecimal.valueOf(120_000_000L), "Nhóm Thiện Nguyện Xanh", DonationStatus.ENDED));
        donations.add(createDonation("Giúp đỡ người già neo đơn", "NGUOIGIA_NEODON_2024", "Thăm hỏi, tặng quà...", today.minusDays(5), today.plusDays(55), BigDecimal.valueOf(70_000_000L), "Quỹ Tấm Lòng Vàng", DonationStatus.ACTIVE));

        for (int i = 7; i <= 20; i++) {
            long targetMoney = (long) (50_000_000 + random.nextInt(20) * 5_000_000);
            donations.add(createDonation("Chiến dịch mẫu số " + i, "SAMPLE_CODE_" + i, "Mô tả cho chiến dịch mẫu " + i, today.minusDays(random.nextInt(60)), today.plusDays(random.nextInt(60)), BigDecimal.valueOf(targetMoney), "Tổ chức ABC", DonationStatus.ACTIVE));
        }

        return donationRepository.saveAll(donations);
    }

    private Donation createDonation(String name, String code, String description, LocalDate startDate, LocalDate endDate, BigDecimal targetMoney, String org, DonationStatus status) {
        Donation donation = new Donation();
        donation.setName(name);
        donation.setCode(code);
        donation.setDescription(description);
        donation.setStartDate(startDate);
        donation.setEndDate(endDate);
        donation.setMoney(targetMoney);
        donation.setOrganizationName(org);
        donation.setStatus(status);
        donation.setAmountRaised(BigDecimal.ZERO); // Khởi tạo số tiền đã quyên góp là 0
        return donation;
    }

    private void createSampleContributions(List<User> users, List<Donation> donations) {
        System.out.println("Creating sample contributions...");
        if (users.isEmpty() || donations.isEmpty()) return;

        List<UserDonation> contributions = new ArrayList<>();
        List<Donation> activeDonations = donations.stream()
                .filter(d -> d.getStatus() == DonationStatus.ACTIVE)
                .toList();

        if (activeDonations.isEmpty()) return;

        String[] messages = {"Cố lên nhé!", "Chúc chương trình thành công.", "Góp một phần nhỏ bé.", ""};
        Random random = new Random();

        for (int i = 0; i < 50; i++) {
            User randomUser = users.get(random.nextInt(users.size() -1) + 1);
            Donation randomDonation = activeDonations.get(random.nextInt(activeDonations.size()));

            // Tạo số tiền quyên góp ngẫu nhiên kiểu BigDecimal
            long amountValue = (long) (random.nextInt(20) + 1) * 50_000;
            BigDecimal amount = BigDecimal.valueOf(amountValue);

            String message = messages[random.nextInt(messages.length)];

            UserDonation contribution = new UserDonation();
            contribution.setUser(randomUser);
            contribution.setDonation(randomDonation);
            contribution.setMoney(amount); // Gán giá trị BigDecimal
            contribution.setText(message);
            contribution.setStatus(1);

            contributions.add(contribution);

            // Cập nhật lại tổng số tiền bằng phương thức .add()
            BigDecimal currentAmount = randomDonation.getAmountRaised();
            randomDonation.setAmountRaised(currentAmount.add(amount));
        }

        userDonationRepository.saveAll(contributions);
        donationRepository.saveAll(activeDonations);
        System.out.println(contributions.size() + " sample contributions created.");
    }
}