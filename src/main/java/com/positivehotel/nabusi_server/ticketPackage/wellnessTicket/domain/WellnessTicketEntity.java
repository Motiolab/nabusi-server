package com.positivehotel.nabusi_server.ticketPackage.wellnessTicket.domain;

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
@Table(name = "wellness_ticket", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "center_id"})
})
@Builder(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class WellnessTicketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long centerId;
    private String type;
    private String name;
    @Column(length = 10)
    private String backgroundColor;
    @Column(length = 10)
    private String textColor;
    private Double price;
    private String limitType;
    private Integer limitCnt;
    private Integer totalUsableCnt;
    private Integer usableDate;
    private Integer discountValue;
    @Column(length = 2000)
    @Convert(converter = ListToLongConverter.class)
    private List<Long> wellnessClassIdList;
    private Integer salesPrice;
    private Boolean isDelete;

    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    public static WellnessTicketEntity create(Long centerId, String type, String name, String backgroundColor, String textColor, Double price, String limitType, Integer limitCnt, Integer totalUsableCnt, Integer usableDate, Integer discountValue, List<Long> wellnessClassIdList, Integer salesPrice, Boolean isDelete) {
        return WellnessTicketEntity.builder()
                .centerId(centerId)
                .type(type)
                .name(name)
                .backgroundColor(backgroundColor)
                .textColor(textColor)
                .price(price)
                .limitType(limitType)
                .limitCnt(limitCnt)
                .totalUsableCnt(totalUsableCnt)
                .usableDate(usableDate)
                .discountValue(discountValue)
                .wellnessClassIdList(wellnessClassIdList)
                .salesPrice(salesPrice)
                .isDelete(isDelete)
                .build();
    }

    public void updateIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public void update(Long centerId, String type, String name, String backgroundColor, String textColor, Double price, String limitType, Integer limitCnt, Integer totalUsableCnt, Integer usableDate, Integer discountValue, List<Long> wellnessClassIdList, Integer salesPrice) {
        this.centerId = centerId;
        this.type = type;
        this.name = name;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.price = price;
        this.limitType = limitType;
        this.limitCnt = limitCnt;
        this.totalUsableCnt = totalUsableCnt;
        this.usableDate = usableDate;
        this.discountValue = discountValue;
        this.wellnessClassIdList = wellnessClassIdList;
        this.salesPrice = salesPrice;
    }
}
