package com.motiolab.nabusi_server.classPackage.wellnessClass.domain;

import com.motiolab.nabusi_server.util.ListToLongConverter;
import com.motiolab.nabusi_server.util.StringListConverter;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table(name="wellness_class", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "center_id"})
})
@Builder(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class WellnessClassEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String description;
    private Long centerId;
    private Integer maxReservationCnt;
    private Long registerId;
    private String room;
    @Convert(converter = StringListConverter.class)
    private List<String> classImageUrlList;
    private Long teacherId;
    private Long wellnessLectureTypeId;
    private Boolean isDelete;

    @Convert(converter = ListToLongConverter.class)
    private List<Long> wellnessTicketManagementIdList;

    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    public void updateId(final long id) {
        setId(id);
    }

    public static WellnessClassEntity create(final String name, final String description, final Long centerId, final Integer maxReservationCnt, final Long registerId, final String room, final List<String> classImageUrlList, final Long teacherId, final Long wellnessLectureTypeId, final Boolean isDelete, final List<Long> wellnessTicketManagementIdList) {
        return WellnessClassEntity.builder()
                .name(name)
                .description(description)
                .centerId(centerId)
                .maxReservationCnt(maxReservationCnt)
                .registerId(registerId)
                .room(room)
                .classImageUrlList(classImageUrlList)
                .teacherId(teacherId)
                .wellnessLectureTypeId(wellnessLectureTypeId)
                .isDelete(isDelete)
                .wellnessTicketManagementIdList(wellnessTicketManagementIdList)
                .build();
    }

    public void update(final String description, final Long centerId, final Integer maxReservationCnt, final Long registerId, final String room, final List<String> classImageUrlList, final Long teacherId, final Long wellnessLectureTypeId, final Boolean isDelete, final List<Long> wellnessTicketManagementIdList) {
        if(description != null) setDescription(description);
        if(centerId != null) setCenterId(centerId);
        if(maxReservationCnt != null) setMaxReservationCnt(maxReservationCnt);
        if(registerId != null) setRegisterId(registerId);
        if(room != null) setRoom(room);
        if(classImageUrlList != null) setClassImageUrlList(classImageUrlList);
        if(teacherId != null) setTeacherId(teacherId);
        if(wellnessLectureTypeId != null) setWellnessLectureTypeId(wellnessLectureTypeId);
        if(wellnessTicketManagementIdList != null) setWellnessTicketManagementIdList(wellnessTicketManagementIdList);
        if(isDelete != null) setIsDelete(isDelete);
    }
}
