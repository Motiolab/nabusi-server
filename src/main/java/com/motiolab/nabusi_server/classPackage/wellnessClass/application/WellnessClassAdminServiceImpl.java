package com.motiolab.nabusi_server.classPackage.wellnessClass.application;

import com.motiolab.nabusi_server.classPackage.wellnessClass.application.dto.WellnessClassAdminDto;
import com.motiolab.nabusi_server.classPackage.wellnessClass.application.dto.WellnessClassDto;
import com.motiolab.nabusi_server.exception.customException.ExistsAlreadyException;
import com.motiolab.nabusi_server.exception.customException.NotFoundException;
import com.motiolab.nabusi_server.teacher.application.TeacherService;
import com.motiolab.nabusi_server.teacher.application.dto.TeacherDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class WellnessClassAdminServiceImpl implements WellnessClassAdminService{
    private final WellnessClassService wellnessClassService;
    private final TeacherService teacherService;

    @Override
    public List<WellnessClassAdminDto> getWellnessClassNameListByCenterId(Long centerId) {
        final List<WellnessClassDto> wellnessClassDtoList = wellnessClassService.getAllByCenterId(centerId);
        final List<Long> teacherIdList = wellnessClassDtoList.stream().map(WellnessClassDto::getTeacherId).distinct().toList();
        final List<TeacherDto> teacherDtoList = teacherService.getAllByIdList(teacherIdList);

        return wellnessClassDtoList.stream()
                .map(wellnessClassDto -> {
                    TeacherDto targetTeacherDto  = teacherDtoList.stream()
                            .filter(teacherDto -> teacherDto.getId().equals(wellnessClassDto.getTeacherId()))
                            .findFirst()
                            .orElse(null);

                    return WellnessClassAdminDto.builder()
                            .wellnessClassDto(wellnessClassDto)
                            .teacherDto(targetTeacherDto)
                            .build();
                }).toList();
    }

    @Override
    public void createWellnessClassByCenterIdAndName(Long registerId, Long centerId, String wellnessClassName) {
         final WellnessClassDto existsWellnessClassDto = wellnessClassService.getByCenterIdAndName(centerId, wellnessClassName);
         if(existsWellnessClassDto != null) throw new ExistsAlreadyException(WellnessClassDto.class, existsWellnessClassDto.getId());

         final WellnessClassDto newWellnessClassDto = WellnessClassDto.builder()
                 .name(wellnessClassName)
                 .centerId(centerId)
                 .registerId(registerId)
                 .isDelete(false)
                 .build();

         wellnessClassService.create(newWellnessClassDto);
    }

    @Override
    public WellnessClassAdminDto getByIdAndCenterId(Long id, Long centerId) {
        final WellnessClassDto wellnessClassDto = wellnessClassService.getByIdAndCenterId(id, centerId);
        if(wellnessClassDto == null) throw new NotFoundException(WellnessClassDto.class, id);

        return WellnessClassAdminDto.builder()
                .wellnessClassDto(wellnessClassDto)
                .build();
    }
}
