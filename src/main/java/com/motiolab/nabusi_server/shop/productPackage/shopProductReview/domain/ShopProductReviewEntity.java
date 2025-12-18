package com.motiolab.nabusi_server.shop.productPackage.shopProductReview.domain;

import com.motiolab.nabusi_server.util.StringListConverter;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table(name="shop_product_review")
@Builder(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class ShopProductReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String content;
    private Boolean isPrivate;
    private Integer rating;
    private Long shopOrderId;
    private Long shopProductId;
    private Long shopProductVariantId;
    @Convert(converter = StringListConverter.class)
    private List<String> imageUrlList;
    private Long memberId;
    private Boolean isDelete;
    private Boolean isBest;
    private Integer idx;

    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    public static ShopProductReviewEntity create(String content, Boolean isPrivate, Integer rating, Long shopOrderId, Long shopProductId, Long shopProductVariantId, List<String> imageUrlList, Long memberId, Boolean isDelete) {
        return ShopProductReviewEntity.builder()
                .content(content)
                .isPrivate(isPrivate)
                .rating(rating)
                .shopOrderId(shopOrderId)
                .shopProductId(shopProductId)
                .shopProductVariantId(shopProductVariantId)
                .imageUrlList(imageUrlList)
                .memberId(memberId)
                .isDelete(isDelete)
                .build();
    }
}
