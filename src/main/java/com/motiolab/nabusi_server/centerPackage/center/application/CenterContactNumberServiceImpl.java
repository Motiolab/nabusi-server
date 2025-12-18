package com.motiolab.nabusi_server.centerPackage.center.application;

import com.motiolab.nabusi_server.centerPackage.center.application.dto.CenterContactNumberDto;
import com.motiolab.nabusi_server.centerPackage.center.domain.CenterContactNumberEntity;
import com.motiolab.nabusi_server.centerPackage.center.domain.CenterContactNumberRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CenterContactNumberServiceImpl implements CenterContactNumberService{
    private static final Logger log = LoggerFactory.getLogger(CenterContactNumberServiceImpl.class);
    private final CenterContactNumberRepository centerContactNumberRepository;

    @Override
    public List<CenterContactNumberDto> getAllByCenterId(Long centerId) {
        final List<CenterContactNumberEntity> centerContactNumberEntityList = centerContactNumberRepository.findByCenterId(centerId);

        return centerContactNumberEntityList.stream()
                .map(CenterContactNumberDto::from)
                .toList();
    }

    @Override
    public void deleteByCenterId(Long centerId) {
        Integer deletedCount = centerContactNumberRepository.deleteByCenterId(centerId);
        log.info("deletedCount: {}", deletedCount);
    }

    @Override
    public Boolean saveAll(List<CenterContactNumberDto> centerContactNumberDtoList) {
        final List<CenterContactNumberEntity> centerContactNumberEntityList = centerContactNumberDtoList.stream()
                .map(centerContactNumberDto -> CenterContactNumberEntity.create(
                        centerContactNumberDto.getCenterId(),
                        centerContactNumberDto.getContactType(),
                        centerContactNumberDto.getNumber()
                ))
                .toList();
        centerContactNumberRepository.saveAll(centerContactNumberEntityList);
        return true;
    }
}
