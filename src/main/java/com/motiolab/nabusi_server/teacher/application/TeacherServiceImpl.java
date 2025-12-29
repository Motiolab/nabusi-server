package com.motiolab.nabusi_server.teacher.application;

import com.motiolab.nabusi_server.exception.customException.DeletedAlreadyException;
import com.motiolab.nabusi_server.exception.customException.NotFoundException;
import com.motiolab.nabusi_server.exception.customException.RestoredAlreadyException;
import com.motiolab.nabusi_server.teacher.application.dto.TeacherDto;
import com.motiolab.nabusi_server.teacher.domain.TeacherEntity;
import com.motiolab.nabusi_server.teacher.domain.TeacherRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    @Override
    public List<TeacherDto> getAllByIdList(List<Long> idList) {
        return teacherRepository.findAllByIdIn(idList)
                .stream()
                .map(TeacherDto::from)
                .toList();
    }

    @Override
    public TeacherDto getByCenterIdAndMemberId(Long centerId, Long memberId) {
        return teacherRepository.findByCenterIdAndMemberId(centerId, memberId)
                .map(TeacherDto::from)
                .orElse(null);
    }

    @Override
    public void create(TeacherDto teacherDto) {
        final TeacherEntity newTeacherEntity = TeacherEntity.create(
                teacherDto.getName(),
                teacherDto.getNickName(),
                teacherDto.getColor(),
                teacherDto.getEmail(),
                teacherDto.getMobile(),
                teacherDto.getIntroduce(),
                teacherDto.getCareer(),
                teacherDto.getCenterId(),
                teacherDto.getMemberId(),
                teacherDto.getUseNickName(),
                teacherDto.getIsDelete());

        teacherRepository.save(newTeacherEntity);
    }

    @Override
    public List<TeacherDto> getAllByCenterId(Long centerId) {
        final List<TeacherEntity> teacherEntityList = teacherRepository.findAllByCenterId(centerId);
        return teacherEntityList.stream().map(TeacherDto::from).toList();
    }

    @Override
    public TeacherDto getById(Long id) {
        return teacherRepository.findById(id)
                .map(TeacherDto::from)
                .orElse(null);
    }

    @Override
    public void update(TeacherDto teacherDto) {
        final TeacherEntity teacherEntity = teacherRepository.findById(teacherDto.getId())
                .orElseThrow(() -> new NotFoundException(TeacherDto.class, teacherDto.getId()));

        teacherEntity.update(
                teacherDto.getNickName(),
                teacherDto.getColor(),
                teacherDto.getIntroduce(),
                teacherDto.getCareer(),
                teacherDto.getUseNickName(),
                teacherDto.getImageUrl(),
                teacherDto.getIsDelete());

        teacherRepository.save(teacherEntity);
    }

    @Override
    public void delete(@NonNull final Long id) {
        teacherRepository.findById(id)
                .map(teacherEntity -> {
                    if (teacherEntity.getIsDelete()) {
                        throw new DeletedAlreadyException(TeacherEntity.class, id);
                    }
                    teacherEntity.updateIsDelete(true);
                    return teacherRepository.save(teacherEntity);
                })
                .orElseThrow(() -> new NotFoundException(TeacherEntity.class, id));
    }

    @Override
    public void restore(Long id) {
        teacherRepository.findById(id)
                .map(teacherEntity -> {
                    if (!teacherEntity.getIsDelete()) {
                        throw new RestoredAlreadyException(TeacherEntity.class, id);
                    }
                    teacherEntity.updateIsDelete(false);
                    return teacherRepository.save(teacherEntity);
                })
                .orElseThrow(() -> new NotFoundException(TeacherEntity.class, id));
    }

    @Override
    public List<TeacherDto> getAllByMemberId(Long memberId) {
        final List<TeacherEntity> teacherEntityList = teacherRepository.findAllByMemberId(memberId);
        return teacherEntityList.stream().map(TeacherDto::from).toList();
    }
}
