package org.example.assignment01.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class DonationCreateRequest {
    @NotBlank(message = "Donation name cannot be blank")
    private String name;

    @NotBlank(message = "Donation code cannot be blank")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "Code can only contain letters, numbers, underscore, and hyphen")
    private String code;

    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "Start date is required")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @Min(value = 1000, message = "Target money must be at least 1000")
    private BigDecimal moneyTarget;

    private String organizationName;
    private String phoneNumber;

    @AssertTrue(message = "End date must be on or after start date")
    public boolean isEndDateValid() {
        return startDate == null || endDate == null || !endDate.isBefore(startDate);
    }
}