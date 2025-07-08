package org.example.assignment01.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.assignment01.entity.Donation;
import org.example.assignment01.entity.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDonationDto {
    private String created;
    private Integer money;
    private String name;
    private Integer status;
    private String text;
    private User user;
    private Donation donation;

}
