package org.example.assignment01.controller;

import jakarta.validation.Valid;
import org.example.assignment01.dto.reponse.ApiResponse;
import org.example.assignment01.dto.reponse.ContributionResponse;
import org.example.assignment01.dto.reponse.DonationResponse;
import org.example.assignment01.dto.request.DonationCreateRequest;
import org.example.assignment01.dto.request.DonationUpdateRequest;
import org.example.assignment01.mapper.DonationMapper;
import org.example.assignment01.repository.DonationRepository;
import org.example.assignment01.service.DonationService;
import org.example.assignment01.service.UserDonationService;
import org.example.assignment01.useenum.DonationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
/**
 * DonationController xử lý các API liên quan đến chiến dịch quyên góp (Donation).
 * Bao gồm: tạo mới, cập nhật, xoá, lấy danh sách hoặc lấy chi tiết từng donation.
 */
@RestController
@RequestMapping("/api/v1/donations")
public class DonationController {

    @Autowired
    private DonationService donationService;

    @Autowired
    private UserDonationService userDonationService;

    /**
     * Tạo một chiến dịch quyên góp mới.
     * @param request thông tin tạo mới
     * @return Thông tin chiến dịch sau khi tạo
     */
    @PostMapping
    public ResponseEntity<ApiResponse<DonationResponse>> createDonation(
            @Valid @RequestBody DonationCreateRequest request) {

        DonationResponse createdDonation = donationService.createDonation(request);

        // Tạo URI của resource vừa tạo để trả về trong header Location
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdDonation.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(ApiResponse.success(createdDonation, "Donation created successfully."));
    }

    /**
     * Lấy danh sách các chiến dịch quyên góp theo từ khoá, trạng thái, có phân trang.
     * @param searchTerm từ khoá tìm kiếm (có thể null)
     * @param status trạng thái chiến dịch (ACTIVE, CLOSED...)
     * @param pageable phân trang (page, size, sort)
     * @return Danh sách các donation phù hợp
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<DonationResponse>>> getAllDonations(
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false) DonationStatus status,
            Pageable pageable) {

        Page<DonationResponse> donations = donationService.getAllDonations(searchTerm, status, pageable);
        return ResponseEntity.ok(ApiResponse.success(donations, "Donations fetched successfully."));
    }

    /**
     * Lấy thông tin chi tiết một donation theo ID.
     * @param id ID của donation
     * @return Chi tiết donation
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DonationResponse>> getDonationById(@PathVariable Integer id) {
        return ResponseEntity.ok(
                ApiResponse.success(donationService.getDonationById(id), "Donation fetched successfully.")
        );
    }

    /**
     * Cập nhật thông tin một donation.
     * @param id ID cần cập nhật
     * @param request thông tin cập nhật
     * @return Donation sau khi cập nhật
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DonationResponse>> updateDonation(
            @PathVariable Integer id,
            @Valid @RequestBody DonationUpdateRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(donationService.updateDonation(id, request), "Donation updated successfully.")
        );
    }

    /**
     * Xoá một donation theo ID.
     * @param id ID cần xoá
     * @return Thông báo xoá thành công
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDonation(@PathVariable Integer id) {
        donationService.deleteDonation(id);
        return ResponseEntity.ok(ApiResponse.success("Donation deleted successfully."));
    }

    /**
     * Lấy danh sách người đã đóng góp cho một chiến dịch cụ thể.
     * @param donationId ID của donation
     * @param pageable Thông tin phân trang
     * @return Danh sách người đóng góp
     */
    @GetMapping("/{donationId}/contributors")
    public ResponseEntity<ApiResponse<Page<ContributionResponse>>> getDonationContributors(
            @PathVariable Integer donationId,
            Pageable pageable) {

        Page<ContributionResponse> contributors = userDonationService.getContributionsByDonationId(donationId, pageable);
        return ResponseEntity.ok(ApiResponse.success(contributors, "Donation contributors fetched successfully."));
    }
}
