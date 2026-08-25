package com.innorun.learningmate.studygroup.service;

import com.innorun.learningmate.studygroup.dto.StudyGroupCreateRequest;
import com.innorun.learningmate.studygroup.dto.StudyGroupCreateResponse;
import com.innorun.learningmate.studygroup.dto.StudyGroupGetResponse;
import com.innorun.learningmate.studygroup.dto.StudyGroupUpdateRequest;
import com.innorun.learningmate.studygroup.dto.StudyGroupUpdateResponse;
import com.innorun.learningmate.studygroup.entity.StudyGroup;
import com.innorun.learningmate.studygroup.exception.StudyGroupErrorCode;
import com.innorun.learningmate.studygroup.repository.StudyGroupRepository;
import com.innorun.learningmate.studymember.entity.StudyMember;
import com.innorun.learningmate.studymember.repository.StudyMemberRepository;
import com.innorun.learningmate.user.entity.User;
import com.innorun.learningmate.user.exception.UserErrorCode;
import com.innorun.learningmate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyGroupService {

    private final StudyGroupRepository studyGroupRepository;
    private final StudyMemberRepository studyMemberRepository;
    private final UserRepository userRepository;

    @Transactional
    public StudyGroupCreateResponse save(
            Long userId,
            StudyGroupCreateRequest request
    ) {
        User leader = getActiveUser(userId);

        StudyGroup studyGroup = new StudyGroup(
                request.getName(),
                request.getDescription(),
                leader
        );

        studyGroupRepository.save(studyGroup);

        // The creator is also the first member of the study group.
        StudyMember leaderMember = new StudyMember(leader, studyGroup);
        studyMemberRepository.save(leaderMember);

        return new StudyGroupCreateResponse(
                studyGroup.getId(),
                studyGroup.getName(),
                studyGroup.getDescription(),
                leader.getId(),
                leader.getNickname()
        );
    }

    @Transactional(readOnly = true)
    public List<StudyGroupGetResponse> getAll() {
        List<StudyGroup> studyGroups = studyGroupRepository.findAll();
        List<StudyGroupGetResponse> responses = new ArrayList<>();

        for (StudyGroup studyGroup : studyGroups) {
            responses.add(toGetResponse(studyGroup));
        }

        return responses;
    }

    @Transactional(readOnly = true)
    public StudyGroupGetResponse getOne(Long studyGroupId) {
        StudyGroup studyGroup = findStudyGroupById(studyGroupId);
        return toGetResponse(studyGroup);
    }

    @Transactional
    public StudyGroupUpdateResponse update(
            Long userId,
            Long studyGroupId,
            StudyGroupUpdateRequest request
    ) {
        User currentUser = getActiveUser(userId);
        StudyGroup studyGroup = findStudyGroupById(studyGroupId);

        validateLeader(studyGroup, currentUser.getId());

        studyGroup.update(
                request.getName(),
                request.getDescription()
        );

        return new StudyGroupUpdateResponse(
                studyGroup.getName(),
                studyGroup.getDescription()
        );
    }

    @Transactional
    public void delete(Long userId, Long studyGroupId) {
        User currentUser = getActiveUser(userId);
        StudyGroup studyGroup = findStudyGroupById(studyGroupId);

        validateLeader(studyGroup, currentUser.getId());

        // Delete memberships first because they reference the study group.
        studyMemberRepository.deleteAllByStudyGroupId(studyGroupId);
        studyGroupRepository.delete(studyGroup);
    }

    private User getActiveUser(Long userId) {
        return userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(UserErrorCode.USER_NOT_FOUND::toException);
    }

    private StudyGroup findStudyGroupById(Long studyGroupId) {
        return studyGroupRepository.findById(studyGroupId)
                .orElseThrow(StudyGroupErrorCode.STUDY_GROUP_NOT_FOUND::toException);
    }

    private void validateLeader(StudyGroup studyGroup, Long userId) {
        if (!studyGroup.getLeader().getId().equals(userId)) {
            throw StudyGroupErrorCode.NOT_STUDY_GROUP_LEADER.toException();
        }
    }

    private StudyGroupGetResponse toGetResponse(StudyGroup studyGroup) {
        return new StudyGroupGetResponse(
                studyGroup.getId(),
                studyGroup.getName(),
                studyGroup.getDescription(),
                studyGroup.getLeader().getId(),
                studyGroup.getLeader().getNickname()
        );
    }
}
