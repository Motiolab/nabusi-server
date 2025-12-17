package com.positivehotel.nabusi_server.centerPackage.center.application;

import com.positivehotel.nabusi_server.centerPackage.center.application.dto.CenterOpenInfoDto;
import com.positivehotel.nabusi_server.centerPackage.center.domain.CenterOpenInfoEntity;
import com.positivehotel.nabusi_server.centerPackage.center.domain.CenterOpenInfoRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CenterOpenInfoServiceImpl implements CenterOpenInfoService{

    private static final Logger log = LoggerFactory.getLogger(CenterOpenInfoServiceImpl.class);
    private final CenterOpenInfoRepository centerOpenInfoRepository;

    @Override
    public List<CenterOpenInfoDto> getAllByCenterId(Long centerId) {
        final List<CenterOpenInfoEntity> centerOpenInfoEntityList = centerOpenInfoRepository.findByCenterId(centerId);

        return centerOpenInfoEntityList.stream()
                .map(CenterOpenInfoDto::from)
                .toList();
    }

    @Override
    public void deleteByCenterId(Long centerId) {
        Integer deletedCount = centerOpenInfoRepository.deleteByCenterId(centerId);
        log.info("deletedCount: {}", deletedCount);
    }

    @Override
    public Boolean saveAll(List<CenterOpenInfoDto> centerOpenInfoDtoList) {
        List<CenterOpenInfoEntity> centerOpenInfoEntityList = centerOpenInfoDtoList.stream()
                .map(centerOpenInfoDto -> CenterOpenInfoEntity.create(
                        centerOpenInfoDto.getCenterId(),
                        centerOpenInfoDto.getCloseTime(),
                        centerOpenInfoDto.getDay(),
                        centerOpenInfoDto.getIsDayOff(),
                        centerOpenInfoDto.getOpenTime()
                ))
                .toList();
        centerOpenInfoRepository.saveAll(centerOpenInfoEntityList);
        return true;
    }
}
