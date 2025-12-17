package com.positivehotel.nabusi_server.centerPackage.centerNotice.application;

import com.positivehotel.nabusi_server.centerPackage.centerNotice.application.dto.CenterNoticeDto;
import com.positivehotel.nabusi_server.centerPackage.centerNotice.domain.CenterNoticeEntity;
import com.positivehotel.nabusi_server.centerPackage.centerNotice.domain.CenterNoticeRepository;
import com.positivehotel.nabusi_server.exception.customException.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CenterNoticeServiceImpl implements CenterNoticeService{
    private final CenterNoticeRepository centerNoticeRepository;

    @Override
    public void create(CenterNoticeDto centerNoticeDto) {
        final CenterNoticeEntity centerNoticeEntity = CenterNoticeEntity.create(
                centerNoticeDto.getCenterId(),
                centerNoticeDto.getRegisterId(),
                centerNoticeDto.getTitle(),
                centerNoticeDto.getContent(),
                centerNoticeDto.getIsPopup(),
                centerNoticeDto.getIsDelete()
        );

        centerNoticeRepository.save(centerNoticeEntity);
    }

    @Override
    public List<CenterNoticeDto> getAllByCenterId(Long centerId) {
        final List<CenterNoticeEntity> centerNoticeEntityList = centerNoticeRepository.findAllByCenterId(centerId);
        return centerNoticeEntityList
                .stream()
                .map(CenterNoticeDto::from)
                .toList();
    }

    @Override
    public CenterNoticeDto getById(Long id) {
        return centerNoticeRepository.findById(id)
                .map(CenterNoticeDto::from)
                .orElse(null);
    }

    @Override
    public void update(CenterNoticeDto centerNoticeDto) {
        final CenterNoticeEntity centerNoticeEntity = centerNoticeRepository.findById(centerNoticeDto.getId())
                .orElseThrow(() -> new NotFoundException(CenterNoticeEntity.class, centerNoticeDto.getId()));

        centerNoticeEntity.put(
                centerNoticeDto.getCenterId(),
                centerNoticeDto.getRegisterId(),
                centerNoticeDto.getTitle(),
                centerNoticeDto.getContent(),
                centerNoticeDto.getIsPopup(),
                centerNoticeDto.getIsDelete()
        );

        centerNoticeRepository.save(centerNoticeEntity);
    }
}
