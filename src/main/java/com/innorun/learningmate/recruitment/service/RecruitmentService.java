package com.innorun.learningmate.recruitment.service;

import com.innorun.learningmate.global.exception.ServiceException;
import com.innorun.learningmate.recruitment.dto.request.RecruitmentCreateRequest;
import com.innorun.learningmate.recruitment.dto.response.RecruitmentResponse;
import com.innorun.learningmate.recruitment.entity.Recruitment;
import com.innorun.learningmate.recruitment.repository.RecruitmentRepository;
import com.innorun.learningmate.user.entity.User;
import com.innorun.learningmate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long create(RecruitmentCreateRequest request) {
        User author = userRepository.findById(request.getAuthorId())
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

    public RecruitmentResponse findById(Long recruitmentId) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new ServiceException(
                        HttpStatus.NOT_FOUND,
                        "모집공고를 찾을 수 없습니다."
                ));

        return new RecruitmentResponse(recruitment);
    }
}
