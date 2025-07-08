package org.example.assignment01.service;

import jakarta.validation.Valid;
import org.example.assignment01.dto.reponse.ApiResponse;
import org.example.assignment01.dto.reponse.DonationResponse;
import org.example.assignment01.dto.request.DonationCreateRequest;
import org.example.assignment01.dto.request.DonationUpdateRequest;
import org.example.assignment01.entity.Donation;
import org.example.assignment01.exception.ResourceNotFoundException;
import org.example.assignment01.mapper.DonationMapper;
import org.example.assignment01.repository.DonationRepository;
import org.example.assignment01.repository.DonationSpecification;
import org.example.assignment01.repository.UserRepository;
import org.example.assignment01.useenum.DonationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class DonationService {

    @Autowired private DonationRepository donationRepository;
    @Autowired private DonationMapper donationMapper;

    public DonationResponse createDonation(DonationCreateRequest request) {
        if (donationRepository.existsByCode(request.getCode())) {
            throw new IllegalArgumentException("Donation code '" + request.getCode() + "' already exists.");
        }

        Donation donation = new Donation();
        donation.setName(request.getName());
        donation.setCode(request.getCode());
        donation.setDescription(request.getDescription());
        donation.setStartDate(request.getStartDate());
        donation.setEndDate(request.getEndDate());
        donation.setMoney(request.getMoneyTarget());
        donation.setOrganizationName(request.getOrganizationName());
        donation.setPhoneNumber(request.getPhoneNumber());
        donation.setStatus(DonationStatus.UPCOMING);

        Donation savedDonation = donationRepository.save(donation);
        return donationMapper.toResponse(savedDonation);
    }

    public Page<DonationResponse> getAllDonations(String searchTerm, DonationStatus status, Pageable pageable) {
        Sort sort = Sort.by(Sort.Direction.ASC, "status").and(pageable.getSort());
        Pageable newPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        Specification<Donation> spec = DonationSpecification.hasStatus(status)
                .and(DonationSpecification.hasNameOrCode(searchTerm));

        return donationRepository.findAll(spec, pageable)
                .map(donationMapper::toResponse);
    }


    public DonationResponse getDonationById(Integer id) {
        Donation donation = donationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donation not found with id: " + id));
        return donationMapper.toResponse(donation);
    }

    public DonationResponse updateDonation(Integer id, DonationUpdateRequest request) {
        Donation existingDonation = donationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donation not found with id: " + id));

        if (request.getName() != null) existingDonation.setName(request.getName());
        if (request.getDescription() != null) existingDonation.setDescription(request.getDescription());
        if (request.getStatus() != null) existingDonation.setStatus(request.getStatus());
        // Các trường khác có thể được thêm vào đây

        Donation updatedDonation = donationRepository.save(existingDonation);
        return donationMapper.toResponse(updatedDonation);
    }

    public void deleteDonation(Integer id) {
        if (!donationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Donation not found with id: " + id);
        }
        donationRepository.deleteById(id);
    }
}