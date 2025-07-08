package org.example.assignment01.dto.reponse;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ContributionResponse {
    private Integer id;
    private BigDecimal amount;
    private String message;
    private LocalDateTime contributionDate;
    private SimpleUserResponse user;
    private SimpleDonationResponse donation;

    /**
     * DTO con chứa thông tin tối giản của người quyên góp.
     */
    @Data
    public static class SimpleUserResponse {
        private Integer id;
        private String userName;
        private String fullName;
    }

    /**
     * DTO con chứa thông tin tối giản của đợt quyên góp.
     */
    @Data
    public static class SimpleDonationResponse {
        private Integer id;
        private String name;
        private String code;
    }
}
