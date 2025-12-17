package com.positivehotel.nabusi_server.ticketPackage.wellnessTicketIssuance.domain;

import com.positivehotel.nabusi_server.ticketPackage.enums.PaymentMethodEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@Getter
@Table(name = "wellness_ticket_issuance")
@Builder(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class WellnessTicketIssuanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long centerId;
    private Long paymentId;
    private String name;
    private ZonedDateTime startDate;
    private ZonedDateTime expireDate;
    private String type;
    @Column(length = 10)
    private String backgroundColor;
    @Column(length = 10)
    private String textColor;
    private String limitType;
    private Integer limitCnt;
    @Builder.Default
    private Boolean isDelete = false;
    private Integer remainingCnt;
    private Integer totalUsableCnt;
    private Long memberId;
    private Long wellnessTicketId;
    @Builder.Default
    private Boolean isSendExpireNotification = false;
    @Builder.Default
    private Boolean isSendRemainingCntNotification = false;
    @Enumerated(EnumType.STRING)
    private PaymentMethodEnum paymentMethod;
    private Integer unpaidValue;

    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    public static WellnessTicketIssuanceEntity create(Long centerId, Long paymentId, String name, ZonedDateTime startDate, ZonedDateTime expireDate, String type, String backgroundColor, String textColor, String limitType, Integer limitCnt, Boolean isDelete, Integer remainingCnt, Integer totalUsableCnt, Long memberId, Long wellnessTicketId, Boolean isSendExpireNotification, Boolean isSendRemainingCntNotification, PaymentMethodEnum paymentMethod, Integer unpaidValue) {
        return WellnessTicketIssuanceEntity.builder()
                .centerId(centerId)
                .paymentId(paymentId)
                .name(name)
                .startDate(startDate)
                .expireDate(expireDate)
                .type(type)
                .backgroundColor(backgroundColor)
                .textColor(textColor)
                .limitType(limitType)
                .limitCnt(limitCnt)
                .isDelete(isDelete)
                .remainingCnt(remainingCnt)
                .totalUsableCnt(totalUsableCnt)
                .memberId(memberId)
                .wellnessTicketId(wellnessTicketId)
                .isSendExpireNotification(isSendExpireNotification)
                .isSendRemainingCntNotification(isSendRemainingCntNotification)
                .paymentMethod(paymentMethod)
                .unpaidValue(unpaidValue)
                .build();
    }

    public void update(Long centerId, Long paymentId, String name, ZonedDateTime startDate, ZonedDateTime expireDate, String type, String backgroundColor, String textColor, String limitType, Integer limitCnt, Boolean isDelete, Integer remainingCnt, Integer totalUsableCnt, Long memberId, Long wellnessTicketId, Boolean isSendExpireNotification, Boolean isSendRemainingCntNotification, PaymentMethodEnum paymentMethod, Integer unpaidValue) {
        this.centerId = centerId;
        this.paymentId = paymentId;
        this.name = name;
        this.startDate = startDate;
        this.expireDate = expireDate;
        this.type = type;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.limitType = limitType;
        this.limitCnt = limitCnt;
        this.isDelete = isDelete;
        this.remainingCnt = remainingCnt;
        this.totalUsableCnt = totalUsableCnt;
        this.memberId = memberId;
        this.wellnessTicketId = wellnessTicketId;
        this.isSendExpireNotification = isSendExpireNotification;
        this.isSendRemainingCntNotification = isSendRemainingCntNotification;
        this.paymentMethod = paymentMethod;
        this.unpaidValue = unpaidValue;
    }
}
