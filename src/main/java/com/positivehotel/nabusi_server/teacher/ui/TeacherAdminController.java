package com.positivehotel.nabusi_server.teacher.ui;

import com.positivehotel.nabusi_server.teacher.application.TeacherAdminService;
import com.positivehotel.nabusi_server.teacher.application.dto.TeacherAdminDto;
import com.positivehotel.nabusi_server.teacher.application.dto.request.UpdateTeacherCareerByIdAdminRequestV1;
import com.positivehotel.nabusi_server.teacher.application.dto.request.UpdateTeacherImageUrlByIdAdminRequestV1;
import com.positivehotel.nabusi_server.teacher.application.dto.request.UpdateTeacherIntroduceAndNickNameByIdAdminRequestV1;
import com.positivehotel.nabusi_server.teacher.application.dto.response.GetTeacherDetailById;
import com.positivehotel.nabusi_server.teacher.application.dto.response.GetTeacherListByCenterIdAdminResponseV1;
import com.positivehotel.nabusi_server.teacher.application.dto.response.GetTeacherNameListAdminResponseV1;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class TeacherAdminController {
    private final TeacherAdminService teacherAdminService;

    @PostMapping("/v1/admin/teacher/add/{centerId}")
    public ResponseEntity<Boolean> addTeacherByCenterId(@PathVariable Long centerId, @RequestParam(defaultValue = "memberId") Long memberId){
        teacherAdminService.addTeacherByCenterId(centerId, memberId);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/v1/admin/teacher/name/list/{centerId}")
    public ResponseEntity<List<GetTeacherNameListAdminResponseV1>> getTeacherNameListByCenterId(@PathVariable Long centerId){
        final List<TeacherAdminDto> teacherAdminDtoList = teacherAdminService.getTeacherNameListByCenterId(centerId);
        final List<GetTeacherNameListAdminResponseV1> getTeacherNameListAdminResponseV1List = teacherAdminDtoList.stream()
                .map(teacherAdminDto -> new GetTeacherNameListAdminResponseV1(
                        teacherAdminDto.getTeacherDto().getId(),
                        teacherAdminDto.getTeacherDto().getName()))
                .toList();
        return ResponseEntity.ok(getTeacherNameListAdminResponseV1List);
    }

    @GetMapping("/v1/admin/teacher/list/{centerId}")
    public ResponseEntity<List<GetTeacherListByCenterIdAdminResponseV1>> getTeacherListByCenterId(@PathVariable Long centerId){
        final List<TeacherAdminDto> teacherAdminDtoList = teacherAdminService.getTeacherListByCenterId(centerId);
        final List<GetTeacherListByCenterIdAdminResponseV1> response = teacherAdminDtoList
                .stream()
                .map(teacherAdminDto -> GetTeacherListByCenterIdAdminResponseV1.builder()
                        .id(teacherAdminDto.getTeacherDto().getId())
                        .name(teacherAdminDto.getTeacherDto().getName())
                        .nickName(teacherAdminDto.getTeacherDto().getNickName())
                        .mobile(teacherAdminDto.getTeacherDto().getMobile())
                        .lectureCnt(teacherAdminDto.getWellnessLectureDtoList().size())
                        .isDelete(teacherAdminDto.getTeacherDto().getIsDelete())
                        .createdDate(teacherAdminDto.getTeacherDto().getCreatedDate())
                        .build())
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/v1/admin/teacher/detail/{centerId}")
    public ResponseEntity<GetTeacherDetailById> getTeacherDetailById(@PathVariable Long centerId, @RequestParam(defaultValue = "id") Long id){
        final TeacherAdminDto teacherAdminDto = teacherAdminService.getTeacherDetailById(id);
        final GetTeacherDetailById response = GetTeacherDetailById.builder()
                .id(teacherAdminDto.getTeacherDto().getId())
                .name(teacherAdminDto.getTeacherDto().getName())
                .nickName(teacherAdminDto.getTeacherDto().getNickName())
                .mobile(teacherAdminDto.getTeacherDto().getMobile())
                .email(teacherAdminDto.getTeacherDto().getEmail())
                .introduce(teacherAdminDto.getTeacherDto().getIntroduce())
                .career(teacherAdminDto.getTeacherDto().getCareer())
                .createdDate(teacherAdminDto.getTeacherDto().getCreatedDate())
                .useNickName(teacherAdminDto.getTeacherDto().getUseNickName())
                .imageUrl(teacherAdminDto.getTeacherDto().getImageUrl())
                .isDelete(teacherAdminDto.getTeacherDto().getIsDelete())
                .build();

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/v1/admin/teacher/update/image/{centerId}")
    public ResponseEntity<Boolean> updateTeacherImageUrlById(@PathVariable Long centerId, @RequestBody UpdateTeacherImageUrlByIdAdminRequestV1 updateTeacherImageUrlByIdAdminRequestV1){
        teacherAdminService.updateTeacherImageUrlById(updateTeacherImageUrlByIdAdminRequestV1);
        return ResponseEntity.ok(true);
    }

    @PatchMapping("/v1/admin/teacher/update/introduce/{centerId}")
    public ResponseEntity<Boolean> updateTeacherIntroduceAndNickNameById(@PathVariable Long centerId, @RequestBody UpdateTeacherIntroduceAndNickNameByIdAdminRequestV1 updateTeacherIntroduceAndNickNameByIdAdminRequestV1){
        teacherAdminService.updateTeacherIntroduceAndNickNameById(updateTeacherIntroduceAndNickNameByIdAdminRequestV1);
        return ResponseEntity.ok(true);
    }

    @PatchMapping("/v1/admin/teacher/update/career/{centerId}")
    public ResponseEntity<Boolean> updateTeacherCareerById(@PathVariable Long centerId, @RequestBody UpdateTeacherCareerByIdAdminRequestV1 updateTeacherCareerByIdAdminRequestV1){
        teacherAdminService.updateTeacherCareerById(updateTeacherCareerByIdAdminRequestV1);
        return ResponseEntity.ok(true);
    }

    @PatchMapping("/v1/admin/teacher/restore/{centerId}")
    public ResponseEntity<Boolean> restoreTeacherById(@PathVariable Long centerId, @RequestParam(defaultValue = "id") Long id) {
        teacherAdminService.restoreTeacherById(id);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/v1/admin/teacher/delete/{centerId}")
    public ResponseEntity<Boolean> deleteTeacherById(@PathVariable Long centerId, @RequestParam(defaultValue = "id") Long id){
        teacherAdminService.deleteTeacherById(id);
        return ResponseEntity.ok(true);
    }
}
