package org.example.assignment01.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

import java.util.List;
@Data
public class DonationDto {
    @NotNull(message = "User ID không được để trống")
    private Integer userId;
    @NotNull(message = "Donation ID không được để trống")
    private Integer donationId;
    @NotNull(message = "Số tiền không được để trống")
    @Min(value = 1, message = "Số tiền phải lớn hơn 0")
    private BigDecimal money;
    private String name; // Tên người quyên góp
    private String text; // Lời nhắn
}
