package com.motiolab.nabusi_server.classPackage.wellnessLecture.domain;

import com.motiolab.nabusi_server.util.ListToLongConverter;
import com.motiolab.nabusi_server.util.StringListConverter;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Getter
@Table(name="wellness_lecture")
@Builder(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class WellnessLectureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Long centerId;
    private Integer maxReservationCnt;
    private Long registerId;
    private String room;
    @Convert(converter = StringListConverter.class)
    private List<String> lectureImageUrlList;
    private Double price;
    private Long wellnessClassId;
    private Long teacherId;
    private Long wellnessLectureTypeId;
    private ZonedDateTime startDateTime;
    private ZonedDateTime endDateTime;
    private Boolean isDelete;

    @Convert(converter = ListToLongConverter.class)
    private List<Long> wellnessTicketManagementIdList;

    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    public void updateId(final long id){
        setId(id);
    }

    public static WellnessLectureEntity create(final String name, final String description, final Long centerId, final Integer maxReservationCnt, final Long registerId, final String room, final List<String> lectureImageUrlList, final Double price, final Long wellnessClassId, final Long teacherId, final Long wellnessLectureTypeId, final ZonedDateTime startDateTime, final ZonedDateTime endDateTime, final Boolean isDelete, final List<Long> wellnessTicketManagementIdList) {
        return WellnessLectureEntity.builder()
                .name(name)
                .description(description)
                .centerId(centerId)
                .maxReservationCnt(maxReservationCnt)
                .registerId(registerId)
                .room(room)
                .lectureImageUrlList(lectureImageUrlList)
                .price(price)
                .wellnessClassId(wellnessClassId)
                .teacherId(teacherId)
                .wellnessLectureTypeId(wellnessLectureTypeId)
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .isDelete(isDelete)
                .wellnessTicketManagementIdList(wellnessTicketManagementIdList)
                .build();
    }

    public void update(final String name, final String description, final Long centerId, final Integer maxReservationCnt, final Long registerId, final String room, final List<String> lectureImageUrlList, final Double price, final Long wellnessClassId, final Long teacherId, final Long wellnessLectureTypeId, final ZonedDateTime startDateTime, final ZonedDateTime endDateTime, final Boolean isDelete, final List<Long> wellnessTicketManagementIdList) {
        if(name != null) setName(name);
        if(description != null) setDescription(description);
        if(centerId != null) setCenterId(centerId);
        if(maxReservationCnt != null) setMaxReservationCnt(maxReservationCnt);
        if(registerId != null) setRegisterId(registerId);
        if(room != null) setRoom(room);
        if(lectureImageUrlList != null) setLectureImageUrlList(lectureImageUrlList);
        if(price != null) setPrice(price);
        if(wellnessClassId != null) setWellnessClassId(wellnessClassId);
        if(teacherId != null) setTeacherId(teacherId);
        if(wellnessLectureTypeId != null) setWellnessLectureTypeId(wellnessLectureTypeId);
        if(startDateTime != null) setStartDateTime(startDateTime);
        if(endDateTime != null) setEndDateTime(endDateTime);
        if(isDelete != null) setIsDelete(isDelete);
        if(wellnessTicketManagementIdList != null) setWellnessTicketManagementIdList(wellnessTicketManagementIdList);
    }

    public void updateIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}

