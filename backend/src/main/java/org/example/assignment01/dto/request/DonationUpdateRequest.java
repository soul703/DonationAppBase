package org.example.assignment01.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.example.assignment01.useenum.DonationStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class DonationUpdateRequest {
    private String name;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private BigDecimal moneyTarget;
    private String organizationName;
    private String phoneNumber;
    private DonationStatus status; // Cho phép admin cập nhật trạng thái
}
