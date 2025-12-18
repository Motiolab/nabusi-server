package com.motiolab.nabusi_server.shop.productPackage.shopProductReview.application;

import com.motiolab.nabusi_server.shop.productPackage.shopProductReview.application.dto.ShopProductReviewDto;
import com.motiolab.nabusi_server.shop.productPackage.shopProductReview.domain.ShopProductReviewEntity;
import com.motiolab.nabusi_server.shop.productPackage.shopProductReview.domain.ShopProductReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopProductReviewServiceImpl implements ShopProductReviewService{
    private final ShopProductReviewRepository shopProductReviewRepository;

    @Override
    public void create(ShopProductReviewDto shopProductReviewDto) {
        final ShopProductReviewEntity shopProductReviewEntity = ShopProductReviewEntity.create(
                shopProductReviewDto.getContent(),
                shopProductReviewDto.getIsPrivate(),
                shopProductReviewDto.getRating(),
                shopProductReviewDto.getShopOrderId(),
                shopProductReviewDto.getShopProductId(),
                shopProductReviewDto.getShopProductVariantId(),
                shopProductReviewDto.getImageUrlList(),
                shopProductReviewDto.getMemberId(),
                shopProductReviewDto.getIsDelete()
        );

        shopProductReviewRepository.save(shopProductReviewEntity);
    }

    @Override
    public List<ShopProductReviewDto> getAllByShopProductId(Long shopProductId) {
        final List<ShopProductReviewEntity> shopProductReviewEntityList = shopProductReviewRepository.findAllOrderByBestAndIdx(shopProductId);
        return shopProductReviewEntityList.stream().map(ShopProductReviewDto::from).toList();
    }

    @Override
    public List<ShopProductReviewDto> getAllByShopProductIdList(List<Long> shopProductIdList) {
        final List<ShopProductReviewEntity> shopProductReviewEntityList =  shopProductReviewRepository.findAllByShopProductIdIn(shopProductIdList);
        return shopProductReviewEntityList.stream().map(ShopProductReviewDto::from).toList();
    }
}
