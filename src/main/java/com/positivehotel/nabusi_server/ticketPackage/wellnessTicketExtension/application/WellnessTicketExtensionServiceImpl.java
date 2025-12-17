package com.positivehotel.nabusi_server.ticketPackage.wellnessTicketExtension.application;

import com.positivehotel.nabusi_server.ticketPackage.wellnessTicketExtension.application.dto.WellnessTicketExtensionDto;
import com.positivehotel.nabusi_server.ticketPackage.wellnessTicketExtension.domain.WellnessTicketExtensionEntity;
import com.positivehotel.nabusi_server.ticketPackage.wellnessTicketExtension.domain.WellnessTicketExtensionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WellnessTicketExtensionServiceImpl implements WellnessTicketExtensionService{
    private final WellnessTicketExtensionRepository wellnessTicketExtensionRepository;

    @Override
    public void create(WellnessTicketExtensionDto wellnessTicketExtensionDto) {
        final WellnessTicketExtensionEntity wellnessTicketExtensionEntity = WellnessTicketExtensionEntity.create(
                wellnessTicketExtensionDto.getExtendDate(),
                wellnessTicketExtensionDto.getIsApplyAfter(),
                wellnessTicketExtensionDto.getRegisterId(),
                wellnessTicketExtensionDto.getReason(),
                wellnessTicketExtensionDto.getTargetDate(),
                wellnessTicketExtensionDto.getWellnessTicketId()
        );

        wellnessTicketExtensionRepository.save(wellnessTicketExtensionEntity);
    }

    @Override
    public List<WellnessTicketExtensionDto> getAllByWellnessTicketId(Long wellnessTicketId) {
        final List<WellnessTicketExtensionEntity> wellnessTicketExtensionEntityList = wellnessTicketExtensionRepository.findAllByWellnessTicketId(wellnessTicketId);
        return wellnessTicketExtensionEntityList
                .stream()
                .map(WellnessTicketExtensionDto::from)
                .toList();
    }
}
