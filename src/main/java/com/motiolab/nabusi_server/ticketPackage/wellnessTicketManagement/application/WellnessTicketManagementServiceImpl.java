package com.motiolab.nabusi_server.ticketPackage.wellnessTicketManagement.application;

import com.motiolab.nabusi_server.exception.customException.MultipleDataException;
import com.motiolab.nabusi_server.exception.customException.NotFoundException;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicketManagement.application.dto.WellnessTicketManagementDto;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicketManagement.domain.WellnessTicketManagementEntity;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicketManagement.domain.WellnessTicketManagementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WellnessTicketManagementServiceImpl implements WellnessTicketManagementService{
    private final WellnessTicketManagementRepository wellnessTicketManagementRepository;

    @Override
    public WellnessTicketManagementDto create(WellnessTicketManagementDto wellnessTicketManagementDto) {
        final WellnessTicketManagementEntity wellnessTicketManagementEntity = WellnessTicketManagementEntity.create(
                wellnessTicketManagementDto.getCenterId(),
                wellnessTicketManagementDto.getWellnessTicketId(),
                wellnessTicketManagementDto.getWellnessTicketIssuanceName(),
                wellnessTicketManagementDto.getWellnessTicketIssuanceIdList()
        );
        final WellnessTicketManagementEntity savedWellnessTicketManagementEntity = wellnessTicketManagementRepository.save(wellnessTicketManagementEntity);
        return WellnessTicketManagementDto.from(savedWellnessTicketManagementEntity);
    }

    @Override
    public List<WellnessTicketManagementDto> getAllByWellnessTicketId(Long wellnessTicketId) {
        return wellnessTicketManagementRepository.findAllByWellnessTicketId(wellnessTicketId)
                .stream()
                .map(WellnessTicketManagementDto::from)
                .toList();
    }

    @Override
    public void deleteByIdList(List<Long> idList) {
        wellnessTicketManagementRepository.deleteAllByIdIn(idList);
    }

    @Override
    public WellnessTicketManagementDto getByWellnessTicketIdAndWellnessTicketIssuanceName(Long wellnessTicketId, String wellnessTicketIssuanceName) {
        return wellnessTicketManagementRepository.findByWellnessTicketIdAndWellnessTicketIssuanceName(wellnessTicketId, wellnessTicketIssuanceName)
                .map(wellnessTicketManagementEntity -> WellnessTicketManagementDto.builder()
                        .id(wellnessTicketManagementEntity.getId())
                        .centerId(wellnessTicketManagementEntity.getCenterId())
                        .wellnessTicketId(wellnessTicketManagementEntity.getWellnessTicketId())
                        .wellnessTicketIssuanceName(wellnessTicketManagementEntity.getWellnessTicketIssuanceName())
                        .build())
                .orElse(null);
    }

    @Override
    public WellnessTicketManagementDto update(WellnessTicketManagementDto wellnessTicketManagementDto) {
        final WellnessTicketManagementEntity wellnessTicketManagementEntity = wellnessTicketManagementRepository.findById(wellnessTicketManagementDto.getId())
                .orElseThrow(() -> new NotFoundException(WellnessTicketManagementEntity.class, wellnessTicketManagementDto.getId()));

        wellnessTicketManagementEntity.update(wellnessTicketManagementDto.getWellnessTicketId(), wellnessTicketManagementDto.getWellnessTicketIssuanceName(), wellnessTicketManagementDto.getWellnessTicketIssuanceIdList());
        final WellnessTicketManagementEntity savedWellnessTicketManagementEntity = wellnessTicketManagementRepository.save(wellnessTicketManagementEntity);

        return WellnessTicketManagementDto.builder()
                .id(savedWellnessTicketManagementEntity.getId())
                .wellnessTicketId(savedWellnessTicketManagementEntity.getWellnessTicketId())
                .wellnessTicketIssuanceName(savedWellnessTicketManagementEntity.getWellnessTicketIssuanceName())
                .build();
    }

    @Override
    public List<WellnessTicketManagementDto> getAllByIdList(List<Long> idList) {
        return wellnessTicketManagementRepository.findAllById(idList)
                .stream()
                .map(WellnessTicketManagementDto::from)
                .toList();
    }

    @Override
    public List<WellnessTicketManagementDto> getAllByCenterId(Long centerId) {
        return wellnessTicketManagementRepository.findAllByCenterId(centerId)
                .stream()
                .map(WellnessTicketManagementDto::from)
                .toList();
    }

    @Override
    public WellnessTicketManagementDto getByCenterIdAndWellnessTicketIdAndWellnessTicketIssuanceName(Long centerId, Long wellnessTicketId, String wellnessTicketIssuanceName) {
        return wellnessTicketManagementRepository.findByCenterIdAndWellnessTicketIdAndWellnessTicketIssuanceName(centerId, wellnessTicketId, wellnessTicketIssuanceName)
                .map(WellnessTicketManagementDto::from)
                .orElse(null);
    }

    @Override
    public WellnessTicketManagementDto getByWellnessTicketIssuanceId(Long wellnessTicketIssuanceId) {
        final List<WellnessTicketManagementEntity> wellnessTicketManagementEntityList = wellnessTicketManagementRepository.findByWellnessTicketIssuanceId(wellnessTicketIssuanceId);
        if (wellnessTicketManagementEntityList.isEmpty()) {
            return null;
        }
        if (wellnessTicketManagementEntityList.size() > 1) {
            throw new MultipleDataException(WellnessTicketManagementEntity.class, wellnessTicketIssuanceId);
        }
        return WellnessTicketManagementDto.from(wellnessTicketManagementEntityList.get(0));
    }
}
