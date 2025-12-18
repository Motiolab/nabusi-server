package com.motiolab.nabusi_server.shop.cartPackage.shopCart.application;

import jakarta.transaction.Transactional;
import com.motiolab.nabusi_server.shop.cartPackage.shopCart.application.dto.ShopCartDto;
import com.motiolab.nabusi_server.shop.cartPackage.shopCart.domain.CartStatus;
import com.motiolab.nabusi_server.shop.cartPackage.shopCart.domain.ShopCartEntity;
import com.motiolab.nabusi_server.shop.cartPackage.shopCart.domain.ShopCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopCartServiceImpl implements ShopCartService {
    private final ShopCartRepository shopCartRepository;

    @Override
    public ShopCartDto getByMemberIdAndStatus(Long memberId, CartStatus cartStatus) {
        return shopCartRepository.findByMemberIdAndStatus(memberId, cartStatus)
                .map(ShopCartDto::from)
                .orElse(null);
    }

    @Override
    public ShopCartDto create(ShopCartDto shopCartDto) {
        final ShopCartEntity shopCartEntity = ShopCartEntity.create(shopCartDto.getMemberId(), shopCartDto.getStatus());
        final ShopCartEntity storedShopCartEntity = shopCartRepository.save(shopCartEntity);
        return ShopCartDto.from(storedShopCartEntity);
    }

    @Override
    @Transactional
    public void updateStatus(Long id, CartStatus status) {
        ShopCartEntity shopCartEntity = shopCartRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));
        shopCartEntity.changeStatus(status);
    }
}
