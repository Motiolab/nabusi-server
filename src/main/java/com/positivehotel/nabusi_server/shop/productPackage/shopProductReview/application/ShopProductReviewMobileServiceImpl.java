package com.positivehotel.nabusi_server.shop.productPackage.shopProductReview.application;

import com.positivehotel.nabusi_server.shop.productPackage.shopProductReview.application.dto.ShopProductReviewDto;
import com.positivehotel.nabusi_server.shop.productPackage.shopProductReview.application.dto.ShopProductReviewMobileDto;
import com.positivehotel.nabusi_server.shop.productPackage.shopProductReview.application.dto.request.CreateShopProductReviewRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopProductReviewMobileServiceImpl implements ShopProductReviewMobileService{
    private final ShopProductReviewService shopProductReviewService;

    @Override
    public void createShopProductReview(CreateShopProductReviewRequest createShopProductReviewRequest) {
        ShopProductReviewDto shopProductReviewDto = ShopProductReviewDto.builder()
                .content(createShopProductReviewRequest.getContent())
                .isPrivate(createShopProductReviewRequest.getIsPrivate())
                .rating(createShopProductReviewRequest.getRating())
                .shopProductId(createShopProductReviewRequest.getShopProductId())
                .shopProductVariantId(createShopProductReviewRequest.getShopProductVariantId())
                .imageUrlList(createShopProductReviewRequest.getImageUrlList())
                .memberId(createShopProductReviewRequest.getMemberId())
                .isDelete(false)
                .build();

        shopProductReviewService.create(shopProductReviewDto);
    }

    @Override
    public List<ShopProductReviewMobileDto> getAllByShopProductId(Long shopProductId) {
        final List<ShopProductReviewDto> shopProductReviewDtoList = shopProductReviewService.getAllByShopProductId(shopProductId);
        return shopProductReviewDtoList
                .stream()
                .map(shopProductReviewDto -> ShopProductReviewMobileDto.builder()
                        .shopProductReviewDto(shopProductReviewDto)
                        .build())
                .toList();
    }
}
