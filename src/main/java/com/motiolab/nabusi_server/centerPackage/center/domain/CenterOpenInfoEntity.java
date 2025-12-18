package com.motiolab.nabusi_server.centerPackage.center.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@Getter
@Table(name = "center_open_info")
@Builder(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class CenterOpenInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long centerId;
    private ZonedDateTime closeTime;
    private Integer day;
    private Boolean isDayOff;
    private ZonedDateTime openTime;

    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;

    public static CenterOpenInfoEntity create(final Long centerId, final ZonedDateTime closeTime, final Integer day, final Boolean isDayOff, final ZonedDateTime openTime) {
        return CenterOpenInfoEntity.builder()
                .centerId(centerId)
                .closeTime(closeTime)
                .day(day)
                .isDayOff(isDayOff)
                .openTime(openTime)
                .build();
    }
}
