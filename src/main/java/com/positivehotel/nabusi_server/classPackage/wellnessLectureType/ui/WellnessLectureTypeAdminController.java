package com.positivehotel.nabusi_server.classPackage.wellnessLectureType.ui;

import com.positivehotel.nabusi_server.classPackage.wellnessLectureType.application.WellnessLectureTypeAdminService;
import com.positivehotel.nabusi_server.classPackage.wellnessLectureType.application.dto.WellnessLectureTypeAdminDto;
import com.positivehotel.nabusi_server.classPackage.wellnessLectureType.application.dto.request.CreateWellnessLectureTypeByCenterIdAdminRequestV1;
import com.positivehotel.nabusi_server.classPackage.wellnessLectureType.application.dto.response.GetWellnessLectureTypeNameListByCenterIdAdminResponseV1;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class WellnessLectureTypeAdminController {

    private final WellnessLectureTypeAdminService wellnessLectureTypeAdminService;

    @GetMapping("/v1/admin/wellness-lecture-type/name/{centerId}")
    public ResponseEntity<List<GetWellnessLectureTypeNameListByCenterIdAdminResponseV1>> getWellnessLectureTypeNameListByCenterId(@PathVariable Long centerId) {
        final List<WellnessLectureTypeAdminDto> wellnessLectureTypeAdminDtoList = wellnessLectureTypeAdminService.getWellnessLectureTypeNameListByCenterId(centerId);

        final List<GetWellnessLectureTypeNameListByCenterIdAdminResponseV1> wellnessLectureTypeNameListByCenterIdAdminResponseV1 =
                wellnessLectureTypeAdminDtoList.stream()
                        .map(wellnessLectureTypeAdminDto -> new GetWellnessLectureTypeNameListByCenterIdAdminResponseV1(
                                wellnessLectureTypeAdminDto.wellnessLectureTypeDto().id(),
                                wellnessLectureTypeAdminDto.wellnessLectureTypeDto().name(),
                                wellnessLectureTypeAdminDto.wellnessLectureTypeDto().description()
                        ))
                        .toList();

        return ResponseEntity.ok(wellnessLectureTypeNameListByCenterIdAdminResponseV1);
    }

    @PostMapping("/v1/admin/wellness-lecture-type/{centerId}")
    public ResponseEntity<Boolean> createWellnessLectureTypeByCenterId(@PathVariable Long centerId, @RequestBody CreateWellnessLectureTypeByCenterIdAdminRequestV1 createWellnessLectureTypeByCenterIdAdminRequestV1) {
        wellnessLectureTypeAdminService.createWellnessLectureTypeByCenterId(centerId, createWellnessLectureTypeByCenterIdAdminRequestV1);
        return ResponseEntity.ok(true);
    }
}
