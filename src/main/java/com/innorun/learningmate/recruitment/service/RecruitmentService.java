package com.innorun.learningmate.recruitment.service;

import com.innorun.learningmate.global.exception.ServiceException;
import com.innorun.learningmate.recruitment.dto.request.RecruitmentCreateRequest;
import com.innorun.learningmate.recruitment.dto.request.RecruitmentUpdateRequest;
import com.innorun.learningmate.recruitment.dto.response.RecruitmentResponse;
import com.innorun.learningmate.recruitment.dto.response.RecruitmentSummaryResponse;
import com.innorun.learningmate.recruitment.entity.Recruitment;
import com.innorun.learningmate.recruitment.entity.RecruitmentStatus;
import com.innorun.learningmate.recruitment.repository.RecruitmentRepository;
import com.innorun.learningmate.recruitment.validator.RecruitmentValidator;
import com.innorun.learningmate.user.entity.User;
import com.innorun.learningmate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;
    private final UserRepository userRepository;
    private final RecruitmentValidator recruitmentValidator;

    @Transactional
    public Long create(Long authorId, RecruitmentCreateRequest request) {
        recruitmentValidator.validateCreate(request);

        User author = userRepository.findByIdAndDeletedAtIsNull(authorId)
                .orElseThrow(() -> new ServiceException(
                        HttpStatus.NOT_FOUND,
                        "사용자를 찾을 수 없습니다."
                ));

        Recruitment recruitment = new Recruitment(
                author,
                request.getTitle(),
                request.getDescription(),
                request.getStudyType(),
                request.getOnlinePlatform(),
                request.getOfflineLocation(),
                request.getCapacity(),
                request.getRecruitmentDeadline(),
                request.getStartAt()
        );

        Recruitment savedRecruitment = recruitmentRepository.save(recruitment);

        return savedRecruitment.getId();
    }

    public RecruitmentResponse getRecruitmentDetail(Long recruitmentId) {
        Recruitment recruitment = findRecruitmentById(recruitmentId);

        return new RecruitmentResponse(recruitment);
    }

    public List<RecruitmentSummaryResponse> getRecruitmentList() {
        List<Recruitment> recruitments = recruitmentRepository.findAll();
        List<RecruitmentSummaryResponse> responses = new ArrayList<>();

        for (Recruitment recruitment : recruitments) {
            responses.add(new RecruitmentSummaryResponse(recruitment));
        }

        return responses;
    }

    @Transactional
    public RecruitmentResponse updateRecruitment(
            Long recruitmentId,
            Long authorId,
            RecruitmentUpdateRequest request
    ) {
        Recruitment recruitment = findRecruitmentById(recruitmentId);

        validateAuthor(recruitment, authorId);
        recruitmentValidator.validateUpdate(recruitment, request);

        recruitment.updateDetails(
                request.getTitle(),
                request.getDescription(),
                request.getStudyType(),
                request.getOnlinePlatform(),
                request.getOfflineLocation(),
                request.getCapacity(),
                request.getRecruitmentDeadline(),
                request.getStartAt()
        );

        return new RecruitmentResponse(recruitment);
    }

    @Transactional
    public RecruitmentResponse closeRecruitment(Long recruitmentId, Long authorId) {
        Recruitment recruitment = findRecruitmentById(recruitmentId);

        validateAuthor(recruitment, authorId);

        if (recruitment.getStatus() != RecruitmentStatus.OPEN) {
            throw new ServiceException(
                    HttpStatus.BAD_REQUEST,
                    "모집 중인 공고만 마감할 수 있습니다."
            );
        }

        recruitment.close();

        return new RecruitmentResponse(recruitment);
    }

    @Transactional
    public RecruitmentResponse cancelRecruitment(Long recruitmentId, Long authorId) {
        Recruitment recruitment = findRecruitmentById(recruitmentId);

        validateAuthor(recruitment, authorId);

        if (recruitment.getStatus() == RecruitmentStatus.CANCELLED) {
            throw new ServiceException(
                    HttpStatus.BAD_REQUEST,
                    "이미 취소된 모집공고입니다."
            );
        }

        recruitment.cancel();

        return new RecruitmentResponse(recruitment);
    }

    private Recruitment findRecruitmentById(Long recruitmentId) {
        return recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new ServiceException(
                        HttpStatus.NOT_FOUND,
                        "모집공고를 찾을 수 없습니다."
                ));
    }

    private void validateAuthor(Recruitment recruitment, Long authorId) {
        if (!recruitment.getAuthor().getId().equals(authorId)) {
            throw new ServiceException(
                    HttpStatus.FORBIDDEN,
                    "모집공고 작성자만 변경할 수 있습니다."
            );
        }
    }
}
