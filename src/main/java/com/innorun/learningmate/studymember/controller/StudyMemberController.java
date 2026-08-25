package com.innorun.learningmate.studymember.controller;

import com.innorun.learningmate.global.security.principal.CurrentUserId;
import com.innorun.learningmate.studymember.dto.StudyMemberGetResponse;
import com.innorun.learningmate.studymember.dto.StudyMemberSaveResponse;
import com.innorun.learningmate.studymember.service.StudyMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StudyMemberController {

    private final StudyMemberService studyMemberService;

    @PostMapping("/study-groups/{studyGroupId}/members")
    public ResponseEntity<StudyMemberSaveResponse> join(
            @CurrentUserId Long userId,
            @PathVariable Long studyGroupId
    ) {
        return ResponseEntity.ok(
                studyMemberService.save(userId, studyGroupId)
        );
    }

    @GetMapping("/study-groups/{studyGroupId}/members")
    public ResponseEntity<List<StudyMemberGetResponse>> getAll(
            @PathVariable Long studyGroupId
    ) {
        return ResponseEntity.ok(
                studyMemberService.getAll(studyGroupId)
        );
    }

    @DeleteMapping("/study-groups/{studyGroupId}/members")
    public ResponseEntity<Void> leave(
            @CurrentUserId Long userId,
            @PathVariable Long studyGroupId
    ) {
        studyMemberService.delete(userId, studyGroupId);
        return ResponseEntity.noContent().build();
    }
}
