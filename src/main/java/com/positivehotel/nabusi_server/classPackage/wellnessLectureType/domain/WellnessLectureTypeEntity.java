package com.positivehotel.nabusi_server.classPackage.wellnessLectureType.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="wellness_lecture_type", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "center_id"})
})
@Builder(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class WellnessLectureTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Long centerId;

    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    public static WellnessLectureTypeEntity create(final String name, final String description, final Long centerId){
        return WellnessLectureTypeEntity.builder().name(name).description(description).centerId(centerId).build();
    }

    public void update(final String name, final String description, final Long centerId){
        if (name != null) setName(name);
        if (description != null) setDescription(description);
        if (centerId != null) setCenterId(centerId);
    }
}