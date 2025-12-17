package com.positivehotel.nabusi_server.centerPackage.centerNotice.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "center_notice")
@Builder(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class CenterNoticeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long centerId;
    private Long registerId;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;
    private Boolean isPopup;
    private Boolean isDelete;

    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    public static CenterNoticeEntity create(Long centerId, Long registerId, String title, String content, Boolean isPopup, Boolean isDelete) {
        return CenterNoticeEntity.builder()
                .centerId(centerId)
                .registerId(registerId)
                .title(title)
                .content(content)
                .isPopup(isPopup)
                .isDelete(isDelete)
                .build();
    }

    public void put(Long centerId, Long registerId, String title, String content, Boolean isPopup, Boolean isDelete) {
        if(centerId != null) setCenterId(centerId);
        if(registerId != null) setRegisterId(registerId);
        if(title != null) setTitle(title);
        if(content != null) setContent(content);
        if(isPopup != null) setIsPopup(isPopup);
        if(isDelete != null) setIsDelete(isDelete);
    }
}
