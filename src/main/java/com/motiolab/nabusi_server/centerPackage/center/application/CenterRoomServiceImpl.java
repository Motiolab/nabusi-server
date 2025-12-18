package com.motiolab.nabusi_server.centerPackage.center.application;

import com.motiolab.nabusi_server.centerPackage.center.application.dto.CenterRoomDto;
import com.motiolab.nabusi_server.centerPackage.center.domain.CenterRoomEntity;
import com.motiolab.nabusi_server.centerPackage.center.domain.CenterRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CenterRoomServiceImpl implements CenterRoomService{
    private final CenterRoomRepository centerRoomRepository;


    @Override
    public List<CenterRoomDto> getAllByCenterId(Long centerId) {
        final List<CenterRoomEntity> centerRoomEntityList = centerRoomRepository.findByCenterId(centerId);

        return centerRoomEntityList.stream()
                .map(CenterRoomDto::from)
                .toList();
    }

    @Override
    public void deleteByCenterId(Long centerId) {
        centerRoomRepository.deleteByCenterId(centerId);
    }

    @Override
    public Boolean saveAll(List<CenterRoomDto> centerRoomDtoList) {
        final List<CenterRoomEntity> centerRoomEntityList =  centerRoomDtoList.stream()
                .map(centerRoomDto -> CenterRoomEntity.create(
                        centerRoomDto.getCenterId(),
                        centerRoomDto.getName(),
                        centerRoomDto.getCapacity())
                ).toList();
        centerRoomRepository.saveAll(centerRoomEntityList);
        return true;
    }

    @Override
    public void create(CenterRoomDto centerRoomDto) {
        final CenterRoomEntity newCenterRoomEntity = CenterRoomEntity.create(
                centerRoomDto.getCenterId(),
                centerRoomDto.getName(),
                centerRoomDto.getCapacity());

        centerRoomRepository.save(newCenterRoomEntity);
    }

    @Override
    public CenterRoomDto getByCenterIdAndName(Long centerId, String name) {
        return centerRoomRepository.findByCenterIdAndName(centerId, name)
                .map(CenterRoomDto::from)
                .orElse(null);
    }

    @Override
    public CenterRoomDto getById(Long id) {
        return centerRoomRepository.findById(id)
                .map(CenterRoomDto::from)
                .orElse(null);
    }

    @Override
    public List<CenterRoomDto> getAllByIdList(List<Long> idList) {
        return centerRoomRepository.findAllByIdIn(idList)
                .stream()
                .map(CenterRoomDto::from)
                .toList();
    }
}
