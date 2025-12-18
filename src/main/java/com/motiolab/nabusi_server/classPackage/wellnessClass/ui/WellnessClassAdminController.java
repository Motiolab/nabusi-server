package com.motiolab.nabusi_server.classPackage.wellnessClass.ui;

import com.motiolab.nabusi_server.argumentResolver.MemberId;
import com.motiolab.nabusi_server.classPackage.wellnessClass.application.WellnessClassAdminService;
import com.motiolab.nabusi_server.classPackage.wellnessClass.application.dto.WellnessClassAdminDto;
import com.motiolab.nabusi_server.classPackage.wellnessClass.application.dto.request.CreateWellnessClassByCenterIdAdminRequestV1;
import com.motiolab.nabusi_server.classPackage.wellnessClass.application.dto.response.GetWellnessClassDetailAdminResponseV1;
import com.motiolab.nabusi_server.classPackage.wellnessClass.application.dto.response.GetWellnessClassNameByCenterIdAdminResponseV1;
import com.motiolab.nabusi_server.teacher.application.dto.TeacherDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class WellnessClassAdminController {
    private final WellnessClassAdminService wellnessClassAdminService;

    @GetMapping("/v1/admin/wellness-class/name/{centerId}")
    public ResponseEntity<List<GetWellnessClassNameByCenterIdAdminResponseV1>> getWellnessClassNameListByCenterId(@PathVariable Long centerId) {
        final List<WellnessClassAdminDto> wellnessClassAdminDtoList = wellnessClassAdminService.getWellnessClassNameListByCenterId(centerId);

        final List<GetWellnessClassNameByCenterIdAdminResponseV1> getWellnessClassByCenterIdAdminResponseV1List = wellnessClassAdminDtoList.stream()
                .map(wellnessClassAdminDto -> {
                    final String teacherName = Optional.ofNullable(wellnessClassAdminDto.getTeacherDto())
                            .map(TeacherDto::getName)
                            .orElse("");

                    return GetWellnessClassNameByCenterIdAdminResponseV1.builder()
                            .id(wellnessClassAdminDto.getWellnessClassDto().getId())
                            .name(wellnessClassAdminDto.getWellnessClassDto().getName())
                            .teacherName(teacherName)
                            .build();
                })
                .toList();

        return ResponseEntity.ok(getWellnessClassByCenterIdAdminResponseV1List);
    }

    @PostMapping("/v1/admin/wellness-class/{centerId}")
    public ResponseEntity<Boolean> createWellnessClassByCenterId(@MemberId Long memberId, @PathVariable Long centerId, @RequestBody CreateWellnessClassByCenterIdAdminRequestV1 createWellnessClassByCenterIdAdminRequestV1) {
        wellnessClassAdminService.createWellnessClassByCenterIdAndName(memberId, centerId, createWellnessClassByCenterIdAdminRequestV1.wellnessClassName());
        return ResponseEntity.ok(true);
    }

    @GetMapping("/v1/admin/wellness-class/detail/{centerId}")
    public ResponseEntity<GetWellnessClassDetailAdminResponseV1> getWellnessClassDetailByCenterId(@PathVariable Long centerId, @RequestParam(defaultValue = "id") Long id) {
        final WellnessClassAdminDto wellnessClassAdminDto = wellnessClassAdminService.getByIdAndCenterId(id, centerId);
        final GetWellnessClassDetailAdminResponseV1 getWellnessClassDetailAdminResponseV1 = new GetWellnessClassDetailAdminResponseV1(
                wellnessClassAdminDto.getWellnessClassDto().getId(),
                wellnessClassAdminDto.getWellnessClassDto().getName(),
                wellnessClassAdminDto.getWellnessClassDto().getDescription(),
                wellnessClassAdminDto.getWellnessClassDto().getCenterId(),
                wellnessClassAdminDto.getWellnessClassDto().getMaxReservationCnt(),
                wellnessClassAdminDto.getWellnessClassDto().getRegisterId(),
                wellnessClassAdminDto.getWellnessClassDto().getRoom(),
                wellnessClassAdminDto.getWellnessClassDto().getClassImageUrlList(),
                wellnessClassAdminDto.getWellnessClassDto().getTeacherId(),
                wellnessClassAdminDto.getWellnessClassDto().getWellnessLectureTypeId(),
                wellnessClassAdminDto.getWellnessClassDto().getWellnessTicketManagementIdList()
        );

        return ResponseEntity.ok(getWellnessClassDetailAdminResponseV1);
    }
}
