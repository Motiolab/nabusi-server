package com.motiolab.nabusi_server.notificationPackage.notificationFcmHistory.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "fcm_notification_history")
@Builder(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class FcmNotificationHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long memberId;
    private String title;
    private String body;
    private String detail;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    public static FcmNotificationHistoryEntity create(Long memberId, String title, String body, String detail) {
        return FcmNotificationHistoryEntity.builder()
                .memberId(memberId)
                .title(title)
                .body(body)
                .detail(detail)
                .build();
    }
}
