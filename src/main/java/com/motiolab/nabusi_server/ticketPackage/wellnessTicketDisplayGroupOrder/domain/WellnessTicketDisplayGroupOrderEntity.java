package com.motiolab.nabusi_server.ticketPackage.wellnessTicketDisplayGroupOrder.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "wellness_ticket_display_group_order", uniqueConstraints = {@UniqueConstraint(name = "UK_wellness_ticket_display_group_id_order_index", columnNames = {"wellnessTicketDisplayGroupId", "orderIndex"})})
@Builder(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class WellnessTicketDisplayGroupOrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long wellnessTicketDisplayGroupId;
    private Long wellnessTicketId;
    private Integer orderIndex;

    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    public static WellnessTicketDisplayGroupOrderEntity create(Long wellnessTicketDisplayGroupId, Long wellnessTicketId, Integer orderIndex) {
        return WellnessTicketDisplayGroupOrderEntity.builder()
                .wellnessTicketDisplayGroupId(wellnessTicketDisplayGroupId)
                .wellnessTicketId(wellnessTicketId)
                .orderIndex(orderIndex)
                .build();
    }
}
