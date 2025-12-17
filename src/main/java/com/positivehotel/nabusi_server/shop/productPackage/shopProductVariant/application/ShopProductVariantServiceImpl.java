package com.positivehotel.nabusi_server.shop.productPackage.shopProductVariant.application;

import com.positivehotel.nabusi_server.shop.productPackage.shopProductVariant.application.dto.ShopProductVariantDto;
import com.positivehotel.nabusi_server.shop.productPackage.shopProductVariant.domain.ShopProductVariantEntity;
import com.positivehotel.nabusi_server.shop.productPackage.shopProductVariant.domain.ShopProductVariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopProductVariantServiceImpl implements ShopProductVariantService {
    private final ShopProductVariantRepository shopProductVariantRepository;

    @Override
    public List<ShopProductVariantDto> getAllByShopProductId(Long shopProductId) {
        final List<ShopProductVariantEntity> shopProductVariantEntityList = shopProductVariantRepository.findAllByShopProductId(shopProductId);
        return shopProductVariantEntityList.stream().map(ShopProductVariantDto::from).toList();
    }

    @Override
    public List<ShopProductVariantDto> getAllByIdList(List<Long> idList) {
        List<ShopProductVariantEntity> shopProductVariantEntityList = shopProductVariantRepository.findAllByIdIn(idList);
        return shopProductVariantEntityList.stream().map(ShopProductVariantDto::from).toList();
    }

    @Override
    public ShopProductVariantDto getById(Long id) {
        return shopProductVariantRepository.findById(id)
                .map(ShopProductVariantDto::from)
                .orElse(null);
    }

    @Override
    public ShopProductVariantDto update(ShopProductVariantDto shopProductVariantDto) {
        ShopProductVariantEntity shopProductVariantEntity = shopProductVariantRepository.findById(shopProductVariantDto.getId()).orElseThrow();
        shopProductVariantEntity.update(
                shopProductVariantDto.getShopProductId(),
                shopProductVariantDto.getOptionName(),
                shopProductVariantDto.getAdditionalPrice(),
                shopProductVariantDto.getDisplay(),
                shopProductVariantDto.getSelling(),
                shopProductVariantDto.getDisplaySoldOut(),
                shopProductVariantDto.getQuantity()
        );
        final ShopProductVariantEntity storedShopProductVariantEntity = shopProductVariantRepository.save(shopProductVariantEntity);
        return ShopProductVariantDto.from(storedShopProductVariantEntity);
    }
}
