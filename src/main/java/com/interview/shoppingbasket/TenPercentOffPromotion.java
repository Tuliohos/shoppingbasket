package com.interview.shoppingbasket;

import lombok.Builder;

import java.util.List;

public class TenPercentOffPromotion extends Promotion{

    @Builder
    public TenPercentOffPromotion(List<String> productCodes) {
        super(productCodes);
    }

    @Override
    public double calculateDiscount(BasketItem basketItem) {
        return basketItem.getProductRetailPrice() * 0.1 * basketItem.getQuantity();
    }

}
