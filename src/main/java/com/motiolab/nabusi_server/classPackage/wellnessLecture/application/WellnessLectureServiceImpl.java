package com.motiolab.nabusi_server.classPackage.wellnessLecture.application;

import com.motiolab.nabusi_server.classPackage.wellnessLecture.application.dto.WellnessLectureDto;
import com.motiolab.nabusi_server.classPackage.wellnessLecture.domain.WellnessLectureEntity;
import com.motiolab.nabusi_server.classPackage.wellnessLecture.domain.WellnessLectureRepository;
import com.motiolab.nabusi_server.exception.customException.DeletedAlreadyException;
import com.motiolab.nabusi_server.exception.customException.NotFoundException;
import com.motiolab.nabusi_server.exception.customException.RestoredAlreadyException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WellnessLectureServiceImpl implements WellnessLectureService{

    private final WellnessLectureRepository wellnessLectureRepository;

    @Override
    public void createAll(List<WellnessLectureDto> wellnessLectureDtoList) {
        final List<WellnessLectureEntity> wellnessLectureEntityList =  wellnessLectureDtoList.stream()
                .map(wellnessLectureDto -> WellnessLectureEntity.create(
                        wellnessLectureDto.getName(),
                        wellnessLectureDto.getDescription(),
                        wellnessLectureDto.getCenterId(),
                        wellnessLectureDto.getMaxReservationCnt(),
                        wellnessLectureDto.getRegisterId(),
                        wellnessLectureDto.getRoom(),
                        wellnessLectureDto.getLectureImageUrlList(),
                        wellnessLectureDto.getPrice(),
                        wellnessLectureDto.getWellnessClassId(),
                        wellnessLectureDto.getTeacherId(),
                        wellnessLectureDto.getWellnessLectureTypeId(),
                        wellnessLectureDto.getStartDateTime(),
                        wellnessLectureDto.getEndDateTime(),
                        wellnessLectureDto.getIsDelete(),
                        wellnessLectureDto.getWellnessTicketManagementIdList()
                ))
                .toList();

        wellnessLectureRepository.saveAll(wellnessLectureEntityList);
    }

    @Override
    public List<WellnessLectureDto> getAllByWellnessClassIdList(List<Long> wellnessClassIdList) {
        final List<WellnessLectureEntity> wellnessLectureEntityList = wellnessLectureRepository.findAllByWellnessClassIdIn(wellnessClassIdList);

        return wellnessLectureEntityList.stream().map(WellnessLectureDto::from).toList();
    }

    @Override
    public void update(WellnessLectureDto wellnessLectureDto) {
        wellnessLectureRepository.findById(wellnessLectureDto.getId())
                .map(wellnessLectureEntity -> {
                    wellnessLectureEntity.update(
                            wellnessLectureDto.getName(),
                            wellnessLectureDto.getDescription(),
                            wellnessLectureDto.getCenterId(),
                            wellnessLectureDto.getMaxReservationCnt(),
                            wellnessLectureDto.getRegisterId(),
                            wellnessLectureDto.getRoom(),
                            wellnessLectureDto.getLectureImageUrlList(),
                            wellnessLectureDto.getPrice(),
                            wellnessLectureDto.getWellnessClassId(),
                            wellnessLectureDto.getTeacherId(),
                            wellnessLectureDto.getWellnessLectureTypeId(),
                            wellnessLectureDto.getStartDateTime(),
                            wellnessLectureDto.getEndDateTime(),
                            wellnessLectureDto.getIsDelete(),
                            wellnessLectureDto.getWellnessTicketManagementIdList()
                    );
                    return wellnessLectureRepository.save(wellnessLectureEntity);
                })
                .orElseThrow(() -> new NotFoundException(WellnessLectureEntity.class, wellnessLectureDto.getId()));
    }

    @Override
    public List<WellnessLectureDto> getAllByWellnessTicketManagementIdListIn(List<Long> wellnessTicketManagementIdList) {
        final String wellnessTicketManagementIdListString = wellnessTicketManagementIdList.stream().map(String::valueOf).collect(Collectors.joining("|"));
        return wellnessLectureRepository.findAllByIssuedWellnessTicketManageIdListFindInSet(wellnessTicketManagementIdListString)
                .stream()
                .map(WellnessLectureDto::from)
                .toList();
    }

    @Override
    public List<WellnessLectureDto> getWellnessLectureByDateRange(ZonedDateTime startDateTime, ZonedDateTime endDateTime) {
        return wellnessLectureRepository.findAllByStartDateTimeBetweenOrderByStartDateTimeAsc(startDateTime, endDateTime)
                .stream()
                .map(WellnessLectureDto::from)
                .toList();
    }

    @Override
    public WellnessLectureDto getById(Long id) {
        return wellnessLectureRepository.findById(id)
                .map(WellnessLectureDto::from)
                .orElse(null);
    }

    @Override
    public void delete(@NonNull final Long id) {
        wellnessLectureRepository.findById(id)
                .map(wellnessLectureEntity -> {
                    if (wellnessLectureEntity.getIsDelete()) {
                        throw new DeletedAlreadyException(WellnessLectureEntity.class, id);
                    }
                    wellnessLectureEntity.updateIsDelete(true);
                    return wellnessLectureRepository.save(wellnessLectureEntity);
                })
                .orElseThrow(() -> new NotFoundException(WellnessLectureEntity.class, id));
    }

    @Override
    public void restore(Long id) {
        wellnessLectureRepository.findById(id)
                .map(wellnessLectureEntity -> {
                    if (!wellnessLectureEntity.getIsDelete()) {
                        throw new RestoredAlreadyException(WellnessLectureEntity.class, id);
                    }
                    wellnessLectureEntity.updateIsDelete(false);
                    return wellnessLectureRepository.save(wellnessLectureEntity);
                })
                .orElseThrow(() -> new NotFoundException(WellnessLectureEntity.class, id));
    }

    @Override
    public List<WellnessLectureDto> getAllByTeacherIdList(List<Long> teacherIdList) {
        return wellnessLectureRepository.findAllByTeacherIdIn(teacherIdList)
                .stream()
                .map(WellnessLectureDto::from)
                .toList();
    }

    @Override
    public List<WellnessLectureDto> getAllByIdList(List<Long> idList) {
        return wellnessLectureRepository.findAllByIdIn(idList)
                .stream()
                .map(WellnessLectureDto::from)
                .toList();
    }

    @Override
    public List<WellnessLectureDto> getAllByCenterIdAndStartDateTimeBetweenAndIsDeleteFalse(Long centerId, ZonedDateTime startDateTime, ZonedDateTime endDateTime) {
        final List<WellnessLectureEntity> wellnessLectureEntityList = wellnessLectureRepository.findAllByCenterIdAndStartDateTimeBetweenAndIsDeleteFalseOrderByStartDateTimeAsc(centerId, startDateTime, endDateTime);
        return wellnessLectureEntityList.stream()
                .map(WellnessLectureDto::from)
                .toList();
    }

    @Override
    public List<WellnessLectureDto> getAllByCenterIdAndTeacherIdAndStartDateTimeBetweenAndIsDeleteFalse(Long centerId, Long teacherId, ZonedDateTime startDateTime, ZonedDateTime endDateTime) {
        final List<WellnessLectureEntity> wellnessLectureEntityList = wellnessLectureRepository.findAllByCenterIdAndTeacherIdAndStartDateTimeBetweenAndIsDeleteFalseOrderByStartDateTimeAsc(centerId, teacherId, startDateTime, endDateTime);
        return wellnessLectureEntityList.stream()
                .map(WellnessLectureDto::from)
                .toList();
    }

    @Override
    public List<WellnessLectureDto> getAvailableBookingListByWellnessClassId(Long wellnessClassId) {
        final List<WellnessLectureEntity> wellnessLectureEntityList = wellnessLectureRepository.findAllByWellnessClassIdAndIsDeleteFalseAndStartDateTimeAfterNow(wellnessClassId, ZonedDateTime.now());
        return wellnessLectureEntityList.stream()
                .map(WellnessLectureDto::from)
                .toList();
    }

    @Override
    public List<WellnessLectureDto> getAllByCenterIdAndIsDeleteFalse(Long centerId) {
        final List<WellnessLectureEntity> wellnessLectureEntityList = wellnessLectureRepository.findAllByCenterIdAndIsDeleteFalseOrderByStartDateTimeAsc(centerId);
        return wellnessLectureEntityList.stream()
                .map(WellnessLectureDto::from)
                .toList();
    }
}
