package com.motiolab.nabusi_server.centerPackage.centerNotice.ui;

import com.motiolab.nabusi_server.argumentResolver.MemberId;
import com.motiolab.nabusi_server.centerPackage.centerNotice.application.CenterNoticeAdminService;
import com.motiolab.nabusi_server.centerPackage.centerNotice.application.dto.CenterNoticeAdminDto;
import com.motiolab.nabusi_server.centerPackage.centerNotice.application.dto.request.CreateCenterNoticeByCenterIdAdminRequestV1;
import com.motiolab.nabusi_server.centerPackage.centerNotice.application.dto.request.UpdateCenterNoticeByIdAdminRequestV1;
import com.motiolab.nabusi_server.centerPackage.centerNotice.application.dto.response.GetCenterNoticeDetailByIdAdminResponseV1;
import com.motiolab.nabusi_server.centerPackage.centerNotice.application.dto.response.GetCenterNoticeListByCenterIdAdminResponseV1;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CenterNoticeAdminController {
    private final CenterNoticeAdminService centerNoticeAdminService;

    @PostMapping("/v1/admin/center/notice/{centerId}")
    public ResponseEntity<Boolean> createCenterNoticeByCenterId(@MemberId Long memberId, @PathVariable Long centerId, @RequestBody CreateCenterNoticeByCenterIdAdminRequestV1 createCenterNoticeByCenterIdAdminRequestV1){
        createCenterNoticeByCenterIdAdminRequestV1.setRegisterId(memberId);
        createCenterNoticeByCenterIdAdminRequestV1.setCenterId(centerId);
        centerNoticeAdminService.createCenterNoticeByCenterId(createCenterNoticeByCenterIdAdminRequestV1);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/v1/admin/center/notice/list/{centerId}")
    public ResponseEntity<List<GetCenterNoticeListByCenterIdAdminResponseV1>> getCenterNoticeListByCenterId(@PathVariable Long centerId){
        final List<CenterNoticeAdminDto> centerNoticeAdminDtoList = centerNoticeAdminService.getCenterNoticeListByCenterId(centerId);
        final List<GetCenterNoticeListByCenterIdAdminResponseV1> response = centerNoticeAdminDtoList
                .stream()
                .map(centerNoticeAdminDto -> GetCenterNoticeListByCenterIdAdminResponseV1.builder()
                        .id(centerNoticeAdminDto.getCenterNoticeDto().getId())
                        .title(centerNoticeAdminDto.getCenterNoticeDto().getTitle())
                        .content(centerNoticeAdminDto.getCenterNoticeDto().getContent())
                        .isPopup(centerNoticeAdminDto.getCenterNoticeDto().getIsPopup())
                        .registerId(centerNoticeAdminDto.getCenterNoticeDto().getRegisterId())
                        .registerName(centerNoticeAdminDto.getMemberDto().getName())
                        .isDelete(centerNoticeAdminDto.getCenterNoticeDto().getIsDelete())
                        .createdDate(centerNoticeAdminDto.getCenterNoticeDto().getCreatedDate())
                        .build())
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/v1/admin/center/notice/detail/{centerId}")
    public ResponseEntity<GetCenterNoticeDetailByIdAdminResponseV1> getCenterNoticeDetailById(@PathVariable Long centerId, @RequestParam(defaultValue = "id") Long id){
        final CenterNoticeAdminDto centerNoticeAdminDto = centerNoticeAdminService.getCenterNoticeDetailById(id);

        final GetCenterNoticeDetailByIdAdminResponseV1 response = GetCenterNoticeDetailByIdAdminResponseV1.builder()
                .id(centerNoticeAdminDto.getCenterNoticeDto().getId())
                .title(centerNoticeAdminDto.getCenterNoticeDto().getTitle())
                .content(centerNoticeAdminDto.getCenterNoticeDto().getContent())
                .isPopup(centerNoticeAdminDto.getCenterNoticeDto().getIsPopup())
                .isDelete(centerNoticeAdminDto.getCenterNoticeDto().getIsDelete())
                .createdDate(centerNoticeAdminDto.getCenterNoticeDto().getCreatedDate())
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/v1/admin/center/notice/update/{centerId}")
    public ResponseEntity<Boolean> updateCenterNoticeById(@MemberId Long memberId, @PathVariable Long centerId, @RequestBody UpdateCenterNoticeByIdAdminRequestV1 updateCenterNoticeByIdAdminRequestV1){
        updateCenterNoticeByIdAdminRequestV1.setRegisterId(memberId);
        updateCenterNoticeByIdAdminRequestV1.setCenterId(centerId);
        centerNoticeAdminService.updateCenterNoticeById(updateCenterNoticeByIdAdminRequestV1);
        return ResponseEntity.ok(true);
    }
}
