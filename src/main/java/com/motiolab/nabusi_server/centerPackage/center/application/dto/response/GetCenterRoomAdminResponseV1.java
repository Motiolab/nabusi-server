package com.motiolab.nabusi_server.centerPackage.center.application.dto.response;

public record GetCenterRoomAdminResponseV1(
        Long id,
        String name,
        Integer capacity
) {
}
