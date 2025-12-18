package com.motiolab.nabusi_server.classPackage.wellnessLectureType.application;

import com.motiolab.nabusi_server.classPackage.wellnessLectureType.application.dto.WellnessLectureTypeDto;
import com.motiolab.nabusi_server.classPackage.wellnessLectureType.domain.WellnessLectureTypeEntity;
import com.motiolab.nabusi_server.classPackage.wellnessLectureType.domain.WellnessLectureTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class WellnessLectureTypeServiceImpl implements WellnessLectureTypeService{
    private final WellnessLectureTypeRepository wellnessLectureTypeRepository;

    @Override
    public List<WellnessLectureTypeDto> getAllByCenterId(Long centerId) {
        return wellnessLectureTypeRepository.findAllByCenterId(centerId)
                .stream()
                .map(WellnessLectureTypeDto::from)
                .toList();
    }

    @Override
    public WellnessLectureTypeDto getByCenterIdAndName(Long centerId, String name) {
        return wellnessLectureTypeRepository.findByCenterIdAndName(centerId, name)
                .map(WellnessLectureTypeDto::from)
                .orElse(null);
    }

    @Override
    public void create(WellnessLectureTypeDto wellnessLectureTypeDto) {
        final WellnessLectureTypeEntity wellnessLectureTypeEntity = WellnessLectureTypeEntity.create(
                wellnessLectureTypeDto.name(),
                wellnessLectureTypeDto.description(),
                wellnessLectureTypeDto.centerId()
        );

        wellnessLectureTypeRepository.save(wellnessLectureTypeEntity);
    }

    @Override
    public List<WellnessLectureTypeDto> getAllByIdList(List<Long> idList) {
        return wellnessLectureTypeRepository.findAllById(idList)
                .stream()
                .map(WellnessLectureTypeDto::from)
                .toList();
    }

    @Override
    public WellnessLectureTypeDto getById(Long id) {
        return wellnessLectureTypeRepository.findById(id)
                .map(WellnessLectureTypeDto::from)
                .orElse(null);
    }
}
