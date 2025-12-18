package com.motiolab.nabusi_server.role.application.dto;

import com.motiolab.nabusi_server.role.domain.RoleEntity;
import com.motiolab.nabusi_server.urlPattern.application.dto.UrlPatternDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Getter
@Builder
public class RoleDto {
    private Long id;
    private String name;
    private String description;
    private Long centerId;
    @Setter
    private List<UrlPatternDto> urlPatternDtoList;

    public static RoleDto from(RoleEntity roleEntity) {
        final List<UrlPatternDto> urlPatternDtoList = Optional.ofNullable(roleEntity.getUrlPatterns())
                .orElse(Collections.emptyList())
                .stream()
                .map(UrlPatternDto::from)
                .toList();

        return RoleDto.builder()
                .id(roleEntity.getId())
                .name(roleEntity.getName())
                .description(roleEntity.getDescription())
                .centerId(roleEntity.getCenterId())
                .urlPatternDtoList(urlPatternDtoList)
                .build();
    }
}
