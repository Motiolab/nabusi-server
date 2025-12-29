package com.motiolab.nabusi_server.classPackage.wellnessLectureReview.application;

import com.motiolab.nabusi_server.classPackage.wellnessLecture.domain.WellnessLectureEntity;
import com.motiolab.nabusi_server.classPackage.wellnessLectureReview.application.dto.WellnessLectureReviewDto;
import com.motiolab.nabusi_server.classPackage.wellnessLectureReview.domain.WellnessLectureReviewEntity;
import com.motiolab.nabusi_server.classPackage.wellnessLectureReview.domain.WellnessLectureReviewRepository;
import com.motiolab.nabusi_server.exception.customException.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class WellnessLectureReviewServiceImpl implements WellnessLectureReviewService {
    private final WellnessLectureReviewRepository wellnessLectureReviewRepository;

    @Override
    public List<WellnessLectureReviewDto> getAllByWellnessLectureIdList(List<Long> wellnessLectureIdList) {
        final List<WellnessLectureReviewEntity> wellnessLectureReviewEntityList = wellnessLectureReviewRepository
                .findAllByWellnessLectureIdIn(wellnessLectureIdList);
        return wellnessLectureReviewEntityList.stream().map(WellnessLectureReviewDto::from).toList();
    }

    @Override
    public List<WellnessLectureReviewDto> getAllByWellnessLectureId(Long wellnessLectureId) {
        final List<WellnessLectureReviewEntity> wellnessLectureReviewEntityList = wellnessLectureReviewRepository
                .findAllByWellnessLectureId(wellnessLectureId);
        return wellnessLectureReviewEntityList.stream().map(WellnessLectureReviewDto::from).toList();
    }

    @Override
    public List<WellnessLectureReviewDto> getAllByWellnessClassIdList(List<Long> wellnessClassIdList) {
        final List<WellnessLectureReviewEntity> wellnessLectureReviewEntityList = wellnessLectureReviewRepository
                .findAllByWellnessClassIdIn(wellnessClassIdList);
        return wellnessLectureReviewEntityList.stream().map(WellnessLectureReviewDto::from).toList();
    }

    @Override
    public List<WellnessLectureReviewDto> getAllByTeacherIdList(List<Long> teacherIdList) {
        final List<WellnessLectureReviewEntity> wellnessLectureReviewEntityList = wellnessLectureReviewRepository
                .findAllByTeacherIdIn(teacherIdList);
        return wellnessLectureReviewEntityList.stream().map(WellnessLectureReviewDto::from).toList();
    }

    @Override
    public List<WellnessLectureReviewDto> getAllByMemberIdAndWellnessLectureIdList(Long memberId,
            List<Long> wellnessLectureIdList) {
        final List<WellnessLectureReviewEntity> wellnessLectureReviewEntityList = wellnessLectureReviewRepository
                .findAllByMemberIdAndWellnessLectureIdIn(memberId, wellnessLectureIdList);
        return wellnessLectureReviewEntityList.stream().map(WellnessLectureReviewDto::from).toList();
    }

    @Override
    public WellnessLectureReviewDto getByMemberIdAndWellnessLectureId(Long memberId, Long wellnessLectureId) {
        return wellnessLectureReviewRepository.findByMemberIdAndWellnessLectureId(memberId, wellnessLectureId)
                .map(WellnessLectureReviewDto::from)
                .orElse(null);
    }

    @Override
    public WellnessLectureReviewDto create(WellnessLectureReviewDto wellnessLectureReviewDto) {
        final WellnessLectureReviewEntity wellnessLectureReviewEntity = WellnessLectureReviewEntity.create(
                wellnessLectureReviewDto.getRating(),
                wellnessLectureReviewDto.getWellnessLectureId(),
                wellnessLectureReviewDto.getWellnessClassId(),
                wellnessLectureReviewDto.getMemberId(),
                wellnessLectureReviewDto.getTeacherId(),
                wellnessLectureReviewDto.getCenterId(),
                wellnessLectureReviewDto.getContent(),
                wellnessLectureReviewDto.getIsPrivate(),
                wellnessLectureReviewDto.getIsDelete());
        return WellnessLectureReviewDto.from(wellnessLectureReviewRepository.save(wellnessLectureReviewEntity));
    }

    @Override
    public WellnessLectureReviewDto getById(Long id) {
        return wellnessLectureReviewRepository.findById(id)
                .map(WellnessLectureReviewDto::from)
                .orElse(null);
    }

    @Override
    public void update(WellnessLectureReviewDto wellnessLectureReviewDto) {
        wellnessLectureReviewRepository.findById(wellnessLectureReviewDto.getId())
                .map(wellnessLectureReviewEntity -> {
                    wellnessLectureReviewEntity.update(
                            wellnessLectureReviewDto.getContent(),
                            wellnessLectureReviewDto.getRating(),
                            wellnessLectureReviewDto.getIsPrivate());
                    return wellnessLectureReviewRepository.save(wellnessLectureReviewEntity);
                })
                .orElseThrow(
                        () -> new NotFoundException(WellnessLectureEntity.class, wellnessLectureReviewDto.getId()));
    }

    @Override
    public List<WellnessLectureReviewDto> getAllByWellnessClassId(Long wellnessClassId) {
        final List<WellnessLectureReviewEntity> wellnessLectureReviewEntityList = wellnessLectureReviewRepository
                .findAllByWellnessClassId(wellnessClassId);
        return wellnessLectureReviewEntityList.stream().map(WellnessLectureReviewDto::from).toList();
    }

    @Override
    public List<WellnessLectureReviewDto> getAllByMemberIdList(List<Long> memberIdList) {
        final List<WellnessLectureReviewEntity> wellnessLectureReviewEntityList = wellnessLectureReviewRepository
                .findAllByMemberIdIn(memberIdList);
        return wellnessLectureReviewEntityList.stream().map(WellnessLectureReviewDto::from).toList();
    }

    @Override
    public List<WellnessLectureReviewDto> getAllByCenterId(Long centerId) {
        final List<WellnessLectureReviewEntity> wellnessLectureReviewEntityList = wellnessLectureReviewRepository
                .findAllByCenterId(centerId);
        return wellnessLectureReviewEntityList.stream().map(WellnessLectureReviewDto::from).toList();
    }

    @Override
    public List<WellnessLectureReviewDto> getAllByTeacherId(Long teacherId) {
        final List<WellnessLectureReviewEntity> wellnessLectureReviewEntityList = wellnessLectureReviewRepository
                .findAllByTeacherId(teacherId);
        return wellnessLectureReviewEntityList.stream().map(WellnessLectureReviewDto::from).toList();
    }

    @Override
    public List<WellnessLectureReviewDto> getAllByCenterIdList(List<Long> centerIdList) {
        final List<WellnessLectureReviewEntity> wellnessLectureReviewEntityList = wellnessLectureReviewRepository
                .findAllByCenterIdIn(centerIdList);
        return wellnessLectureReviewEntityList.stream().map(WellnessLectureReviewDto::from).toList();
    }

    @Override
    public long countByMemberId(Long memberId) {
        return wellnessLectureReviewRepository.countByMemberId(memberId);
    }
}
