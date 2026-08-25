package com.innorun.learningmate.studymember.service;

import com.innorun.learningmate.studygroup.entity.StudyGroup;
import com.innorun.learningmate.studygroup.exception.StudyGroupErrorCode;
import com.innorun.learningmate.studygroup.repository.StudyGroupRepository;
import com.innorun.learningmate.studymember.dto.StudyMemberGetResponse;
import com.innorun.learningmate.studymember.dto.StudyMemberSaveResponse;
import com.innorun.learningmate.studymember.entity.StudyMember;
import com.innorun.learningmate.studymember.exception.StudyMemberErrorCode;
import com.innorun.learningmate.studymember.repository.StudyMemberRepository;
import com.innorun.learningmate.user.entity.User;
import com.innorun.learningmate.user.exception.UserErrorCode;
import com.innorun.learningmate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyMemberService {

    private final StudyMemberRepository studyMemberRepository;
    private final StudyGroupRepository studyGroupRepository;
    private final UserRepository userRepository;

    @Transactional
    public StudyMemberSaveResponse save(Long userId, Long studyGroupId) {
        User user = getActiveUser(userId);
        StudyGroup studyGroup = findStudyGroupById(studyGroupId);

        if (studyMemberRepository.existsByUserIdAndStudyGroupId(
                userId,
                studyGroupId
        )) {
            throw StudyMemberErrorCode.ALREADY_JOINED.toException();
        }

        StudyMember studyMember = new StudyMember(user, studyGroup);
        studyMemberRepository.save(studyMember);

        return new StudyMemberSaveResponse(
                studyMember.getId(),
                user.getId(),
                user.getNickname(),
                studyGroup.getId()
        );
    }

    @Transactional(readOnly = true)
    public List<StudyMemberGetResponse> getAll(Long studyGroupId) {
        findStudyGroupById(studyGroupId);

        List<StudyMember> members =
                studyMemberRepository.findAllByStudyGroupIdAndUserDeletedAtIsNull(
                        studyGroupId
                );

        return members.stream()
                .map(member -> new StudyMemberGetResponse(
                        member.getId(),
                        member.getUser().getId(),
                        member.getUser().getNickname(),
                        member.getStudyGroup().getId()
                ))
                .toList();
    }

    @Transactional
    public void delete(Long userId, Long studyGroupId) {
        User currentUser = getActiveUser(userId);
        StudyGroup studyGroup = findStudyGroupById(studyGroupId);

        if (studyGroup.getLeader().getId().equals(currentUser.getId())) {
            throw StudyMemberErrorCode.LEADER_CANNOT_LEAVE.toException();
        }

        StudyMember studyMember =
                studyMemberRepository.findByUserIdAndStudyGroupId(
                        currentUser.getId(),
                        studyGroupId
                ).orElseThrow(
                        StudyMemberErrorCode.STUDY_MEMBER_NOT_FOUND::toException
                );

        studyMemberRepository.delete(studyMember);
    }

    private User getActiveUser(Long userId) {
        return userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(UserErrorCode.USER_NOT_FOUND::toException);
    }

    private StudyGroup findStudyGroupById(Long studyGroupId) {
        return studyGroupRepository.findById(studyGroupId)
                .orElseThrow(StudyGroupErrorCode.STUDY_GROUP_NOT_FOUND::toException);
    }
}
