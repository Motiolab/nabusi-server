package com.motiolab.nabusi_server.centerPackage.centerNotice.application;

import com.motiolab.nabusi_server.centerPackage.centerNotice.application.dto.CenterNoticeAdminDto;
import com.motiolab.nabusi_server.centerPackage.centerNotice.application.dto.CenterNoticeDto;
import com.motiolab.nabusi_server.centerPackage.centerNotice.application.dto.request.CreateCenterNoticeByCenterIdAdminRequestV1;
import com.motiolab.nabusi_server.centerPackage.centerNotice.application.dto.request.UpdateCenterNoticeByIdAdminRequestV1;
import com.motiolab.nabusi_server.exception.customException.NotFoundException;
import com.motiolab.nabusi_server.memberPackage.member.application.MemberService;
import com.motiolab.nabusi_server.memberPackage.member.application.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CenterNoticeAdminServiceImpl implements CenterNoticeAdminService{
    private final CenterNoticeService centerNoticeService;
    private final MemberService memberService;

    @Override
    public void createCenterNoticeByCenterId(CreateCenterNoticeByCenterIdAdminRequestV1 createCenterNoticeByCenterIdAdminRequestV1) {
        final CenterNoticeDto centerNoticeDto = CenterNoticeDto.builder()
                .centerId(createCenterNoticeByCenterIdAdminRequestV1.getCenterId())
                .registerId(createCenterNoticeByCenterIdAdminRequestV1.getRegisterId())
                .title(createCenterNoticeByCenterIdAdminRequestV1.getTitle())
                .content(createCenterNoticeByCenterIdAdminRequestV1.getContent())
                .isPopup(createCenterNoticeByCenterIdAdminRequestV1.getIsPopup())
                .isDelete(createCenterNoticeByCenterIdAdminRequestV1.getIsDelete())
                .build();

        centerNoticeService.create(centerNoticeDto);
    }

    @Override
    public List<CenterNoticeAdminDto> getCenterNoticeListByCenterId(Long centerId) {
        final List<CenterNoticeDto> centerNoticeDtoList = centerNoticeService.getAllByCenterId(centerId);
        final List<Long> registerIdList = centerNoticeDtoList
                .stream()
                .map(CenterNoticeDto::getRegisterId)
                .distinct()
                .toList();
        final List<MemberDto> memberDtoList = memberService.getAllByIdList(registerIdList);

        return centerNoticeDtoList
                .stream()
                .map(centerNoticeDto -> CenterNoticeAdminDto.builder()
                        .centerNoticeDto(centerNoticeDto)
                        .memberDto(memberDtoList
                                .stream()
                                .filter(memberDto -> memberDto.getId().equals(centerNoticeDto.getRegisterId()))
                                .findFirst()
                                .orElseThrow(() -> new NotFoundException(MemberDto.class, centerNoticeDto.getRegisterId())))
                        .build())
                .toList();
    }

    @Override
    public CenterNoticeAdminDto getCenterNoticeDetailById(Long id) {
        final CenterNoticeDto centerNoticeDto = centerNoticeService.getById(id);
        if (centerNoticeDto == null) {
            throw new NotFoundException(CenterNoticeDto.class, id);
        }

        return CenterNoticeAdminDto.builder()
                .centerNoticeDto(centerNoticeDto)
                .build();
    }

    @Transactional
    @Override
    public void updateCenterNoticeById(UpdateCenterNoticeByIdAdminRequestV1 updateCenterNoticeByIdAdminRequestV1) {
        final CenterNoticeDto centerNoticeDto = CenterNoticeDto.builder()
                .id(updateCenterNoticeByIdAdminRequestV1.getId())
                .title(updateCenterNoticeByIdAdminRequestV1.getTitle())
                .content(updateCenterNoticeByIdAdminRequestV1.getContent())
                .isPopup(updateCenterNoticeByIdAdminRequestV1.getIsPopup())
                .isDelete(updateCenterNoticeByIdAdminRequestV1.getIsDelete())
                .registerId(updateCenterNoticeByIdAdminRequestV1.getRegisterId())
                .build();

        if(centerNoticeDto.getIsPopup()) {
            final List<CenterNoticeDto> centerNoticeDtoList = centerNoticeService.getAllByCenterId(updateCenterNoticeByIdAdminRequestV1.getCenterId());
            centerNoticeDtoList.stream().filter(CenterNoticeDto::getIsPopup)
                    .forEach(centerNoticeDto1 -> {
                        centerNoticeDto1.setIsPopup(false);
                        centerNoticeService.update(centerNoticeDto1);
                    });
        }
        centerNoticeService.update(centerNoticeDto);
    }

}
