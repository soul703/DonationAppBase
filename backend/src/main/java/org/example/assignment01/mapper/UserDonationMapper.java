package org.example.assignment01.mapper;

import org.example.assignment01.dto.reponse.ContributionResponse;
import org.example.assignment01.entity.UserDonation;
import org.springframework.stereotype.Component;

@Component
public class UserDonationMapper {

    public ContributionResponse toResponse(UserDonation userDonation) {
        if (userDonation == null) {
            return null;
        }

        ContributionResponse response = new ContributionResponse();
        response.setId(userDonation.getId());
        response.setAmount(userDonation.getMoney());
        response.setMessage(userDonation.getText());
        response.setContributionDate(userDonation.getCreatedAt());

        if (userDonation.getUser() != null) {
            ContributionResponse.SimpleUserResponse userDto = new ContributionResponse.SimpleUserResponse();
            userDto.setId(userDonation.getUser().getId());
            userDto.setUserName(userDonation.getUser().getUserName());
            userDto.setFullName(userDonation.getUser().getFullName());
            response.setUser(userDto);
        }

        if (userDonation.getDonation() != null) {
            ContributionResponse.SimpleDonationResponse donationDto = new ContributionResponse.SimpleDonationResponse();
            donationDto.setId(userDonation.getDonation().getId());
            donationDto.setName(userDonation.getDonation().getName());
            donationDto.setCode(userDonation.getDonation().getCode());
            response.setDonation(donationDto);
        }

        return response;
    }
}
