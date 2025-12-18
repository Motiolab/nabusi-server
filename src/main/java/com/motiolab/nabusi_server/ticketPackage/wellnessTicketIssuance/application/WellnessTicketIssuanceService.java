package com.motiolab.nabusi_server.ticketPackage.wellnessTicketIssuance.application;

import com.motiolab.nabusi_server.exception.customException.NotFoundException;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicketIssuance.application.dto.WellnessTicketIssuanceDto;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicketIssuance.domain.WellnessTicketIssuanceEntity;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicketIssuance.domain.WellnessTicketIssuanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WellnessTicketIssuanceService {
    private final WellnessTicketIssuanceRepository wellnessTicketIssuanceRepository;

    public List<WellnessTicketIssuanceDto> getAllByWellnessTicketId(Long wellnessTicketId) {
        final List<WellnessTicketIssuanceEntity> wellnessTicketIssuanceEntityList = wellnessTicketIssuanceRepository.findAllByWellnessTicketId(wellnessTicketId);
        return wellnessTicketIssuanceEntityList.stream().map(WellnessTicketIssuanceDto::from).toList();
    }

    public List<WellnessTicketIssuanceDto> getAllByMemberIdAndStartDateBeforeAndExpireDateAfterAndIsDeleteFalse(final Long memberId) {
        final ZonedDateTime now = ZonedDateTime.now();
        List<WellnessTicketIssuanceEntity> wellnessTicketIssuanceEntityList = wellnessTicketIssuanceRepository.findAllByMemberIdAndStartDateBeforeAndExpireDateAfterAndIsDeleteFalse(memberId, now, now);
        return wellnessTicketIssuanceEntityList.stream().map(WellnessTicketIssuanceDto::from).toList();
    }

    public List<WellnessTicketIssuanceDto> getAllByMemberIdListAndStartDateBeforeAndExpireDateAfterAndIsDeleteFalse(final List<Long> memberIdList) {
        final ZonedDateTime now = ZonedDateTime.now();
        List<WellnessTicketIssuanceEntity> wellnessTicketIssuanceEntityList = wellnessTicketIssuanceRepository.findAllByMemberIdInAndStartDateBeforeAndExpireDateAfterAndIsDeleteFalse(memberIdList, now, now);
        return wellnessTicketIssuanceEntityList.stream().map(WellnessTicketIssuanceDto::from).toList();
    }

    public List<WellnessTicketIssuanceDto> getAllByMemberId(final Long memberId) {
        final List<WellnessTicketIssuanceEntity> wellnessTicketIssuanceEntityList = wellnessTicketIssuanceRepository.findAllByMemberId(memberId);
        return wellnessTicketIssuanceEntityList.stream().map(WellnessTicketIssuanceDto::from).toList();
    }

    public WellnessTicketIssuanceDto create(WellnessTicketIssuanceDto wellnessTicketIssuanceDto) {
        final WellnessTicketIssuanceEntity wellnessTicketIssuanceEntity =  WellnessTicketIssuanceEntity.create(
                wellnessTicketIssuanceDto.getPaymentId(),
                wellnessTicketIssuanceDto.getCenterId(),
                wellnessTicketIssuanceDto.getName(),
                wellnessTicketIssuanceDto.getStartDate(),
                wellnessTicketIssuanceDto.getExpireDate(),
                wellnessTicketIssuanceDto.getType(),
                wellnessTicketIssuanceDto.getBackgroundColor(),
                wellnessTicketIssuanceDto.getTextColor(),
                wellnessTicketIssuanceDto.getLimitType(),
                wellnessTicketIssuanceDto.getLimitCnt(),
                wellnessTicketIssuanceDto.getIsDelete(),
                wellnessTicketIssuanceDto.getRemainingCnt(),
                wellnessTicketIssuanceDto.getTotalUsableCnt(),
                wellnessTicketIssuanceDto.getMemberId(),
                wellnessTicketIssuanceDto.getWellnessTicketId(),
                wellnessTicketIssuanceDto.getIsSendExpireNotification(),
                wellnessTicketIssuanceDto.getIsSendRemainingCntNotification(),
                wellnessTicketIssuanceDto.getPaymentMethod(),
                wellnessTicketIssuanceDto.getUnpaidValue()
        );

        final WellnessTicketIssuanceEntity savedWellnessTicketIssuanceEntity = wellnessTicketIssuanceRepository.save(wellnessTicketIssuanceEntity);
        return WellnessTicketIssuanceDto.from(savedWellnessTicketIssuanceEntity);
    }

    public void update(WellnessTicketIssuanceDto wellnessTicketIssuanceDto) {
        final WellnessTicketIssuanceEntity wellnessTicketIssuanceEntity = wellnessTicketIssuanceRepository.findById(wellnessTicketIssuanceDto.getId())
                .orElseThrow(() -> new NotFoundException(WellnessTicketIssuanceEntity.class, wellnessTicketIssuanceDto.getId()));

        wellnessTicketIssuanceEntity.update(
                wellnessTicketIssuanceDto.getCenterId(),
                wellnessTicketIssuanceDto.getPaymentId(),
                wellnessTicketIssuanceDto.getName(),
                wellnessTicketIssuanceDto.getStartDate(),
                wellnessTicketIssuanceDto.getExpireDate(),
                wellnessTicketIssuanceDto.getType(),
                wellnessTicketIssuanceDto.getBackgroundColor(),
                wellnessTicketIssuanceDto.getTextColor(),
                wellnessTicketIssuanceDto.getLimitType(),
                wellnessTicketIssuanceDto.getLimitCnt(),
                wellnessTicketIssuanceDto.getIsDelete(),
                wellnessTicketIssuanceDto.getRemainingCnt(),
                wellnessTicketIssuanceDto.getTotalUsableCnt(),
                wellnessTicketIssuanceDto.getMemberId(),
                wellnessTicketIssuanceDto.getWellnessTicketId(),
                wellnessTicketIssuanceDto.getIsSendExpireNotification(),
                wellnessTicketIssuanceDto.getIsSendRemainingCntNotification(),
                wellnessTicketIssuanceDto.getPaymentMethod(),
                wellnessTicketIssuanceDto.getUnpaidValue()
        );

        wellnessTicketIssuanceRepository.save(wellnessTicketIssuanceEntity);
    }

    public WellnessTicketIssuanceDto getById(Long id) {
        final WellnessTicketIssuanceEntity wellnessTicketIssuanceEntity = wellnessTicketIssuanceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(WellnessTicketIssuanceEntity.class, id));
        return WellnessTicketIssuanceDto.from(wellnessTicketIssuanceEntity);
    }

    public List<WellnessTicketIssuanceDto> getAllByIdList(List<Long> idList) {
        final List<WellnessTicketIssuanceEntity> wellnessTicketIssuanceEntityList = wellnessTicketIssuanceRepository.findAllByIdIn(idList);
        return wellnessTicketIssuanceEntityList.stream().map(WellnessTicketIssuanceDto::from).toList();
    }
}
