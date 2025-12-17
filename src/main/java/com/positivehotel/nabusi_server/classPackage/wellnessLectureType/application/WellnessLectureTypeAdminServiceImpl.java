package com.positivehotel.nabusi_server.classPackage.wellnessLectureType.application;

import com.positivehotel.nabusi_server.classPackage.wellnessLectureType.application.dto.WellnessLectureTypeAdminDto;
import com.positivehotel.nabusi_server.classPackage.wellnessLectureType.application.dto.WellnessLectureTypeDto;
import com.positivehotel.nabusi_server.classPackage.wellnessLectureType.application.dto.request.CreateWellnessLectureTypeByCenterIdAdminRequestV1;
import com.positivehotel.nabusi_server.exception.customException.ExistsAlreadyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class WellnessLectureTypeAdminServiceImpl implements WellnessLectureTypeAdminService{
    private final WellnessLectureTypeService wellnessLectureTypeService;

    @Override
    public List<WellnessLectureTypeAdminDto> getWellnessLectureTypeNameListByCenterId(Long centerId) {
        List<WellnessLectureTypeDto> wellnessLectureTypeDtoList = wellnessLectureTypeService.getAllByCenterId(centerId);
        return wellnessLectureTypeDtoList.stream()
                .map(WellnessLectureTypeAdminDto::new)
                .toList();
    }

    @Override
    public void createWellnessLectureTypeByCenterId(Long centerId, CreateWellnessLectureTypeByCenterIdAdminRequestV1 createWellnessLectureTypeByCenterIdAdminRequestV1) {
        final WellnessLectureTypeDto existswellnessLectureTypeDto = wellnessLectureTypeService.getByCenterIdAndName(centerId, createWellnessLectureTypeByCenterIdAdminRequestV1.name());
        if(existswellnessLectureTypeDto != null) throw new ExistsAlreadyException(WellnessLectureTypeDto.class, existswellnessLectureTypeDto.id());

        final WellnessLectureTypeDto wellnessLectureTypeDto = new WellnessLectureTypeDto(
                null,
                createWellnessLectureTypeByCenterIdAdminRequestV1.name(),
                createWellnessLectureTypeByCenterIdAdminRequestV1.description(),
                centerId
        );

        wellnessLectureTypeService.create(wellnessLectureTypeDto);
    }
}
