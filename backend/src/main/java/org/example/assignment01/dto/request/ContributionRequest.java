package org.example.assignment01.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ContributionRequest {

    // Giai đoạn chưa có Security, chúng ta cần client gửi lên userId.
    // Sau khi có Security, trường này sẽ được loại bỏ và lấy từ Principal.
    @NotNull(message = "User ID cannot be null")
    private Integer userId;

    @NotNull(message = "Donation ID cannot be null")
    private Integer donationId;

    @NotNull(message = "Amount cannot be null")
    @Min(value = 1000, message = "Contribution amount must be at least 1000")
    private BigDecimal amount;

    private String message;
}