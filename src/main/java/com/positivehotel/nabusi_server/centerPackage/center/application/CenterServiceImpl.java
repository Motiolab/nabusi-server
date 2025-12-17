package com.positivehotel.nabusi_server.centerPackage.center.application;

import com.positivehotel.nabusi_server.centerPackage.center.application.dto.CenterDto;
import com.positivehotel.nabusi_server.centerPackage.center.domain.CenterEntity;
import com.positivehotel.nabusi_server.centerPackage.center.domain.CenterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CenterServiceImpl implements CenterService{
    private final CenterRepository centerRepository;

    @Override
    public List<CenterDto> getAllByMemberId(Long memberId) {
        return centerRepository.findByMemberId(memberId)
                .stream().map(CenterDto::from).toList();
    }

    @Override
    public List<CenterDto> getAllByIsActiveTrue() {
        return centerRepository.findAllByIsActiveTrue()
                .stream().map(CenterDto::from).toList();
    }

    @Override
    public CenterDto postCenter(CenterDto centerDto) {
        final CenterEntity centerEntity = CenterEntity.create(
                centerDto.getName(),
                centerDto.getAddress(),
                centerDto.getCode(),
                centerDto.getDetailAddress(),
                centerDto.getRoadName(),
                false,
                centerDto.getMemberIdList()
        );
        final CenterEntity savedCenter = centerRepository.save(centerEntity);
        return CenterDto.from(savedCenter);
    }

    @Override
    public CenterDto getById(Long id) {
        return centerRepository.findById(id)
                .map(CenterDto::from)
                .orElse(null);
    }

    @Override
    public CenterDto put(CenterDto centerDto) {
        return centerRepository.findById(centerDto.getId())
                .map(centerEntity -> {
                    centerEntity.update(
                            centerDto.getName(),
                            centerDto.getAddress(),
                            centerEntity.getCode(),
                            centerDto.getDetailAddress(),
                            centerDto.getRoadName(),
                            centerDto.getDescription(),
                            centerEntity.getMemberIdList(),
                            centerDto.getImageUrlList()
                    );
                    return CenterDto.from(centerRepository.save(centerEntity));
                })
                .orElseThrow(() -> new IllegalArgumentException("Center not found with id: " + centerDto.getId()));
    }

    @Override
    public List<CenterDto> getAllByIdList(List<Long> idList) {
        return centerRepository.findAllById(idList)
                .stream()
                .map(CenterDto::from)
                .toList();
    }
}

