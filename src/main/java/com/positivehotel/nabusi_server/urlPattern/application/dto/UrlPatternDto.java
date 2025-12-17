package com.positivehotel.nabusi_server.urlPattern.application.dto;

import com.positivehotel.nabusi_server.urlPattern.domain.UrlPatternEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UrlPatternDto {
    private Long id;
    private String actionName;
    private String url;
    private String method;
    private String description;
    private Long centerId;

    public static UrlPatternDto create(String actionName, String url, String method, String description, Long centerId){
        return UrlPatternDto.builder()
                .actionName(actionName)
                .url(url)
                .method(method)
                .description(description)
                .centerId(centerId)
                .build();
    }

    public static UrlPatternDto from(UrlPatternEntity urlPatternEntity) {
        return UrlPatternDto.builder()
                .id(urlPatternEntity.getId())
                .actionName(urlPatternEntity.getActionName())
                .url(urlPatternEntity.getUrl())
                .method(urlPatternEntity.getMethod())
                .description(urlPatternEntity.getDescription())
                .centerId(urlPatternEntity.getCenterId())
                .build();
    }
}
