package com.motiolab.nabusi_server.role.application.dto.response;

import com.motiolab.nabusi_server.role.application.dto.RoleAdminDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class GetRoleAndUrlPatternAdminResponseV1 {
    private Long id;
    private String name;
    private String description;
    private Long centerId;
    private List<UrlPattern> urlPatternList;

    @Builder
    @Getter
    public static class UrlPattern{
        private Long id;
        private String actionName;
        private String url;
        private String method;
        private String description;
    }

    public static GetRoleAndUrlPatternAdminResponseV1 from(RoleAdminDto roleAdminDto) {
        final List<GetRoleAndUrlPatternAdminResponseV1.UrlPattern> urlPatternDtoList = roleAdminDto.getRoleDto().getUrlPatternDtoList().stream()
                .map(urlPatternDto -> GetRoleAndUrlPatternAdminResponseV1.UrlPattern.builder()
                        .id(urlPatternDto.getId())
                        .actionName(urlPatternDto.getActionName())
                        .url(urlPatternDto.getUrl())
                        .method(urlPatternDto.getMethod())
                        .description(urlPatternDto.getDescription())
                        .build())
                .toList();

        return GetRoleAndUrlPatternAdminResponseV1.builder()
                .id(roleAdminDto.getRoleDto().getId())
                .name(roleAdminDto.getRoleDto().getName())
                .description(roleAdminDto.getRoleDto().getDescription())
                .centerId(roleAdminDto.getRoleDto().getCenterId())
                .urlPatternList(urlPatternDtoList)
                .build();
    }
}
