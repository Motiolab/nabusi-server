package com.motiolab.nabusi_server.teacher.application;

import com.motiolab.nabusi_server.classPackage.wellnessLecture.application.WellnessLectureService;
import com.motiolab.nabusi_server.classPackage.wellnessLecture.application.dto.WellnessLectureDto;
import com.motiolab.nabusi_server.exception.customException.ExistsAlreadyException;
import com.motiolab.nabusi_server.exception.customException.NotFoundException;
import com.motiolab.nabusi_server.memberPackage.member.application.MemberService;
import com.motiolab.nabusi_server.memberPackage.member.application.dto.MemberDto;
import com.motiolab.nabusi_server.teacher.application.dto.TeacherAdminDto;
import com.motiolab.nabusi_server.teacher.application.dto.TeacherDto;
import com.motiolab.nabusi_server.teacher.application.dto.request.UpdateTeacherCareerByIdAdminRequestV1;
import com.motiolab.nabusi_server.teacher.application.dto.request.UpdateTeacherImageUrlByIdAdminRequestV1;
import com.motiolab.nabusi_server.teacher.application.dto.request.UpdateTeacherIntroduceAndNickNameByIdAdminRequestV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TeacherAdminServiceImpl implements TeacherAdminService{
    private final TeacherService teacherService;
    private final MemberService memberService;
    private final WellnessLectureService wellnessLectureService;

    @Override
    public void addTeacherByCenterId(Long centerId, Long memberId) {
        final TeacherDto existsTeacherDto = teacherService.getByCenterIdAndMemberId(centerId, memberId);
        if (existsTeacherDto != null) {
            throw new ExistsAlreadyException(TeacherDto.class, existsTeacherDto.getId());
        }

        final MemberDto memberDto = memberService.getById(memberId);
        if(memberDto == null){
            throw new NotFoundException(MemberDto.class, memberId);
        }

        final TeacherDto teacherDto = TeacherDto.builder()
                .name(memberDto.getName())
                .email(memberDto.getEmail())
                .mobile(memberDto.getMobile())
                .centerId(centerId)
                .memberId(memberId)
                .useNickName(false)
                .isDelete(false)
                .build();

        teacherService.create(teacherDto);
    }

    @Override
    public List<TeacherAdminDto> getTeacherNameListByCenterId(Long centerId) {
        final List<TeacherDto> teacherDtoList = teacherService.getAllByCenterId(centerId);
        return teacherDtoList.stream()
                .map(teacherDto -> TeacherAdminDto.builder()
                        .teacherDto(teacherDto)
                        .build())
                .toList();
    }

    @Override
    public List<TeacherAdminDto> getTeacherListByCenterId(Long centerId) {
        final List<TeacherDto> teacherDtoList = teacherService.getAllByCenterId(centerId);
        final List<Long> teacherIdList = teacherDtoList.stream().map(TeacherDto::getId).distinct().toList();
        final List<WellnessLectureDto> wellnessLectureDtoList = wellnessLectureService.getAllByTeacherIdList(teacherIdList);
        return teacherDtoList.stream()
                .map(teacherDto -> TeacherAdminDto.builder()
                        .teacherDto(teacherDto)
                        .wellnessLectureDtoList(wellnessLectureDtoList
                                .stream()
                                .filter(wellnessLectureDto -> wellnessLectureDto.getTeacherId().equals(teacherDto.getId()))
                                .toList())
                        .build())
                .toList();
    }

    @Override
    public TeacherAdminDto getTeacherDetailById(Long id) {
        final TeacherDto teacherDto = teacherService.getById(id);
        if(teacherDto == null){
            throw new NotFoundException(TeacherDto.class, id);
        }

        return TeacherAdminDto.builder()
                .teacherDto(teacherDto)
                .build();
    }

    @Override
    public void updateTeacherIntroduceAndNickNameById(UpdateTeacherIntroduceAndNickNameByIdAdminRequestV1 updateTeacherIntroduceAndNickNameByIdAdminRequestV1) {
        final TeacherDto teacherDto = teacherService.getById(updateTeacherIntroduceAndNickNameByIdAdminRequestV1.getId());
        if(teacherDto == null) {
            throw new NotFoundException(TeacherDto.class, updateTeacherIntroduceAndNickNameByIdAdminRequestV1.getId());
        }
        teacherDto.setIntroduce(updateTeacherIntroduceAndNickNameByIdAdminRequestV1.getIntroduce());
        teacherDto.setUseNickName(updateTeacherIntroduceAndNickNameByIdAdminRequestV1.getUseNickName());
        teacherDto.setNickName(updateTeacherIntroduceAndNickNameByIdAdminRequestV1.getNickName());
        teacherService.update(teacherDto);
    }

    @Override
    public void updateTeacherCareerById(UpdateTeacherCareerByIdAdminRequestV1 updateTeacherCareerByIdAdminRequestV1) {
        final TeacherDto teacherDto = teacherService.getById(updateTeacherCareerByIdAdminRequestV1.getId());
        if(teacherDto == null) {
            throw new NotFoundException(TeacherDto.class, updateTeacherCareerByIdAdminRequestV1.getId());
        }
        teacherDto.setCareer(updateTeacherCareerByIdAdminRequestV1.getCareer());
        teacherService.update(teacherDto);
    }

    @Override
    public void deleteTeacherById(Long id) {
        teacherService.delete(id);
        //todo role, urlPattern 수정
    }

    @Override
    public void restoreTeacherById(Long id) {
        teacherService.restore(id);
        //todo role, urlPattern 수정
    }

    @Override
    public void updateTeacherImageUrlById(UpdateTeacherImageUrlByIdAdminRequestV1 updateTeacherImageUrlByIdAdminRequestV1) {
        final TeacherDto teacherDto = teacherService.getById(updateTeacherImageUrlByIdAdminRequestV1.getId());
        if(teacherDto == null) {
            throw new NotFoundException(TeacherDto.class, updateTeacherImageUrlByIdAdminRequestV1.getId());
        }
        teacherDto.setImageUrl(updateTeacherImageUrlByIdAdminRequestV1.getImageUrl());
        teacherService.update(teacherDto);
    }

}
