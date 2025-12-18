package com.motiolab.nabusi_server.classPackage.wellnessClass.application;

import com.motiolab.nabusi_server.classPackage.wellnessClass.application.dto.WellnessClassDto;
import com.motiolab.nabusi_server.classPackage.wellnessClass.domain.WellnessClassEntity;
import com.motiolab.nabusi_server.classPackage.wellnessClass.domain.WellnessClassRepository;
import com.motiolab.nabusi_server.classPackage.wellnessLecture.domain.WellnessLectureEntity;
import com.motiolab.nabusi_server.exception.customException.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WellnessClassServiceImpl implements WellnessClassService{
    private final WellnessClassRepository wellnessClassRepository;

    @Override
    public List<WellnessClassDto> getAllByCenterId(Long centerId) {
        final List<WellnessClassEntity> wellnessClassEntityList = wellnessClassRepository.findAllByCenterId(centerId);

        return wellnessClassEntityList.stream()
                .map(WellnessClassDto::from)
                .toList();
    }

    @Override
    public WellnessClassDto getByCenterIdAndName(Long centerId, String name) {
        return wellnessClassRepository.findByCenterIdAndName(centerId, name)
                .map(WellnessClassDto::from)
                .orElse(null);
    }

    @Override
    public void create(WellnessClassDto wellnessClassDto) {
        WellnessClassEntity newWellnessClassEntity = WellnessClassEntity.create(
                wellnessClassDto.getName(),
                wellnessClassDto.getDescription(),
                wellnessClassDto.getCenterId(),
                wellnessClassDto.getMaxReservationCnt(),
                wellnessClassDto.getRegisterId(),
                wellnessClassDto.getRoom(),
                wellnessClassDto.getClassImageUrlList(),
                wellnessClassDto.getTeacherId(),
                wellnessClassDto.getWellnessLectureTypeId(),
                wellnessClassDto.getIsDelete(),
                wellnessClassDto.getWellnessTicketManagementIdList());

        wellnessClassRepository.save(newWellnessClassEntity);
    }

    @Override
    public WellnessClassDto getByIdAndCenterId(Long id, Long centerId) {
        return wellnessClassRepository.findByIdAndCenterId(id, centerId)
                .map(WellnessClassDto::from)
                .orElse(null);
    }

    @Transactional
    @Override
    public Boolean update(WellnessClassDto wellnessClassDto) {
        return wellnessClassRepository.findById(wellnessClassDto.getId())
                .map(wellnessClassEntity -> {
                    wellnessClassEntity.update(
                            wellnessClassDto.getDescription(),
                            wellnessClassDto.getCenterId(),
                            wellnessClassDto.getMaxReservationCnt(),
                            wellnessClassDto.getRegisterId(),
                            wellnessClassDto.getRoom(),
                            wellnessClassDto.getClassImageUrlList(),
                            wellnessClassDto.getTeacherId(),
                            wellnessClassDto.getWellnessLectureTypeId(),
                            wellnessClassDto.getIsDelete(),
                            wellnessClassDto.getWellnessTicketManagementIdList()
                    );
                    return true;
                })
                .orElseThrow(() -> new NotFoundException(WellnessLectureEntity.class, wellnessClassDto.getId()));
    }

    @Override
    public List<WellnessClassDto> getAllById(List<Long> idList) {
         List<WellnessClassEntity> wellnessClassEntityList = wellnessClassRepository.findAllById(idList);
         return wellnessClassEntityList.stream()
                 .map(WellnessClassDto::from)
                 .toList();
    }

    @Override
    public List<WellnessClassDto> getAllByWellnessTicketManagementIdListIn(List<Long> wellnessTicketManagementIdList) {
        final String wellnessTicketManagementIdListString = wellnessTicketManagementIdList.stream().map(String::valueOf).collect(Collectors.joining("|"));
        return wellnessClassRepository.findAllByIssuedWellnessTicketManageIdListFindInSet(wellnessTicketManagementIdListString)
                .stream()
                .map(WellnessClassDto::from)
                .toList();
    }

    @Override
    public WellnessClassDto getById(Long id) {
        return wellnessClassRepository.findById(id)
                 .map(WellnessClassDto::from)
                 .orElse(null);
    }
}
