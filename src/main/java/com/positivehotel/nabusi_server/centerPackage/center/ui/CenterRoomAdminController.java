package com.positivehotel.nabusi_server.centerPackage.center.ui;

import com.positivehotel.nabusi_server.centerPackage.center.application.CenterRoomAdminService;
import com.positivehotel.nabusi_server.centerPackage.center.application.dto.CenterRoomAdminDto;
import com.positivehotel.nabusi_server.centerPackage.center.application.dto.request.CreateCenterRoomAdminRequestV1;
import com.positivehotel.nabusi_server.centerPackage.center.application.dto.response.GetCenterRoomAdminResponseV1;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CenterRoomAdminController {
    private final CenterRoomAdminService centerRoomAdminService;

    @GetMapping("/v1/admin/center/room/list/{centerId}")
    public ResponseEntity<List<GetCenterRoomAdminResponseV1>> getCenterRoomListByCenterId(@PathVariable Long centerId){
        final List<CenterRoomAdminDto> centerRoomAdminDtoList = centerRoomAdminService.getCenterRoomListByCenterId(centerId);

        final List<GetCenterRoomAdminResponseV1> getCenterRoomAdminResponseV1List = centerRoomAdminDtoList.stream()
                .map(centerRoomAdminDto -> new GetCenterRoomAdminResponseV1(
                        centerRoomAdminDto.centerRoomDto().getId(),
                        centerRoomAdminDto.centerRoomDto().getName(),
                        centerRoomAdminDto.centerRoomDto().getCapacity()
                ))
                .toList();

        return ResponseEntity.ok(getCenterRoomAdminResponseV1List);
    }

    @PostMapping("/v1/admin/center/room/{centerId}")
    public ResponseEntity<Boolean> createCenterRoomByCenterId(@PathVariable Long centerId, @RequestBody CreateCenterRoomAdminRequestV1 createCenterRoomAdminRequestV1){
        centerRoomAdminService.createCenterRoomByCenterId(centerId, createCenterRoomAdminRequestV1.centerRoomName());
        return ResponseEntity.ok(true);
    }
}
