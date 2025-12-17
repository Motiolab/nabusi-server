package com.positivehotel.nabusi_server.ticketPackage.wellnessTicketManagement.domain;

import com.positivehotel.nabusi_server.util.ListToLongConverter;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table(name = "wellness_ticket_management")
@Builder(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class WellnessTicketManagementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long centerId;
    private Long wellnessTicketId;
    private String wellnessTicketIssuanceName;
    @Convert(converter = ListToLongConverter.class)
    private List<Long> wellnessTicketIssuanceIdList;
    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    public static WellnessTicketManagementEntity create(Long centerId, Long wellnessTicketId, String wellnessTicketIssuanceName, List<Long> wellnessTicketIssuanceIdList) {
        return WellnessTicketManagementEntity.builder()
                .centerId(centerId)
                .wellnessTicketId(wellnessTicketId)
                .wellnessTicketIssuanceName(wellnessTicketIssuanceName)
                .wellnessTicketIssuanceIdList(wellnessTicketIssuanceIdList)
                .build();
    }

    public void update(Long wellnessTicketId, String wellnessTicketIssuanceName, List<Long> wellnessTicketIssuanceIdList) {
        this.wellnessTicketId = wellnessTicketId;
        this.wellnessTicketIssuanceName = wellnessTicketIssuanceName;
        this.wellnessTicketIssuanceIdList = wellnessTicketIssuanceIdList;
    }
}
