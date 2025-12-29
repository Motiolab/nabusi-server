package com.motiolab.nabusi_server.reservation.application;

import com.motiolab.nabusi_server.exception.customException.NotFoundException;
import com.motiolab.nabusi_server.reservation.application.dto.ReservationDto;
import com.motiolab.nabusi_server.reservation.domain.ReservationEntity;
import com.motiolab.nabusi_server.reservation.domain.ReservationRepository;
import com.motiolab.nabusi_server.reservation.enums.ReservationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;

    @Override
    public ReservationDto create(ReservationDto reservationDto) {
        final ReservationEntity reservationEntity = ReservationEntity.create(
                reservationDto.getCenterId(),
                reservationDto.getMemberId(),
                reservationDto.getPaymentId(),
                reservationDto.getActionMemberId(),
                reservationDto.getStatus(),
                reservationDto.getWellnessLectureId(),
                reservationDto.getWellnessTicketIssuanceId());
        final ReservationEntity savedReservationEntity = reservationRepository.save(reservationEntity);
        return ReservationDto.from(savedReservationEntity);
    }

    @Override
    public List<ReservationDto> getAllByWellnessLectureId(Long wellnessLectureId) {
        final List<ReservationEntity> reservationEntityList = reservationRepository
                .findAllByWellnessLectureId(wellnessLectureId);
        return reservationEntityList.stream().map(ReservationDto::from).toList();
    }

    @Override
    public List<ReservationDto> getAllByWellnessTicketIssuanceId(Long wellnessTicketIssuanceId) {
        final List<ReservationEntity> reservationEntityList = reservationRepository
                .findAllByWellnessTicketIssuanceId(wellnessTicketIssuanceId);
        return reservationEntityList.stream().map(ReservationDto::from).toList();
    }

    @Override
    public List<ReservationDto> getAllByWellnessLectureIdList(List<Long> wellnessLectureIdList) {
        final List<ReservationEntity> reservationEntityList = reservationRepository
                .findAllByWellnessLectureIdIn(wellnessLectureIdList);
        return reservationEntityList.stream().map(ReservationDto::from).toList();
    }

    @Override
    public ReservationDto getById(Long id) {
        return reservationRepository.findById(id)
                .map(ReservationDto::from)
                .orElse(null);
    }

    @Override
    public ReservationDto update(ReservationDto reservationDto) {
        final ReservationEntity reservationEntity = reservationRepository.findById(reservationDto.getId())
                .orElseThrow(() -> new NotFoundException(ReservationEntity.class, reservationDto.getId()));

        reservationEntity.updateStatus(reservationDto.getStatus());
        final ReservationEntity savedReservationEntity = reservationRepository.save(reservationEntity);

        return ReservationDto.from(savedReservationEntity);
    }

    @Override
    public List<ReservationDto> getAllByMemberIdAndStatusList(Long memberId,
            List<ReservationStatus> reservationStatusList) {
        final List<ReservationEntity> reservationEntityList = reservationRepository
                .findAllByMemberIdAndStatusIn(memberId, reservationStatusList);
        return reservationEntityList.stream().map(ReservationDto::from).toList();
    }

    @Override
    public ReservationDto getByMemberIdAndWellnessLectureId(Long memberId, Long wellnessLectureId) {
        return reservationRepository.findByMemberIdAndWellnessLectureId(memberId, wellnessLectureId)
                .map(ReservationDto::from)
                .orElse(null);
    }

    @Override
    public List<ReservationDto> getAllByMemberIdListAndStatusList(List<Long> memberIdList,
            List<ReservationStatus> reservationStatusList) {
        final List<ReservationEntity> reservationEntityList = reservationRepository
                .findAllByMemberIdInAndStatusIn(memberIdList, reservationStatusList);
        return reservationEntityList.stream().map(ReservationDto::from).toList();
    }

    @Override
    public long countByMemberIdAndStatus(Long memberId, ReservationStatus status) {
        return reservationRepository.countByMemberIdAndStatus(memberId, status);
    }

    @Override
    public long countByMemberIdAndStatusIn(Long memberId, List<ReservationStatus> statusList) {
        return reservationRepository.countByMemberIdAndStatusIn(memberId, statusList);
    }
}
