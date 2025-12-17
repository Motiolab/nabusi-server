package com.positivehotel.nabusi_server.ticketPackage.wellnessTicket.application;

import com.positivehotel.nabusi_server.classPackage.wellnessClass.application.WellnessClassService;
import com.positivehotel.nabusi_server.classPackage.wellnessClass.application.dto.WellnessClassDto;
import com.positivehotel.nabusi_server.classPackage.wellnessLecture.application.WellnessLectureService;
import com.positivehotel.nabusi_server.classPackage.wellnessLecture.application.dto.WellnessLectureDto;
import com.positivehotel.nabusi_server.exception.customException.ExistsAlreadyException;
import com.positivehotel.nabusi_server.ticketPackage.wellnessTicket.application.dtos.WellnessTicketAdminDto;
import com.positivehotel.nabusi_server.ticketPackage.wellnessTicket.application.dtos.WellnessTicketDto;
import com.positivehotel.nabusi_server.ticketPackage.wellnessTicket.application.dtos.request.CreateWellnessTicketAdminRequestV1;
import com.positivehotel.nabusi_server.ticketPackage.wellnessTicket.application.dtos.request.UpdateWellnessTicketAdminRequestV1;
import com.positivehotel.nabusi_server.ticketPackage.wellnessTicketIssuance.application.WellnessTicketIssuanceService;
import com.positivehotel.nabusi_server.ticketPackage.wellnessTicketIssuance.application.dto.WellnessTicketIssuanceDto;
import com.positivehotel.nabusi_server.ticketPackage.wellnessTicketManagement.application.WellnessTicketManagementService;
import com.positivehotel.nabusi_server.ticketPackage.wellnessTicketManagement.application.dto.WellnessTicketManagementDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class WellnessTicketAdminService {
    private final WellnessTicketService wellnessTicketService;
    private final WellnessTicketManagementService wellnessTicketManagementService;
    private final WellnessClassService wellnessClassService;
    private final WellnessLectureService wellnessLectureService;
    private final WellnessTicketIssuanceService wellnessTicketIssuanceService;

    public List<WellnessTicketAdminDto> getAllWellnessTicketByCenterId(@NonNull final Long centerId) {
        return wellnessTicketService.getAllByCenterId(centerId)
                .stream()
                .map(wellnessTicketDto -> new WellnessTicketAdminDto(wellnessTicketDto, null))
                .toList();
    }

    @Transactional
    public void updateWellnessTicket(UpdateWellnessTicketAdminRequestV1 updateWellnessTicketAdminRequestV1) {
        final WellnessTicketDto wellnessTicketDto = WellnessTicketDto.builder()
                .id(updateWellnessTicketAdminRequestV1.getId())
                .centerId(updateWellnessTicketAdminRequestV1.getCenterId())
                .type(updateWellnessTicketAdminRequestV1.getType())
                .name(updateWellnessTicketAdminRequestV1.getName())
                .backgroundColor(updateWellnessTicketAdminRequestV1.getBackgroundColor())
                .textColor(updateWellnessTicketAdminRequestV1.getTextColor())
                .price(updateWellnessTicketAdminRequestV1.getPrice())
                .limitType(updateWellnessTicketAdminRequestV1.getLimitType())
                .limitCnt(updateWellnessTicketAdminRequestV1.getLimitCnt())
                .totalUsableCnt(updateWellnessTicketAdminRequestV1.getTotalUsableCnt())
                .usableDate(updateWellnessTicketAdminRequestV1.getUsableDate())
                .discountValue(updateWellnessTicketAdminRequestV1.getDiscountValue())
                .wellnessClassIdList(updateWellnessTicketAdminRequestV1.getWellnessClassIdList())
                .salesPrice(updateWellnessTicketAdminRequestV1.getSalesPrice())
                .build();

        wellnessTicketService.update(wellnessTicketDto);

        final List<WellnessTicketDto> allWellnessTicketDtoList = wellnessTicketService.getAllByCenterId(wellnessTicketDto.getCenterId());
        final List<WellnessTicketIssuanceDto> wellnessTicketIssuanceDtoList = wellnessTicketIssuanceService.getAllByWellnessTicketId(wellnessTicketDto.getId());

        final List<String> nameList = Stream.concat(
                wellnessTicketIssuanceDtoList.stream().map(WellnessTicketIssuanceDto::getName),
                allWellnessTicketDtoList.stream().map(WellnessTicketDto::getName)
        ).distinct().toList();

        final List<WellnessTicketManagementDto> wellnessTicketManagementDtoList = wellnessTicketManagementService.getAllByWellnessTicketId(wellnessTicketDto.getId());
        final List<Long> unUsedIdList = wellnessTicketManagementDtoList.stream()
                .filter(wellnessTicketManagementDto -> !nameList.contains(wellnessTicketManagementDto.getWellnessTicketIssuanceName()))
                .map(WellnessTicketManagementDto::getId)
                .toList();

        if(!unUsedIdList.isEmpty()) {
            wellnessTicketManagementService.deleteByIdList(unUsedIdList);

            List<WellnessClassDto> wellnessClassDtoList = wellnessClassService.getAllByWellnessTicketManagementIdListIn(unUsedIdList);
            wellnessClassDtoList.forEach(wellnessClassDto -> {
                List<Long> wellnessTicketIssuanceIdList = new ArrayList<>(wellnessClassDto.getWellnessTicketManagementIdList());
                wellnessTicketIssuanceIdList.removeAll(unUsedIdList);
                wellnessClassDto.setWellnessTicketManagementIdList(wellnessTicketIssuanceIdList);
                wellnessClassService.update(wellnessClassDto);
            });

            List<WellnessLectureDto> wellnessLectureDtoList = wellnessLectureService.getAllByWellnessTicketManagementIdListIn(unUsedIdList);
            wellnessLectureDtoList.forEach(wellnessLectureDto -> {
                List<Long> wellnessTicketIssuanceIdList = new ArrayList<>(wellnessLectureDto.getWellnessTicketManagementIdList());
                wellnessTicketIssuanceIdList.removeAll(unUsedIdList);
                wellnessLectureDto.setWellnessTicketManagementIdList(wellnessTicketIssuanceIdList);
                wellnessLectureService.update(wellnessLectureDto);
            });
        }

        final WellnessTicketManagementDto wellnessTicketManagementDto = wellnessTicketManagementService.getByWellnessTicketIdAndWellnessTicketIssuanceName(wellnessTicketDto.getId(), wellnessTicketDto.getName());
        WellnessTicketManagementDto storedWellnessTicketManagementDto;
        if(wellnessTicketManagementDto != null) {
            storedWellnessTicketManagementDto = wellnessTicketManagementService.update(wellnessTicketManagementDto);
        }else{
            WellnessTicketManagementDto newWellnessTicketManagementDto = WellnessTicketManagementDto.builder()
                    .centerId(wellnessTicketDto.getCenterId())
                    .wellnessTicketId(wellnessTicketDto.getId())
                    .wellnessTicketIssuanceName(wellnessTicketDto.getName())
                    .build();

            storedWellnessTicketManagementDto = wellnessTicketManagementService.create(newWellnessTicketManagementDto);
        }

        final List<WellnessClassDto> wellnessClassDtoList = wellnessClassService.getAllById(wellnessTicketDto.getWellnessClassIdList());

        wellnessClassDtoList.forEach(wellnessClassDto -> {
            List<Long> wellnessTicketManagementIdList = new ArrayList<>(wellnessClassDto.getWellnessTicketManagementIdList());
            wellnessTicketManagementIdList.add(storedWellnessTicketManagementDto.getId());
            wellnessClassDto.setWellnessTicketManagementIdList(wellnessTicketManagementIdList);
            wellnessClassService.update(wellnessClassDto);
        });

        final List<WellnessLectureDto> wellnessLectureDtoList = wellnessLectureService.getAllByWellnessClassIdList(wellnessTicketDto.getWellnessClassIdList());

        wellnessLectureDtoList.forEach(wellnessLectureDto -> {
            List<Long> wellnessTicketManagementIdList = new ArrayList<>(wellnessLectureDto.getWellnessTicketManagementIdList());
            wellnessTicketManagementIdList.add(storedWellnessTicketManagementDto.getId());
            wellnessLectureDto.setWellnessTicketManagementIdList(wellnessTicketManagementIdList);
            wellnessLectureService.update(wellnessLectureDto);
        });
    }

    public void createWellnessTicket(CreateWellnessTicketAdminRequestV1 createWellnessTicketAdminRequestV1) {
        final WellnessTicketDto wellnessTicketDto = WellnessTicketDto.builder()
                .centerId(createWellnessTicketAdminRequestV1.getCenterId())
                .type(createWellnessTicketAdminRequestV1.getType())
                .name(createWellnessTicketAdminRequestV1.getName())
                .backgroundColor(createWellnessTicketAdminRequestV1.getBackgroundColor())
                .textColor(createWellnessTicketAdminRequestV1.getTextColor())
                .price(createWellnessTicketAdminRequestV1.getPrice())
                .limitType(createWellnessTicketAdminRequestV1.getLimitType())
                .limitCnt(createWellnessTicketAdminRequestV1.getLimitCnt())
                .totalUsableCnt(createWellnessTicketAdminRequestV1.getTotalUsableCnt())
                .usableDate(createWellnessTicketAdminRequestV1.getUsableDate())
                .discountValue(createWellnessTicketAdminRequestV1.getDiscountValue())
                .wellnessClassIdList(createWellnessTicketAdminRequestV1.getWellnessClassIdList())
                .salesPrice(createWellnessTicketAdminRequestV1.getSalesPrice())
                .isDelete(false)
                .build();

         final WellnessTicketDto isExistWellnessTicketDto = wellnessTicketService.getByNameAndCenterId(wellnessTicketDto.getName(), wellnessTicketDto.getCenterId());
         if(isExistWellnessTicketDto!=null) {
             throw new ExistsAlreadyException(WellnessTicketDto.class, wellnessTicketDto.getName(), wellnessTicketDto.getCenterId());
         }

        final WellnessTicketDto storedWellnessTicketDto = wellnessTicketService.create(wellnessTicketDto);
        final WellnessTicketManagementDto wellnessTicketManagementDto = WellnessTicketManagementDto.builder()
                .centerId(storedWellnessTicketDto.getCenterId())
                .wellnessTicketId(storedWellnessTicketDto.getId())
                .wellnessTicketIssuanceName(storedWellnessTicketDto.getName())
                .build();

        final WellnessTicketManagementDto storedWellnessTicketManagementDto = wellnessTicketManagementService.create(wellnessTicketManagementDto);
        final List<WellnessClassDto> wellnessClassDtoList = wellnessClassService.getAllById(wellnessTicketDto.getWellnessClassIdList());

        wellnessClassDtoList.forEach(wellnessClassDto -> {
            List<Long> wellnessTicketManagementIdList = new ArrayList<>(wellnessClassDto.getWellnessTicketManagementIdList());
            wellnessTicketManagementIdList.add(storedWellnessTicketManagementDto.getId());
            wellnessClassDto.setWellnessTicketManagementIdList(wellnessTicketManagementIdList.stream().distinct().toList());
            wellnessClassService.update(wellnessClassDto);
        });

        final List<WellnessLectureDto> wellnessLectureDtoList = wellnessLectureService.getAllByWellnessClassIdList(wellnessTicketDto.getWellnessClassIdList());

        wellnessLectureDtoList.forEach(wellnessLectureDto -> {
            List<Long> wellnessTicketManagementIdList = new ArrayList<>(wellnessLectureDto.getWellnessTicketManagementIdList());
            wellnessTicketManagementIdList.add(storedWellnessTicketManagementDto.getId());
            wellnessLectureDto.setWellnessTicketManagementIdList(wellnessTicketManagementIdList.stream().distinct().toList());
            wellnessLectureService.update(wellnessLectureDto);
        });
    }

    public WellnessTicketAdminDto getWellnessTicketDetailById(@NonNull final Long id) {
        final WellnessTicketDto wellnessTicketDto = wellnessTicketService.getById(id);
        final List<WellnessClassDto> wellnessClassNameList = wellnessClassService.getAllById(wellnessTicketDto.getWellnessClassIdList());

        return new WellnessTicketAdminDto(wellnessTicketDto, wellnessClassNameList);
    }

    public void deleteWellnessTicketById(@NonNull final Long id) {
        wellnessTicketService.delete(id);
    }

    public void restoreWellnessTicketById(@NonNull final Long id) {
        wellnessTicketService.restore(id);
    }
}
