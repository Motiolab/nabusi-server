package com.positivehotel.nabusi_server.shop.productPackage.shopProduct.domain;

import com.positivehotel.nabusi_server.util.StringListConverter;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table(name = "shop_product")
@Builder(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class ShopProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private String modelName;
    private Integer price;
    private Integer discountPrice;
    private String display;
    @Column(length = 2000)
    private String mobileDescription;
    @Column(length = 2000)
    private String paymentInfo;
    @Column(length = 2000)
    private String shippingInfo;
    @Column(length = 2000)
    private String exchangeInfo;
    @Column(length = 2000)
    private String serviceInfo;
    private String selling;
    private String simpleDescription;
    private String summaryDescription;
    private String listImage;
    @Convert(converter = StringListConverter.class)
    private List<String> detailImage;
    private String hasOption;
    private String soldOut;
    private Integer idx;
    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    public void changeSoldOut(String soldOut) {
        this.soldOut = soldOut;
    }
}