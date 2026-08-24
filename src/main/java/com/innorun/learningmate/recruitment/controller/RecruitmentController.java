package com.innorun.learningmate.recruitment.controller;

import com.innorun.learningmate.recruitment.dto.request.RecruitmentCreateRequest;
import com.innorun.learningmate.recruitment.dto.response.RecruitmentResponse;
import com.innorun.learningmate.recruitment.service.RecruitmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class RecruitmentController {

    private final RecruitmentService recruitmentService;

    @PostMapping("/recruitments")
    public ResponseEntity<Long> create(
            @RequestBody RecruitmentCreateRequest request
    ) {
        Long recruitmentId = recruitmentService.create(request);

        return ResponseEntity
                .created(URI.create("/recruitments/" + recruitmentId))
                .body(recruitmentId);
    }

    @GetMapping("/recruitments/{recruitmentId}")
    public ResponseEntity<RecruitmentResponse> findById(
            @PathVariable Long recruitmentId
    ) {
        RecruitmentResponse response = recruitmentService.findById(recruitmentId);

        return ResponseEntity.ok(response);
    }
}
