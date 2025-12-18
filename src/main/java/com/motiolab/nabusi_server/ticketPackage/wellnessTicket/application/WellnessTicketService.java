package com.motiolab.nabusi_server.ticketPackage.wellnessTicket.application;

import com.motiolab.nabusi_server.exception.customException.DeletedAlreadyException;
import com.motiolab.nabusi_server.exception.customException.NotFoundException;
import com.motiolab.nabusi_server.exception.customException.RestoredAlreadyException;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicket.application.dtos.WellnessTicketDto;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicket.domain.WellnessTicketEntity;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicket.domain.WellnessTicketRepository;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WellnessTicketService {
    private final WellnessTicketRepository wellnessTicketRepository;

    public List<WellnessTicketDto> getAllByCenterId(@NonNull final Long centerId) {
        return wellnessTicketRepository.findAllByCenterId(centerId)
                .stream()
                .map(WellnessTicketDto::from)
                .toList();
    }

    public WellnessTicketDto create(WellnessTicketDto wellnessTicketDto) {
        final WellnessTicketEntity wellnessTicketEntity = WellnessTicketEntity.create(
                wellnessTicketDto.getCenterId(),
                wellnessTicketDto.getType(),
                wellnessTicketDto.getName(),
                wellnessTicketDto.getBackgroundColor(),
                wellnessTicketDto.getTextColor(),
                wellnessTicketDto.getPrice(),
                wellnessTicketDto.getLimitType(),
                wellnessTicketDto.getLimitCnt(),
                wellnessTicketDto.getTotalUsableCnt(),
                wellnessTicketDto.getUsableDate(),
                wellnessTicketDto.getDiscountValue(),
                wellnessTicketDto.getWellnessClassIdList(),
                wellnessTicketDto.getSalesPrice(),
                wellnessTicketDto.getIsDelete()
        );
        final WellnessTicketEntity storedWellnessTicketEntity = wellnessTicketRepository.save(wellnessTicketEntity);
        return WellnessTicketDto.from(storedWellnessTicketEntity);
    }

    public WellnessTicketDto getById(@NotNull final Long id) {
        return wellnessTicketRepository.findById(id)
                .map(WellnessTicketDto::from)
                .orElse(null);
    }

    public void delete(@NonNull final Long id) {
        wellnessTicketRepository.findById(id)
                .map(wellnessTicketEntity -> {
                    if(wellnessTicketEntity.getIsDelete()) {
                        throw new DeletedAlreadyException(WellnessTicketEntity.class, id);
                    }
                    wellnessTicketEntity.updateIsDelete(true);
                    return wellnessTicketRepository.save(wellnessTicketEntity);
                })
                .orElseThrow(() -> new NotFoundException(WellnessTicketEntity.class, id));
    }

    public void restore(@NonNull final Long id) {
        wellnessTicketRepository.findById(id)
                .map(wellnessTicketEntity -> {
                    if(!wellnessTicketEntity.getIsDelete()) {
                        throw new RestoredAlreadyException(WellnessTicketEntity.class, id);
                    }
                    wellnessTicketEntity.updateIsDelete(false);
                    return wellnessTicketRepository.save(wellnessTicketEntity);
                })
                .orElseThrow(() -> new NotFoundException(WellnessTicketEntity.class, id));
    }

    public WellnessTicketDto getByNameAndCenterId(@NonNull final String name, @NonNull final Long centerId) {
        return wellnessTicketRepository.findByNameAndCenterId(name, centerId)
                .map(WellnessTicketDto::from)
                .orElse(null);
    }

    public void update(WellnessTicketDto wellnessTicketDto) {
        wellnessTicketRepository.findById(wellnessTicketDto.getId())
                .map(wellnessTicketEntity -> {
                    wellnessTicketEntity.update(
                            wellnessTicketDto.getCenterId(),
                            wellnessTicketDto.getType(),
                            wellnessTicketDto.getName(),
                            wellnessTicketDto.getBackgroundColor(),
                            wellnessTicketDto.getTextColor(),
                            wellnessTicketDto.getPrice(),
                            wellnessTicketDto.getLimitType(),
                            wellnessTicketDto.getLimitCnt(),
                            wellnessTicketDto.getTotalUsableCnt(),
                            wellnessTicketDto.getUsableDate(),
                            wellnessTicketDto.getDiscountValue(),
                            wellnessTicketDto.getWellnessClassIdList(),
                            wellnessTicketDto.getSalesPrice()
                    );
                    return wellnessTicketRepository.save(wellnessTicketEntity);
                })
                .orElseThrow(() -> new NotFoundException(WellnessTicketEntity.class, wellnessTicketDto.getId()));
    }

    public List<WellnessTicketDto> getAllById(@NonNull final List<Long> idList) {
        return wellnessTicketRepository.findAllById(idList)
                .stream()
                .map(WellnessTicketDto::from)
                .toList();
    }
}
