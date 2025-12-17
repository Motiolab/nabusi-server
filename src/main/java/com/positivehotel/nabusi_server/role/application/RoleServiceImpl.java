package com.positivehotel.nabusi_server.role.application;

import com.positivehotel.nabusi_server.role.application.dto.RoleDto;
import com.positivehotel.nabusi_server.role.domain.RoleEntity;
import com.positivehotel.nabusi_server.role.domain.RoleRepository;
import com.positivehotel.nabusi_server.urlPattern.domain.UrlPatternEntity;
import com.positivehotel.nabusi_server.urlPattern.domain.UrlPatternRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final UrlPatternRepository urlPatternRepository;

    @Override
    public Optional<RoleDto> getByName(String name) {
        Optional<RoleEntity> roleEntityOptional = roleRepository.findByName(name);
        return roleEntityOptional.map(roleEntity -> RoleDto.builder()
                .id(roleEntity.getId())
                .name(roleEntity.getName())
                .description(roleEntity.getDescription())
                .centerId(roleEntity.getCenterId())
                .build());
    }

    @Override
    public RoleDto findAllByCenterIdAndName(Long centerId, String name) {
        return roleRepository.findAllByCenterIdAndName(centerId, name)
                .map(RoleDto::from)
                .orElse(null);
    }

    @Override
    public List<RoleDto> createRoleList(List<RoleDto> roleDtoList) {
        final List<RoleEntity> roleEntityList = roleDtoList.stream()
                .map(roleDto -> {
                    final List<UrlPatternEntity> associatedUrlPatterns = roleDto.getUrlPatternDtoList().stream()
                            .map(urlPatternDto -> UrlPatternEntity.create(urlPatternDto.getId(), urlPatternDto.getActionName(), urlPatternDto.getUrl(), urlPatternDto.getMethod(), urlPatternDto.getDescription(), urlPatternDto.getCenterId()))
                            .toList();

                    return RoleEntity.create(roleDto.getName(), roleDto.getDescription(), roleDto.getCenterId(), associatedUrlPatterns);
                })
                .toList();

        final List<RoleEntity> savedRoleEntityList = roleRepository.saveAll(roleEntityList);

        return savedRoleEntityList.stream()
                .map(RoleDto::from)
                .toList();
    }

    @Override
    public void create(RoleDto roleDto) {
        final List<UrlPatternEntity> associatedUrlPatterns = roleDto.getUrlPatternDtoList().stream()
                .map(urlPatternDto -> UrlPatternEntity.create(urlPatternDto.getId(), urlPatternDto.getActionName(), urlPatternDto.getUrl(), urlPatternDto.getMethod(), urlPatternDto.getDescription(), urlPatternDto.getCenterId()))
                .toList();

        RoleEntity newRoleEntity = RoleEntity.create(roleDto.getName(), roleDto.getDescription(), roleDto.getCenterId(), associatedUrlPatterns);
        roleRepository.save(newRoleEntity);
    }

    @Override
    public List<RoleDto> getAllByCenterId(Long centerId) {
        final List<RoleEntity> roleEntityList = roleRepository.findAllByCenterId(centerId);
        return roleEntityList.stream().map(RoleDto::from).toList();
    }

    @Transactional
    @Override
    public void patch(RoleDto roleDto) {
        final RoleEntity roleEntity = roleRepository.findById(roleDto.getId()).orElseThrow(() -> new RuntimeException("Role not found"));

        final List<UrlPatternEntity> urlPatternEntityList = roleDto.getUrlPatternDtoList()
                .stream()
                .map(urlPatternDto -> UrlPatternEntity.create(
                        urlPatternDto.getId(),
                        urlPatternDto.getActionName(),
                        urlPatternDto.getUrl(),
                        urlPatternDto.getMethod(),
                        urlPatternDto.getDescription(),
                        urlPatternDto.getCenterId()))
                .toList();

        roleEntity.patchUrlPatterns(new ArrayList<>(urlPatternEntityList));
    }

    @Transactional
    @Override
    public void updateAll(List<RoleDto> roleDtoList) {
        final List<Long> roleIdList = roleDtoList.stream().map(RoleDto::getId).toList();
        final List<RoleEntity> roleEntityList = roleRepository.findAllById(roleIdList);

        roleEntityList.forEach(roleEntity -> {
            final RoleDto targetRoleDto = roleDtoList.stream()
                    .filter(roleDto -> roleDto.getId().equals(roleEntity.getId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Role not found"));

            final List<UrlPatternEntity> newUrlPatternEntityList = targetRoleDto.getUrlPatternDtoList()
                    .stream()
                    .map(urlPatternDto -> UrlPatternEntity.create(
                            urlPatternDto.getId(),
                            urlPatternDto.getActionName(),
                            urlPatternDto.getUrl(),
                            urlPatternDto.getMethod(),
                            urlPatternDto.getDescription(),
                            urlPatternDto.getCenterId()))
                    .toList();

            roleEntity.patchUrlPatterns(new ArrayList<>(newUrlPatternEntityList));
        });
    }

    @Override
    public RoleDto getById(Long id) {
        return roleRepository.findById(id).map(RoleDto::from).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public RoleDto getByNameAndCenterId(String name, Long centerId) {
        return roleRepository.findByNameAndCenterId(name, centerId)
                .map(RoleDto::from)
                .orElse(null);
    }
}
