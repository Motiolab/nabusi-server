package com.motiolab.nabusi_server.role.application;

import com.motiolab.nabusi_server.role.application.dto.RoleDto;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Optional<RoleDto> getByName(String name);
    RoleDto findAllByCenterIdAndName(Long centerId, String name);
    List<RoleDto> createRoleList(List<RoleDto> roleDtoList);
    void create(RoleDto roleDto);
    List<RoleDto> getAllByCenterId(Long centerId);
    void patch(RoleDto roleDto);
    void updateAll(List<RoleDto> roleDtoList);
    RoleDto getById(Long id);
    void deleteById(Long id);
    RoleDto getByNameAndCenterId(String name, Long centerId);
}
