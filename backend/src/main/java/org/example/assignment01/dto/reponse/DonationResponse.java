package org.example.assignment01.dto.reponse;

import lombok.Data;
import org.example.assignment01.useenum.DonationStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Lớp `DonationResponse` đại diện cho dữ liệu phản hồi (response) của một chiến dịch quyên góp (donation).
 *
 * Thường được sử dụng trong các API trả thông tin về donation như:
 * - GET /api/v1/donations
 * - POST /api/v1/donations
 *
 * Bao gồm các thông tin cơ bản về một chiến dịch từ thiện.
 */
@Data
public class DonationResponse {

    /**
     * ID duy nhất của chiến dịch quyên góp.
     */
    private Integer id;

    /**
     * Tên chiến dịch quyên góp (ví dụ: "Ủng hộ miền Trung").
     */
    private String name;

    /**
     * Mã định danh duy nhất cho chiến dịch (sử dụng cho tìm kiếm nhanh hoặc URL).
     */
    private String code;

    /**
     * Mô tả chi tiết về mục đích hoặc nội dung của chiến dịch.
     */
    private String description;

    /**
     * Ngày bắt đầu chiến dịch.
     */
    private LocalDate startDate;

    /**
     * Ngày kết thúc chiến dịch.
     */
    private LocalDate endDate;

    /**
     * Mục tiêu quyên góp (ví dụ: 100.000.000 VNĐ).
     */
    private BigDecimal moneyTarget;

    /**
     * Tên tổ chức hoặc đơn vị đứng ra tổ chức quyên góp.
     */
    private String organizationName;

    /**
     * Số điện thoại liên hệ của tổ chức.
     */
    private String phoneNumber;

    /**
     * Trạng thái chiến dịch (ACTIVE, INACTIVE, CLOSED...).
     */
    private DonationStatus status;

    /**
     * Tổng số tiền đã quyên góp được tính đến hiện tại.
     * Mặc định = 0.
     */
    private BigDecimal amountRaised = BigDecimal.ZERO;
}
