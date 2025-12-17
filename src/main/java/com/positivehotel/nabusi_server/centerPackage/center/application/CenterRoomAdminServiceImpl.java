package com.positivehotel.nabusi_server.centerPackage.center.application;

import com.positivehotel.nabusi_server.centerPackage.center.application.dto.CenterRoomAdminDto;
import com.positivehotel.nabusi_server.centerPackage.center.application.dto.CenterRoomDto;
import com.positivehotel.nabusi_server.exception.customException.ExistsAlreadyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CenterRoomAdminServiceImpl implements CenterRoomAdminService{
    private final CenterRoomService centerRoomService;


    @Override
    public List<CenterRoomAdminDto> getCenterRoomListByCenterId(Long centerId) {
        List<CenterRoomDto> centerRoomDtoList = centerRoomService.getAllByCenterId(centerId);

        return centerRoomDtoList.stream()
                .map(CenterRoomAdminDto::new)
                .toList();
    }

    @Override
    public void createCenterRoomByCenterId(Long centerId, String centerRoomName) {
        final CenterRoomDto existsCenterRoomDto =  centerRoomService.getByCenterIdAndName(centerId, centerRoomName);
        if(existsCenterRoomDto != null){
            throw new ExistsAlreadyException(CenterRoomDto.class, existsCenterRoomDto.getId());
        }

        final CenterRoomDto centerRoomDto = CenterRoomDto.builder()
                .name(centerRoomName)
                .centerId(centerId)
                .build();

        centerRoomService.create(centerRoomDto);
    }
}
