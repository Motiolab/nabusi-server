package com.motiolab.nabusi_server.ticketPackage.wellnessTicketDisplayGroup.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "wellness_ticket_display_group")
@Builder(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class WellnessTicketDisplayGroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private Long centerId;
    @Column(unique = true)
    private Integer orderIndex;
    private Boolean isDisplay;
    private Boolean isDelete;

    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    public static WellnessTicketDisplayGroupEntity create(String name, Long centerId, Integer orderIndex, Boolean isDisplay, Boolean isDelete) {
        return WellnessTicketDisplayGroupEntity.builder()
                .name(name)
                .centerId(centerId)
                .orderIndex(orderIndex)
                .isDisplay(isDisplay)
                .isDelete(isDelete)
                .build();
    }
}
