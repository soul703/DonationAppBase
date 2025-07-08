package org.example.assignment01.service;

import org.example.assignment01.dto.reponse.ContributionResponse;
import org.example.assignment01.dto.request.ContributionRequest;
import org.example.assignment01.entity.Donation;
import org.example.assignment01.entity.User;
import org.example.assignment01.entity.UserDonation;
import org.example.assignment01.exception.ResourceNotFoundException;
import org.example.assignment01.mapper.UserDonationMapper;
import org.example.assignment01.repository.DonationRepository;
import org.example.assignment01.repository.UserDonationRepository;
import org.example.assignment01.repository.UserRepository;
import org.example.assignment01.useenum.DonationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class UserDonationService {

    @Autowired
    private UserDonationRepository userDonationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DonationRepository donationRepository;
    @Autowired
    private UserDonationMapper userDonationMapper;

    @Transactional
    public ContributionResponse  makeContribution(ContributionRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getUserId()));

        Donation donation = donationRepository.findById(request.getDonationId())
                .orElseThrow(() -> new ResourceNotFoundException("Donation not found with id: " + request.getDonationId()));

        if (donation.getStatus() != DonationStatus.ACTIVE) {
            throw new IllegalStateException("This donation is not currently active. Status: " + donation.getStatus());
        }

        UserDonation userDonation = new UserDonation();
        userDonation.setUser(user);
        userDonation.setDonation(donation);
        userDonation.setMoney(request.getAmount());
        userDonation.setText(request.getMessage());
        userDonation.setStatus(1); // Giả sử 1 là thành công

        UserDonation savedUserDonation = userDonationRepository.save(userDonation);

        BigDecimal contributionAmount = request.getAmount();

        // 1. Kiểm tra null để đảm bảo an toàn
        if (contributionAmount == null) {
            throw new IllegalArgumentException("Contribution amount cannot be null.");
        }

        // (Tùy chọn) Kiểm tra giá trị tối thiểu
        // BigDecimal.ZERO là một hằng số tiện lợi
        if (contributionAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Contribution amount must be positive.");
        }

        // ... (code tạo UserDonation)
        // userDonation.setMoney(contributionAmount); // Gán giá trị BigDecimal

        // 2. LẤY SỐ TIỀN HIỆN TẠI VÀ THỰC HIỆN PHÉP CỘNG
        BigDecimal currentAmountRaised = donation.getAmountRaised();

        // Nếu số tiền hiện tại có thể là null, cần xử lý
        if (currentAmountRaised == null) {
            currentAmountRaised = BigDecimal.ZERO;
        }

        // 3. SỬ DỤNG PHƯƠNG THỨC .add() ĐỂ CỘNG
        BigDecimal newAmountRaised = currentAmountRaised.add(contributionAmount);

        // 4. CẬP NHẬT LẠI GIÁ TRỊ MỚI
        donation.setAmountRaised(newAmountRaised);

        donationRepository.save(donation);

        return userDonationMapper.toResponse(savedUserDonation);
    }

    public Page<ContributionResponse> getContributionsByUserId(Integer userId, Pageable pageable) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
        return userDonationRepository.findByUser_Id(userId, pageable).map(userDonationMapper::toResponse);
    }

    public Page<ContributionResponse> getContributionsByDonationId(Integer donationId, Pageable pageable) {
        if (!donationRepository.existsById(donationId)) {
            throw new ResourceNotFoundException("Donation not found with id: " + donationId);
        }
        return userDonationRepository.findByDonation_Id(donationId, pageable).map(userDonationMapper::toResponse);
    }
}