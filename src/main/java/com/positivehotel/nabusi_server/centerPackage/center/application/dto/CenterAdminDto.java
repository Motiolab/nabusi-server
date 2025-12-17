package com.positivehotel.nabusi_server.centerPackage.center.application.dto;

import com.positivehotel.nabusi_server.role.application.dto.RoleDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CenterAdminDto {
    CenterDto centerDto;
    RoleDto myRoleDto;
    List<CenterRoomDto> centerRoomDtoList;
    List<CenterOpenInfoDto> centerOpenInfoDtoList;
    List<CenterContactNumberDto> centerContactNumberDtoList;
}
