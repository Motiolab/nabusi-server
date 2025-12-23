package com.motiolab.nabusi_server.shop.productPackage.shopProduct.application;

import com.motiolab.nabusi_server.shop.productPackage.shopProduct.application.dto.ShopProductDto;
import com.motiolab.nabusi_server.shop.productPackage.shopProduct.domain.ShopProductEntity;
import com.motiolab.nabusi_server.shop.productPackage.shopProduct.domain.ShopProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ShopProductServiceImpl implements ShopProductService {
    private final ShopProductRepository shopProductRepository;

    @Override
    public List<ShopProductDto> getAllByDisplay(String display) {
        final List<ShopProductEntity> shopProductEntityList = shopProductRepository.findAllByDisplay(display);
        return shopProductEntityList.stream().map(ShopProductDto::from).toList();
    }

    @Override
    public ShopProductDto getById(Long id) {
        return shopProductRepository.findById(id)
                .map(ShopProductDto::from)
                .orElse(null);
    }

    @Override
    public List<ShopProductDto> getAllByIdList(List<Long> idList) {
        final List<ShopProductEntity> shopProductEntityList = shopProductRepository.findAllByIdIn(idList);
        return shopProductEntityList.stream().map(ShopProductDto::from).toList();
    }

    @Override
    @Transactional
    public void updateSoldOut(Long id, String soldOut) {
        ShopProductEntity shopProductEntity = shopProductRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        shopProductEntity.changeSoldOut(soldOut);
    }
}
