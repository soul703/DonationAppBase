package org.example.assignment01.controller;

import jakarta.validation.Valid;
import org.example.assignment01.dto.reponse.ApiResponse;
import org.example.assignment01.dto.reponse.ContributionResponse;
import org.example.assignment01.dto.request.ContributionRequest;
import org.example.assignment01.service.UserDonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * UserDonationController xử lý các API liên quan đến việc người dùng đóng góp cho các chiến dịch quyên góp.
 * Bao gồm: tạo mới đóng góp.
 */
@RestController
@RequestMapping("/api/v1/contributions")
public class UserDonationController {

    @Autowired
    private UserDonationService userDonationService;
/**
     * API để người dùng thực hiện đóng góp cho một chiến dịch quyên góp.
     *
     * @param request thông tin đóng góp từ người dùng
     * @return Thông tin về đóng góp vừa được thực hiện
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ContributionResponse>> makeContribution(@Valid @RequestBody ContributionRequest request) {
        ContributionResponse contribution = userDonationService.makeContribution(request);
        return new ResponseEntity<>(ApiResponse.success(contribution, "Contribution made successfully!"), HttpStatus.CREATED);
    }


}
