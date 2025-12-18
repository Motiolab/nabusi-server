package com.motiolab.nabusi_server.centerPackage.center.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "center_contact_number")
@Builder(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class CenterContactNumberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long centerId;
    private String contactType;
    private String number;

    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;


    public static CenterContactNumberEntity create(final Long centerId, final String contactType, final String number) {
        return CenterContactNumberEntity.builder()
                .centerId(centerId)
                .contactType(contactType)
                .number(number)
                .build();
    }
}
