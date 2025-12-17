package com.positivehotel.nabusi_server.urlPattern.application;

import com.positivehotel.nabusi_server.urlPattern.application.dto.UrlPatternDto;
import com.positivehotel.nabusi_server.urlPattern.domain.UrlPatternEntity;
import com.positivehotel.nabusi_server.urlPattern.domain.UrlPatternRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UrlPatternServiceImpl implements UrlPatternService{
    private final UrlPatternRepository urlPatternRepository;

    @Override
    public List<UrlPatternDto> postAll(List<UrlPatternDto> urlPatternDtoList) {
        final List<UrlPatternEntity> urlPatternEntityList = urlPatternDtoList.stream()
                .map(urlPatternDto -> UrlPatternEntity.create(
                        urlPatternDto.getId(),
                        urlPatternDto.getActionName(),
                        urlPatternDto.getUrl(),
                        urlPatternDto.getMethod(),
                        urlPatternDto.getDescription(),
                        urlPatternDto.getCenterId()
                )).toList();
         List<UrlPatternEntity> savedUrlPatternEntityList = urlPatternRepository.saveAll(urlPatternEntityList);
        return savedUrlPatternEntityList.stream().map(UrlPatternDto::from).toList();

    }

    @Override
    public List<UrlPatternDto> getAllByCenterIdAndActionName(Long centerId, String actionName) {
        List<UrlPatternEntity> urlPatternEntityList = urlPatternRepository.findAllByCenterIdAndActionName(centerId, actionName);
        return urlPatternEntityList.stream().map(UrlPatternDto::from).toList();
    }

    @Override
    public List<UrlPatternDto> getAllByCenterId(Long centerId) {
        List<UrlPatternEntity> urlPatternEntityList = urlPatternRepository.findAllByCenterId(centerId);
        return urlPatternEntityList.stream().map(UrlPatternDto::from).toList();
    }
}
