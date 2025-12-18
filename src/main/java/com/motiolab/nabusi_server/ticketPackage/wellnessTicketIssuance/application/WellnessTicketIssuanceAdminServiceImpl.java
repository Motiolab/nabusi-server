package com.motiolab.nabusi_server.ticketPackage.wellnessTicketIssuance.application;

import com.motiolab.nabusi_server.exception.customException.NotFoundException;
import com.motiolab.nabusi_server.memberPackage.member.application.MemberService;
import com.motiolab.nabusi_server.memberPackage.member.application.dto.MemberDto;
import com.motiolab.nabusi_server.paymentPackage.onSitePay.application.OnSitePayService;
import com.motiolab.nabusi_server.paymentPackage.onSitePay.application.dto.OnSitePayDto;
import com.motiolab.nabusi_server.paymentPackage.payment.application.PaymentService;
import com.motiolab.nabusi_server.paymentPackage.payment.application.dto.PaymentDto;
import com.motiolab.nabusi_server.paymentPackage.payment.enums.PaymentStatusEnum;
import com.motiolab.nabusi_server.ticketPackage.enums.CntChangedReason;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicket.application.WellnessTicketService;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicket.application.dtos.WellnessTicketDto;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicketExtension.application.WellnessTicketExtensionService;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicketExtension.application.dto.WellnessTicketExtensionDto;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicketIssuance.application.dto.WellnessTicketIssuanceAdminDto;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicketIssuance.application.dto.WellnessTicketIssuanceDto;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicketIssuance.application.dto.request.CreateWellnessTicketIssuanceAdminRequestV1;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicketIssuance.application.dto.request.UpdateWellnessTicketIssuanceAdminRequestV1;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicketIssuanceHistory.application.WellnessTicketIssuanceHistoryService;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicketIssuanceHistory.application.dto.WellnessTicketIssuanceHistoryDto;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicketManagement.application.WellnessTicketManagementService;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicketManagement.application.dto.WellnessTicketManagementDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class WellnessTicketIssuanceAdminServiceImpl implements WellnessTicketIssuanceAdminService{
    private final WellnessTicketIssuanceService wellnessTicketIssuanceService;
    private final WellnessTicketExtensionService wellnessTicketExtensionService;
    private final WellnessTicketService wellnessTicketService;
    private final WellnessTicketManagementService wellnessTicketManagementService;
    private final OnSitePayService onSitePayService;
    private final WellnessTicketIssuanceHistoryService wellnessTicketIssuanceHistoryService;
    private final MemberService memberService;
    private final PaymentService paymentService;

    @Transactional
    @Override
    public void createWellnessTicketIssuance(CreateWellnessTicketIssuanceAdminRequestV1 createWellnessTicketIssuanceAdminRequestV1) {
        PaymentDto paymentDto = PaymentDto.builder()
                .memberId(createWellnessTicketIssuanceAdminRequestV1.getMemberId())
                .status(PaymentStatusEnum.UNPAID)
                .build();

        PaymentDto savedPaymentDto = paymentService.create(paymentDto);

        final OnSitePayDto onSitePayDto = OnSitePayDto.builder()
                .paymentId(savedPaymentDto.getId())
                .discountRate(createWellnessTicketIssuanceAdminRequestV1.getDiscountRate())
                .totalPayValue(createWellnessTicketIssuanceAdminRequestV1.getTotalPayValue())
                .unpaidValue(createWellnessTicketIssuanceAdminRequestV1.getUnpaidValue())
                .cardInstallment(createWellnessTicketIssuanceAdminRequestV1.getCardInstallment())
                .cardPayValue(createWellnessTicketIssuanceAdminRequestV1.getCardPayValue())
                .cashPayValue(createWellnessTicketIssuanceAdminRequestV1.getCashPayValue())
                .payerMemberId(createWellnessTicketIssuanceAdminRequestV1.getPayerMemberId())
                .payeeMemberId(createWellnessTicketIssuanceAdminRequestV1.getActionMemberId())
                .note(createWellnessTicketIssuanceAdminRequestV1.getNote())
                .paymentDate(ZonedDateTime.now())
                .paymentMethod(createWellnessTicketIssuanceAdminRequestV1.getPaymentMethod())
                .updateMemberId(createWellnessTicketIssuanceAdminRequestV1.getActionMemberId())
                .build();
        OnSitePayDto savedOnSitePayDto = onSitePayService.create(onSitePayDto);

//        savedPaymentDto.setStatus(PaymentStatusEnum.COMPLETE);
        savedPaymentDto.setOnSitePayId(savedOnSitePayDto.getId());
        PaymentDto updatedPaymentDto = paymentService.update(savedPaymentDto);


        final List<WellnessTicketExtensionDto> wellnessTicketExtensionDtoList = wellnessTicketExtensionService.getAllByWellnessTicketId(createWellnessTicketIssuanceAdminRequestV1.getWellnessTicketId());
        final ZonedDateTime expireDate = calcExprieDate(createWellnessTicketIssuanceAdminRequestV1, wellnessTicketExtensionDtoList);

        final WellnessTicketDto wellnessTicketDto = wellnessTicketService.getById(createWellnessTicketIssuanceAdminRequestV1.getWellnessTicketId());
        if(wellnessTicketDto == null) {
            throw new NotFoundException(WellnessTicketDto.class, createWellnessTicketIssuanceAdminRequestV1.getWellnessTicketId());
        }

        final WellnessTicketIssuanceDto wellnessTicketIssuanceDto = WellnessTicketIssuanceDto.builder()
                .centerId(createWellnessTicketIssuanceAdminRequestV1.getCenterId())
                .paymentId(updatedPaymentDto.getId())
                .name(wellnessTicketDto.getName())
                .startDate(createWellnessTicketIssuanceAdminRequestV1.getStartDate())
                .expireDate(expireDate)
                .type(wellnessTicketDto.getType())
                .backgroundColor(wellnessTicketDto.getBackgroundColor())
                .textColor(wellnessTicketDto.getTextColor())
                .limitType(createWellnessTicketIssuanceAdminRequestV1.getLimitType())
                .limitCnt(createWellnessTicketIssuanceAdminRequestV1.getLimitCnt())
                .isDelete(false)
                .remainingCnt(createWellnessTicketIssuanceAdminRequestV1.getTotalUsableCnt())
                .totalUsableCnt(createWellnessTicketIssuanceAdminRequestV1.getTotalUsableCnt())
                .memberId(createWellnessTicketIssuanceAdminRequestV1.getMemberId())
                .wellnessTicketId(createWellnessTicketIssuanceAdminRequestV1.getWellnessTicketId())
                .isSendExpireNotification(false)
                .isSendRemainingCntNotification(false)
                .paymentMethod(createWellnessTicketIssuanceAdminRequestV1.getPaymentMethod())
                .unpaidValue(createWellnessTicketIssuanceAdminRequestV1.getUnpaidValue())
                .build();

        final WellnessTicketIssuanceDto savedWellnessTicketIssuanceDto =  wellnessTicketIssuanceService.create(wellnessTicketIssuanceDto);

        final WellnessTicketManagementDto wellnessTicketManagementDto = wellnessTicketManagementService.getByCenterIdAndWellnessTicketIdAndWellnessTicketIssuanceName(createWellnessTicketIssuanceAdminRequestV1.getCenterId(), savedWellnessTicketIssuanceDto.getWellnessTicketId(), savedWellnessTicketIssuanceDto.getName());
        final List<Long> wellnessTicketIssuanceIdList = new ArrayList<>(wellnessTicketManagementDto.getWellnessTicketIssuanceIdList());
        wellnessTicketIssuanceIdList.add(savedWellnessTicketIssuanceDto.getId());
        wellnessTicketManagementDto.setWellnessTicketIssuanceIdList(wellnessTicketIssuanceIdList);
        wellnessTicketManagementService.update(wellnessTicketManagementDto);

        final WellnessTicketIssuanceHistoryDto wellnessTicketIssuanceHistoryDto = WellnessTicketIssuanceHistoryDto.builder()
                .changedCnt(savedWellnessTicketIssuanceDto.getRemainingCnt())
                .wellnessLectureId(null)
                .reservationId(null)
                .actionMemberId(createWellnessTicketIssuanceAdminRequestV1.getActionMemberId())
                .wellnessTicketIssuanceId(savedWellnessTicketIssuanceDto.getId())
                .reason(CntChangedReason.ADMIN_ISSUED)
                .build();
        wellnessTicketIssuanceHistoryService.create(wellnessTicketIssuanceHistoryDto);
    }

    private static ZonedDateTime calcExprieDate(CreateWellnessTicketIssuanceAdminRequestV1 createWellnessTicketIssuanceAdminRequestV1, List<WellnessTicketExtensionDto> wellnessTicketExtensionDtoList) {
        final ZonedDateTime startDate = createWellnessTicketIssuanceAdminRequestV1.getStartDate();
        ZonedDateTime expireDate = createWellnessTicketIssuanceAdminRequestV1.getExpireDate();

        for (WellnessTicketExtensionDto wellnessTicketExtensionDto : wellnessTicketExtensionDtoList) {
            ZonedDateTime targetDate = wellnessTicketExtensionDto.getTargetDate();

            boolean isWithinRange = targetDate.isAfter(startDate) && targetDate.isBefore(expireDate)
                    || targetDate.isEqual(startDate) || targetDate.isEqual(expireDate);

            if (isWithinRange) {
                expireDate = expireDate.plusDays(wellnessTicketExtensionDto.getExtendDate());
            }
        }
        return expireDate;
    }

    @Override
    public List<WellnessTicketIssuanceAdminDto> getWellnessTicketIssuanceListByWellnessTicketId(Long wellnessTicketId) {
        final List<WellnessTicketIssuanceDto> wellnessTicketIssuanceDtoList = wellnessTicketIssuanceService.getAllByWellnessTicketId(wellnessTicketId);
        final List<Long> memberIdList = wellnessTicketIssuanceDtoList.stream().map(WellnessTicketIssuanceDto::getMemberId).distinct().toList();
        final List<MemberDto> memberDtoList = memberService.getAllByIdList(memberIdList);

        return wellnessTicketIssuanceDtoList.stream().map(wellnessTicketIssuanceDto -> WellnessTicketIssuanceAdminDto.builder()
                        .wellnessTicketIssuanceDto(wellnessTicketIssuanceDto)
                        .memberDto(memberDtoList.stream().filter(memberDto -> memberDto.getId().equals(wellnessTicketIssuanceDto.getMemberId())).findFirst().orElse(null))
                        .build())
                .toList();
    }

    @Override
    public WellnessTicketIssuanceAdminDto getWellnessTicketIssuanceUpdateDetailById(Long id) {
        final WellnessTicketIssuanceDto wellnessTicketIssuanceDto = wellnessTicketIssuanceService.getById(id);
        final MemberDto memberDto = memberService.getById(wellnessTicketIssuanceDto.getMemberId());

        return WellnessTicketIssuanceAdminDto.builder()
                .memberDto(memberDto)
                .wellnessTicketIssuanceDto(wellnessTicketIssuanceDto)
                .build();
    }

    @Transactional
    @Override
    public void updateWellnessTicketIssuance(UpdateWellnessTicketIssuanceAdminRequestV1 updateWellnessTicketIssuanceAdminRequestV1) {
        final WellnessTicketIssuanceDto wellnessTicketIssuanceDto = wellnessTicketIssuanceService.getById(updateWellnessTicketIssuanceAdminRequestV1.getId());

        if (!wellnessTicketIssuanceDto.getName().equals(updateWellnessTicketIssuanceAdminRequestV1.getName())) {
            final WellnessTicketManagementDto wellnessTicketManagementDto = wellnessTicketManagementService.getByWellnessTicketIdAndWellnessTicketIssuanceName(wellnessTicketIssuanceDto.getWellnessTicketId(), updateWellnessTicketIssuanceAdminRequestV1.getName());
            if(wellnessTicketManagementDto == null) {
                final WellnessTicketManagementDto newWellnessTicketManagementDto = WellnessTicketManagementDto.builder()
                        .centerId(wellnessTicketIssuanceDto.getCenterId())
                        .wellnessTicketId(wellnessTicketIssuanceDto.getWellnessTicketId())
                        .wellnessTicketIssuanceName(updateWellnessTicketIssuanceAdminRequestV1.getName())
                        .build();
                wellnessTicketManagementService.create(newWellnessTicketManagementDto);
            }else{
                final List<Long> wellnessTicketIssuanceIdList = new ArrayList<>(wellnessTicketManagementDto.getWellnessTicketIssuanceIdList());
                wellnessTicketIssuanceIdList.add(wellnessTicketIssuanceDto.getId());
                wellnessTicketManagementDto.setWellnessTicketIssuanceIdList(wellnessTicketIssuanceIdList);
                wellnessTicketManagementService.update(wellnessTicketManagementDto);
            }
        }

        if (Objects.equals(wellnessTicketIssuanceDto.getRemainingCnt(), updateWellnessTicketIssuanceAdminRequestV1.getRemainingCnt())) {
            final Integer changedCnt = updateWellnessTicketIssuanceAdminRequestV1.getRemainingCnt() - wellnessTicketIssuanceDto.getRemainingCnt();
            final WellnessTicketIssuanceHistoryDto wellnessTicketIssuanceHistoryDto = WellnessTicketIssuanceHistoryDto.builder()
                    .changedCnt(changedCnt)
                    .actionMemberId(updateWellnessTicketIssuanceAdminRequestV1.getActionMemberId())
                    .wellnessTicketIssuanceId(wellnessTicketIssuanceDto.getId())
                    .reason(CntChangedReason.ADMIN_MODIFY_CNT)
                    .build();
            wellnessTicketIssuanceHistoryService.create(wellnessTicketIssuanceHistoryDto);
        }

        wellnessTicketIssuanceDto.setName(updateWellnessTicketIssuanceAdminRequestV1.getName());
        wellnessTicketIssuanceDto.setBackgroundColor(updateWellnessTicketIssuanceAdminRequestV1.getBackgroundColor());
        wellnessTicketIssuanceDto.setTextColor(updateWellnessTicketIssuanceAdminRequestV1.getTextColor());
        wellnessTicketIssuanceDto.setType(updateWellnessTicketIssuanceAdminRequestV1.getType());
        wellnessTicketIssuanceDto.setStartDate(updateWellnessTicketIssuanceAdminRequestV1.getStartDate());
        wellnessTicketIssuanceDto.setExpireDate(updateWellnessTicketIssuanceAdminRequestV1.getExpireDate());
        final Integer changedCnt = updateWellnessTicketIssuanceAdminRequestV1.getRemainingCnt() - wellnessTicketIssuanceDto.getRemainingCnt();
        final Integer totalUsableCnt = wellnessTicketIssuanceDto.getTotalUsableCnt() + changedCnt;
        wellnessTicketIssuanceDto.setTotalUsableCnt(totalUsableCnt);
        wellnessTicketIssuanceDto.setRemainingCnt(updateWellnessTicketIssuanceAdminRequestV1.getRemainingCnt());
        wellnessTicketIssuanceDto.setLimitType(updateWellnessTicketIssuanceAdminRequestV1.getLimitType());
        wellnessTicketIssuanceDto.setLimitCnt(updateWellnessTicketIssuanceAdminRequestV1.getLimitCnt());
        wellnessTicketIssuanceDto.setIsDelete(updateWellnessTicketIssuanceAdminRequestV1.getIsDelete());
        wellnessTicketIssuanceService.update(wellnessTicketIssuanceDto);
    }
}
