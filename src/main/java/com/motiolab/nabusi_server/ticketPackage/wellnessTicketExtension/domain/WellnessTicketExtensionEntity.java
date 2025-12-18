package com.motiolab.nabusi_server.ticketPackage.wellnessTicketExtension.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@Getter
@Table(name = "wellness_ticket_extension")
@Builder(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class WellnessTicketExtensionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer extendDate;
    private Boolean isApplyAfter;
    private Long registerId;
    private String reason;
    private ZonedDateTime targetDate;
    private Long wellnessTicketId;

    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    public static WellnessTicketExtensionEntity create(Integer extendDate, Boolean isApplyAfter, Long registerId, String reason, ZonedDateTime targetDate, Long wellnessTicketId) {
        return WellnessTicketExtensionEntity.builder()
                .extendDate(extendDate)
                .isApplyAfter(isApplyAfter)
                .registerId(registerId)
                .reason(reason)
                .targetDate(targetDate)
                .wellnessTicketId(wellnessTicketId)
                .build();
    }
}
