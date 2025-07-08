package org.example.assignment01.mapper;


import org.example.assignment01.dto.reponse.DonationResponse;
import org.example.assignment01.entity.Donation;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DonationMapper {

    public DonationResponse toResponse(Donation donation) {
        if (donation == null) return null;

        DonationResponse response = new DonationResponse();
        response.setId(donation.getId());
        response.setName(donation.getName());
        response.setCode(donation.getCode());
        response.setDescription(donation.getDescription());
        response.setStartDate(donation.getStartDate());
        response.setEndDate(donation.getEndDate());
        response.setMoneyTarget(donation.getMoney());
        response.setOrganizationName(donation.getOrganizationName());
        response.setPhoneNumber(donation.getPhoneNumber());
       response.setStatus(donation.getStatus());
        response.setAmountRaised(donation.getAmountRaised());
        return response;
    }

    public List<DonationResponse> toResponseList(List<Donation> donations) {
        return donations.stream().map(this::toResponse).collect(Collectors.toList());
    }
}