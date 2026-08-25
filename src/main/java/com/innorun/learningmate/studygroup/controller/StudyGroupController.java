package com.innorun.learningmate.studygroup.controller;

import com.innorun.learningmate.global.security.principal.CurrentUserId;
import com.innorun.learningmate.studygroup.dto.StudyGroupCreateRequest;
import com.innorun.learningmate.studygroup.dto.StudyGroupCreateResponse;
import com.innorun.learningmate.studygroup.dto.StudyGroupGetResponse;
import com.innorun.learningmate.studygroup.dto.StudyGroupUpdateRequest;
import com.innorun.learningmate.studygroup.dto.StudyGroupUpdateResponse;
import com.innorun.learningmate.studygroup.service.StudyGroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StudyGroupController {

    private final StudyGroupService studyGroupService;

    @PostMapping("/study-groups")
    public ResponseEntity<StudyGroupCreateResponse> create(
            @CurrentUserId Long userId,
            @Valid @RequestBody StudyGroupCreateRequest request
    ) {
        return ResponseEntity.ok(studyGroupService.save(userId, request));
    }

    @GetMapping("/study-groups")
    public ResponseEntity<List<StudyGroupGetResponse>> getAll() {
        return ResponseEntity.ok(studyGroupService.getAll());
    }

    @GetMapping("/study-groups/{studyGroupId}")
    public ResponseEntity<StudyGroupGetResponse> getOne(
            @PathVariable Long studyGroupId
    ) {
        return ResponseEntity.ok(studyGroupService.getOne(studyGroupId));
    }

    @PutMapping("/study-groups/{studyGroupId}")
    public ResponseEntity<StudyGroupUpdateResponse> update(
            @CurrentUserId Long userId,
            @PathVariable Long studyGroupId,
            @Valid @RequestBody StudyGroupUpdateRequest request
    ) {
        return ResponseEntity.ok(
                studyGroupService.update(userId, studyGroupId, request)
        );
    }

    @DeleteMapping("/study-groups/{studyGroupId}")
    public ResponseEntity<Void> delete(
            @CurrentUserId Long userId,
            @PathVariable Long studyGroupId
    ) {
        studyGroupService.delete(userId, studyGroupId);
        return ResponseEntity.noContent().build();
    }
}
